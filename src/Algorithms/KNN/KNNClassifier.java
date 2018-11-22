package Algorithms.KNN;

import weka.core.Instance;
import weka.core.Instances;
import java.util.*;


public class KNNClassifier {

    Instances instances ;
    TreeMap<String,BitSet> mapAttributeValueToCodification = new TreeMap<>();

    int K = 3;

    public KNNClassifier(Instances instances,int K)
    {
        this.instances= instances;
        this.K = K;
        for (int i = 0; i < instances.numAttributes(); i++) {
            if(instances.attribute(i).isNominal())
            {
                Enumeration a = instances.attribute(i).enumerateValues();
                int codif = 0 ;
                while (a.hasMoreElements())
                {
                   mapAttributeValueToCodification.put(instances.attribute(i).name()+" : " +a.nextElement().toString().toLowerCase(),BitSet.valueOf(new long[]{codif++}));
                }

            }
        }
        System.out.println("mapAttributeToPossibleValues = " + mapAttributeValueToCodification);
    }


    private double hamDistance(BitSet a,BitSet b)
    {
        BitSet tmp;
        if(a == null || b == null)
        {
            System.out.println("a  = " + a );
            System.out.println("b = " + b);
        }
        tmp = (BitSet) a.clone();
        tmp.xor(b);
        return (double) tmp.cardinality();
    }

    private double distance(Instance A , Instance B)
    {
        double sum = 0;
        for (int i = 0; i < A.numAttributes(); i++) {
            if(A.attribute(i).isNumeric())
            {
                sum+=Math.pow(A.value(i)-B.value(i),2);
            }
            if(A.attribute(i).isNominal())
            {
                String key_a = instances.attribute(i).name()+" : " + A.stringValue(i).toLowerCase();
                String key_b = instances.attribute(i).name()+" : " + B.stringValue(i).toLowerCase();
//                System.out.println("key_a = " + key_a);
//                System.out.println("mapAttributeValueToCodification = " + mapAttributeValueToCodification.get(key_a));
//                System.out.println("key_b = " + key_b);
//                System.out.println("mapAttributeValueToCodification = " + mapAttributeValueToCodification.get(key_a));

                sum+=Math.pow(hamDistance(mapAttributeValueToCodification.get(key_a),mapAttributeValueToCodification.get(key_b)),2);
            }
        }
        return Math.sqrt(sum);
    }

    public String classify(Instance instance)
    {

        TreeMap<Integer,Double> mapInstanceToDistance = new TreeMap<>();



        for (int i = 0; i < instances.numInstances(); i++) {
            double dst = distance(instance,instances.instance(i));
            mapInstanceToDistance.put(i,dst);
        }

        TreeMap<Integer,Double> sorted  = new TreeMap<>(new ItemComaparator(mapInstanceToDistance));
        sorted.putAll(mapInstanceToDistance);

        String[] knns = new String[this.K];

        int cpt = 0;
        for (Integer k: sorted.keySet()) {
            System.out.println(cpt +" : " +instances.instance(k) +" distacnce : "+sorted.get(k));
            if(cpt>= this.K){
                System.out.println("cpt = " + cpt);
                break;
            }
            knns[cpt] = instances.instance(k).stringValue(instances.classIndex());
            cpt++;
        }

        for (int i = 0; i < knns.length; i++) {
            System.out.println("knns =  "+ i +" " + knns[i]);
        }
        return getMostFrequentClass(knns);
    }


    private String getMostFrequentClass(String[] cls)
    {
        TreeMap<String, Integer> map = new TreeMap<>();
        String tempStr;
        for (int i = 0; i < cls.length; i++)
        {
            tempStr = cls[i];
            if(map.containsKey(tempStr))
            {
                map.put(tempStr, map.get(tempStr) + 1);
            }
            else
            {
                map.put(tempStr,1);
            }
        }
        TreeMap<String,Integer> sorted = new TreeMap<>(new ItemComaparator2(map));
        sorted.putAll(map);
        System.out.println("sorted = " + sorted);
        return sorted.firstKey();
    }


    private class ItemComaparator implements Comparator<Integer>{
        TreeMap<Integer,Double> map;
        public ItemComaparator(Map<Integer, Double> map) {
            this.map = (TreeMap<Integer, Double>) map;
        }
        @Override
        public int compare(Integer a, Integer b) {
            return map.get(a).compareTo(map.get(b));
        }

    }

    private class ItemComaparator2 implements Comparator<String>{
        TreeMap<String,Integer> map;
        public ItemComaparator2(Map<String, Integer> map) {
            this.map = (TreeMap<String, Integer>) map;
        }
        @Override
        public int compare(String a, String b) {
            return -map.get(a).compareTo(map.get(b));
        }

    }


}
