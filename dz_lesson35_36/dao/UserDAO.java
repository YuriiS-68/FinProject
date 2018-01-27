package dz_lesson35_36.dao;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.User;
import dz_lesson35_36.model.UserType;

import java.io.*;
import java.util.LinkedList;

public class UserDAO extends GeneralDAO {

    //считывание данных - считывание файла
    //обработка данных - маппинг данных
    private static String pathUserDB = "C:\\Users\\Skorodielov\\Desktop\\UserDB.txt";

    public UserDAO() {
        setPathDB(pathUserDB);
    }

    public static User registerUser(User user)throws Exception{
        //проверяю есть ли такое имя сохраняемого пользователя в базе, если нет
        //присваиваю пользователю уникальный id, тип пользователя, пароль и страну
        //save user to DB (file)
        if (user == null)
            throw new BadRequestException("Invalid incoming data");

        if (!checkValidLoginName(user.getUserName()))
            throw new BadRequestException("User with name " + user.getUserName() + " already exists");

        assignmentId(user);

        writerToFile(user);

        return user;
    }

    private static LinkedList<User> getUsers()throws Exception{
        LinkedList<User> arrays = new LinkedList<>();

        int index = 0;
        for (String el : readFromFile()){
            if (el != null){
                arrays.add(mapUsers(readFromFile().get(index)));
            }
            index++;
        }
        return arrays;
    }

    private static boolean checkValidLoginName(String loginName)throws Exception{
        if (loginName == null)
            throw new BadRequestException("Invalid incoming data");

        for (User user : getUsers()) {
            if (user != null && user.getUserName().equals(loginName)){
                return false;
            }
        }
        return true;
    }

    private static User mapUsers(String string)throws Exception{
        if (string == null)
            throw new BadRequestException("String does not exist");

        String[] fields = string.split(",");

        User user = new User();
        user.setId(Long.parseLong(fields[0]));
        user.setUserName(fields[1]);
        user.setPassword(fields[2]);
        user.setCountry(fields[3]);
        if (fields[4].equals("USER")){
            user.setUserType(UserType.USER);
        }else {
            user.setUserType(UserType.ADMIN);
        }
        return user;
    }

    //методы входа и выхода из системы оставляем на самый конец
    /*public static void login(String userName, String password)throws Exception {
        if (userName == null || password == null)
            throw new BadRequestException("Username or password is not exists");

        String[] lines = readingFromFile(utils.getPathUserDB()).split(",");
        for (String el : lines) {
            if (el != null && el.contains(userName) && el.contains(password)) {

            }
        }
    }*/
}
