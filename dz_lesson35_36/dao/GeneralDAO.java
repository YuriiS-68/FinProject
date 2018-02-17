package dz_lesson35_36.dao;

import dz_lesson35_36.exception.BadRequestException;
import dz_lesson35_36.model.IdEntity;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public abstract class GeneralDAO {

    private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private static String pathDB;

    static ArrayList<String> readFromFile()throws Exception{
        ArrayList<String> arrayList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathDB))){
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
            throw new IOException("Reading from file " + pathDB + " failed");
        }
        return arrayList;
    }

    static <T> void writerToFile(T t)throws Exception{
        if (t == null)
            throw new BadRequestException("Invalid incoming data");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathDB, true))){
            bufferedWriter.write(t.toString() + ("\n"));
        }catch (IOException e){
            throw new IOException("Can not write to file " + pathDB);
        }
    }

    static void writerToFile(String path, StringBuffer content)throws Exception{
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            bufferedWriter.append(content);
        }catch (IOException e){
            throw new IOException("Can not write to file " + path);
        }
    }

    /*static <T extends IdEntity> void setId(T t)throws Exception{
        if (t == null)
            throw new BadRequestException("User does not exist");

        Random random = new Random();
        t.setId(random.nextInt());
        if (t.getId() < 0){
            t.setId(-1 * t.getId());
        }
    }*/

    public static long generateId(){
        long id;

        Random random = new Random();
        id = random.nextInt();

        if (id < 0){
            id = -1 * id;
        }
        return id;
    }

    static void setPathDB(String pathDB) {
        GeneralDAO.pathDB = pathDB;
    }

    static DateFormat getFORMAT() {
        return FORMAT;
    }
}
