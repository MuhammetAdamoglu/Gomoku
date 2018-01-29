package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Map{

    public static final int Empty=0;
    public static final int User=1;
    public static final int Pc=2;
    public static final int True=3;
    public static final int Selected=4;

    public Context context;
    public int x=0,y=0;
    int[][] map;
    ImageView[][] ımageMap;
    private int [] locale = new int[2];
    private LinearLayout linearLayout;
    ConstraintLayout Actionbar;
    User user;
    Pc pc;
    MainActivity mainActivity;

    Map(Context context, MainActivity mainActivity, LinearLayout linearLayout, Pc pc, ConstraintLayout Actionbar) {

        user=new User();
        this.mainActivity=mainActivity;
        this.Actionbar=Actionbar;
        this.pc=pc;
        this.context=context;
        this.linearLayout=linearLayout;
    }


    private void CreateMatrix(int x, int y){
        //Gerekli matrisler oluşturulur
        map = new int[x][y];//Kimin oynadıgı bilgilerini tutmak için oluşturuldu
        ımageMap=new ImageView[x][y];//Her butonun referansını tutmak için oluşturuldu
        System.out.println("Created Matrix..");
    }
    public int[][] getMap(){
        return map;
    }
    public ImageView[][] getImageMap(){
        return ımageMap;
    }

    public void Empty(ImageView ımageView){
        //Seçilen noktalra boş atar, sadece ilk map oluşturulken kullanılır
        ımageView.setImageResource(R.drawable.img_1);
        ımageView.setColorFilter(color_empty);
    }

    public void Select(ImageView ımageView){
        //Kullanıcının seçtiği noktayı göstermek için kullanılır
        ımageView.setImageResource(R.drawable.img_1);
        ımageView.setColorFilter(color_user);
    }

    public void True(ImageView ımageView,int color){
        //Oyun kazanılınca kazanılan noktaya tik koymak için kullanılır
        ımageView.setImageResource(R.drawable.img_3);
        ımageView.setColorFilter(color);
    }

    public void User(ImageView ımageView){
        //Kullanıcının oynadıgı yeri belirlemik için kullanılır
        ımageView.setImageResource(R.drawable.img_2);
        ımageView.setColorFilter(color_user);
    }

    public void Pc(ImageView ımageView){
        //PC'nin oynadıgı yeri belirlemik için kullanılır
        ımageView.setImageResource(R.drawable.img_2);
        ımageView.setColorFilter(color_pc);
    }

    public int[] getLocale(ImageView ımageView){
        //Gelen butonun x ve y kordinatlarını bir dizi içerisinde döndürür
        String string = (String) ımageView.getTag();
        String[] parts = string.split("-");

        locale[0]=Integer.parseInt(parts[0]);
        locale[1]=Integer.parseInt(parts[1]);

        return locale;
    }



    private void CreateListeners(final ImageView ımageView){
        //Gelen buton için listener oluşturulur
        ımageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.playUser(context,Map.this,mainActivity,ımageView);
            }
        });
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public void CreateMap(boolean newgame){
        //Map oluşturulur
        int[][] savedmap=SaveGame.getsaveGame();

        int width = linearLayout.getWidth();//linearlayoutun eni
        int height = linearLayout.getHeight();//linearlayoutun boyu

        int n=dpToPx(35);//butonların büyüklüğü

        //Ekran için en uygun buton boyu aranır
        while (width%n!=0 && height%n!=0){
            n--;
        }
        y=(width/n);
        x=(height/n);


        CreateMatrix(x,y);//Matrisler üretilir

        //x sayısı kadar buton, y sayısı kadar linearlayout üretilir. Butonlar linearlayouta yatay şeklinde yerleştirili+
        //+ve o layoutlarda dikey şeklinde başka bir linearlayouta aktarılır
        for(int i=0;i<x;i++) {
            LinearLayout yeni = new LinearLayout(context);
            yeni.setGravity(1);

            LinearLayout.LayoutParams param= new LinearLayout.LayoutParams(n,n);

            for(int j=0; j<y;j++) {

                final ImageView ımageView= new ImageView(context);

                yeni.addView(ımageView,param);

                ımageView.setTag((i)+"-"+(j));//Butonların kordinatını bilmek için tag lerine x ve y yazılır


                if(!newgame){//Yeni oyunsa
                    //bütün butonlara boş atılır
                    map[i][j]=Empty;
                    ımageMap[i][j]=ımageView;

                    Empty(ımageView);
                }else {//Devam eden kayıtlı bir oyunsa
                    //kayıtlı olan matrise göre map oluşturulur
                    map[i][j]=savedmap[i][j];
                    ımageMap[i][j]=ımageView;

                    if(savedmap[i][j]==User)
                        User(ımageView);
                    else if(savedmap[i][j]==Pc)
                        Pc(ımageView);
                    else if(savedmap[i][j]==Selected){
                        map[i][j]=Empty;
                        Empty(ımageView);}
                    else
                        Empty(ımageView);
                }
                //Butonlar için lisstener oluşturulur
                CreateListeners(ımageView);

            }

            linearLayout.addView(yeni);
        }

        System.out.println("Created Map..");

    }



    //Butolar için renkler verilir
    static int color_empty=Color.parseColor("#f5f5f5");

    static int color_user =Color.parseColor("#E53935");
    static int color_user_lastgame=Color.parseColor("#C62828");
    static int color_userwin =Color.parseColor("#B71C1C");

    static int color_pc =Color.parseColor("#00897B");
    static int color_pc_lastgame=Color.parseColor("#00695C");
    static int color_pcwin =Color.parseColor("#004D40");

    public static void setColor(int color_user, int color_user_lastgame, int color_userwin, int color_pc, int color_pc_lastgame, int color_pcwin){

        Map.color_user =color_user;
        Map.color_user_lastgame =color_user_lastgame;
        Map.color_userwin =color_userwin;

        Map.color_pc =color_pc;
        Map.color_pc_lastgame =color_pc_lastgame;
        Map.color_pcwin =color_pcwin;
    }

}
