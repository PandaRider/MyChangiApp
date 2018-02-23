package com.example.chris.changiappv3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by edmund on 1/12/17.
 */

public class NearestNeighbour {

    private static int[] destinations;
    private static int[] path;
    private static int[] transport;
    private static double[][] distanceTimeRatio;

    private static final double[][][] COST = Data.costArray;
    private static final int[][][] TIME = Data.timeArray;

    private static int totalTime = 0;
    private static double totalCost = 0;
    private static double budget;

    public NearestNeighbour() {

    }

//
//    public static void main(String[] args){
//
//        int[] input = {1, 5, 4, 3};
//        NearestNeighbour smart = new NearestNeighbour();
//        smart.setBudget(25.3);
//        smart.setDestination(input);
//        smart.computeModeOfTransport();
//        System.out.println("This algorithm prioritises the efficiency of each dollar you spent!");
//        System.out.println();
//        System.out.println(smart);
//        System.out.println("Total cost is: $" + smart.getTotalCost());
//        System.out.println("Total time is: " + smart.getTotalTime() + " minutes");
//        System.out.println("Remaining budget is: " + smart.budget);
//    }

// Private helper functions
    private static int getMinEleIndex(int[] arr) {
        int small = arr[0];                   //state of min element
        int j = 0;                        //state of min element's index
        for (int k=0; k<arr.length; k++){
            j = (small > arr[k] ? k : j);
            small = (small > arr[k] ? arr[k] : small);
        }
        return j;
    }

    private static boolean checkArrInt(int[] arr, int c){  //find if key (Int) is in array
        for (int anArr : arr) {
            if (anArr == c) return true;
        }
        return false;
    }

    private static boolean checkList(int[] arr, int c){
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == c) return false;
        }
        return true;
    }

    //returns c[] with elements of a[] + b[]
    private static double[] addArray(double[] a, double[] b) {
        double[] c = new double[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, 0 + a.length, b.length);
        return c;
    }

// Nearest neighbour that makes a finds a graph with input nodes, should be O(nk)
    public static int[] findPath(){
        int[][][] cloneTime = TIME.clone();     //don't count this but it's probably O(n)

        path = new int[destinations.length + 2];
        path[0] = 0;
        int currentLocation = 0;                //state for current node's index
        int count = 0;                          //count path array's number of nodes
        int bestNextDestination = 0;            //
        while (count < destinations.length){    // O(n)
            bestNextDestination = getMinEleIndex(cloneTime[0][currentLocation]);            //get index of destination of least time. O(length_of_destinations)
            if (checkArrInt(path, bestNextDestination) || !checkArrInt(destinations, bestNextDestination))
                cloneTime[0][currentLocation][bestNextDestination] = 9999;                  //O(1)
            else {
                count++;
                currentLocation = bestNextDestination;
                path[count] = currentLocation;                  //O(1)
            }
        }
        path[destinations.length + 1] = 0;      //add a zero value for last element of path array
        return path;
    }

//calculate ratio of distance over price
    private static double[][] computeDistanceOverPrice(){
        distanceTimeRatio = new double[3][destinations.length + 1];
        findPath();                                                     //O(nk)
        for (int i = 0; i <= path.length - 2; i++){
            distanceTimeRatio[1][i] = TIME[1][path[i]][path[i+1]]*COST[1][path[i]][path[i+1]];
            distanceTimeRatio[2][i] = TIME[2][path[i]][path[i+1]]*COST[2][path[i]][path[i+1]];
        }
        return distanceTimeRatio;
    }

//get total
    public static void computeModeOfTransport(){
        transport = new int[destinations.length + 1];
        computeDistanceOverPrice();                     // O(nk)

        int l = distanceTimeRatio[1].length;
        double[] distanceTimeRatiosort = addArray(distanceTimeRatio[1], distanceTimeRatio[2]);  //data of ratio

        ArrComparator comparator = new ArrComparator(distanceTimeRatiosort);
        Integer[] indexes = comparator.createIndexArray();                              //state of index already checked
        Arrays.sort(indexes, comparator);

        int[] potentialTransport = new int[path.length];
        int i = 0;
        int j = 0;
        double storeBudget = budget;
        double cost = 0;
        while (storeBudget > 0 && j < path.length && i < indexes.length){
            if (checkList(potentialTransport, indexes[i] + l) && checkList(potentialTransport, indexes[i]-l)) {
                if (indexes[i] >= l) cost = COST[2][path[indexes[i] - l]][path[indexes[i] - l + 1]];        //update COST
                else cost = COST[1][path[indexes[i]]][path[indexes[i] + 1]];
                if (storeBudget > cost){
                    potentialTransport[j] = indexes[i];
                    storeBudget -= cost;
                    i++;
                    j++;
                } else i++;
            }
            else i++;
        }

        for (int k : potentialTransport){
            if (k < l) transport[k] = 1;
            else transport[k - l] = 2;
        }
        int n = 0;
        for (Integer indexe : indexes) {
            if (indexe >= l) n = indexe - l;
            if (indexe >= l && transport[n] == 1 && storeBudget > COST[2][path[n]][path[n + 1]] - COST[1][path[n]][path[n + 1]]) {
                transport[n] = 2;
                storeBudget = storeBudget - COST[2][path[n]][path[n + 1]] + COST[1][path[n]][path[n + 1]];
            }
        }
        budget = storeBudget;
    }

    public static void getTimeAndCost(){
        totalCost = 0;
        totalTime = 0;
        for (int i = 0; i < path.length - 1; i++){
            totalTime += TIME[transport[i]][path[i]][path[i+1]];
            totalCost += COST[transport[i]][path[i]][path[i+1]];
        }
    }

//    public static ArrayList<Integer> getTime(){
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        for (int i = 0; i < transport.length; i++){
//            list.add(Data.timeArray[transport[i]][path[i]][path[i+1]]);
//        }
//        return list;
//    }

//    public static ArrayList<Double> getCost(){
//        ArrayList<Double> list = new ArrayList<Double>();
//        for (int i = 0; i < transport.length; i++){
//            list.add(Data.costArray[transport[i]][path[i]][path[i+1]]);
//        }
//        return list;
//    }

    public static void setBudget(double b){
        budget = b;
    }

    public static int getTotalTime(){
        return totalTime;
    }

    public static double getTotalCost(){
        return totalCost;
    }

    public static void setDestination(int[] a){
        destinations = new int[a.length];
        System.arraycopy(a, 0, destinations, 0, a.length);
    }

    public static ArrayList<String> getTripPath(){
        ArrayList<String> list = new ArrayList<String>();
        for (int aPath : path) {
            list.add(Data.locations2.get(aPath));
        }
        return list;
    }

    public static ArrayList<String> getTripTransport(){
        getTimeAndCost();
        ArrayList<String> list = new ArrayList<String>();
        for (int aTransport : transport) {
            list.add(Data.transportMode.get(aTransport));
        }
        return list;
    }

    @Override
    public String toString(){
        String s = "";
        for (int i = 0; i < transport.length; i++){
            s+= "Leg #" + i + ": Going from " + getTripPath().get(i) + " to " + getTripPath().get(i+1) + " via " + getTripTransport().get(i) + "\n";
        }
        return s;
    }



}
