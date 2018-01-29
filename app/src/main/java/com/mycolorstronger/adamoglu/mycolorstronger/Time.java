package com.mycolorstronger.adamoglu.mycolorstronger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;


public class Time {

    //Süre ile ilgili işlemler yapılır

    static int BackCountTime;
    static int BackCountTimeFirstTime;

    static int Timer;
    static int FirstTime;

    @SuppressLint("StaticFieldLeak")
    static TextView textView_Time;
    @SuppressLint("StaticFieldLeak")
    static TextView textView_Timer;
    @SuppressLint("StaticFieldLeak")
    static MainActivity mainActivity;
    @SuppressLint("StaticFieldLeak")
    static Context context;


    static CountDownTimer downTimer;
    static CountDownTimer timer;


    Time(final TextView textView_Time, final TextView textView_Timer, MainActivity MainActivity, Context con, final int Time){
        Timer=0;
        FirstTime=0;

        BackCountTime=Time;
        BackCountTimeFirstTime=Time;

        com.mycolorstronger.adamoglu.mycolorstronger.Time.textView_Time =textView_Time;
        com.mycolorstronger.adamoglu.mycolorstronger.Time.textView_Timer =textView_Timer;

        mainActivity=MainActivity;
        context=con;

        textView_Time.setText(String.valueOf(BackCountTimeFirstTime));
        textView_Timer.setText(Convert(Timer));



        downTimer =new CountDownTimer(BackCountTime*1000+1000, 1000) {
            public void onTick(long millisUntilFinished) {

                textView_Time.setText(String.valueOf(BackCountTime));//süre ekrana yazılır
                BackCountTime--;
            }

            public void onFinish() {
                //Süre bittiginde
                Score.addScore(-1*Score.getScore()/4,1);//puan kesilir
                resetBackCountTime();//süre basa sarılır
                mainActivity.PlayPc(context,0,0);//Pc hamle yapar
            }
        };

        timer =new CountDownTimer(100*1000, 1000) {
            public void onTick(long millisUntilFinished) {

                textView_Timer.setText(Convert(Timer));//zaman ekrana gösterilir
                Timer++;
            }

            public void onFinish() {
                timer.start();
            }
        };

    }

    public static void startBackCountTime(){
        downTimer.cancel();
        downTimer.start();
    }

    public static void stopBackCountTime(){
        downTimer.cancel();
    }


    public static void setBackCountTime(int Time){
        BackCountTime=Time;
    }

    public static int getBackCountTime() {
        return BackCountTime;
    }


    public static void resetBackCountTime(){
        BackCountTime=BackCountTimeFirstTime;
        textView_Time.setText(String.valueOf(BackCountTimeFirstTime));
    }
    //////////////////////////////////////////////////////////////

    public static void startTime(){
        timer.cancel();
        timer.start();
    }

    public static void stopTime(){
        timer.cancel();
    }

    public static void setTime(int setTime){
        Timer=setTime;
    }

    public static int getTime() {
        return Timer;
    }

    public static void resetTime(){
        Timer=FirstTime;
        textView_Timer.setText(Convert(Timer));
    }


    static String Convert(int second){
        //Verilen saniye, saat ve dakikaya dönüştürülür

        String str_hour = "00";
        String str_min = "00";
        String str_sec = "00";

        int hours = second / 3600;
        int remainder = second - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;


        if(secs<10)
            str_sec="0"+secs;
        else
            str_sec= String.valueOf(secs);
        if(mins<10)
            str_min="0"+mins;
        else
            str_min= String.valueOf(mins);
        if(hours<10)
            str_hour="0"+hours;
        else
            str_hour= String.valueOf(hours);

        if(hours==0 && mins==0){

            return str_sec;
        }
        else if(hours==0){
            return str_min+":"+str_sec;
        }

        else return str_hour+":"+str_min+":"+str_sec;
    }

}
