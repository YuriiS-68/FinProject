package dz_lesson35_36.service;

import dz_lesson35_36.dao.HotelDAO;
import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.Hotel;

import java.util.LinkedList;

public class HotelService {

    public static Hotel addHotel(Hotel hotel)throws Exception{
        if (hotel == null)
            throw new BadRequestException("This " + hotel + " is not exist");

        return HotelDAO.addHotel(hotel);
    }

    public static void deleteHotel(Long idHotel)throws Exception {
        if (idHotel == null)
            throw new BadRequestException("This id " + idHotel + " is not exist");

    }

    public static LinkedList<Hotel> findHotelByName(String name)throws Exception{
        if (name == null)
            throw new BadRequestException("This name - " + name + " does not exist." );

        return HotelDAO.findHotelByName(name);
    }

    public static LinkedList<Hotel> findHotelByCity(String city)throws Exception{
        if (city == null)
            throw new BadRequestException("This city - " + city + " does not exist." );

        return HotelDAO.findHotelByName(city);
    }
}
