package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.Context;
import android.database.Cursor;


public class SaveGame {
    Context context;
    static database myDb;
    static private int gametime;
    static private int score;
    static private int whoplay;

    SaveGame(Context context){
        myDb=new database(context);
        this.context=context;
    }



    private static int[][] StringToArray(String array){
        //Gelen string matris bilgisini integer  matrise çevirir

        String[] rows = array.split("], \\[");
        for (int i = 0; i < rows.length; i++) {
            rows[i] = rows[i].replace("[[", "").replace("]]", "").replaceAll(" ", "");
        }

        int numberOfColumns = rows[0].split(",").length;

        String[][] matrix = new String[rows.length][numberOfColumns];

        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].split(",");
        }

        int[][] map = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[1].length; j++) {
               map[i][j]=Integer.parseInt(matrix[i][j]);
            }
        }
        return map;
    }

    static void saveGame(int map[][]){
        //Oyunu kaydeder
        ClearSavegame();
        myDb.Add(map,Time.getTime(),Score.getScore(),MainActivity.whoPlay);
        System.out.println("Saved Game");
    }

    static int[][] getsaveGame(){
        //Kayıtlı matrisi alır
        final Cursor res = myDb.getAllData();
        String s_map = null;

        if(res.getCount()!=0){
            while(res.moveToNext()){
                s_map=res.getString(0);
                gametime=res.getInt(1);
                score=res.getInt(2);
                whoplay=res.getInt(3);
            }
        }


        if(s_map!=null)
            return StringToArray(s_map);
        else
            return null;
    }

    static int getDataCount(){
        //Veritabanındaki veri sayısını verir
        final Cursor res = myDb.getAllData();
        return res.getCount();
    }
    static boolean ControlData(){
        //Veri tabnını kontrol eder(boş yada dolu)
        final Cursor res = myDb.getAllData();
        return res.getCount() != 0;
    }

    static void ClearSavegame(){
        myDb.DeleteAllData();
    }



    static public int getGametime() {
        return gametime;
    }

    static public int getScore() {
        return score;
    }

    static public int getWhoplay() {
        return whoplay;
    }
}
