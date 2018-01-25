package dz_lesson35_36.dao;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.IdEntity;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public abstract class GeneralDAO {


    private static String pathUserDB = "C:\\Users\\Skorodielov\\Desktop\\UserDB.txt";
    private static String pathHotelDB = "C:\\Users\\Skorodielov\\Desktop\\HotelDB.txt";
    private static String pathRoomDB = "C:\\Users\\Skorodielov\\Desktop\\RoomDB.txt";
    private static String pathOrderDB = "C:\\Users\\Skorodielov\\Desktop\\OrderDB.txt";
    private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    static ArrayList<String> readFromFile(String path)throws Exception{
        ArrayList<String> arrayList = new ArrayList<>();


        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;

            while ((line = br.readLine()) != null){
                String[] result = line.split("\n");
                for (String el : result){
                    if (el != null){
                        arrayList.add(el);
                    }
                }
            }
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("File does not exist");
        } catch (IOException e) {
            throw new IOException("Reading from file " + path + " failed");
        }
        return arrayList;
    }

    public static <T> void writerToFile(T t, String path)throws Exception{
        if (t == null)
            throw new BadRequestException("Invalid incoming data");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))){
            bufferedWriter.append(t.toString() + ("\n"));
        }catch (IOException e){
            throw new IOException("Can not write to file " + path);
        }
    }

    static void writerInFailBD(String path, StringBuffer content)throws Exception{
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            bufferedWriter.append(content);
        }catch (IOException e){
            throw new IOException("Can not write to file " + path);
        }
    }

    static <T extends IdEntity> void assignmentObjectId(T t)throws Exception{
        if (t == null)
            throw new BadRequestException("User does not exist");

        Random random = new Random();
        t.setId(random.nextInt());
        if (t.getId() < 0){
            t.setId(-1 * t.getId());
        }
    }

    public static String getPathUserDB() {
        return pathUserDB;
    }

    public static String getPathHotelDB() {
        return pathHotelDB;
    }

    public static String getPathRoomDB() {
        return pathRoomDB;
    }

    public static String getPathOrderDB() {
        return pathOrderDB;
    }

    public static DateFormat getFORMAT() {
        return FORMAT;
    }

}
