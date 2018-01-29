package com.mycolorstronger.adamoglu.mycolorstronger;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        Time.stopBackCountTime();
        Time.stopTime();
        //Oyun durdugunda veya oyundan cıkıldıysa oyun kaydedilir
        if(Menu.getVisibility()==View.GONE && FinisMenu.getVisibility()==View.GONE){//Kayıt olması için menü ve bitiş ekranı olmaması lagzım, yani oyun esnasında olunmalı
            if(!Wander.isFinish){//Eger oyun bitmediyse
                SaveGame.saveGame(map.getMap());//Kaydet
            }
        }
        super.onPause();
    }


    Animation FadeIn(){
        //Giriş animasyonu
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        return fadeIn;
    }

    Animation FadeOut(){
        //Cıkıs animasyonu
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setDuration(1000);

        return fadeOut;
    }

    Animation Animate(){
        //Animasyon

        Animation fadeIn = FadeIn();

        Animation fadeOut = FadeOut();
        fadeOut.setStartOffset(1200);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        return animation;
    }

    private void AnimateListener(Animation animation, final boolean newgame){
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //Animasyon bitince

                GameIsStop=false;

                //İlk kimin başlayacagı random ile seçilir, ilk pc ise pc random ile bir nokta oynar
                if(!newgame)
                if(setRandom.getRand(0,2)==0){
                    PlayPc(getApplicationContext(),0,0);
                    System.out.println("First Gamer Is PC..");
                }else {
                    System.out.println("First Gamer Is USER..");
                }

                //Süreler başlatılır
                Time.startBackCountTime();
                Time.startTime();

                setNativeBarColor(R.color.color4);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void PlayPc(Context context,int i,int j){
        //Bilgisayar oynar
        if(!GameIsStop)//Oyun durmadıysa
            pc.Play(map,i,j,context);
    }

    public void FinishGame(){
        //Oyun bittiginde
        System.out.println("Game Finished..");

        FinishMenu.Show();

        linearLayout.setVisibility(View.GONE);

        FinisMenu.setAnimation(FadeIn());

        FinisMenu.setVisibility(View.VISIBLE);
        button_PlayAgain.setVisibility(View.VISIBLE);
        button_Exit.setVisibility(View.VISIBLE);
        win.setVisibility(View.VISIBLE);
        scorestag.setVisibility(View.VISIBLE);
        scores.setVisibility(View.VISIBLE);
        gametime.setVisibility(View.VISIBLE);
        played.setVisibility(View.VISIBLE);

        //butonlar eklenir
        button_PlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Visibility.setVisibility_FinishMenu(View.GONE,Animate(),FinisMenu, button_PlayAgain, button_Exit,win,scorestag,scores,gametime,played);
                Play.callOnClick();//Tekrar oynamak için
            }
        });


        button_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(-1);
            }
        });

        setNativeBarColor(R.color.color1);

    }


    Map map;
    Pc pc;

    static int Count=5;
    static int Move=3;
    static boolean GameIsStop =true;
    public static int whoPlay;

    LinearLayout linearLayout;
    TextView Play,Continued;

    ConstraintLayout Menu,FinisMenu,Actionbar;

    ImageView button_PlayAgain, button_Exit;

    TextView textView_Score,textView_Time,textView_Timer;

    TextView win,scorestag,scores,gametime,played;

    MainActivity mainActivity=this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Full ekran için
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /////////////////

        setContentView(R.layout.activity_main);

        setNativeBarColor(R.color.color1);


        System.out.println("Game Entered..");

        linearLayout = findViewById(R.id.LinearLayout);

        Menu = findViewById(R.id.Menu);
        Play = findViewById(R.id.textView_NewGame);
        Continued = findViewById(R.id.textView_Continued);

        Actionbar =findViewById(R.id.ActionBar);

        FinisMenu=findViewById(R.id.FinishMenu);
        button_PlayAgain = findViewById(R.id.button_restart);
        button_Exit = findViewById(R.id.button_exit);

        textView_Score=findViewById(R.id.textView_Score);
        textView_Time=findViewById(R.id.textView_Time);
        textView_Timer=findViewById(R.id.textView_Timer);

        win=findViewById(R.id.textView_win);
        scorestag=findViewById(R.id.textView_tagscore);
        scores=findViewById(R.id.textView_scores);
        gametime=findViewById(R.id.textView_gametime);
        played=findViewById(R.id.textView_played);

        Menu.setVisibility(View.VISIBLE);


        //Gerekli sınıflar üretiliyor
        new Score(textView_Score);
        new Time(textView_Time,textView_Timer,this,getApplicationContext(),20);
        new FinishMenu(win,scores,gametime,played,getApplicationContext());
        new SaveGame(getApplicationContext());


        if(SaveGame.ControlData()){//Kayıtlı oyun varsa devam eet butonu aktif edilir
            Continued.setVisibility(View.VISIBLE);
        }else {//pasif edilir
            Continued.setVisibility(View.GONE);
        }
        


        //Oyna butonu
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartGame(false);
            }
        });

        //Devam et butonu
        Continued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartGame(true);

                //Kayıtlı olan süre ve skor bilgileri set edilir
                Score.addScore(SaveGame.getScore(),1);
                Time.setTime(SaveGame.getGametime());

                if(SaveGame.getWhoplay()==Map.Pc){//Kayıtlı oyun en son pc de kaldıysa pc oynayacak
                    PlayPc(getApplicationContext(),0,0);
                }
            }
        });


    }

    public void setNativeBarColor(int ResourceColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(ContextCompat.getColor(this, ResourceColor));
        }
    }

    void StartGame(boolean newgame){

        linearLayout.setVisibility(View.VISIBLE);

        Actionbar.setVisibility(View.GONE);
        Actionbar.setAnimation(FadeIn());
        Actionbar.setVisibility(View.VISIBLE);

        Animation animation=Animate();
        Visibility.setVisibility_Menu(View.GONE,animation,Menu,Play,Continued);

        linearLayout.removeAllViews();

        //Pc nin yapay zekası üretilir
        pc= new Pc(mainActivity);
        //Harita üretilir
        map=new Map(getApplicationContext(),mainActivity,linearLayout,pc, Actionbar);
        //Harita oluşturulur
        map.CreateMap(newgame);

        //Skor ve süreler resetlenir
        Score.resetScore();
        Time.resetBackCountTime();
        Time.resetTime();
        //Hamle sayısı resetlenir
        Control_FullMap.Reset(map);


        System.out.println("Game Started..");

        //Animasyon dinleyici oluşturulur
        AnimateListener(animation,newgame);
    }
}
