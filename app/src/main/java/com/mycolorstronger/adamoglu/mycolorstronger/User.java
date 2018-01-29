package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;


public class User {
    //Kullanıcının oynaması için

    int[] isSelectedlocale = new int[2];
    ImageView isSelected = null;
    private static int move=0;

    void playUser(Context context,Map map, MainActivity mainActivity, ImageView ımageView){

        if(!MainActivity.GameIsStop){//Oyun durmadıysa

            int[] locale=map.getLocale(ımageView);


            if(map.getMap()[locale[0]][locale[1]]==Map.Selected){//Tıklanan buton seçili ise

                map.getMap()[locale[0]][locale[1]]=Map.User;//Kullaınıcı oynar
                map.User(ımageView);

                isSelected=null;//seçili olan taş hafızadan silinir

                Score.ScoreUser(locale[0],locale[1],map);//Oynama stratejisine göre skor verilir
                Time.resetBackCountTime();//süre sıfırlanır
                Time.stopBackCountTime();//süre durdurulur
                System.out.println("User Puted x:"+locale[1]+" y:"+locale[0]+"..");

                if(Control_FullMap.DoIt(context,map)){//Harita dolduysa oyun bitirilir
                    Wander.FinishGame(mainActivity);
                    return;
                }

                MainActivity.whoPlay=Map.Pc;//Sıra pcde oldugunu bildirir
                move++;//hamle sayısı arttırılır
                mainActivity.PlayPc(context,locale[0],locale[1]);//Pc yapay zekası devreye sokulur


            }else if (map.getMap()[locale[0]][locale[1]]==Map.Empty){//Tıklanan buton boş ise

                if(isSelected!=null){
                    //Bir önceki seçilen nokta kaldırılır, boş atanır
                    map.Empty(isSelected);
                    map.getMap()[isSelectedlocale[0]][isSelectedlocale[1]]=Map.Empty;
                    isSelected=null;
                    System.out.println("Selected Removed..");
                }

                //Tıklanan nokta seçilir
                map.getMap()[locale[0]][locale[1]]=Map.Selected;
                map.Select(ımageView);

                isSelected=ımageView;
                isSelectedlocale[0]=locale[0];
                isSelectedlocale[1]=locale[1];

                System.out.println("User Selected x:"+isSelectedlocale[1]+" y:"+isSelectedlocale[0]+"..");

            }

        }
    }

    public static int getMove() {
        return move;
    }
    public static void resetMove() {
        move=0;
    }
}
