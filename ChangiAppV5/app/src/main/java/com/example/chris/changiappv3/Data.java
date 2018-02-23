package com.example.chris.changiappv3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jingxuan on 30/11/2017.
 */

public class Data {
    public static final HashMap<String,Integer> locations=new HashMap<String,Integer>();
    public static final HashMap<Integer,String> locations2=new HashMap<Integer,String>();

    static {
        locations.put("Marina Bay Sands",0);
        locations.put("Singapore Flyer",1);
        locations.put("Vivo City",2);
        locations.put("Universal Studios Singapore",3);
        locations.put("Buddha Tooth Relic Temple",4);
        locations.put("Singapore Zoo",5);
        locations.put("Orchard Road",6);
        locations.put("Jurong Bird Park",7);
        locations.put("Singapore Botanic Gardens",8);
        locations.put("SUTD",9);

        locations2.put(0,"Marina Bay Sands");
        locations2.put(1,"Singapore Flyer");
        locations2.put(2,"Vivo City");
        locations2.put(3,"Universal Studios Singapore");
        locations2.put(4,"Buddha Tooth Relic Temple");
        locations2.put(5,"Singapore Zoo");
        locations2.put(6,"Orchard Road");
        locations2.put(7,"Jurong Bird Park");
        locations2.put(8,"Singapore Botanic Gardens");
        locations2.put(9,"SUTD");
    }
    //[mode transport (0:walk, 1: public transport, 2: taxi)][from][to]
    public static final HashMap<Integer, String> transportMode = new HashMap<Integer,String>();
    static{
        transportMode.put(0,"Walk");
        transportMode.put(1,"Public Transport");
        transportMode.put(2,"Taxi");
    }

    // number of key-value mappings in the map
    public static final int num_locations = locations.size();

    //to store info in the 3D Array
    public static final double[][][] costArray = new double[3][num_locations][num_locations];
    static{
        //walking cost
        costArray[0][0][1] = 0;
        costArray[0][0][2] = 0;
        costArray[0][0][3] = 0;
        costArray[0][0][4] = 0;
        costArray[0][0][5] = 0;
        costArray[0][0][6] = 0;
        costArray[0][0][7] = 0;
        costArray[0][0][8] = 0;
        costArray[0][0][9] = 0;

        costArray[0][1][0] = 0;
        costArray[0][1][2] = 0;
        costArray[0][1][3] = 0;
        costArray[0][1][4] = 0;
        costArray[0][1][5] = 0;
        costArray[0][1][6] = 0;
        costArray[0][1][7] = 0;
        costArray[0][1][8] = 0;
        costArray[0][1][9] = 0;

        costArray[0][2][0] = 0;
        costArray[0][2][1] = 0;
        costArray[0][2][3] = 0;
        costArray[0][2][4] = 0;
        costArray[0][2][5] = 0;
        costArray[0][2][6] = 0;
        costArray[0][2][7] = 0;
        costArray[0][2][8] = 0;
        costArray[0][2][9] = 0;

        costArray[0][3][0] = 0;
        costArray[0][3][1] = 0;
        costArray[0][3][2] = 0;
        costArray[0][3][4] = 0;
        costArray[0][3][5] = 0;
        costArray[0][3][6] = 0;
        costArray[0][3][7] = 0;
        costArray[0][3][8] = 0;
        costArray[0][3][9] = 0;

        costArray[0][4][0] = 0;
        costArray[0][4][1] = 0;
        costArray[0][4][2] = 0;
        costArray[0][4][3] = 0;
        costArray[0][4][5] = 0;
        costArray[0][4][6] = 0;
        costArray[0][4][7] = 0;
        costArray[0][4][8] = 0;
        costArray[0][4][9] = 0;

        costArray[0][5][0] = 0;
        costArray[0][5][1] = 0;
        costArray[0][5][2] = 0;
        costArray[0][5][3] = 0;
        costArray[0][5][4] = 0;
        costArray[0][5][6] = 0;
        costArray[0][5][7] = 0;
        costArray[0][5][8] = 0;
        costArray[0][5][9] = 0;

        costArray[0][6][0] = 0;
        costArray[0][6][1] = 0;
        costArray[0][6][2] = 0;
        costArray[0][6][3] = 0;
        costArray[0][6][4] = 0;
        costArray[0][6][5] = 0;
        costArray[0][6][7] = 0;
        costArray[0][6][8] = 0;
        costArray[0][6][9] = 0;

        costArray[0][7][0] = 0;
        costArray[0][7][1] = 0;
        costArray[0][7][2] = 0;
        costArray[0][7][3] = 0;
        costArray[0][7][4] = 0;
        costArray[0][7][5] = 0;
        costArray[0][7][6] = 0;
        costArray[0][7][8] = 0;
        costArray[0][7][9] = 0;

        costArray[0][8][0] = 0;
        costArray[0][8][1] = 0;
        costArray[0][8][2] = 0;
        costArray[0][8][3] = 0;
        costArray[0][8][4] = 0;
        costArray[0][8][5] = 0;
        costArray[0][8][6] = 0;
        costArray[0][8][7] = 0;
        costArray[0][8][9] = 0;

        costArray[0][9][0] = 0;
        costArray[0][9][1] = 0;
        costArray[0][9][2] = 0;
        costArray[0][9][3] = 0;
        costArray[0][9][4] = 0;
        costArray[0][9][5] = 0;
        costArray[0][9][6] = 0;
        costArray[0][9][7] = 0;
        costArray[0][9][8] = 0;

        //public transport cost
        costArray[1][0][1] = 0.83;
        costArray[1][0][2] = 1.18;
        costArray[1][0][3] = 1.18;
        costArray[1][0][4] = 0.88;
        costArray[1][0][5] = 1.88;
        costArray[1][0][6] = 1.07;
        costArray[1][0][7] = 1.89;
        costArray[1][0][8] = 1.33;
        costArray[1][0][9] = 1.78;

        costArray[1][1][0] = 0.83;
        costArray[1][1][2] = 1.26;
        costArray[1][1][3] = 1.23;
        costArray[1][1][4] = 0.98;
        costArray[1][1][5] = 1.96;
        costArray[1][1][6] = 0.97;
        costArray[1][1][7] = 1.87;
        costArray[1][1][8] = 1.29;
        costArray[1][1][9] = 1.81;

        costArray[1][2][0] = 1.18;
        costArray[1][2][1] = 1.26;
        costArray[1][2][3] = 0;
        costArray[1][2][4] = 0.98;
        costArray[1][2][5] = 2.11;
        costArray[1][2][6] = 1.16;
        costArray[1][2][7] = 1.81;
        costArray[1][2][8] = 1.37;
        costArray[1][2][9] = 1.81;

        costArray[1][3][0] = 4.03;
        costArray[1][3][1] = 4.03;
        costArray[1][3][2] = 2.00;
        costArray[1][3][4] = 3.98;
        costArray[1][3][5] = 4.99;
        costArray[1][3][6] = 3.16;
        costArray[1][3][7] = 1.85;
        costArray[1][3][8] = 3.37;
        costArray[1][3][9] = 3.81;

        costArray[1][4][0] = 0.88;
        costArray[1][4][1] = 0.98;
        costArray[1][4][2] = 0.98;
        costArray[1][4][3] = 0.98;
        costArray[1][4][5] = 1.91;
        costArray[1][4][6] = 0.87;
        costArray[1][4][7] = 1.75;
        costArray[1][4][8] = 1.23;
        costArray[1][4][9] = 1.72;

        costArray[1][5][0] = 1.96;
        costArray[1][5][1] = 1.89;
        costArray[1][5][2] = 1.99;
        costArray[1][5][3] = 1.99;
        costArray[1][5][4] = 1.91;
        costArray[1][5][6] = 1.78;
        costArray[1][5][7] = 1.83;
        costArray[1][5][8] = 1.65;
        costArray[1][5][9] = 1.96;

        costArray[1][6][0] = 1.07;
        costArray[1][6][1] = 0.97;
        costArray[1][6][2] = 1.16;
        costArray[1][6][3] = 1.37;
        costArray[1][6][4] = 0.87;
        costArray[1][6][5] = 1.78;
        costArray[1][6][7] = 1.88;
        costArray[1][6][8] = 0.97;
        costArray[1][6][9] = 1.72;

        costArray[1][7][0] = 1.89;
        costArray[1][7][1] = 1.87;
        costArray[1][7][2] = 1.81;
        costArray[1][7][3] = 1.85;
        costArray[1][7][4] = 1.75;
        costArray[1][7][5] = 1.83;
        costArray[1][7][6] = 1.72;
        costArray[1][7][8] = 1.61;
        costArray[1][7][9] = 1.99;

        costArray[1][8][0] = 1.29;
        costArray[1][8][1] = 1.23;
        costArray[1][8][2] = 1.37;
        costArray[1][8][3] = 3.37;
        costArray[1][8][4] = 1.23;
        costArray[1][8][5] = 1.81;
        costArray[1][8][6] = 0.97;
        costArray[1][8][7] = 1.65;
        costArray[1][8][9] = 1.78;

        costArray[1][9][0] = 1.78;
        costArray[1][9][1] = 1.81;
        costArray[1][9][2] = 1.81;
        costArray[1][9][3] = 3.81;
        costArray[1][9][4] = 1.72;
        costArray[1][9][5] = 1.93;
        costArray[1][9][6] = 2.35;
        costArray[1][9][7] = 1.99;
        costArray[1][9][8] = 1.78;

        //taxi cost
        costArray[2][0][1] = 4.32;
        costArray[2][0][2] = 8.30;
        costArray[2][0][3] = 8.74;
        costArray[2][0][4] = 5.32;
        costArray[2][0][5] = 22.48;
        costArray[2][0][6] = 14.23;
        costArray[2][0][7] = 29.93;
        costArray[2][0][8] = 16.80;
        costArray[2][0][9] = 26.08;

        costArray[2][1][0] = 3.22;
        costArray[2][1][2] = 7.96;
        costArray[2][1][3] = 8.40;
        costArray[2][1][4] = 4.76;
        costArray[2][1][5] = 19.40;
        costArray[2][1][6] = 10.61;
        costArray[2][1][7] = 25.84;
        costArray[2][1][8] = 13.37;
        costArray[2][1][9] = 23.20;

        costArray[2][2][0] = 6.96;
        costArray[2][2][1] = 7.84;
        costArray[2][2][3] = 3.22;
        costArray[2][2][4] = 4.98;
        costArray[2][2][5] = 21.48;
        costArray[2][2][6] = 9.38;
        costArray[2][2][7] = 20.31;
        costArray[2][2][8] = 10.78;
        costArray[2][2][9] = 27.70;

        costArray[2][3][0] = 8.50;
        costArray[2][3][1] = 9.38;
        costArray[2][3][2] = 4.54;
        costArray[2][3][4] = 6.52;
        costArray[2][3][5] = 23.68;
        costArray[2][3][6] = 10.49;
        costArray[2][3][7] = 21.55;
        costArray[2][3][8] = 11.89;
        costArray[2][3][9] = 28.93;

        costArray[2][4][0] = 4.98;
        costArray[2][4][1] = 4.76;
        costArray[2][4][2] = 6.42;
        costArray[2][4][3] = 6.64;
        costArray[2][4][5] = 21.60;
        costArray[2][4][6] = 10.66;
        costArray[2][4][7] = 23.40;
        costArray[2][4][8] = 12.29;
        costArray[2][4][9] = 25.81;

        costArray[2][5][0] = 18.40;
        costArray[2][5][1] = 18.18;
        costArray[2][5][2] = 22.58;
        costArray[2][5][3] = 22.80;
        costArray[2][5][4] = 18.40;
        costArray[2][5][6] = 20.51;
        costArray[2][5][7] = 21.65;
        costArray[2][5][8] = 19.34;
        costArray[2][5][9] = 33.00;

        costArray[2][6][0] = 7.94;
        costArray[2][6][1] = 7.42;
        costArray[2][6][2] = 8.67;
        costArray[2][6][3] = 12.16;
        costArray[2][6][4] = 9.02;
        costArray[2][6][5] = 23.88;
        costArray[2][6][7] = 26.01;
        costArray[2][6][8] = 7.83;
        costArray[2][6][9] = 21.46;

        costArray[2][7][0] = 31.30;
        costArray[2][7][1] = 31.93;
        costArray[2][7][2] = 26.74;
        costArray[2][7][3] = 28.42;
        costArray[2][7][4] = 28.79;
        costArray[2][7][5] = 30.16;
        costArray[2][7][6] = 28.86;
        costArray[2][7][8] = 26.17;
        costArray[2][7][9] = 39.26;

        costArray[2][8][0] = 11.84;
        costArray[2][8][1] = 11.21;
        costArray[2][8][2] = 15.35;
        costArray[2][8][3] = 19.17;
        costArray[2][8][4] = 11.30;
        costArray[2][8][5] = 21.85;
        costArray[2][8][6] = 5.83;
        costArray[2][8][7] = 19.58;
        costArray[2][8][9] = 22.56;

        costArray[2][9][0] = 20.34;
        costArray[2][9][1] = 20.48;
        costArray[2][9][2] = 26.10;
        costArray[2][9][3] = 27.73;
        costArray[2][9][4] = 22.60;
        costArray[2][9][5] = 30.46;
        costArray[2][9][6] = 23.04;
        costArray[2][9][7] = 39.41;
        costArray[2][9][8] = 22.95;
    }

    //[mode transport (0:walk, 1: public transport, 2: taxi)][from][to]
    public static final int[][][] timeArray = new int[3][num_locations][num_locations];
    static {
        //walking time
        timeArray[0][0][1] = 14;
        timeArray[0][0][2] = 69;
        timeArray[0][0][3] = 76;
        timeArray[0][0][4] = 28;
        timeArray[0][0][5] = 269;
        timeArray[0][0][6] = 49;
        timeArray[0][0][7] = 271;
        timeArray[0][0][8] = 83;
        timeArray[0][0][9] = 183;

        timeArray[0][1][0] = 14;
        timeArray[0][1][2] = 81;
        timeArray[0][1][3] = 88;
        timeArray[0][1][4] = 39;
        timeArray[0][1][5] = 264;
        timeArray[0][1][6] = 45;
        timeArray[0][1][7] = 273;
        timeArray[0][1][8] = 79;
        timeArray[0][1][9] = 175;

        timeArray[0][2][0] = 69;
        timeArray[0][2][1] = 81;
        timeArray[0][2][3] = 12;
        timeArray[0][2][4] = 47;
        timeArray[0][2][5] = 270;
        timeArray[0][2][6] = 73;
        timeArray[0][2][7] = 210;
        timeArray[0][2][8] = 82;
        timeArray[0][2][9] = 244;

        timeArray[0][3][0] = 76;
        timeArray[0][3][1] = 88;
        timeArray[0][3][2] = 12;
        timeArray[0][3][4] = 55;
        timeArray[0][3][5] = 285;
        timeArray[0][3][6] = 88;
        timeArray[0][3][7] = 229;
        timeArray[0][3][8] = 199;
        timeArray[0][3][9] = 254;

        timeArray[0][4][0] = 28;
        timeArray[0][4][1] = 39;
        timeArray[0][4][2] = 47;
        timeArray[0][4][3] = 55;
        timeArray[0][4][5] = 264;
        timeArray[0][4][6] = 40;
        timeArray[0][4][7] = 245;
        timeArray[0][4][8] = 68;
        timeArray[0][4][9] = 197;

        timeArray[0][5][0] = 269;
        timeArray[0][5][1] = 264;
        timeArray[0][5][2] = 270;
        timeArray[0][5][3] = 285;
        timeArray[0][5][4] = 264;
        timeArray[0][5][6] = 253;
        timeArray[0][5][7] = 246;
        timeArray[0][5][8] = 224;
        timeArray[0][5][9] = 361;

        timeArray[0][6][0] = 49;
        timeArray[0][6][1] = 45;
        timeArray[0][6][2] = 73;
        timeArray[0][6][3] = 88;
        timeArray[0][6][4] = 40;
        timeArray[0][6][5] = 253;
        timeArray[0][6][7] = 229;
        timeArray[0][6][8] = 34;
        timeArray[0][6][9] = 195;

        timeArray[0][7][0] = 271;
        timeArray[0][7][1] = 273;
        timeArray[0][7][2] = 210;
        timeArray[0][7][3] = 229;
        timeArray[0][7][4] = 245;
        timeArray[0][7][5] = 246;
        timeArray[0][7][6] = 229;
        timeArray[0][7][8] = 199;
        timeArray[0][7][9] = 424;

        timeArray[0][8][0] = 83;
        timeArray[0][8][1] = 79;
        timeArray[0][8][2] = 82;
        timeArray[0][8][3] = 199;
        timeArray[0][8][4] = 68;
        timeArray[0][8][5] = 224;
        timeArray[0][8][6] = 34;
        timeArray[0][8][7] = 199;
        timeArray[0][8][9] = 230;

        timeArray[0][9][0] = 183;
        timeArray[0][9][1] = 175;
        timeArray[0][9][2] = 244;
        timeArray[0][9][3] = 254;
        timeArray[0][9][4] = 197;
        timeArray[0][9][5] = 361;
        timeArray[0][9][6] = 195;
        timeArray[0][9][7] = 424;
        timeArray[0][9][8] = 230;

        //public transport time
        timeArray[1][0][1] = 17;
        timeArray[1][0][2] = 24;
        timeArray[1][0][3] = 33;
        timeArray[1][0][4] = 18;
        timeArray[1][0][5] = 86;
        timeArray[1][0][6] = 27;
        timeArray[1][0][7] = 74;
        timeArray[1][0][8] = 46;
        timeArray[1][0][9] = 46;

        timeArray[1][1][0] = 17;
        timeArray[1][1][2] = 29;
        timeArray[1][1][3] = 38;
        timeArray[1][1][4] = 23;
        timeArray[1][1][5] = 87;
        timeArray[1][1][6] = 29;
        timeArray[1][1][7] = 76;
        timeArray[1][1][8] = 48;
        timeArray[1][1][9] = 51;

        timeArray[1][2][0] = 26;
        timeArray[1][2][1] = 31;
        timeArray[1][2][3] = 10;
        timeArray[1][2][4] = 19;
        timeArray[1][2][5] = 86;
        timeArray[1][2][6] = 30;
        timeArray[1][2][7] = 67;
        timeArray[1][2][8] = 46;
        timeArray[1][2][9] = 53;

        timeArray[1][3][0] = 35;
        timeArray[1][3][1] = 38;
        timeArray[1][3][2] = 10;
        timeArray[1][3][4] = 25;
        timeArray[1][3][5] = 96;
        timeArray[1][3][6] = 46;
        timeArray[1][3][7] = 82;
        timeArray[1][3][8] = 62;
        timeArray[1][3][9] = 69;

        timeArray[1][4][0] = 19;
        timeArray[1][4][1] = 24;
        timeArray[1][4][2] = 18;
        timeArray[1][4][3] = 27;
        timeArray[1][4][5] = 84;
        timeArray[1][4][6] = 28;
        timeArray[1][4][7] = 60;
        timeArray[1][4][8] = 53;
        timeArray[1][4][9] = 45;

        timeArray[1][5][0] = 84;
        timeArray[1][5][1] = 85;
        timeArray[1][5][2] = 85;
        timeArray[1][5][3] = 92;
        timeArray[1][5][4] = 83;
        timeArray[1][5][6] = 65;
        timeArray[1][5][7] = 87;
        timeArray[1][5][8] = 80;
        timeArray[1][5][9] = 97;

        timeArray[1][6][0] = 27;
        timeArray[1][6][1] = 29;
        timeArray[1][6][2] = 31;
        timeArray[1][6][3] = 51;
        timeArray[1][6][4] = 28;
        timeArray[1][6][5] = 64;
        timeArray[1][6][7] = 70;
        timeArray[1][6][8] = 40;
        timeArray[1][6][9] = 50;

        timeArray[1][7][0] = 72;
        timeArray[1][7][1] = 76;
        timeArray[1][7][2] = 68;
        timeArray[1][7][3] = 82;
        timeArray[1][7][4] = 60;
        timeArray[1][7][5] = 89;
        timeArray[1][7][6] = 69;
        timeArray[1][7][8] = 75;
        timeArray[1][7][9] = 96;

        timeArray[1][8][0] = 46;
        timeArray[1][8][1] = 49;
        timeArray[1][8][2] = 45;
        timeArray[1][8][3] = 65;
        timeArray[1][8][4] = 49;
        timeArray[1][8][5] = 90;
        timeArray[1][8][6] = 44;
        timeArray[1][8][7] = 75;
        timeArray[1][8][9] = 73;

        timeArray[1][9][0] = 45;
        timeArray[1][9][1] = 50;
        timeArray[1][9][2] = 54;
        timeArray[1][9][3] = 74;
        timeArray[1][9][4] = 45;
        timeArray[1][9][5] = 90;
        timeArray[1][9][6] = 52;
        timeArray[1][9][7] = 95;
        timeArray[1][9][8] = 74;

        //taxi time
        timeArray[2][0][1] = 6;
        timeArray[2][0][2] = 12;
        timeArray[2][0][3] = 13;
        timeArray[2][0][4] = 7;
        timeArray[2][0][5] = 32;
        timeArray[2][0][6] = 10;
        timeArray[2][0][7] = 21;
        timeArray[2][0][8] = 15;
        timeArray[2][0][9] = 18;

        timeArray[2][1][0] = 3;
        timeArray[2][1][2] = 14;
        timeArray[2][1][3] = 14;
        timeArray[2][1][4] = 8;
        timeArray[2][1][5] = 29;
        timeArray[2][1][6] = 9;
        timeArray[2][1][7] = 22;
        timeArray[2][1][8] = 15;
        timeArray[2][1][9] = 20;

        timeArray[2][2][0] = 14;
        timeArray[2][2][1] = 13;
        timeArray[2][2][3] = 4;
        timeArray[2][2][4] = 9;
        timeArray[2][2][5] = 32;
        timeArray[2][2][6] = 13;
        timeArray[2][2][7] = 19;
        timeArray[2][2][8] = 19;
        timeArray[2][2][9] = 27;

        timeArray[2][3][0] = 19;
        timeArray[2][3][1] = 18;
        timeArray[2][3][2] = 9;
        timeArray[2][3][4] = 14;
        timeArray[2][3][5] = 36;
        timeArray[2][3][6] = 16;
        timeArray[2][3][7] = 22;
        timeArray[2][3][8] = 22;
        timeArray[2][3][9] = 30;

        timeArray[2][4][0] = 8;
        timeArray[2][4][1] = 8;
        timeArray[2][4][2] = 11;
        timeArray[2][4][3] = 12;
        timeArray[2][4][5] = 30;
        timeArray[2][4][6] = 9;
        timeArray[2][4][7] = 19;
        timeArray[2][4][8] = 14;
        timeArray[2][4][9] = 22;

        timeArray[2][5][0] = 30;
        timeArray[2][5][1] = 29;
        timeArray[2][5][2] = 31;
        timeArray[2][5][3] = 32;
        timeArray[2][5][4] = 30;
        timeArray[2][5][6] = 23;
        timeArray[2][5][7] = 27;
        timeArray[2][5][8] = 23;
        timeArray[2][5][9] = 32;

        timeArray[2][6][0] = 10;
        timeArray[2][6][1] = 9;
        timeArray[2][6][2] = 12;
        timeArray[2][6][3] = 17;
        timeArray[2][6][4] = 9;
        timeArray[2][6][5] = 21;
        timeArray[2][6][7] = 21;
        timeArray[2][6][8] = 8;
        timeArray[2][6][9] = 21;

        timeArray[2][7][0] = 23;
        timeArray[2][7][1] = 26;
        timeArray[2][7][2] = 22;
        timeArray[2][7][3] = 26;
        timeArray[2][7][4] = 21;
        timeArray[2][7][5] = 26;
        timeArray[2][7][6] = 22;
        timeArray[2][7][8] = 24;
        timeArray[2][7][9] = 36;

        timeArray[2][8][0] = 16;
        timeArray[2][8][1] = 15;
        timeArray[2][8][2] = 22;
        timeArray[2][8][3] = 25;
        timeArray[2][8][4] = 15;
        timeArray[2][8][5] = 21;
        timeArray[2][8][6] = 7;
        timeArray[2][8][7] = 23;
        timeArray[2][8][9] = 24;

        timeArray[2][9][0] = 19;
        timeArray[2][9][1] = 20;
        timeArray[2][9][2] = 25;
        timeArray[2][9][3] = 30;
        timeArray[2][9][4] = 24;
        timeArray[2][9][5] = 30;
        timeArray[2][9][6] = 23;
        timeArray[2][9][7] = 35;
        timeArray[2][9][8] = 23 ;
    }

    //to obtain the name of location using index
    public static Object getKeyFromValue(HashMap hm, Integer index){
        for (Object o : hm.keySet()){
            if (hm.get(o).equals(index)){
                return o;
            }
        }
        return null;
    }

    //testing
    public static void main(String[] args) {
        int mode = 0;
        int from = 0;
        int to = 1;

        // costArray[mode][from][to]
        System.out.println("For transport mode: " + transportMode.get(mode));
        System.out.println("Time from " + getKeyFromValue(locations, from)
                + " to " + getKeyFromValue(locations, to)
                + " is $" + costArray[mode][from][to]);

        // timeArray[mode][from][to]
        System.out.println("For transport mode: " + transportMode.get(mode));
        System.out.println("Time from " + getKeyFromValue(locations, from)
                + " to " + getKeyFromValue(locations, to)
                + " is " + timeArray[mode][from][to] + " mins");

    }

}
