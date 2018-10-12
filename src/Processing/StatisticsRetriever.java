package Processing;

import weka.core.Instances;

import java.util.HashMap;

/**
 * Created by ressay on 10/10/18.
 */
public class StatisticsRetriever
{
    Instances data;

    public StatisticsRetriever(Instances data)
    {
        this.data = data;
    }

    public double getMin(int attributeIndex)
    {
        if(data.attribute(attributeIndex).isNominal())
            return -1;
        double min = data.instance(0).value(attributeIndex);
        for (int i = 1; i < data.numInstances(); i++)
        {
            if(min > data.instance(i).value(attributeIndex))
                min = data.instance(i).value(attributeIndex);
        }
        return min;
    }

    public double getMax(int attributeIndex)
    {
        if(data.attribute(attributeIndex).isNominal())
            return -1;
        double max = data.instance(0).value(attributeIndex);
        for (int i = 1; i < data.numInstances(); i++)
        {
            if(max < data.instance(i).value(attributeIndex))
                max = data.instance(i).value(attributeIndex);
        }
        return max;
    }

    public double getMean(int attributeIndex)
    {
        if(data.attribute(attributeIndex).isNominal())
            return -1;
        double sum = 0;
        for (int i = 1; i < data.numInstances(); i++)
        {
            sum += data.instance(i).value(attributeIndex);
        }
        return sum/data.numInstances();
    }

    public double getMedian(int attributeIndex)
    {
        if(data.numInstances() % 2 == 1)
        {
            return data.instance(data.numInstances()/2+1).value(attributeIndex);
        }
        else
        {
            int n = data.numInstances();
            return (data.instance(n/2).value(attributeIndex)+data.instance(n/2+1).value(attributeIndex))/2;
        }
    }

    public double getQ1(int attributeIndex)
    {
        int n = data.numInstances()/2;
        if(n%2 == 1)
        {
            return data.instance(n/2+1).value(attributeIndex);
        }
        else
        {
            return (data.instance(n/2).value(attributeIndex)+data.instance(n/2+1).value(attributeIndex))/2;
        }
    }

    public double getQ3(int attributeIndex)
    {
        int n = data.numInstances()/2;
        if(n%2 == 1)
        {
            return data.instance(n+n/2+1).value(attributeIndex);
        }
        else
        {
            return (data.instance(n+n/2).value(attributeIndex)+data.instance(n+n/2+1).value(attributeIndex))/2;
        }
    }

    public double getMode(int attributeIndex)
    {
        HashMap<Double,Integer> freq = new HashMap<>();
        for (int i = 0; i < data.numInstances(); i++)
        {
            Double val = data.instance(i).value(attributeIndex);
            if(!freq.containsKey(val))
                freq.put(val,0);
            freq.put(val,freq.get(val)+1);
        }
        int max = 0;
        double maxKey = -1;
        for (Double d : freq.keySet())
            if(max < freq.get(d))
            {
                max = freq.get(d);
                maxKey = d;
            }
        return maxKey;
    }

//    double getMidRange(int attributeIndex)
//    {
//        return 0;
//    }
}
