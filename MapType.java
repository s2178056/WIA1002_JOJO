public interface MapType {
    String getName();
    Graph getMap();
}
class DefaultMap implements MapType{
    private Graph map= new Graph();

    public DefaultMap(){
        map.addTown("Town Hall");
        map.addTown("Cafe Deux Magots");
        map.addTown("Jade Garden");
        map.addTown("Morioh Grand Hotel");
        map.addTown("Trattoria Trussardi");
        map.addTown("Green Dolphin Street Prison");
        map.addTown("Polnareff Land");
        map.addTown("San Giorgio Maggiore");
        map.addTown("Libeccio");
        map.addTown("Savage Garden");
        map.addTown("Joestar Mansion");
        map.addTown("DIO's Mansion");
        map.addTown("Angelo Rock");
        map.addTown("Vineyard");
        map.addRoad("Town Hall", "Cafe Deux Magots", 4);
        map.addRoad("Town Hall", "Jade Garden", 5);
        map.addRoad("Town Hall", "Morioh Grand Hotel", 5);
        map.addRoad("Morioh Grand Hotel", "Trattoria Trussardi", 6);
        map.addRoad("Morioh Grand Hotel", "Jade Garden", 3);
        map.addRoad("Trattoria Trussardi", "San Giorgio Maggiore", 3);
        map.addRoad("Trattoria Trussardi", "Green Dolphin Street Prison", 6);
        map.addRoad("Cafe Deux Magots", "Polnareff Land", 4);
        map.addRoad("Cafe Deux Magots", "Jade Garden", 3);
        map.addRoad("Cafe Deux Magots", "Savage Garden", 4);
        map.addRoad("Jade Garden", "San Giorgio Maggiore", 4);
        map.addRoad("Jade Garden", "Joestar Mansion", 2);
        map.addRoad("San Giorgio Maggiore", "Libeccio", 4);
        map.addRoad("Libeccio", "Green Dolphin Street Prison", 3);
        map.addRoad("Libeccio", "Joestar Mansion", 6);
        map.addRoad("Libeccio", "Vineyard", 6);
        map.addRoad("Libeccio", "DIO's Mansion", 2);
        map.addRoad("Green Dolphin Street Prison", "Angelo Rock", 2);
        map.addRoad("Polnareff Land", "Savage Garden", 6);
        map.addRoad("Savage Garden", "Joestar Mansion", 4);
        map.addRoad("Savage Garden", "Vineyard", 8);
        map.addRoad("Vineyard", "DIO's Mansion", 3);
        map.addRoad("DIO's Mansion", "Angelo Rock", 2);
    }

    @Override
    public String getName() {
        return "Default Map";
    }

    @Override
    public Graph getMap() {
        return map;
    }
}

class ParallelMap implements MapType{
    private Graph map= new Graph();

    public ParallelMap(){
        map.addTown("Town Hall");
        map.addTown("Cafe Deux Magots");
        map.addTown("Jade Garden");
        map.addTown("Morioh Grand Hotel");
        map.addTown("Trattoria Trussardi");
        map.addTown("Green Dolphin Street Prison");
        map.addTown("Polnareff Land");
        map.addTown("San Giorgio Maggiore");
        map.addTown("Libeccio");
        map.addTown("Savage Garden");
        map.addTown("Joestar Mansion");
        map.addTown("DIO's Mansion");
        map.addTown("Angelo Rock");
        map.addTown("Vineyard");
        map.addRoad("San Giorgio Maggiore","Joestar Mansion",5);
        map.addRoad("San Giorgio Maggiore","Savage Garden",6);
        map.addRoad("Joestar Mansion","Morioh Grand Hotel",4);
        map.addRoad("Joestar Mansion","Trattoria Trussardi",5);
        map.addRoad("Joestar Mansion","Jade Garden",3);
        map.addRoad("Trattoria Trussardi","Town Hall",6);
        map.addRoad("Trattoria Trussardi","Angelo Rock",3);
        map.addRoad("Trattoria Trussardi","DIO's Mansion",4);
        map.addRoad("Angelo Rock","DIO's Mansion",1);
        map.addRoad("Angelo Rock","Green Dolphin Street Prison",8);
        map.addRoad("Jade Garden","Savage Garden",4);
        map.addRoad("Jade Garden","Cafe Deux Magots",3);
        map.addRoad("Morioh Grand Hotel","Cafe Deux Magots",6);
        map.addRoad("DIO's Mansion","Green Dolphin Street Prison",6);
        map.addRoad("Savage Garden","Cafe Deux Magots",5);
        map.addRoad("Cafe Deux Magots","Town Hall",4);
        map.addRoad("Cafe Deux Magots","Polnareff Land",2);
        map.addRoad("Town Hall","Libeccio",2);
        map.addRoad("Town Hall","Vineyard",3);
        map.addRoad("Libeccio","Vineyard",3);
    }

    @Override
    public String getName() {
        return "Paralle Map";
    }

    @Override
    public Graph getMap() {
        return map;
    }
}
class AlternateMap implements MapType{
    private Graph map= new Graph();

    public AlternateMap(){
        map.addTown("Town Hall");
        map.addTown("Cafe Deux Magots");
        map.addTown("Jade Garden");
        map.addTown("Morioh Grand Hotel");
        map.addTown("Trattoria Trussardi");
        map.addTown("Green Dolphin Street Prison");
        map.addTown("Polnareff Land");
        map.addTown("San Giorgio Maggiore");
        map.addTown("Libeccio");
        map.addTown("Savage Garden");
        map.addTown("Joestar Mansion");
        map.addTown("DIO's Mansion");
        map.addTown("Angelo Rock");
        map.addTown("Vineyard");
        map.addTown("Passione Restaurant");
        map.addRoad("San Giorgio Maggiore","Morioh Grand Hotel",3);
        map.addRoad("San Giorgio Maggiore","Savage Garden",6);
        map.addRoad("Joestar Mansion","Morioh Grand Hotel",4);
        map.addRoad("Joestar Mansion","Trattoria Trussardi",5);
        map.addRoad("Trattoria Trussardi","Green Dolphin Street Prison",4);
        map.addRoad("Trattoria Trussardi","Passione Restaurant",1);
        map.addRoad("Angelo Rock","Passione Restaurant",6);
        map.addRoad("Angelo Rock","Polnareff Land",2);
        map.addRoad("Angelo Rock","Jade Garden",1);
        map.addRoad("Morioh Grand Hotel","Green Dolphin Street Prison",2);
        map.addRoad("Morioh Grand Hotel","Town Hall",2);
        map.addRoad("Green Dolphin Street Prison","Town Hall",3);
        map.addRoad("Passione Restaurant","Town Hall",7);
        map.addRoad("Passione Restaurant","Cafe Deux Magots",4);
        map.addRoad("Passione Restaurant","DIO's Mansion",2);
        map.addRoad("Polnareff Land","Jade Garden",2);
        map.addRoad("Savage Garden","Vineyard",4);
        map.addRoad("Vineyard","Cafe Deux Magots",4);
        map.addRoad("Cafe Deux Magots","DIO's Mansion",1);
    }

    @Override
    public String getName() {
        return "Parallel Map";
    }

    @Override
    public Graph getMap() {
        return this.map;
    }
}
