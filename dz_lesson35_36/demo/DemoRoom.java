package dz_lesson35_36.demo;

import dz_lesson35_36.dao.RoomDAO;
import dz_lesson35_36.model.Hotel;
import dz_lesson35_36.model.Room;
import dz_lesson35_36.model.Filter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoRoom {
    public static void main(String[] args)throws Exception {

        //RoomDAO roomDAO = new RoomDAO();

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String date = "23.12.2017";
        String date2 = "20.12.2017";
        String date3 = "04.12.2017";
        String date4 = "22.12.2017";

        Hotel hotel1 = new Hotel("Ukraine", "Kiev", "Hrehsatik", "Sputnik");
        Hotel hotel2 = new Hotel("Ukraine", "Harkiv", "Sumskaya", "Meteor");

        Room room1 = new Room(3, 50.00, false, true, format.parse(date), hotel1);
        Room room2 = new Room(5, 120.00, false, true, format.parse(date2), hotel1);
        Room room3 = new Room(2, 30.00, false, true, format.parse(date), hotel2);
        Room room4 = new Room(3, 50.00, false, true, format.parse(date3), hotel2);

        Room room5 = new Room(5, 120.00, false, true, format.parse(date4), hotel1);
        Room room6 = new Room(3, 40.00, true, true, new Date(), hotel2);
        Room room7 = new Room(3, 40.00, false, true, new Date(), hotel2);
                                                                                                     //format.parse(date)
        Filter filter = new Filter(0, 0, false, true, format.parse(date3), "Ukraine", "Harkiv");

        RoomDAO.addRoom(room2);

        //RoomDAO.addRoom(room1);
        //RoomDAO.addRoom(room6);
        //RoomDAO.addRoom(room7);

        //roomDAO.deleteRoom(1231792585L);

        //System.out.println("RoomsFinish - " + RoomDAO.findRooms(filter));

        //RoomDAO.mapRooms("268765496,3,50.0,false,true,23.12.2017,94675877");

    }
}
