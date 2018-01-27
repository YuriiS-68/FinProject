package dz_lesson35_36.dao;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.*;

import java.io.*;
import java.util.*;

public class OrderDAO extends GeneralDAO{

    private static String pathOrderDB = "C:\\Users\\Skorodielov\\Desktop\\OrderDB.txt";

    private static UserDAO userDAO = new UserDAO();
    private static HotelDAO hotelDAO = new HotelDAO();
    private static RoomDAO roomDAO = new RoomDAO();

    public OrderDAO() {
        setPathDB(pathOrderDB);
    }

    public static void bookRoom(long roomId, long userId, long hotelId)throws Exception{
        //проверить есть ли в файлах БД такие данные
        //если есть создать ордер и просетить ему данные по всем полям
        //сохранить в файл БД
        if (roomId == 0 || userId == 0 || hotelId == 0)
            throw new BadRequestException("Invalid incoming data");

        if(roomDAO.checkIdRoom(roomId))
            throw new BadRequestException("Room with id " + roomId + " is not exist");

        if (userDAO.checkIdUser(userId))
            throw new BadRequestException("User with id " + userId + " is not exist");

        if (hotelDAO.checkIdHotel(hotelId))
            throw new BadRequestException("Hotel with id " + hotelId + " is not exist");

        writerToFile(createOrder(roomId, userId));
    }

    public static void cancelReservation(long roomId, long userId)throws Exception{
        if (roomId == 0 || userId == 0)
            throw new BadRequestException("Invalid incoming data");

        if(!checkId(roomId, userId))
            throw new BadRequestException("Room with id " + roomId + " is not exist");

        if (!checkId(roomId, userId))
            throw new BadRequestException("User with id " + userId + " is not exist");

        writerInFailBD(pathOrderDB, resultForWriting(roomId, userId));
    }

    private static Order createOrder(long roomId, long userId)throws Exception{
        Order order = new Order();

        assignmentObjectId(order);

        String dateFrom = "23.11.2017";
        String dateTo = "06.12.2017";
        Date dateStart = GeneralDAO.getFORMAT().parse(dateFrom);
        Date dateFinish = GeneralDAO.getFORMAT().parse(dateTo);

        order.setUser(userDAO.findUserById(userId));
        order.setRoom(roomDAO.findRoomById(roomId));
        order.setDateFrom(GeneralDAO.getFORMAT().parse(dateFrom));
        order.setDateTo(GeneralDAO.getFORMAT().parse(dateTo));

        long difference = dateStart.getTime() - dateFinish.getTime();
        int days = (int)(difference / (24 * 60 * 60 * 1000));
        double orderCost = roomDAO.findRoomById(roomId).getPrice() * days;
        if (orderCost < 0){
            orderCost = -1 * orderCost;
        }

        order.setMoneyPaid(orderCost);

        return order;
    }

    private static boolean checkId(long idRoom, long idUser)throws Exception{
        if (idRoom == 0 || idUser == 0)
            throw new BadRequestException("Invalid incoming data");

        for (Order el : getOrders()){
            if (el != null && el.getRoom().getId() == idRoom || el != null && el.getUser().getId() == idUser){
                return true;
            }
        }
        return false;
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

        String[] fields = string.split(",");

        Order order = new Order();
        order.setId(Long.parseLong(fields[0]));
        order.setUser(userDAO.findUserById(Long.parseLong(fields[1])));
        order.setRoom(roomDAO.findRoomById(Long.parseLong(fields[2])));
        order.setDateFrom(GeneralDAO.getFORMAT().parse(fields[3]));
        order.setDateTo(GeneralDAO.getFORMAT().parse(fields[4]));
        order.setMoneyPaid(Double.parseDouble(fields[5]));

        return order;
    }

    private static StringBuffer resultForWriting(long roomId, long userId)throws Exception{
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
