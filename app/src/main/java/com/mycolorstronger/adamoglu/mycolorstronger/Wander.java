package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;


public class Wander {


    ArrayList<int[]> CriticalPoints=new ArrayList<>();
    int[] positions = new int[10];
    int Move,Count;
    boolean ControlRandomPlay=true;
    Map map;
    Context context;
    int user_number, pc_number;

    ScanID scanID =new ScanID();


    private boolean ControlLocationForWin(int i, int j, int scanmap_id, int forwho){
        //Taranan bölgede 5 li yapılıp yapılamayacagını kontrol eder
        int scan;
        for (scan=0; scan<Count; scan++){
            if(forwho==Definitions.forPc){
                if(scanID.scan(i,j,scan,map,scanmap_id)==Map.User){
                    return false;
                }
            }else if(forwho==Definitions.forUser){
                if(scanID.scan(i,j,scan,map,scanmap_id)==Map.Pc){
                    return false;
                }
            }
        }
        return true;
    }

    static boolean isFinish=false;
    static public void FinishGame(final MainActivity mainActivity){
        //Map doldugunda veya harhangi biri oyunu kazandıgında çalışır
        isFinish=true;
        MainActivity.GameIsStop =true;
        Time.stopBackCountTime();
        Time.stopTime();
        SaveGame.ClearSavegame();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainActivity.FinishGame();
            }
        }, 2000);


    }

    private void setTrue(int i, int j, int scanmap_id,int color){
        //Kazanan kişinin taşları tik haline alınır
        int[] location;
        for (int scan=0; scan<Count; scan++){
            location=scanID.GetLocation(i,j,scan,scanmap_id);

            map.getMap()[location[0]][location[1]]=Map.True;
            map.True(map.getImageMap()[location[0]][location[1]],color);
        }
    }

    private static int WhoWin=-1;
    public static int getWhoWin(){
        return WhoWin;//Kimin kazandıgını döndürür
    }
    public static void resetWhoWin(){
        WhoWin=-1;
    }

    void ControlWhoWin(int i, int j, int scanmap_id){
        //Oyunu kimin kazandığını bulur
        int scan,pc=0,user=0;

        for (scan=0; scan<Count; scan++){
            if(scanID.scan(i,j,scan,map,scanmap_id)==Map.User){
                user++;
            }else if(scanID.scan(i,j,scan,map,scanmap_id)==Map.Pc){
                pc++;
            }
        }


        if(user>=Count){//Eger kullanıcının 5(Count) adet taş varsa oyunu kazanmıştır
            System.out.println("User Won..");
            FinishGame(mainActivity);
            setTrue(i,j,scanmap_id,Map.color_userwin);
            WhoWin=Map.User;
        }else if(pc>=Count){//Eger pc nin 5(Count) adet taş varsa oyunu kazanmıştır
            System.out.println("Pc Won..");
            FinishGame(mainActivity);
            setTrue(i,j,scanmap_id,Map.color_pcwin);
            WhoWin=Map.Pc;
        }
    }


    private int Edge_Control(int i, int j, int scanmap_id){
        //Taşların başını ve sonunu kontrol eder
        //İki köşeside boş ise 1, herhangi bir köşesi doluysa 0 döndürür

        if(scanID.control_for_putNear(i,j,map,scanmap_id)){
            if(scanID.scan(i,j,-1,map,scanmap_id)==Map.Empty){

                if(scanID.control_for_putNear2(i,j,finish_scan,map,scanmap_id)){
                    if(scanID.scan(i,j,finish_scan+1,map,scanmap_id)==Map.Empty){
                        return 1;
                    }
                }

            }
        }
        return 0;
    }

    private void PlayForUser(int i, int j, int scan,int edge, int scanmap_id){
        //Taranan 5li de kullanıcının taşlarınını engellemek için oynanabilecek yerleri bulur

        int[] locations;
        if(scanID.scan(i,j,scan,map,scanmap_id)==Map.Empty){
            if(ControlLocationForWin(i,j,scanmap_id,Definitions.forUser)){

                locations=scanID.GetLocation(i,j,scan,scanmap_id);

                PutCriticalPoint(locations[0],locations[1],i,j,edge, user_number,scanmap_id,0);
            }

        }else if(scanID.scan(i,j,scan,map,scanmap_id)==Map.Pc){

            //Eger taranan 5lide bilgisayarın taşı bulunursa en başa bakılır, o nokta uygunsa kaydedilir
            if(scanID.control_for_putNear(i,j,map,scanmap_id))
                if(scanID.scan(i,j,-1,map,scanmap_id)==Map.Empty){
                    if(ControlLocationForWin(i,j,scanmap_id,Definitions.forUser)){

                        locations=scanID.PutNear(i,j,scanmap_id);
                        PutCriticalPoint(locations[0],locations[1],i,j,edge, user_number,scanmap_id,0);
                    }
                }
        }

    }
    private void PlayForPc(int i, int j, int scan,int edge, int scanmap_id){
        //Taranan 5li de kazanmak için uygun yerleri bulur

        int[] locations;

        if(scanID.scan(i,j,scan,map,scanmap_id)==Map.Empty){
            if(ControlLocationForWin(i,j,scanmap_id,Definitions.forPc)){

                locations=scanID.GetLocation(i,j,scan,scanmap_id);
                PutCriticalPoint(locations[0],locations[1],i,j,edge, pc_number,scanmap_id,1);

            }

        }else if(scanID.scan(i,j,scan,map,scanmap_id)==Map.User){
            //Eger taranan 5lide kullanıcının taşı bulunursa en başa bakılır, o nokta uygunsa kaydedilir
            if(scanID.control_for_putNear(i,j,map,scanmap_id))

                if(scanID.scan(i,j,-1,map,scanmap_id)==Map.Empty){
                    if(ControlLocationForWin(i,j,scanmap_id,Definitions.forPc)){

                        locations=scanID.PutNear(i,j,scanmap_id);
                        PutCriticalPoint(locations[0],locations[1],i,j,edge, pc_number,scanmap_id,1);

                    }
                }
        }
    }
    int finish_scan=0;
    private void CountStone(int i,int j, int scanmap_id){
        //Taranan 5lide ki oynanmış taş sayısını hesaplar

        int scan;

        for (scan=0; scan<Count; scan++){

            if(scanID.scan(i,j,scan,map,scanmap_id)==Map.User){//Kullanıcı taşlarını sayar
                finish_scan=scan;
                user_number++;
            }
            else if(scanID.scan(i,j,scan,map,scanmap_id)==Map.Pc){//Pc taşlarını sayar
                finish_scan=scan;
                pc_number++;
            }
        }

    }

    private void scan(int i, int j, int scanmap_id){
        //Haritayı Tarayıp Kritik Noktaları Bulur
        user_number =0;
        pc_number =0;
        int scan;
        int edge;

        if(scanID.control_for_scan(i,j,map,scanmap_id)){//Taranacak Bölge Uygun İse

            ControlWhoWin(i,j,scanmap_id);
            CountStone(i,j,scanmap_id);
            edge=Edge_Control(i,j,scanmap_id);

            for (scan=0; scan<Count; scan++){

                if(user_number >=1){//Eger taranan 5lide bir ve birden fazla kullanıcı taşı varsa kritik noktalara bakar
                    PlayForUser(i,j,scan,edge,scanmap_id);
                }
                if(pc_number >=1){//Eger taranan 5lide bir ve birden fazla pc taşı varsa kritik noktalara bakar
                    PlayForPc(i,j,scan,edge,scanmap_id);
                }
            }
        }
    }

    MainActivity mainActivity;
    Wander(int i,int j,Map map, ArrayList<int[]> CriticalPoints,MainActivity mainActivity, Context context){

        //Gerekli set'ler yapılır
        this.mainActivity=mainActivity;
        this.map=map;
        this.Move=MainActivity.Move;
        this.Count=MainActivity.Count;
        this.context=context;
        this.CriticalPoints=CriticalPoints;


        //Taranma şekilleri bir diziye aktarılır(yukarı,asagı,sag,sol ve caprazlara bakmak için
        int[] play_array = new int[8];
        play_array[0]=Definitions.JPlus;
        play_array[1]=Definitions.JMinus;
        play_array[2]=Definitions.IPlus;
        play_array[3]=Definitions.IMinus;
        play_array[4]=Definitions.IPlusJPlus;
        play_array[5]=Definitions.IMinusJMinus;
        play_array[6]=Definitions.IPlusJMinus;
        play_array[7]=Definitions.IMinusJPlus;

        //Tarama sırasının randomlu olması, her oyunda farklı stratejiler doguracak
        //Tarama sırası randomlu bir şekilde yapılır
        for (int s=1; s<=8;s++){
            int random,play=0;
            while (play==0){//eger atılan random daha öncede atılmış ise tekrar random atılır
                random=setRandom.getRand(0,8);
                play=play_array[random];
                play_array[random]=0;

            }
            scan(i,j,play);
        }


    }

    private void Save(int[] positions){
        //Kritik Noktaları Diziye Kaydeder
        positions=new Weights().setWeight(positions, Count, pc_number, user_number);

        if(positions!=null && positions[2]!=0){
            positions = PutNewArray(positions);
            CriticalPoints.add(positions);
        }

    }


    private int[] PutNewArray(int[] positions){
        //Gelen Diziyi Yeni Bir Diziye Aktarır
        int[] array= new int[10];
        array[0]=positions[0];
        array[1]=positions[1];
        array[2]=positions[2];
        array[3]=positions[3];
        array[4]=positions[4];
        array[5]=positions[5];
        array[6]=positions[6];
        array[7]=positions[7];
        array[8]=positions[8];
        array[9]=positions[9];

        return array;
    }

    private void PutCriticalPoint(int i, int j, int scani, int scanj, int edge, int move_num, int scanmap_id, int who){
        //Kritik Noktanın Bilgilerini Kaydeder
        ControlRandomPlay=false;
        positions[0]=i;//x noktası
        positions[1]=j;//y noktası
        positions[2]=0;//Noktanın agırlıgı
        positions[3]=scani;//Taranan x noktası
        positions[4]=scanj;//Taranan y noktası
        positions[5]=scanmap_id;//Tarama id'si(yukarı,asagı,sag,sol,capraz)
        positions[6]=move_num;//Oynanmış taş sayısı
        positions[8]=who;//Kim için oynandıgı(Kullanıcıcyı engelleme||Kazanma)
        positions[9]=edge;//Köşeleri boşmu dolumu

        Save(positions);
    }

    ArrayList<int[]> getSavePos(){
        return CriticalPoints;
    }

}
