package dz_lesson35_36.demo;

import dz_lesson35_36.dao.OrderDAO;
import dz_lesson35_36.model.Hotel;
import dz_lesson35_36.model.Room;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoOrder {
    public static void main(String[] args) throws Exception{

        OrderDAO orderDAO = new OrderDAO();

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

        OrderDAO.bookRoom(1951140740, 600109633, 985864179);
        //OrderDAO.bookRoom(2002, 7312364, 111111);
        //OrderDAO.bookRoom(3003, 8392838, 222222);
        //OrderDAO.bookRoom(1001, 7364205, 111111);

        //OrderDAO.cancelReservation(1001, 171705076L);
    }
}
