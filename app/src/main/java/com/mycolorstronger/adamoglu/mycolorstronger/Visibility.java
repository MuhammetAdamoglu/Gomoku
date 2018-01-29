package com.mycolorstronger.adamoglu.mycolorstronger;

import android.support.constraint.ConstraintLayout;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;


public class Visibility {
    //Menü Oyun ve Bitiş Ekranı arasında geçişler için kullanılır

    static void setVisibility_Menu(int visibility,Animation animation,ConstraintLayout Menu, TextView Play,TextView Contunue){

        Menu.setAnimation(animation);
        Menu.setVisibility(visibility);

        Play.setVisibility(visibility);
        Contunue.setVisibility(visibility);
    }


    static void setVisibility_FinishMenu(int visibility, Animation animation, ConstraintLayout FinishMenu, ImageView PlayAgain, ImageView GoToMenu,TextView win,TextView scorestag, TextView scores, TextView gametime,TextView played){

        FinishMenu.setAnimation(animation);
        FinishMenu.setVisibility(visibility);

        PlayAgain.setVisibility(visibility);
        GoToMenu.setVisibility(visibility);

        win.setVisibility(visibility);
        scorestag.setVisibility(visibility);
        scores.setVisibility(visibility);
        gametime.setVisibility(visibility);
        played.setVisibility(visibility);
    }

}
