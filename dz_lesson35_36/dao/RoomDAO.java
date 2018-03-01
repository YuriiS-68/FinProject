package dz_lesson35_36.dao;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.Filter;
import dz_lesson35_36.model.Hotel;
import dz_lesson35_36.model.Room;

import java.util.*;

public class RoomDAO extends GeneralDAO{

    private static String pathRoomDB = "C:\\Users\\Skorodielov\\Desktop\\RoomDB.txt";

    static {setPathDB(pathRoomDB);}

    public static Room addRoom(Room room)throws Exception{
        if (room == null)
            throw new BadRequestException("Invalid incoming data");

        if (checkById(room.getId()))
            throw new BadRequestException("Room with id " + room.getId() + " in file RoomDB already exists.");

        writerToFile(room);

        return room;
    }

    public static void deleteRoom(Long idRoom)throws Exception{
        if (idRoom == null)
            throw new BadRequestException("Invalid incoming data");

        if (!checkById(idRoom))
            throw new BadRequestException("Room with id " + idRoom + " in file RoomDB not found.");

        writerToFile(pathRoomDB, contentForWriting(idRoom));
    }

    public static Collection findRooms(Filter filter)throws Exception{
        if (filter == null)
            throw new BadRequestException("Invalid incoming data");

        LinkedList<Room> foundRooms = new LinkedList<>();

        for (Room room : getRooms()){
            if (filterCheck(room, filter)){
                foundRooms.add(room);
            }
        }
        return foundRooms;
    }

    public static LinkedList<Room> getRooms()throws Exception{
        LinkedList<Room> arrays = new LinkedList<>();

        for (String str : readFromFile()){
            if (str != null){
                arrays.add(mapRooms(str));
            }
        }
        return arrays;
    }

    public static boolean checkById(long id)throws Exception{
        if (id == 0 )
            throw new BadRequestException("Invalid incoming data");

        for (Room room : getRooms()){
            if (room != null && room.getId() == id){
                return true;
            }
        }
        return false;
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

    public static Room mapRooms(String string)throws Exception{
        if (string == null)
            throw new BadRequestException("Invalid incoming data");

        System.out.println("Incoming string from file rooms - " + string); //смотрю входящую строку

        String[] fields = string.split(",");

        System.out.println(Arrays.toString(fields));  //смотрю содержимое массива подстрок полученного из входящей строки

        if (!checkFields(fields))                    //проверяю содержимое каждой ячейки массива на null
            throw new BadRequestException("Invalid incoming data. Field is null.");

        Room room = new Room();
        room.setId(Long.parseLong(fields[0]));
        room.setNumberOfGuests(Integer.parseInt(fields[1]));
        room.setPrice(Double.parseDouble(fields[2]));
        room.setBreakfastIncluded(Boolean.parseBoolean(fields[3]));
        room.setPetsAllowed(Boolean.parseBoolean(fields[4]));
        room.setDateAvailableFrom(GeneralDAO.getFORMAT().parse(fields[5]));

        for (Hotel hotel : HotelDAO.getHotels()){
            if (hotel != null && hotel.getId() == Long.parseLong(fields[6])){
                room.setHotel(hotel);
            }
        }

        return room;
    }

    private static boolean checkFields(String[] fields)throws Exception{
        if (fields == null)
            throw new BadRequestException("Invalid incoming data");

        for (String field : fields){
            if (field == null){
                return false;
            }
        }
        return true;
    }

    private static StringBuffer contentForWriting(long idRoom)throws Exception{
        if (idRoom == 0)
            throw new BadRequestException("Invalid incoming data");

        StringBuffer res = new StringBuffer();

        for (Room room : getRooms()){
            if (room.getId() != idRoom){
                res.append(room.toString() + ("\n"));
            }
        }
        return res;
    }
}
