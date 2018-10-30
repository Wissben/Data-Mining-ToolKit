package Processing;

import weka.core.Instances;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

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

    public double getMeanSameClass(int attributeIndex,String classification)
    {
        if(data.attribute(attributeIndex).isNominal())
            return -1;
        double sum = 0;
        int count = 0;
        for (int i = 1; i < data.numInstances(); i++)
        {
            if(data.instance(i).stringValue(data.classIndex()).equals(classification) && !data.instance(i).isMissing(attributeIndex))
            {
                sum += data.instance(i).value(attributeIndex);
                count++;
            }
        }
        if(count == 0)
            return 0;
        return sum/count;
    }

    public double getMedian(int attributeIndex)
    {
        double[] values = new double[data.numInstances()];
        for (int i = 0; i < values.length; i++)
        {
            values[i] = data.instance(i).value(attributeIndex);
        }
        Arrays.sort(values);
        if(data.numInstances() % 2 == 1)
        {
            return values[data.numInstances()/2+1];
        }
        else
        {
            int n = data.numInstances();
            return (values[n/2]+values[n/2+1])/2;
        }
    }

    public double getQ1(int attributeIndex)
    {
        double[] values = new double[data.numInstances()];
        for (int i = 0; i < values.length; i++)
        {
            values[i] = data.instance(i).value(attributeIndex);
        }
        Arrays.sort(values);
        int n = data.numInstances()/2;
        if(n%2 == 1)
        {
            return values[(n/2+1)];
        }
        else
        {
            return values[(n/2)]+values[(n/2+1)]/2;
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

    public String getMode(int attributeIndex)
    {
        TreeMap<String,Integer> freq = new TreeMap<>();
        for (int i = 0; i < data.numInstances(); i++)
        {
            String val = data.attribute(attributeIndex).isNominal()?
                    data.instance(i).stringValue(attributeIndex):data.instance(i).value(attributeIndex)+"";
            if(!freq.containsKey(val))
                freq.put(val,0);
            freq.put(val,freq.get(val)+1);
        }
        int max = 0;
        String maxKey = "-";
        for (String d : freq.keySet())
            if(max < freq.get(d))
            {
                max = freq.get(d);
                maxKey = d;
            }
        return maxKey;
    }


    /**
     * Didn't know how to name that shit
     * @return
     */
    public  boolean isAttributeSymetric(int attribute_index)
    {
        double mean = this.getMean(attribute_index);
        try
        {
            double mode = Double.parseDouble(this.getMode(attribute_index));
            double median = this.getMedian(attribute_index);
            double error = 1e-2;
            return (((mean-mode>=3*(mode-median)-error) && (mean-mode<=3*(mode-median)+error)) && data.attribute(attribute_index).isNumeric());
        }catch (NumberFormatException e)
        {

        }
        return false;
    }




    public double getMidRange(int attributeIndex)
    {
        if(!data.attribute(attributeIndex).isNumeric())
            return -1;
        return (getMax(attributeIndex)+getMin(attributeIndex))/2;
    }
}
