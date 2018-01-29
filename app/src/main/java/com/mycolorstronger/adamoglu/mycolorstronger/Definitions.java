package com.mycolorstronger.adamoglu.mycolorstronger;


public class Definitions {
    //Burada ID ler saklanır

    static final int forPc=0;
    static final int forUser=1;

    public final static int JPlus=1;
    public final static int JMinus=2;
    public final static int IPlus=3;
    public final static int IMinus=4;
    public final static int IPlusJPlus=5;
    public final static int IMinusJMinus=6;
    public final static int IPlusJMinus=7;
    public final static int IMinusJPlus=8;



    //Hangi sitrateşinin daha önemli oldugu burda belirlenir. 12 en önemsiz, 1 ise en önemli

        //////////////PC//////////////                  /////////////USER///////////////
    public final static int PC_Weight_4=1;          public final static int User_Weight_4=2;

    public final static int PC_Weight_3=7;          public final static int User_Weight_3=8;
    public final static int PC_Weight_3_Edge=3;     public final static int User_Weight_3_Edge=4;

    public final static int PC_Weight_2=9;          public final static int User_Weight_2=10;
    public final static int PC_Weight_2_Edge=5;     public final static int User_Weight_2_Edge=6;

    public final static int PC_Weight_1=13;         public final static int User_Weight_1=14;
    public final static int PC_Weight_1_Edge=11;    public final static int User_Weight_1_Edge=12;

}
