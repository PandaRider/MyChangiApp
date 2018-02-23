package com.example.chris.changiappv3;

import com.example.chris.changiappv3.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * Created by Jingxuan on 3/12/2017.
 */

public class BruteForce {

    //Method to call brute
    public static TripObject CallBrute(ArrayList<Integer> attractions, double amount){
        double budget = amount;
        ArrayList<ArrayList<Integer>> permutations = generatePermutations(attractions);
        ArrayList<ArrayList<Integer>> newPaths = processPath(permutations);
        TripObject tripObject = chooseMethod(newPaths, budget);
        return tripObject;
    }

    //Generate all permutations of attractions
    public static ArrayList<ArrayList<Integer>> generatePermutations(ArrayList<Integer> attractions) {
        if (attractions.size() <= 1) {
            ArrayList<ArrayList<Integer>> permuteLists = new ArrayList<ArrayList<Integer>>();
            permuteLists.add(attractions);
            return permuteLists;
        }

        //remove the first element from destinations
        int lastElement = attractions.remove(0);
        ArrayList<ArrayList<Integer>> oldPermLists = generatePermutations(attractions);
        ArrayList<ArrayList<Integer>> newPermLists = new ArrayList<ArrayList<Integer>>();

        for (ArrayList<Integer> list : oldPermLists) {
            //modifying the arraylist elements
            for (int i = 0; i <= list.size(); i++) {
                ArrayList<Integer> newList = new ArrayList<Integer>(list);
                newList.add(i, lastElement);
                newPermLists.add(newList);
            }
        }
        return newPermLists;
    }



    //Method to get ALL Combination of paths, adding 0 (default Start & End point to list aka MBS)
    public static ArrayList<ArrayList<Integer>> processPath(ArrayList<ArrayList<Integer>>pathLists) {
        for (int i = 0; i < pathLists.size(); i++) {
            ArrayList array = pathLists.get(i);
            array.add(0, 0);
            array.add(0);
        }
        return pathLists;
    }

    //Method to choose aggregate cost path depending on number of attractions
    public static TripObject chooseMethod (ArrayList<ArrayList<Integer>>pathLists, double budget){
        int i = pathLists.get(0).size();
        //Number of attractions are sorted into different cases
        switch (i) {
            case 3: //2 Attractions
                return testAggregateCostsV2(pathLists,budget);
            case 4: //3 Attractions
                return  testAggregateCostsV3(pathLists,budget);
            case 5: //4 Attractions
                return testAggregateCostsV4(pathLists,budget);
            case 6: //5 Attractions
                return testAggregateCostsV5(pathLists,budget);
            case 7: //6 Attractions
                return testAggregateCostsV6(pathLists,budget);
            default:
                break;
        }
        return testAggregateCostsV6(pathLists,budget);
    }

    //Method for case 3: 2 Attractions
    public static TripObject testAggregateCostsV2 (ArrayList<ArrayList<Integer>>pathLists,double budget) {
        ArrayList<TripObject> costsList =new ArrayList<>();

        for (int i = 0; i < pathLists.size(); i++) {
            ArrayList array = pathLists.get(i);
            double cost = 0;
            int time = 0;
            // a is mode of transport from start point to attraction 1
            for (int a = 0; a<3; a++) {
                int start1 = (int) array.get(0);
                int attraction1 = (int) array.get(1);
                int pathTime1 = Data.timeArray[a][start1][attraction1];
                double pathCost1 = Data.costArray[a][start1][attraction1];
                // b is mode of transport from attraction 1 (aka start2) to attraction 2
                for (int b = 0; b<3; b++) {
                    int start2 = (int) array.get(1);
                    int attraction2 = (int) array.get(2);
                    int pathTime2 = Data.timeArray[b][start2][attraction2];
                    double pathCost2 = Data.costArray[b][start2][attraction2];

                    String mode1 = Data.transportMode.get(a);
                    String mode2 = Data.transportMode.get(b);
                    String startPoint = Data.locations2.get(start1);
                    String point1 = Data.locations2.get(attraction1);
                    String point2 = Data.locations2.get(attraction2);
                    String trip = (startPoint + " to "+ point1 + ": "+ mode1 + "\n"
                            + point1 +" to " + point2 + ": "+ mode2 + "\n");
                    double totalCost = pathCost1 + pathCost2;

                    int totalTime = pathTime1 + pathTime2;

                    String appPrint = ("Total Time: " + totalTime + "mins           Total Cost: $" + totalCost + "\n"
                            + startPoint + " to "+ point1 + " ("+ mode1 + ", " + pathTime1 + " mins): $" + pathCost1 +  "\n"
                            + point1 + " to " + point2 + " (" + mode2 + ", " + pathTime2 + " mins): $" + pathCost2 + "\n");

                    ArrayList<String> tripList = new ArrayList<>();
                    tripList.add(startPoint);
                    tripList.add(point1);
                    tripList.add(point2);

                    ArrayList<String> timeAndCost = new ArrayList<>();
                    timeAndCost.add("Mode: "+ mode1 + "\nTime: " + pathTime1 + " mins" + "\nCost: $" + pathCost1);
                    timeAndCost.add("Mode: "+ mode2 + "\nTime: " + pathTime2 + " mins" + "\nCost: $" + pathCost2);

                    if (totalCost <= budget){
                        TripObject tripObject = new TripObject(trip,totalTime,totalCost,appPrint);
                        tripObject.setTripList(tripList);
                        tripObject.setTimeAndCostList(timeAndCost);
                        costsList.add(tripObject);
                    }
                    else{
                        continue;
                    }
                }
            }
        }
        Collections.sort(costsList);
        return costsList.get(0);
    }


    //Method for case 4: 3 Attractions
    public static TripObject testAggregateCostsV3 (ArrayList<ArrayList<Integer>>pathLists, double budget) {
        ArrayList<TripObject> costsList =new ArrayList<>();

        for (int i = 0; i < pathLists.size(); i++) {
            ArrayList array = pathLists.get(i);
            double cost = 0;
            int time = 0;

            // a is mode of transport from start point to attraction 1
            for (int a = 0; a < 3; a++) {
                int start1 = (int) array.get(0);
                int attraction1 = (int) array.get(1);
                int pathTime1 = Data.timeArray[a][start1][attraction1];
                double pathCost1 = Data.costArray[a][start1][attraction1];
                // b is mode of transport from attraction 1 (aka start2) to attraction 2
                for (int b = 0; b<3; b++) {
                    int start2 = (int) array.get(1);
                    int attraction2 = (int) array.get(2);
                    int pathTime2 = Data.timeArray[b][start2][attraction2];
                    double pathCost2 = Data.costArray[b][start2][attraction2];
                    // C is mode of transport from attraction 2 (aka start3) to attraction 3
                    for (int c = 0; c < 3; c++) {
                        int start3 = (int) array.get(2);
                        int attraction3 = (int) array.get(3);
                        int pathTime3 = Data.timeArray[c][start3][attraction3];
                        double pathCost3 = Data.costArray[c][start3][attraction3];

                        String mode1 = Data.transportMode.get(a);
                        String mode2 = Data.transportMode.get(b);
                        String mode3 = Data.transportMode.get(c);
                        String startPoint = Data.locations2.get(start1);
                        String point1 = Data.locations2.get(attraction1);
                        String point2 = Data.locations2.get(attraction2);
                        String point3 = Data.locations2.get(attraction3);
                        String trip = (startPoint + " to " + point1 + ": "+ mode1 + "\n"
                                + point1 + " to " + point2 + ": " + mode2 + "\n"
                                + point2 + " to " + point3 + ": " + mode3 + "\n");

                        double totalCost = pathCost1 + pathCost2 + pathCost3;
                        int totalTime = pathTime1 + pathTime2 + pathTime3 ;

                        String appPrint = ("Total Time: " + totalTime + "mins           Total Cost: $" + totalCost + "\n"
                                + startPoint + " to "+ point1 + " ("+ mode1 + ", " + pathTime1 + " mins): $" + pathCost1 +  "\n"
                                + point1 + " to " + point2 + " (" + mode2 + ", " + pathTime2 + " mins): $" + pathCost2 + "\n"
                                + point2 + " to " + point3 + " (" + mode3 + ", " + pathTime3 + " mins): $" + pathCost3 + "\n");

                        ArrayList<String> tripList = new ArrayList<>();
                        tripList.add(startPoint);
                        tripList.add(point1);
                        tripList.add(point2);
                        tripList.add(point3);

                        ArrayList<String> timeAndCost = new ArrayList<>();
                        timeAndCost.add("Mode: " + mode1 + "\nTime: " + pathTime1 + " mins" + "\nCost: $" + pathCost1);
                        timeAndCost.add("Mode: " + mode2 + "\nTime: " + pathTime2 + " mins" + "\nCost: $" + pathCost2);
                        timeAndCost.add("Mode: " + mode3 + "\nTime: " + pathTime3 + " mins" + "\nCost: $" + pathCost3);

                        if(totalCost <= budget){
                            TripObject tripObject = new TripObject(trip,totalTime,totalCost,appPrint);
                            tripObject.setTripList(tripList);
                            tripObject.setTimeAndCostList(timeAndCost);
                            costsList.add(tripObject);
                        }
                        else{
                            continue;
                        }
                    }
                }
            }
        }
        Collections.sort(costsList);
        return costsList.get(0);
    }


    //Method for case 5: 4 Attractions
    public static TripObject testAggregateCostsV4 (ArrayList<ArrayList<Integer>>pathLists, double budget) {
        ArrayList<TripObject> costsList =new ArrayList<>();

        for (int i = 0; i < pathLists.size(); i++) {
            ArrayList array = pathLists.get(i);
            double cost = 0;
            int time = 0;

            // a is mode of transport from start point to attraction 1
            for (int a = 0; a < 3; a++) {
                int start1 = (int) array.get(0);
                int attraction1 = (int) array.get(1);
                int pathTime1 = Data.timeArray[a][start1][attraction1];
                double pathCost1 = Data.costArray[a][start1][attraction1];
                // b is mode of transport from attraction 1 (aka start2) to attraction 2
                for (int b = 0; b<3; b++) {
                    int start2 = (int) array.get(1);
                    int attraction2 = (int) array.get(2);
                    int pathTime2 = Data.timeArray[b][start2][attraction2];
                    double pathCost2 = Data.costArray[b][start2][attraction2];
                    // c is mode of transport from attraction 2 (aka start3) to attraction 3
                    for (int c = 0; c < 3; c++) {
                        int start3 = (int) array.get(2);
                        int attraction3 = (int) array.get(3);
                        int pathTime3 = Data.timeArray[c][start3][attraction3];
                        double pathCost3 = Data.costArray[c][start3][attraction3];
                        // d is mode of transport from attraction 3 (aka start4) to attraction 4
                        for (int d = 0; d < 3; d++) {
                            int start4 = (int) array.get(3);
                            int attraction4 = (int) array.get(4);
                            int pathTime4 = Data.timeArray[d][start4][attraction4];
                            double pathCost4 = Data.costArray[d][start4][attraction4];

                            String mode1 = Data.transportMode.get(a);
                            String mode2 = Data.transportMode.get(b);
                            String mode3 = Data.transportMode.get(c);
                            String mode4 = Data.transportMode.get(d);

                            String startPoint = Data.locations2.get(start1);
                            String point1 = Data.locations2.get(attraction1);
                            String point2 = Data.locations2.get(attraction2);
                            String point3 = Data.locations2.get(attraction3);
                            String point4 = Data.locations2.get(attraction4);

                            ArrayList<String> tripList = new ArrayList<>();
                            tripList.add(startPoint);
                            tripList.add(point1);
                            tripList.add(point2);
                            tripList.add(point3);
                            tripList.add(point4);

                            String trip = (startPoint + " to " + point1 + ": "+ mode1 +"\n"
                                    + point1 + " to " + point2 + ": " + mode2 + "\n"
                                    + point2 + " to " + point3 + ": " + mode3 + "\n"
                                    + point3 + " to " + point4 + ": " + mode4 + "\n" );

                            double totalCost = pathCost1 + pathCost2 + pathCost3 + pathCost4;
                            int totalTime = pathTime1 + pathTime2 + pathTime3 + pathTime4;

                            String appPrint = ("Total Time: " + totalTime + "mins           Total Cost: $" + totalCost + "\n"
                                    + startPoint + " to "+ point1 + " ("+ mode1 + ", " + pathTime1 + " mins): $" + pathCost1 +  "\n"
                                    + point1 + " to " + point2 + " (" + mode2 + ", " + pathTime2 + " mins): $" + pathCost2 + "\n"
                                    + point2 + " to " + point3 + " (" + mode3 + ", " + pathTime3 + " mins): $" + pathCost3 + "\n"
                                    + point3 + " to " + point4 + " (" + mode4 + ", " + pathTime4 + " mins): $" + pathCost4 + "\n");

                            ArrayList<String> timeAndCost = new ArrayList<>();
                            timeAndCost.add("Mode: " + mode1 + "\nTime: " + pathTime1 + " mins" + "\nCost: $" + pathCost1);
                            timeAndCost.add("Mode: " + mode2 + "\nTime: " + pathTime2 + " mins" + "\nCost: $" + pathCost2);
                            timeAndCost.add("Mode: " + mode3 + "\nTime: " + pathTime3 + " mins" + "\nCost: $" + pathCost3);
                            timeAndCost.add("Mode: " + mode4 + "\nTime: " + pathTime4 + " mins" + "\nCost: $" + pathCost4);

                            if (totalCost <= budget){

                                TripObject tripObject = new TripObject(trip,totalTime,totalCost,appPrint);
                                tripObject.setTripList(tripList);
                                tripObject.setTimeAndCostList(timeAndCost);
                                costsList.add(tripObject);
                            }
                            else{
                                continue;
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(costsList);
        return costsList.get(0);
    }



    //Method for case 6: 5 Attractions
    public static TripObject testAggregateCostsV5 (ArrayList<ArrayList<Integer>>pathLists,double budget) {
        ArrayList<TripObject> costsList =new ArrayList<>();

        for (int i = 0; i < pathLists.size(); i++) {
            ArrayList array = pathLists.get(i);
            //System.out.println("new path!");
            double cost = 0;
            int time = 0;

            // a is mode of transport from start point to attraction 1
            for (int a = 0; a < 3; a++) {
                int start1 = (int) array.get(0);
                int attraction1 = (int) array.get(1);
                int pathTime1 = Data.timeArray[a][start1][attraction1];
                double pathCost1 = Data.costArray[a][start1][attraction1];
                // b is mode of transport from attraction 1 (aka start2) to attraction 2
                for (int b = 0; b<3; b++) {
                    int start2 = (int) array.get(1);
                    int attraction2 = (int) array.get(2);
                    int pathTime2 = Data.timeArray[b][start2][attraction2];
                    double pathCost2 = Data.costArray[b][start2][attraction2];
                    // c is mode of transport from attraction 2 (aka start3) to attraction 3
                    for (int c = 0; c < 3; c++) {
                        int start3 = (int) array.get(2);
                        int attraction3 = (int) array.get(3);
                        int pathTime3 = Data.timeArray[c][start3][attraction3];
                        double pathCost3 = Data.costArray[c][start3][attraction3];
                        // d is mode of transport from attraction 3 (aka start4) to attraction 4
                        for (int d = 0; d < 3; d++) {
                            int start4 = (int) array.get(3);
                            int attraction4 = (int) array.get(4);
                            int pathTime4 = Data.timeArray[d][start4][attraction4];
                            double pathCost4 = Data.costArray[d][start4][attraction4];
                            // e is mode of transport from attraction 4 (aka start5) to attraction 5
                            for (int e = 0; e < 3; e++) {
                                int start5 = (int) array.get(4);
                                int attraction5 = (int) array.get(5);
                                int pathTime5 = Data.timeArray[e][start5][attraction5];
                                double pathCost5 = Data.costArray[e][start5][attraction5];

                                String mode1 = Data.transportMode.get(a);
                                String mode2 = Data.transportMode.get(b);
                                String mode3 = Data.transportMode.get(c);
                                String mode4 = Data.transportMode.get(d);
                                String mode5 = Data.transportMode.get(e);


                                String startPoint = Data.locations2.get(start1);
                                String point1 = Data.locations2.get(attraction1);
                                String point2 = Data.locations2.get(attraction2);
                                String point3 = Data.locations2.get(attraction3);
                                String point4 = Data.locations2.get(attraction4);
                                String point5 = Data.locations2.get(attraction5);

                                ArrayList<String> tripList = new ArrayList<>();
                                tripList.add(startPoint);
                                tripList.add(point1);
                                tripList.add(point2);
                                tripList.add(point3);
                                tripList.add(point4);
                                tripList.add(point5);

                                String trip = (startPoint + " to " + point1 + ": " + mode1 + "\n"
                                        + point1 + " to " + point2 + ": " + mode2 + "\n"
                                        + point2 + " to " + point3 + ": " + mode3 + "\n"
                                        + point3 + " to " + point4 + ": " + mode4 + "\n"
                                        + point4 + " to " + point5 + ": " + mode5 + "\n");

                                double totalCost = pathCost1 + pathCost2 + pathCost3 + pathCost4 + pathCost5;
                                int totalTime = pathTime1 + pathTime2 + pathTime3 + pathTime4 + pathTime5;

                                String appPrint = ("Total Time: " + totalTime + "mins           Total Cost: $" + totalCost + "\n"
                                        + startPoint + " to "+ point1 + " ("+ mode1 + ", " + pathTime1 + " mins): $" + pathCost1 +  "\n"
                                        + point1 + " to " + point2 + " (" + mode2 + ", " + pathTime2 + " mins): $" + pathCost2 + "\n"
                                        + point2 + " to " + point3 + " (" + mode3 + ", " + pathTime3 + " mins): $" + pathCost3 + "\n"
                                        + point3 + " to " + point4 + " (" + mode4 + ", " + pathTime4 + " mins): $" + pathCost4 + "\n"
                                        + point4 + " to " + point5 + " (" + mode5 + ", " + pathTime5 + " mins): $" + pathCost5 + "\n");

                                ArrayList<String> timeAndCost = new ArrayList<>();
                                timeAndCost.add("Mode: " + mode1 + "\nTime: " + pathTime1 + " mins" + "\nCost: $" + pathCost1);
                                timeAndCost.add("Mode: " + mode2 + "\nTime: " + pathTime2 + " mins" + "\nCost: $" + pathCost2);
                                timeAndCost.add("Mode: " + mode3 + "\nTime: " + pathTime3 + " mins" + "\nCost: $" + pathCost3);
                                timeAndCost.add("Mode: " + mode4 + "\nTime: " + pathTime4 + " mins" + "\nCost: $" + pathCost4);
                                timeAndCost.add("Mode: " + mode5 + "\nTime: " + pathTime5 + " mins" + "\nCost: $" + pathCost5);

                                if (totalCost <= budget) {
                                    TripObject tripObject = new TripObject(trip, totalTime, totalCost,appPrint);
                                    tripObject.setTripList(tripList);
                                    tripObject.setTimeAndCostList(timeAndCost);
                                    costsList.add(tripObject);
                                } else {
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(costsList);
        return costsList.get(0);
    }


    //Method for case 7: 6 Attractions
    public static TripObject testAggregateCostsV6 (ArrayList<ArrayList<Integer>>pathLists, double budget) {
        ArrayList<TripObject> costsList =new ArrayList<>();

        for (int i = 0; i < pathLists.size(); i++) {
            ArrayList array = pathLists.get(i);
            double cost = 0;
            int time = 0;

            // a is mode of transport from start point to attraction 1
            for (int a = 0; a < 3; a++) {
                int start1 = (int) array.get(0);
                int attraction1 = (int) array.get(1);
                int pathTime1 = Data.timeArray[a][start1][attraction1];
                double pathCost1 = Data.costArray[a][start1][attraction1];
                // b is mode of transport from attraction 1 (aka start2) to attraction 2
                for (int b = 0; b<3; b++) {
                    int start2 = (int) array.get(1);
                    int attraction2 = (int) array.get(2);
                    int pathTime2 = Data.timeArray[b][start2][attraction2];
                    double pathCost2 = Data.costArray[b][start2][attraction2];
                    // c is mode of transport from attraction 2 (aka start3) to attraction 3
                    for (int c = 0; c < 3; c++) {
                        int start3 = (int) array.get(2);
                        int attraction3 = (int) array.get(3);
                        int pathTime3 = Data.timeArray[c][start3][attraction3];
                        double pathCost3 = Data.costArray[c][start3][attraction3];
                        // d is mode of transport from attraction 3 (aka start4) to attraction 4
                        for (int d = 0; d < 3; d++) {
                            int start4 = (int) array.get(3);
                            int attraction4 = (int) array.get(4);
                            int pathTime4 = Data.timeArray[d][start4][attraction4];
                            double pathCost4 = Data.costArray[d][start4][attraction4];
                            // e is mode of transport from attraction 4 (aka start5) to attraction 5
                            for (int e = 0; e < 3; e++) {
                                int start5 = (int) array.get(4);
                                int attraction5 = (int) array.get(5);
                                int pathTime5 = Data.timeArray[e][start5][attraction5];
                                double pathCost5 = Data.costArray[e][start5][attraction5];
                                // f is mode of transport from attraction 5 (aka start6) to attraction 6
                                for (int f = 0; f < 3; f++) {
                                    int start6 = (int) array.get(5);
                                    int attraction6 = (int) array.get(6);
                                    int pathTime6 = Data.timeArray[f][start6][attraction6];
                                    double pathCost6 = Data.costArray[f][start6][attraction6];

                                    String mode1 = Data.transportMode.get(a);
                                    String mode2 = Data.transportMode.get(b);
                                    String mode3 = Data.transportMode.get(c);
                                    String mode4 = Data.transportMode.get(d);
                                    String mode5 = Data.transportMode.get(e);
                                    String mode6 = Data.transportMode.get(f);

                                    String startPoint = Data.locations2.get(start1);
                                    String point1 = Data.locations2.get(attraction1);
                                    String point2 = Data.locations2.get(attraction2);
                                    String point3 = Data.locations2.get(attraction3);
                                    String point4 = Data.locations2.get(attraction4);
                                    String point5 = Data.locations2.get(attraction5);
                                    String point6 = Data.locations2.get(attraction6);

                                    ArrayList<String> tripList = new ArrayList<>();
                                    tripList.add(startPoint);
                                    tripList.add(point1);
                                    tripList.add(point2);
                                    tripList.add(point3);
                                    tripList.add(point4);
                                    tripList.add(point5);
                                    tripList.add(point6);

                                    ArrayList<String> timeAndCost = new ArrayList<>();
                                    timeAndCost.add("Mode: "+mode1+"\nTime: "+pathTime1+" mins"+"\nCost: $"+pathCost1);
                                    timeAndCost.add("Mode: "+mode2+"\nTime: "+pathTime2+" mins"+"\nCost: $"+pathCost2);
                                    timeAndCost.add("Mode: "+mode3+"\nTime: "+pathTime3+" mins"+"\nCost: $"+pathCost3);
                                    timeAndCost.add("Mode: "+mode4+"\nTime: "+pathTime4+" mins"+"\nCost: $"+pathCost4);
                                    timeAndCost.add("Mode: "+mode5+"\nTime: "+pathTime5+" mins"+"\nCost: $"+pathCost5);
                                    timeAndCost.add("Mode: "+mode6+"\nTime: "+pathTime6+" mins"+"\nCost: $"+pathCost6);


                                    String trip = (startPoint + " to " + point1 + ": " + mode1 + "\n"
                                            + point1 + " to " + point2 + ": " + mode2 + "\n"
                                            + point2 + " to " + point3 + ": " + mode3 + "\n"
                                            + point3 + " to " + point4 + ": " + mode4 + "\n"
                                            + point4 + " to " + point5 + ": " + mode5 + "\n"
                                            + point5 + " to " + point6 + ": " + mode6 + "\n");

                                    double totalCost = pathCost1 + pathCost2 + pathCost3 + pathCost4 + pathCost5 + pathCost6;
                                    int totalTime = pathTime1 + pathTime2 + pathTime3 + pathTime4 + pathTime5 + pathTime6;

                                    String appPrint = ("Total Time: " + totalTime + "mins           Total Cost: $" + totalCost + "\n"
                                            + startPoint + " to "+ point1 + " ("+ mode1 + ", " + pathTime1 + " mins): $" + pathCost1 +  "\n"
                                            + point1 + " to " + point2 + " (" + mode2 + ", " + pathTime2 + " mins): $" + pathCost2 + "\n"
                                            + point2 + " to " + point3 + " (" + mode3 + ", " + pathTime3 + " mins): $" + pathCost3 + "\n"
                                            + point3 + " to " + point4 + " (" + mode4 + ", " + pathTime4 + " mins): $" + pathCost4 + "\n"
                                            + point4 + " to " + point5 + " (" + mode5 + ", " + pathTime5 + " mins): $" + pathCost5 + "\n"
                                            + point5 + " to " + point6 + " (" + mode6 + ", " + pathTime6 + " mins): $" + pathCost6 + "\n");

                                    if (totalCost <= budget){
                                        TripObject tripObject = new TripObject(trip,totalTime,totalCost,appPrint);
                                        tripObject.setTripList(tripList);
                                        tripObject.setTimeAndCostList(timeAndCost);
                                        costsList.add(tripObject);
                                    }
                                    else{
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(costsList);
        return costsList.get(0);
    }


    static class TripObject implements Comparable<TripObject> {
        private String tripSignature;
        private String infoPrint;
        //private data fields which are only modifiable by get, set
        private int totalTime = 0;
        private double totalCost = 0;
        private ArrayList<String> tripList;
        private ArrayList<String> timeAndCostList;

        TripObject(String trip, int time, double cost, String appPrint) {
            this.tripSignature = trip;
            this.totalTime = time;
            this.totalCost = cost;
            this.infoPrint = appPrint;
        }

        @Override
        public int compareTo(TripObject tripObject) {
            if (this.totalTime > tripObject.totalTime)
                return 1;
            else if (this.totalTime == tripObject.totalTime)
                return 0;
            else
                return -1;
        }

        public String getTripSignature() {
            return tripSignature;// Accessor
        }

        public String getInfoPrint() {
            return infoPrint;// Accessor
        }

        public int getTotalTime() {
            return totalTime;// Accessor
        }

        public double getTotalCost() {
            return totalCost;// Accessor
        }

        public ArrayList<String> getTripList() {
            return tripList;
        }

        public void setTripList(ArrayList<String> tripList) {
            this.tripList = tripList;
        }

        public void setTimeAndCostList(ArrayList<String> a) {
            this.timeAndCostList = a;
        }

        public ArrayList<String> getTimeAndCostList() {
            return this.timeAndCostList;
        }
    }
    //testing
//     public static void main(String[] args) {
//        System.out.println("ALL COMBINATIONS");
//        int set1[] = {0, 1, 2};
//        int k = 3;
//        printAllKLength(set1, k);
//        System.out.println(combList);
//        ArrayList<Integer> locations = new ArrayList<>();
//        locations.add(1);
//        locations.add(2);
//        System.out.println(generatePermutations(locations));
//        int possroute = generatePermutations(locations).size();
//        System.out.println("Number of Possible Routes: " + possroute);
//        System.out.println(generateCombinations(combList));
//        int trancomb = generateCombinations(combList).size();
//        System.out.println("Number of Transport Combinations: " + trancomb);
//        ArrayList<Integer> s = new ArrayList<>();
//        s.add(1);
//        s.add(2);
//        s.add(3);
//        s.add(4);
//        s.add(5);
//        TripObject o = CallBrute(s,20);
//
//        System.out.println(o.getTripList());
//        System.out.println(o.getTimeAndCostList());
//        System.out.println("Total Cost is $" + o.getTotalCost());
//        System.out.println("Total Time Taken is " + o.getTotalTime());
//        System.out.println(o.getTripSignature());
//        System.out.println(o.getInfoPrint());
//     }
}

