package dz_lesson35_36.dao;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.Filter;
import dz_lesson35_36.model.Hotel;
import dz_lesson35_36.model.Room;

import java.io.*;
import java.util.*;

public class RoomDAO extends GeneralDAO{

    private static String pathRoomDB = "C:\\Users\\Skorodielov\\Desktop\\RoomDB.txt";
    private static HotelDAO hotelDAO = new HotelDAO();

    public RoomDAO() {
        setPathDB(pathRoomDB);
    }

    public static Room addRoom(Room room)throws Exception{
        if (room == null)
            throw new BadRequestException("This " + room + " is not exist");

        if (!checkRoomById(room.getId()))
            throw new BadRequestException("Room with id " + room.getId() + " in file RoomDB already exists.");

        writerToFile(room);

        return room;
    }

    public static void deleteRoom(Long idRoom)throws Exception{
        if (idRoom == null)
            throw new BadRequestException("This id " + idRoom + " is not exist.");

        if (checkRoomById(idRoom))
            throw new BadRequestException("Room with id " + idRoom + " in file RoomDB not found.");

        writerInFailBD(pathRoomDB, resultForWriting(idRoom));
    }

    public static Collection findRooms(Filter filter)throws Exception{
        if (filter == null)
            throw new BadRequestException("This filter - " + filter + " does not exist." );

        LinkedList<Room> foundRooms = new LinkedList<>();

        for (Room room : getRooms()){
            if (filterCheck(room, filter)){
                foundRooms.add(room);
            }
        }
        return foundRooms;
    }

    private static boolean filterCheck(Room room, Filter filter)throws Exception{
        if (room == null || filter == null)
            throw new BadRequestException("Invalid incoming data");

        if (filter.getNumberOfGuests() != 0 && room.getNumberOfGuests() != filter.getNumberOfGuests())
            return false;

        if (filter.getPrice() != 0 && room.getPrice() != filter.getPrice())
            return false;

        if (filter.getDateAvailableFrom() != null && (room.getDateAvailableFrom().compareTo(filter.getDateAvailableFrom()) != 0) && (room.getDateAvailableFrom().compareTo(filter.getDateAvailableFrom()) <= 0))
            return false;

        if (room.isPetsAllowed() != filter.isPetsAllowed() && room.isBreakfastIncluded() != filter.isBreakfastIncluded())
            return false;

        if (filter.getCountry() != null && (!room.getHotel().getCountry().equals(filter.getCountry())))
            return false;

        if (filter.getCity() != null && (!room.getHotel().getCity().equals(filter.getCity())))
            return false;

        return true;
    }

    private static boolean checkRoomById(long id)throws Exception{
        if (id == 0 )
            throw new BadRequestException("Invalid incoming data");

        for (Room room : getRooms()){
            if (room != null && room.getId() == id){
                return false;
            }
        }
        return true;
    }

    private static LinkedList<Room> getRooms()throws Exception{
        LinkedList<Room> arrays = new LinkedList<>();

        int index = 0;
        for (String el : readFromFile()){
            if (el != null){
                arrays.add(mapRooms(readFromFile().get(index)));
            }
            index++;
        }
        return arrays;
    }

    private static Room mapRooms(String string)throws Exception{
        if (string == null)
            throw new BadRequestException("String does not exist");

        String[] fields = string.split(",");

        Room room = new Room();
        room.setId(Long.parseLong(fields[0]));
        room.setNumberOfGuests(Integer.parseInt(fields[1]));
        room.setPrice(Double.parseDouble(fields[2]));
        room.setBreakfastIncluded(Boolean.parseBoolean(fields[3]));
        room.setPetsAllowed(Boolean.parseBoolean(fields[4]));
        room.setDateAvailableFrom(GeneralDAO.getFORMAT().parse(fields[5]));
        room.setHotel(hotelDAO.findHotelById(Long.parseLong(fields[0])));

        return room;
    }

    private static StringBuffer resultForWriting(Long idRoom)throws Exception{
        StringBuffer res = new StringBuffer();

        for (Room el : getRooms()){
            if (el != null && el.getId() != idRoom){
                System.out.println(el.toString());
                res.append(el.toString() + ("\n"));
            }
        }
        return res;
    }
}
