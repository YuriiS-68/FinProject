package dz_lesson35_36.demo;

import dz_lesson35_36.dao.HotelDAO;
import dz_lesson35_36.model.Hotel;

public class DemoHotel {
    public static void main(String[] args)throws Exception {

        HotelDAO hotelDAO = new HotelDAO();

        Hotel hotel1 = new Hotel(111111, "Ukraine", "Kiev", "Hrehsatik", "Sputnik");
        Hotel hotel2 = new Hotel(222222, "Ukraine", "Harkiv", "Sumskaya", "Meteor");
        Hotel hotel3 = new Hotel(333333, "Ukraine", "Lviv", "Bolotnaya", "Volna");
        Hotel hotel4 = new Hotel(444444, "Ukraine", "Dnepr", "Centralnaya", "Radisson");
        Hotel hotel5 = new Hotel(555555, "Ukraine", "Donetsk", "Lenina", "Meteor");
        Hotel hotel6 = new Hotel(666666, "Germany", "Berlin", "Ulrih Olafson", "Western");

        //hotelDAO.addHotel(hotel6);
        /*HotelDAO.addHotel(hotel2);
        HotelDAO.addHotel(hotel3);
        HotelDAO.addHotel(hotel4);
        HotelDAO.addHotel(hotel5);*/
        //HotelDAO.deleteHotel(222222L);
        //System.out.println(HotelDAO.findHotelByName("Meteor"));
        //System.out.println(HotelDAO.findHotelByCity("Lviv"));
    }
}
