package Algorithms.DBScan;

import Processing.StatisticsRetriever;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Cluster implements Comparable<Cluster> {

    private List<Point> elements;
    public Cluster() {
        this.elements = new ArrayList<>();

    }

    public void addToCluster(Point p) {
        if(elements.contains(p))
            return;
        this.elements.add(p);
    }


    @Override
    public int compareTo(Cluster cluster) {
        return this.elements.size() - cluster.elements.size();
    }

    @Override
    public String toString() {
        return elements.toString();
    }


    public List<Point> getElements() {
        return elements;
    }


    public double getClassScore() {

        Point G = getCenterOfCluster();
        if(G==null) {
            return -1;
        }
        double sum = 0;
        for (Point p : elements) {
            sum+=Math.pow(Point.distance(p,G,2),2);
        }
        System.out.println("sum  INTRACLASSE= " + sum);
        return sum;
    }

    public Point getCenterOfCluster() {
        if (elements.size() == 0)
            return null;

        // 1,0,0.511685,0.49704,0.45038,0.440255,0.496185,0.44088,0.57353,0.533185,0.51893,0.46849,0.5,0.5,0.47714,0.4223,0.498285,0.44902,0.442125,0.47293,0.50919,0.518345,0.507595,0.50444,0.517565,0.492325,0.4838,0.546115,0.460705,0.50366,0.5,0.5,0.499805,0.560055
        // 1,0,0.98794,0.44699,0.973005,0.396,0.96403,0.35825,0.92998,0.36329,0.89883,0.260355,0.891125,0.24618,0.87314,0.19282,0.789725,0.15957,0.68926,0.131795,0.68162,0.11719,0.65949,0.101235,0.61396,0.09183,0.568295,0.08745,0.52303,0.088025,0.47869,0.09341,0.43084,0.095125
        Instances data = new Instances(this.toString(), elements.get(0).getAttInfos(), elements.size());
        for (Point p : this.elements) {
//            System.out.println("p.getPoint().numAttributes() = " + p.getPoint().numAttributes());
            data.add(p.getPoint());
        }
//        System.out.println("data = " + data);

        StatisticsRetriever sr = new StatisticsRetriever(data);

        Point G = new Point(elements.get(0));
        System.out.println("G.getPoint().numAttributes() = " + G.getPoint().numAttributes());
        for (int i = 0; i < G.getPoint().numAttributes()-1; i++) {
            if (G.getPoint().attribute(i).isNumeric()) {

                G.getPoint().attribute(i).setStringValue(String.valueOf(sr.getMean(i)));
            } else {

                G.getPoint().attribute(i).setStringValue(sr.getMode(i));
            }
        }

        return G;
    }

}
