package dz_lesson35_36.demo;

import dz_lesson35_36.dao.HotelDAO;
import dz_lesson35_36.model.Hotel;

public class DemoHotel {
    public static void main(String[] args)throws Exception {

        HotelDAO hotelDAO = new HotelDAO();

        Hotel hotel1 = new Hotel("Ukraine", "Kiev", "Hrehsatik", "Sputnik");
        Hotel hotel2 = new Hotel("Ukraine", "Harkiv", "Sumskaya", "Meteor");
        Hotel hotel3 = new Hotel("Ukraine", "Lviv", "Bolotnaya", "Volna");
        Hotel hotel4 = new Hotel("Ukraine", "Dnepr", "Centralnaya", "Radisson");
        Hotel hotel5 = new Hotel("Ukraine", "Donetsk", "Lenina", "Meteor");
        Hotel hotel6 = new Hotel("Germany", "Berlin", "Ulrih Olafson", "Western");

        HotelDAO.addHotel(hotel2);
        /*HotelDAO.addHotel(hotel2);
        HotelDAO.addHotel(hotel3);
        HotelDAO.addHotel(hotel4);
        HotelDAO.addHotel(hotel5);*/
        //HotelDAO.deleteHotel(799453853L);
        //System.out.println(HotelDAO.findHotelByName("Meteor"));
        //System.out.println(HotelDAO.findHotelByCity("Lviv"));
    }
}
