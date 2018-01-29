package com.mycolorstronger.adamoglu.mycolorstronger;

public class ScanID {
    //Bütün tarama yöntemleri burada bulunur

    public int scan(int i, int j, int scan,Map map,int id){
        //seçilen noktanın 5 erli şekilde kenralarının tarar
        try {
            switch (id){
                case 1:return j_plus(i,j,map,scan);//saga
                case 2:return j_minus(i,j,map,scan);//sola
                case 3:return i_plus(i,j,map,scan);//asagı
                case 4:return i_minus(i,j,map,scan);//yukarı
                case 5:return j_plus_and_i_plus(i,j,map,scan);//sag alt capraz
                case 6:return j_minus_and_i_minus(i,j,map,scan);//sol üst capraz
                case 7:return i_plus_and_j_minus(i,j,map,scan);//sol alt capraz
                case 8:return j_plus_and_i_minus(i,j,map,scan);//sag üst capraz
            }
        }catch (Exception ignored){}

        return -1;
    }

    public boolean control_for_scan(int i, int j, Map map, int id){
        //Secilenen noktanın taranabilrligini belirler, mapın dışını taramaması için

        int Count=MainActivity.Count;
        i=i+1;
        j=j+1;

        switch (id){
            case 1:return j+(Count)<=map.getMap()[0].length+1;
            case 2:return j-(Count)>=0;
            case 3:return i+(Count)<=map.getMap().length+1;
            case 4:return i-(Count)>=0;
            case 5:return i+(Count)<=map.getMap().length+1 && j+(Count)<=map.getMap()[0].length+1;
            case 6:return i-(Count)>=0 && j-(Count)>=0 ;
            case 7:return i+(Count)<=map.getMap().length+1 && j-(Count)>=0;
            case 8:return j+(Count)<=map.getMap()[0].length+1 && i-(Count)>=0;
        }

        return false;
    }

    public boolean control_for_putNear(int i, int j, Map map, int id){
        //secilen noktanın kenarlarının oynanablirliğini kontrol eder
        i=i+1;
        j=j+1;

        switch (id){
            case 1:return j>1;
            case 2:return j<map.getMap()[0].length;
            case 3:return i>1;
            case 4:return i<map.getMap().length;
            case 5:return i>1 && j>1;
            case 6:return i<map.getMap().length && j<map.getMap()[0].length;
            case 7:return i>1 && j<map.getMap()[0].length;
            case 8:return i<map.getMap().length && j>1;
        }

        return false;
    }

    public boolean control_for_putNear2(int i, int j, int scan, Map map, int id){
        //taranmakta olan noktanın kenarlarının oynanablirliğini kontrol eder
        i=i+1;
        j=j+1;

        scan=scan+1;

        switch (id){
            case 1:return j+scan<map.getMap()[0].length;
            case 2:return j-scan>1;
            case 3:return i+scan<map.getMap().length;
            case 4:return i-scan>1;
            case 5:return i+scan<map.getMap().length && j+scan<map.getMap()[0].length;
            case 6:return i-scan>1 && j-scan>1;
            case 7:return i+scan<map.getMap().length && j-scan>1;
            case 8:return i-scan>1 && j+scan<map.getMap()[0].length;
        }

        return false;
    }

    public int[] GetLocation(int i, int j, int scan, int scanmap_id){
        //Taranan noktayı,taranma id'sini ve taranma sayısını alıp bulunan noktanın kordinatlarını verir
        int[] location=new int[2];
        switch (scanmap_id){
            case 1:
                location[0]=i;
                location[1]=j+scan;
                return location;
            case 2:
                location[0]=i;
                location[1]=j-scan;
                return location;
            case 3:
                location[0]=i+scan;
                location[1]=j;
                return location;
            case 4:
                location[0]=i-scan;
                location[1]=j;
                return location;
            case 5:
                location[0]=i+scan;
                location[1]=j+scan;
                return location;
            case 6:
                location[0]=i-scan;
                location[1]=j-scan;
                return location;
            case 7:
                location[0]=i+scan;
                location[1]=j-scan;
                return location;
            case 8:
                location[0]=i-scan;
                location[1]=j+scan;
                return location;
        }

        return location;
    }

    public int[] PutNear(int i, int j, int id){
        //Verilen noktanın taranma id'sine göre bir yanını verir
        int[] location=new int[2];
        switch (id){
            case 1:
                location[0]=i;
                location[1]=j-1;
                return location;
            case 2:
                location[0]=i;
                location[1]=j+1;
                return location;
            case 3:
                location[0]=i-1;
                location[1]=j;
                return location;
            case 4:
                location[0]=i+1;
                location[1]=j;
                return location;
            case 5:
                location[0]=i-1;
                location[1]=j-1;
                return location;
            case 6:
                location[0]=i+1;
                location[1]=j+1;
                return location;
            case 7:
                location[0]=i-1;
                location[1]=j+1;
                return location;
            case 8:
                location[0]=i+1;
                location[1]=j-1;
                return location;
        }

        return location;
    }

    private int j_plus(int i, int j,Map map, int scan){
        return map.getMap()[i][j+scan];
    }
    private int j_minus(int i, int j,Map map, int scan){
        return map.getMap()[i][j-scan];
    }
    private int i_plus(int i, int j,Map map, int scan){
        return map.getMap()[i+scan][j];
    }
    private int i_minus(int i, int j,Map map, int scan){
        return map.getMap()[i-scan][j];
    }
    private int j_plus_and_i_plus(int i, int j,Map map, int scan){return map.getMap()[i+scan][j+scan];}
    private int j_minus_and_i_minus(int i, int j,Map map, int scan){return map.getMap()[i-scan][j-scan];}
    private int i_plus_and_j_minus(int i, int j,Map map, int scan){return map.getMap()[i+scan][j-scan];}
    private int j_plus_and_i_minus(int i, int j,Map map, int scan){return map.getMap()[i-scan][j+scan];}
}
