import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class SaveLoad {
    private static final String SAVE_FILE_PATH = "C:\\Users\\User\\IdeaProjects\\JOJO\\progress.json";

    public static void saveGame(int mapChoice,int dayCount,List<PearlJam.Resident> resident) {
        Progress gameProgress = new Progress(mapChoice,dayCount,resident);
        String json = convertToJson(gameProgress);
        if (json != null) {
            try (FileWriter writer = new FileWriter(SAVE_FILE_PATH)) {
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loadGame() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(SAVE_FILE_PATH)));
            Gson gson = new Gson();
            Progress gameProgress = gson.fromJson(json, Progress.class);
            int mapChoice=gameProgress.getMapChoice();
            int dayCount = gameProgress.getDayCount();
            List<PearlJam.Resident> residents = gameProgress.getResidents();
            jojoLand jojoLand = null;
            switch (mapChoice) {
                case 1:
                    jojoLand = new jojoLand(new DefaultMap().getMap());
                    break;
                case 2:
                    jojoLand = new jojoLand(new ParallelMap().getMap());
                    break;
                case 3:
                    jojoLand = new jojoLand(new AlternateMap().getMap());
                    break;
                default:
                    System.out.println("Invalid map choice. Exiting...");
                    System.exit(0);
            }
            jojoLand.setDayCount(dayCount);
            // Set the residents in your game
           PearlJam.Resident.setAllResident(residents);
           jojoLand.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertToJson(Progress gameProgress) {
        Gson gson = new Gson();
        return gson.toJson(gameProgress);
    }

    private static Progress convertFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Progress.class);
    }

    static class Progress {
        private int mapChoice;
        private int dayCount;
        private List<PearlJam.Resident> residents;

        public Progress(int mapChoice,int dayCount, List<PearlJam.Resident> residents) {
            this.mapChoice=mapChoice;
            this.dayCount = dayCount;
            this.residents = residents;
        }

        public int getMapChoice() {
            return mapChoice;
        }

        public int getDayCount() {
            return dayCount;
        }

        public void setDayCount(int dayCount) {
            this.dayCount = dayCount;
        }

        public List<PearlJam.Resident> getResidents() {
            return residents;
        }
    }
}

