package dz_lesson35_36.dao;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.*;

import java.util.*;

public class OrderDAO extends GeneralDAO{

    private static String pathOrderDB = "C:\\Users\\Skorodielov\\Desktop\\OrderDB.txt";

    static {setPathDB(pathOrderDB);}

    public static void bookRoom(long roomId, long userId, long hotelId)throws Exception{
        //проверить есть ли в файлах БД такие данные
        //если есть создать ордер и просетить ему данные по всем полям
        //сохранить в файл БД
        if (roomId == 0 || userId == 0 || hotelId == 0)
            throw new BadRequestException("Invalid incoming data");

        if(RoomDAO.checkById(roomId))
            throw new BadRequestException("Room with id " + roomId + " is not exist");

        if (UserDAO.checkById(userId))
            throw new BadRequestException("User with id " + userId + " is not exist");

        if (HotelDAO.checkById(hotelId))
            throw new BadRequestException("Hotel with id " + hotelId + " is not exist");

        writerToFile(createOrder(roomId, userId));
    }

    public static void cancelReservation(long roomId, long userId)throws Exception{
        if (roomId == 0 || userId == 0)
            throw new BadRequestException("Invalid incoming data");

        if (checkById(roomId, userId))
            throw new BadRequestException("User with id " + userId + " is not exist");

        if(checkById(roomId, userId))
            throw new BadRequestException("Room with id " + roomId + " is not exist");

        writerToFile(pathOrderDB, contentForWriting(roomId, userId));
    }

    private static Order createOrder(long roomId, long userId)throws Exception{
        if (roomId == 0 || userId == 0)
            throw new BadRequestException("Invalid incoming data");

        Order order = new Order();

        setId(order);

        String dateFrom = "23.11.2017";
        String dateTo = "06.12.2017";
        Date dateStart = GeneralDAO.getFORMAT().parse(dateFrom);
        Date dateFinish = GeneralDAO.getFORMAT().parse(dateTo);

        order.setUserId(userId);

        order.setRoomId(roomId);

        order.setDateFrom(GeneralDAO.getFORMAT().parse(dateFrom));
        order.setDateTo(GeneralDAO.getFORMAT().parse(dateTo));

        order.setMoneyPaid(orderCost(dateStart, dateFinish, roomId));

        return order;
    }

    private static double orderCost(Date dateStart, Date dateFinish, long roomId)throws Exception{
        if (dateStart == null || dateFinish == null || roomId == 0)
            throw new BadRequestException("Invalid incoming data");

        long difference = dateStart.getTime() - dateFinish.getTime();
        int days = (int)(difference / (24 * 60 * 60 * 1000));
        double orderCost = 0;
        for (Room room : RoomDAO.getRooms()){
            if (room.getId() == roomId){
                orderCost = room.getPrice() * days;
                if (orderCost < 0) {
                    orderCost = -1 * orderCost;
                }
            }
        }
        return orderCost;
    }

    private static boolean checkById(long idRoom, long idUser)throws Exception{
        if (idRoom == 0 || idUser == 0)
            throw new BadRequestException("Invalid incoming data");

        for (Order order : getOrders()){
            if (order != null && order.getRoom().getId() != idRoom || order != null && order.getUser().getId() != idUser){
                return false;
            }
        }
        return true;
    }

    private static LinkedList<Order> getOrders()throws Exception{
        LinkedList<Order> arrays = new LinkedList<>();

        int index = 0;
        for (String el : readFromFile()){
            if (el != null){
                arrays.add(mapOrders(readFromFile().get(index)));
            }
            index++;
        }
        return arrays;
    }

    private static Order mapOrders(String string)throws Exception{
        if (string == null)
            throw new BadRequestException("String does not exist");

        System.out.println(string);
        String[] fields = string.split(",");

        Order order = new Order();
        order.setId(Long.parseLong(fields[0]));
        for (User user : UserDAO.getUsers()){
            if (user != null && user.getId() == Long.parseLong(fields[1])){
                order.setUser(user);
            }
        }

        for (Room room : RoomDAO.getRooms()){
            if (room != null && room.getId() == Long.parseLong(fields[2])){
                order.setRoom(room);
            }
        }

        order.setDateFrom(GeneralDAO.getFORMAT().parse(fields[3]));
        order.setDateTo(GeneralDAO.getFORMAT().parse(fields[4]));
        order.setMoneyPaid(Double.parseDouble(fields[5]));

        return order;
    }

    private static StringBuffer contentForWriting(long roomId, long userId)throws Exception{
        StringBuffer res = new StringBuffer();

        int index = 0;
        for (Order el : getOrders()){
            if (el != null && el.getUser().getId() == userId && el.getRoom().getId() == roomId) {
                el = null;
            }else {
                if (el != null){
                    res.append(el.toString() + "\n");
                }
            }
            index++;
        }
        return res;
    }
}
