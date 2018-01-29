package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;


public class Pc {

    Context context;
    int Count,Move;
    MainActivity mainActivity;

    Pc(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }

    public void Play(Map map,int i, int j,Context context){

        this.context=context;
        this.Count=MainActivity.Count;
        this.Move=MainActivity.Move;
        ScanMap(map,i,j);
    }


    ImageView save_for_color;
    private void ChangeColor(ImageView ımageView){
        //Pc'nin Son Hamlesini Gösterir
        if(save_for_color!=null)
            save_for_color.setColorFilter(Map.color_pc);

        ımageView.setColorFilter(Map.color_pc_lastgame);

        save_for_color=ımageView;


    }


    int[]played=new int[3];
    public void Put(final int x, final int y, final Map map){

        if(!MainActivity.GameIsStop){//Oyun devam ediyorsa
            map.getMap()[x][y]=Map.Pc;//Haritaya pcnin id si atılır
            map.Pc(map.getImageMap()[x][y]);//atılan yerin buton resmi degistirilir

            ChangeColor(map.getImageMap()[x][y]);//Son atılan yer oldugundan rengi farklı yapılır

            Time.startBackCountTime();//Sıra kullanıcıda oldugundan süre tekrar baslatılır

            if(CriticalPoints.size()!=0 && wander!=null)//diziler bos degilse
                wander.ControlWhoWin(played[0],played[1],played[2]);//kazanıp kazanmadıgını kontrol eder

            System.out.println("Pc Puted x:"+x+" y:"+y+"..");
            MainActivity.whoPlay=Map.User;//Sıra kulanıcıda oldugunu bildirir

            if(Control_FullMap.DoIt(context,map)){//Haritanın dolup dolmadıgını klontrol eder
                Wander.FinishGame(mainActivity);//Dolmuş ise oyunu bitirir
            }
        }

    }

    ArrayList<int[]> CriticalPoints = new ArrayList<>();
    Wander wander;

    private void ScanMap(Map inmap,int i,int j){
        int[][] map=inmap.getMap();

        //İlk Önce Kullanıcının Yaptığı Son hamle taranır
        wander=new Wander(i,j,inmap, CriticalPoints,mainActivity,context);
        CriticalPoints =wander.getSavePos();

        //Sonra diğer noktalar taranır
        for(i=0; i<map.length; i++){

            for(j=0; j<map[0].length;j++){

                if(map[i][j]== Map.User || map[i][j]== Map.Pc){//Eger pc veya kullanıcı hamlesi varsa
                    System.out.println("Checking x:"+i+" y:"+j+"..");
                    //O nokta kayıt edilir
                    wander=new Wander(i,j,inmap, CriticalPoints,mainActivity,context);
                    //Bulunan kritik nokta bir diziye aktarılır
                    CriticalPoints =wander.getSavePos();
                }
            }
        }

        System.out.println("Critical Points..\n");
        for (int k = 0; k< CriticalPoints.size(); k++)
            System.out.println("["+ CriticalPoints.get(k)[0]+"]["+ CriticalPoints.get(k)[1]+"]  Size:"+ CriticalPoints.size()+"  Weight:"+ CriticalPoints.get(k)[2]+"  ["+ CriticalPoints.get(k)[3]+"]["+ CriticalPoints.get(k)[4]+"]"+"  ControlScanID:"+ CriticalPoints.get(k)[5]+"  Stone:"+ CriticalPoints.get(k)[6]+"  Pc:"+ CriticalPoints.get(k)[8]+"  Edge:"+ CriticalPoints.get(k)[9]+"\n");

        if(CriticalPoints.size()!=0)
            System.out.println("--------------------------------------------------------------");


        //Eger kritik nokta varsa en uygun nokta aranır
        if(CriticalPoints.size()!=0)
            findPos(CriticalPoints,inmap);
        else//yoksa random atılır
            PutRandom(inmap);


        CriticalPoints.clear();

        System.out.println("Map Scanned..");

    }

    public void PutRandom(Map map){

        int x=setRandom.getRand(0,map.getMap().length);
        int y=setRandom.getRand(0,map.getMap()[0].length);

        if(map.getMap()[x][y]!=Map.Empty){//Eger random atılan yer boş degilse
            PutRandom(map);//tekrar random atılır
        }else {//boş ise oraya hamle yapılır
            Put(x,y,map);
            System.out.println("Pc Is Playing Random..");
        }

    }


    private int[] CalculatePoint(ArrayList<int[]> arrayList_mostweight,ArrayList<int[]> criticalPoints){
        //Agırlıgı wen yüksek olan noktalar arasından en uygunu seçilir

        ArrayList<int[]> arrayList_singles=new ArrayList<>();//Aynı noktadan birden fazla bulunan için o nokta teke indirilir

        boolean Control=false;

        //Benzersiz Noktalar hesaplanır
        for (int i=0; i<arrayList_mostweight.size();i++){//Agırlıgı en yüksek olan dizi taranır

            for (int j=0; j<arrayList_singles.size();j++){//Bulunan benzersiz noktalara bakılır

                if(arrayList_mostweight.get(i)[2]!=0)//Agrlıgı sıfır olan varsa es geçilir(0 olması demek, o dizinin boş olması demek)
                if(arrayList_mostweight.get(i)[0]==arrayList_singles.get(j)[0] && arrayList_mostweight.get(i)[1]==arrayList_singles.get(j)[1]){//iki dizinin x ye y lerinin eşitligine bakılır
                    Control=true;
                    break;
                }

            }

            if(!Control){//Eger aynı olan bulunmuş ise diziye bir tanesi kaydedilir
                arrayList_singles.add(arrayList_mostweight.get(i));
            }
            Control=false;
        }
        //////////////////////////////

        //Bulunan Benzersi dizilerden kaç adet oldugu hesaplanır
        int[] piece = new int[arrayList_singles.size()];

        for (int i=0; i<arrayList_singles.size();i++){//Benzersiz noktalar taranır

            for (int j=0; j<criticalPoints.size();j++){//

                if(arrayList_singles.get(i)[0]==criticalPoints.get(j)[0] && arrayList_singles.get(i)[1]==criticalPoints.get(j)[1]){
                    piece[i]++;
                }
            }
        }
        /////////////////////////////////////////////////////////

        System.out.println("Most Weights..\n");
        for (int s=0; s<arrayList_singles.size();s++){
            System.out.println(arrayList_singles.get(s)[0]+" "+arrayList_singles.get(s)[1]+" Piece:"+piece[s]+"\n");
        }

        //Aynı noktadan en cok bulunan yer hesaplanır
        int max=0,max_indis = 0;
        for (int counter = 0; counter < piece.length; counter++)
        {
            if (piece[counter] > max)
            {
                max=piece[counter];//max sayı bulunur
                max_indis=counter;//max sayının oldugu indis bulunur
            }
        }
        ///////////////////////////////////

        System.out.println( "\nToo Piece: ["+String.valueOf(arrayList_singles.get(max_indis)[0]+"]["+arrayList_singles.get(max_indis)[1])+"]  Piece:"+piece[max_indis]+"  Weight:"+arrayList_singles.get(max_indis)[2]+"\n\n");

        //bulunan yere kaydedilir
        played[0]=arrayList_singles.get(max_indis)[3];
        played[1]=arrayList_singles.get(max_indis)[4];
        played[2]=arrayList_singles.get(max_indis)[5];

        System.out.println("Scan Finished..");

        return arrayList_singles.get(max_indis);
    }


    private void findPos(ArrayList<int[]> criticalPoints,Map map){
        //En uygun kritik nokta aranır
        System.out.println("Searching Locations..");
        ArrayList<int[]> saveforcalculate=new ArrayList<>();
        int[] put;

        //Agırlıgı 1 olan en nemli olan oldugu için agırlıklar 1 den 14 de kadar taranır
        for (int weight=1; weight<=14; weight++){

            for (int i=0; i<criticalPoints.size(); i++){//Kritik noktalar taranır
                if(criticalPoints.get(i)[2]==weight){//Agırlıgı en yüksek olan alınır
                    saveforcalculate.add(criticalPoints.get(i));//agırlıgı en yüksek olanlar arasından +
                    //+ mantıklı olanı seçmek için bir diziye aktarılır
                }
            }

            if(saveforcalculate.size()!=0){//Dizi boş degilse
                put=CalculatePoint(saveforcalculate,criticalPoints);//Agırlıgı en yüksek olanlar arasından en uygunu seçilir
                Put(put[0],put[1],map);//bulunan noktaya hamle yapılır
                System.out.println("Location Found..");
                return;
            }
        }
        System.out.println("Location Not Found..");
        PutRandom(map);

    }


}
