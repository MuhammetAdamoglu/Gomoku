package com.mycolorstronger.adamoglu.mycolorstronger;

import android.annotation.SuppressLint;
import android.widget.TextView;

public class Score {
    //Stratejiye göre puan hesaplar

    private static int Score=0;
    @SuppressLint("StaticFieldLeak")
    static TextView textView;
    static ScanID scanID = new ScanID();

    static int stone_yatay=1;
    static int stone_dikey=1;
    static int stone_capraz1=1;
    static int stone_capraz2=1;

    static int count_yatay=1;
    static int count_dikey=1;
    static int count_capraz1=1;
    static int count_capraz2=1;

    Score(TextView textView){
        com.mycolorstronger.adamoglu.mycolorstronger.Score.textView =textView;
        showScore();
    }

    static void ScoreUser(int i,int j,Map map){

        //Oynanan noktadaki toplam taş sayısı ve tarama sayısına bakılır
        for (int scanmap_id=1; scanmap_id<=8;scanmap_id++){
            for (int scan=1; scan<MainActivity.Count; scan++){

                if(scanID.scan(i,j,scan, map,scanmap_id)==Map.User) {
                    addUserStone(scanmap_id);
                }else if(scanID.scan(i,j,scan, map,scanmap_id)==Map.Pc){
                    break;
                }
                addScanCount(scanmap_id);
            }
        }

        //Dikey, Yatay ve Caprazdaki taş sayıları kontrol edilir
        for (int scanmap_id=1; scanmap_id<=8;scanmap_id++){
            if(getStone(scanmap_id)>1){//Tarama sayısı 1 den büyükse
                if(getCount(scanmap_id)>=MainActivity.Count){//Taş sayısı Counta eşit ve büyükse
                    addScore(getStone(scanmap_id)*Time.getBackCountTime()/4,1);//Kullanıcn kalan zamana ve taş sayısına göre puanlama yapılır
                    System.out.println(getStone(scanmap_id)+"  "+getCount(scanmap_id)+"  "+scanmap_id);
                }
            }
        }

        resetCounts();
        resetStones();
    }

    private static int getStone(int scanmap_id){
        if(scanmap_id==1 || scanmap_id==2){
            //Yatay
            return stone_yatay;
        }else if(scanmap_id==3 || scanmap_id==4){
            //Dikey
            return stone_dikey;
        }else if(scanmap_id==5 || scanmap_id==6){
            //Capraz1
            return stone_capraz1;
        }else if(scanmap_id==7 || scanmap_id==8){
            //Capraz2
            return stone_capraz2;
        }
        return 0;
    }

    private static void addUserStone(int scanmap_id){
        if(scanmap_id==1 || scanmap_id==2){
            //Yatay
            stone_yatay++;
        }else if(scanmap_id==3 || scanmap_id==4){
            //Dikey
            stone_dikey++;
        }else if(scanmap_id==5 || scanmap_id==6){
            //Capraz1
            stone_capraz1++;
        }else if(scanmap_id==7 || scanmap_id==8){
            //Capraz2
            stone_capraz2++;
        }
    }

    private static void resetStones(){
        stone_yatay=1;
        stone_dikey=1;
        stone_capraz1=1;
        stone_capraz2=1;
    }

    private static int getCount(int scanmap_id){
        if(scanmap_id==1 || scanmap_id==2){
            //Yatay
            return count_yatay;
        }else if(scanmap_id==3 || scanmap_id==4){
            //Dikey
            return count_dikey;
        }else if(scanmap_id==5 || scanmap_id==6){
            //Capraz1
            return count_capraz1;
        }else if(scanmap_id==7 || scanmap_id==8){
            //Capraz2
            return count_capraz2;
        }
        return 0;
    }

    private static void addScanCount(int scanmap_id){
        if(scanmap_id==1 || scanmap_id==2){
            //Yatay
            count_yatay++;
        }else if(scanmap_id==3 || scanmap_id==4){
            //Dikey
            count_dikey++;
        }else if(scanmap_id==5 || scanmap_id==6){
            //Capraz1
            count_capraz1++;
        }else if(scanmap_id==7 || scanmap_id==8){
            //Capraz2
            count_capraz2++;
        }
    }

    private static void resetCounts(){
        count_yatay=1;
        count_dikey=1;
        count_capraz1=1;
        count_capraz2=1;
    }


    public static void addScore(int addscore,int foldaddscore){
        Score=Score+addscore*foldaddscore;
        showScore();
    }
    public static void foldScore(int foldscore){
        Score=Score*foldscore;
        showScore();
    }
    public static void resetScore(){
        Score=0;
        resetCounts();
        resetStones();
        showScore();
    }
    public static void setScore(int Score){
        com.mycolorstronger.adamoglu.mycolorstronger.Score.Score =Score;
        showScore();
    }
    public static int getScore(){
        return Score;
    }
    private static void showScore(){
        textView.setText(String.valueOf(Score));
    }
}
