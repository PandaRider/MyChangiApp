package com.example.chris.changiappv3;

import java.util.ArrayList;

/**
 * Created by Jingxuan on 30/11/2017.
 */

public class Utility {
    public static int getTime(int mode, String fromNode, String toNode) {
        return Data.timeArray[mode][Data.locations.get(fromNode)][Data.locations.get(toNode)];
    }

    public static int getTotalTime(int[] modes, ArrayList<String> itinerary) {
        int total=0;
        for(int i =0; i < modes.length; i++)
        {
            total += getTime(modes[i], itinerary.get(i), itinerary.get(i==modes.length-1?0:i+1));
        }

        return total;
    }

    public static double getCost(int mode, String fromNode, String toNode) {
        return Data.costArray[mode][Data.locations.get(fromNode)][Data.locations.get(toNode)];
    }

    public static double getTotalCost(int[] modes, ArrayList<String> itinerary) {
        double total=0;
        for(int i =0; i<modes.length; i++)
        {
            total+= getCost(modes[i], itinerary.get(i),itinerary.get(i==modes.length-1?0:i+1));
        }

        return total;
    }

//    public static void main(String[] args) {
//        System.out.println(getCost(1,"Marina Bay Sands", "SUTD"));
//        System.out.println(getTime(2,"Marina Bay Sands", "SUTD"));
//        int[] md = new int[]{0,1,2,1};
//        ArrayList<String> it = new ArrayList<String>();
//        it.add("Marina Bay Sands");
//        it.add("SUTD");
//        it.add("Vivo City");
//        it.add("Orchard Road");
//        System.out.println("Total cost: $" + getTotalCost(md,it));
//        System.out.println("Total time: " + getTotalTime(md,it) + " min");
//    }
}
