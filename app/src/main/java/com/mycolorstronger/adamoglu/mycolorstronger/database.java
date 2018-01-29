package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;

import java.util.Arrays;

public class database extends SQLiteOpenHelper {
    //Oyuna devam etme özelliği katmak için kullanılır, matris kaydedilir

    private static final String VERITABANI = "SaveGame";
    private static final String TABLE = "savegame";
    private static String MAP = "map";
    private static String GAMETIME = "gametime";
    private static String SCORE = "score";
    private static String WHO = "whoplay";
    private static String ID = "ID";


    public database(Context context) {
        super(context, VERITABANI, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Günlük veri için
        db.execSQL(
                "CREATE TABLE " + TABLE + " ( "

                        + MAP + " TEXT, "
                        + GAMETIME + " TEXT, "
                        + SCORE + " TEXT, "
                        + WHO + " TEXT, "
                        + ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
                        + " )"
        );



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXITS" + TABLE);
        onCreate(db);

    }


    public boolean Add(int map[][], int gametime, int score, int who) {

        //Matrisi kaydetmek için

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues veriler = new ContentValues();

        veriler.put(MAP, Arrays.deepToString(map));//Gelen matris, stringe dönüştürülüp kaydedilir
        veriler.put(GAMETIME, gametime);
        veriler.put(SCORE, score);
        veriler.put(WHO, who);

        long result = db.insert(TABLE, null, veriler);
        if (result == -1)
            return false;
        else
            return true;

    }



    public Cursor getAllData() {

        //String tipindeki matrisi çekmek için

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor result = db.rawQuery("select * from " + TABLE, null);

        return result;
    }

    public void DeleteAllData(){
        //Matrisi silmek için
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();

    }


}