package com.mycolorstronger.adamoglu.mycolorstronger;



public class Weights {

    int[] setWeight(int[] positions,int Count, int pc_move_num, int user_move_num){

        //Yapay zeka stratejisine göre kritik noktalara bir agırlık verilir

        if(pc_move_num==Count-1){
            //PC 4 Tane

            positions[2]=Definitions.PC_Weight_4;
            return positions;

        }else if(pc_move_num==Count-2){

            if(positions[9]==1){
                //PC 3 Tane Kenarlar Boş
                positions[2]=Definitions.PC_Weight_3_Edge;
                return positions;
            }else {
                //PC 3 Tane
                positions[2]=Definitions.PC_Weight_3;
                return positions;
            }
        }else if(pc_move_num==2){
            if(positions[9]==1){
                //PC 2 Tane Kenarlar Boş
                positions[2]=Definitions.PC_Weight_2_Edge;
                return positions;
            }else {
                //PC 2 Tane
                positions[2]=Definitions.PC_Weight_2;
                return positions;
            }

        }else if(pc_move_num==1){
            if(positions[9]==1){
                //PC 1 Tane Kenarlar Boş
                positions[2]=Definitions.PC_Weight_1_Edge;
                return positions;
            }else {
                //PC 1 Tane
                positions[2]=Definitions.PC_Weight_1;
                return positions;
            }
        }

        ////////////////////////////////////////////

        if(user_move_num==Count-1){
            //Kullanıcı 4 Tane

            positions[2]=Definitions.User_Weight_4;
            return positions;

        }else if(user_move_num==Count-2){

            if(positions[9]==1){
                //Kullanıcı 3 Tane Kenarlar Boş
                positions[2]=Definitions.User_Weight_3_Edge;
                return positions;
            }else {
                //Kullanıcı 3 Tane
                positions[2]=Definitions.User_Weight_3;
                return positions;
            }
        }else if(user_move_num==2){
            if(positions[9]==1){
                //Kullanıcı 2 Tane Kenarlar Boş
                positions[2]=Definitions.User_Weight_2_Edge;
                return positions;
            }else {
                //Kullanıcı 2 Tane
                positions[2]=Definitions.User_Weight_2;
                return positions;
            }
        }else if(user_move_num==1){
            if(positions[9]==1){
                //PC 1 Tane Kenarlar Boş
                positions[2]=Definitions.User_Weight_1_Edge;
                return positions;
            }else {
                //PC 1 Tane
                positions[2]=Definitions.User_Weight_1;
                return positions;
            }
        }

        return null;
    }
}
