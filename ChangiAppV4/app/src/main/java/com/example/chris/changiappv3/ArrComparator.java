package com.example.chris.changiappv3;

/**
 * Created by edmund on 1/12/17.
 */
import java.util.Comparator;

public class ArrComparator implements Comparator<Integer>
{
    private final double[] array;

    public ArrComparator(double[] array)
    {
        this.array = array;
    }

    public Integer[] createIndexArray()
    {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
            indexes[i] = i;
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2)
    {
        if (array[index1] < (array[index2])) return -1;
        else if (array[index1] == array[index2] ) return 0;
        else return +1;
    }
}
