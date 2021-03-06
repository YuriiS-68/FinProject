package dz_lesson35_36.dao;

import com.sun.xml.internal.stream.writers.WriterUtility;
import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.Hotel;

import java.io.*;
import java.util.LinkedList;

public class HotelDAO extends GeneralDAO {

    static {setPathDB("C:\\Users\\Skorodielov\\Desktop\\HotelDB.txt");}

    public static Hotel addHotel(Hotel hotel)throws Exception{
        //проверить по id есть ли такой отель в файле
        //если нет, добавить в файл
        if (hotel == null)
            throw new BadRequestException("Invalid incoming data");

        if (checkById(hotel.getId()))
            throw new BadRequestException("Hotel with id " + hotel.getId() + " already exists");

        writerToFile(hotel);

        return hotel;
    }

    public static void deleteHotel(Long idHotel)throws Exception{
        //считать файл
        //разбить на строки по отелям
        //если строка содержит заданный отель, удалить эту строку
        //перезаписать файл
        if (idHotel == null)
            throw new BadRequestException("Invalid incoming data");

        if (!checkById(idHotel))
            throw new BadRequestException("Hotel with id " + idHotel + " does not exist");

        writerToFile(contentForWriting(idHotel));
    }

    public static LinkedList<Hotel> findHotelByName(String name)throws Exception{
        //считать файл
        //разбить сплитом по символу переноса строки и найти нужную строку содержащую имя отеля
        //полученную строку разбить сплитом по запятой
        //получаю массив стрингов
        if (name == null)
            throw new BadRequestException("Invalid incoming data");

        LinkedList<Hotel> hotels = new LinkedList<>();

        for (Hotel el : getHotels()){
            if (el != null && el.getName().equals(name)){
                hotels.add(el);
            }
        }

        if (hotels.size() == 0)
            throw new BadRequestException("Method findHotelByName did not find any hotels by name " + name);

        return hotels;
    }

    public static LinkedList<Hotel> findHotelByCity(String city)throws Exception{
        //считать файл
        //разбить сплитом по символу переноса строки и найти нужную строку содержащую имя отеля
        //полученную строку разбить сплитом по запятой
        //получаю массив стрингов
        if (city == null)
            throw new BadRequestException("Invalid incoming data");

        LinkedList<Hotel> hotels = new LinkedList<>();

        for (Hotel el : getHotels()){
            if (el != null && el.getCity().equals(city)){
                hotels.add(el);
            }
        }

        if (hotels.size() == 0)
            throw new BadRequestException("Method findHotelByCity did not find any hotels from " + city);

        return hotels;
    }

    public static LinkedList<Hotel> getHotels()throws Exception{
        LinkedList<Hotel> arrayHotels = new LinkedList<>();

        for (String str : readFromFile()){
            if (str != null){
                arrayHotels.add(mapHotels(str));
            }
        }
        return arrayHotels;
    }

    public static boolean checkById(Long id)throws Exception{
        if (id == 0)
            throw new BadRequestException("Invalid incoming data");

        for (Hotel hotel : getHotels()) {
            if (hotel != null && hotel.getId() == id){
                return true;
            }

            if (hotel == null)
                throw new BadRequestException("Invalid incoming data");
        }
        return false;
    }

    private static Hotel mapHotels(String string)throws Exception{
        if (string == null)
            throw new BadRequestException("String does not exist");

        String[] fields = string.split(",");

        if (fields.length != 5)
            throw new BadRequestException("The length of the array does not match the specified");

        if (checkFields(fields))
            throw new BadRequestException("Invalid incoming data. Field is null.");

        Hotel hotel = new Hotel();
        hotel.setId(Long.parseLong(fields[0]));
        hotel.setCountry(fields[1]);
        hotel.setCity(fields[2]);
        hotel.setStreet(fields[3]);
        hotel.setName(fields[4]);

        return hotel;
    }

    private static StringBuffer contentForWriting(long idHotel)throws Exception{
        if (idHotel == 0)
            throw new BadRequestException("Invalid incoming data");

        StringBuffer res = new StringBuffer();

        for (Hotel hotel : getHotels()){
            if (hotel.getId() != idHotel){
                res.append(hotel.toString() + ("\n"));
            }
        }
        return res;
    }
}

