package com.mycolorstronger.adamoglu.mycolorstronger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;


public class FinishMenu {
    //Oyun bittiğinde puanlama ve süre gösterilir.

    @SuppressLint("StaticFieldLeak")
    static TextView win,scores,gametime,played;
    @SuppressLint("StaticFieldLeak")
    static Context context;

    public static SharedPreferences.Editor Editor() {
        //Herhangi bir veri kaydetmek için
        final SharedPreferences prefSettings = context.getSharedPreferences("",0);
        return prefSettings.edit();
    }

    public static SharedPreferences SharedPreferences() {
        //Kaydedilen herhangi bir veriyi çekmek için
        return context.getSharedPreferences("",0);
    }

    FinishMenu(TextView win, TextView scores, TextView gametime,TextView played, Context context){
        //Gerekli bilgiler set edilir
        FinishMenu.context=context;
        FinishMenu.win =win;
        FinishMenu.scores =scores;
        FinishMenu.gametime =gametime;
        FinishMenu.played=played;
    }

    @SuppressLint("SetTextI18n")
    public static void Show(){

        int Win=Wander.getWhoWin();//Kimin kazandıgı bilgisine erişilir
        int strategyscore=0;
        int timescore=0;
        int movesscore=0;
        int total=0;
        int totalscore;
        int played=SharedPreferences().getInt("Played",0);//Kayıtlı oynama sayısı çekilr
        int earned=SharedPreferences().getInt("Earned",0);//Kayıtlı kazanma sayısı çekilr
        int scoreless=SharedPreferences().getInt("Scoreless",0);//Kayıtlı beraberlik sayısı çekilr

        Editor().putInt("Played",++played).commit();//Oynama sayısı bir arttırılıp tekrar kaydedilir

        int moves=User.getMove();//Kullanıcının hamle sayısı bilgisine ulaşılır

        if(Win==Map.User){//Eger Kullanıcı kazandıysa
            win.setText("YOU WIN");

            Editor().putInt("Earned",++earned).commit();//Kazanma sayısı bir arttırılıp tekrar kaydedilir

            strategyscore=Score.getScore();//Oynarken kazanılan strateji skoru alınır

            timescore=10000/Time.getTime();//Oyun zamanı alınır ve matamatiksel işlemle puana dönüştürülür
            Score.addScore(timescore,1);//Süre puanı eklenir

            movesscore=10000/moves;//Kullanıcı hamle sayısı alınır ve matamatiksel işlemle puana dönüştürülür
            Score.addScore(movesscore,1);//Hamle puanı eklenir

        }else if(Win==Map.Pc){//Eger Biligisyar kazandıysa
            win.setText("PC WIN");
            //Bilgisayar kazandıysa hiçbir puan verilmez
        }else {//Kimse kazanmadıysa(beraberlik)
            win.setText("SCORELESS");

            Editor().putInt("Scoreless",++scoreless).commit();//Beraberlik sayısı bir arttırışıp kaydedilir

            strategyscore=Score.getScore();//Oynarken kazanılan strateji skoru alınır
        }
        User.resetMove();//Kullanıcının hareket sayısı sıfırlanır
        Wander.resetWhoWin();//Kimin kazandıgı bilgisi sıfırlanır

        //Bütün puanlar ve önceki skor toplanır ve kaydedilir
        total=timescore+movesscore+strategyscore;

        totalscore=SharedPreferences().getInt("Score",0)+total;
        Editor().putInt("Score",totalscore).commit();

        scores.setText(timescore+"\n"+movesscore+"\n"+strategyscore+"\n"+total+"\n\n"+totalscore);

        gametime.setText("Game Time\t "+Time.Convert(Time.getTime()));

        //Oynanma,kazanma ve beraberlik sayıları tekrar cekilerek gösterilir
        played=SharedPreferences().getInt("Played",0);
        earned=SharedPreferences().getInt("Earned",0);
        scoreless=SharedPreferences().getInt("Scoreless",0);

        FinishMenu.played.setText("Played    "+played+"\n"+"Earned    "+earned+"\n"+"Scoreless   "+scoreless);
    }
}
