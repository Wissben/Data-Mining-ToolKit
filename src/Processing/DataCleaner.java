package Processing;

import weka.core.Instance;
import weka.core.Instances;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataCleaner {

    private Instances data;
    private StatisticsRetriever sr;


    public DataCleaner(Instances instances)
    {
        this.data = instances;
        this.sr = new StatisticsRetriever(instances);
    }


    public void normalizeAttribute(int indexe)
    {


        Instance instance;
        double xmax = sr.getMax(indexe);
        double xmin = sr.getMin(indexe);

        for (int i = 0; i < this.data.numInstances() ; i++) {
            instance = this.data.instance(i);
            if(instance.attribute(indexe).isNumeric())
            {
                double x = instance.value(indexe);

                instance.setValue(indexe,(x-xmin)/(xmax-xmin));
            }

        }

    }



    public void normalizeAllAttributes()
    {


        Instance instance;

        Double[] maxs = new Double[data.numAttributes()];
        Double[] mins = new Double[data.numAttributes()];

        for (int i = 0; i < maxs.length; i++) {
            maxs[i] = sr.getMax(i);
            mins[i] = sr.getMin(i);
        }


        for (int i = 0; i < this.data.numInstances() ; i++) {
            instance = this.data.instance(i);
            for (int j = 0; j < instance.numAttributes(); j++)
            {
                if(instance.attribute(j).isNumeric())
                {
                    double x = instance.value(j);
                    double xmax = maxs[j];
                    double xmin = mins[j];
                    instance.setValue(j,(x-xmin)/(xmax-xmin));
                }
            }

        }

    }


//    public ArrayList<String> getAttributeValues(int index)
//    {
//        if (data.attribute(index).isNominal())
//        {
//            TreeSet<String> vals = new TreeSet<>();
//            for (int i = 0; i < data.numInstances(); i++)
//            {
//                String val = data.instance(i).stringValue(index);
//                vals.add(val);
//
//            }
//            return vals.toArray((new String[vals.size()]);
//        }
//
//        return null;
//    }


    public String getMostCommonValueOfSameClass(int index,String classification)
    {
        if (data.attribute(index).isNominal())
        {
            TreeMap<String,Integer> freq = new TreeMap<>();
            for (int i = 0; i < data.numInstances(); i++)
            {
               if(data.instance(i).stringValue(data.classIndex()).equals(classification) && !data.instance(i).isMissing(index))
               {

                   String val = data.instance(i).stringValue(index);
                   if(!freq.containsKey(val))
                       freq.put(val,0);
                   freq.put(val,freq.get(val)-1);
               }
            }
            return freq.firstKey();
        }
        return null;
    }



    public void fillInMissingValues()
    {
        Instance instance;

        for (int i = 0; i < this.data.numInstances() ; i++) {
            instance = this.data.instance(i);
            if(instance.hasMissingValue())
            {
                for (int j = 0; j < instance.numAttributes(); j++)
                {
                    if(instance.attribute(j).isNumeric() && instance.isMissing(j))
                    {
                        instance.setValue(j,sr.getMeanSameClass(j,instance.stringValue(data.classIndex())));
                    }
                    if(instance.attribute(j).isNominal() && instance.isMissing(j))
                    {
                        instance.setValue(j,this.getMostCommonValueOfSameClass(j,instance.stringValue(data.classIndex())));
                    }
                }

            }

        }
    }




    public Instances getData()
    {
        return this.data;
    }

}
