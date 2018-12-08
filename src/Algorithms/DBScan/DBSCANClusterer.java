package Algorithms.DBScan;

import weka.core.Instances;

import java.util.*;

public class DBSCANClusterer {

    private final double epsilon;
    private final double minPts;

    private enum PointStatus {
        /**
         * The point has is considered to be noise.
         */
        NOISE,
        /**
         * The point is already part of a cluster.
         */
        CLUSTERED
    }


    private TreeMap<String, PointStatus> visitedPoints = new TreeMap<>();
    private List<Cluster> clusters = new ArrayList<>();
    private Collection<Point> allPoints = new ArrayList<>();

    private TreeMap<String, BitSet> mapAttributeValueToCodification = new TreeMap<>();

    public DBSCANClusterer(double epsilon, double minPts, Instances instances) {
        this.epsilon = epsilon;
        this.minPts = minPts;

//        initCodificationMap(instances);

        for (int i = 0; i < instances.numAttributes() - 1; i++) {
//            System.out.println("instances = " + instances.attribute(i).name());
            if (instances.attribute(i).isNominal()) {
                Enumeration a = instances.attribute(i).enumerateValues();
                int codif = 0;
                if (a == null)
                    return;
                while (a.hasMoreElements()) {
                    mapAttributeValueToCodification.put(
                            instances.attribute(i).name().toLowerCase() + " : " + a.nextElement().toString().toLowerCase(),
                            BitSet.valueOf(new long[]{codif++}));
                }

            }
        }

        Point.mapAttributeValueToCodification = mapAttributeValueToCodification;

        for (int i = 0; i < instances.numInstances(); i++) {
            this.allPoints.add(new Point(instances.instance(i)));
        }

    }


    public void start() {
        int itter = 0;

//        System.out.println("allPoints = " + allPoints.size());
        for (Point point : allPoints) {
            if (visitedPoints.get(point.toString()) != null) {
                continue;
            }
            List<Point> neighbors = getDensityReachableNeighbors(point, allPoints);
//            System.out.println("neighbors = " + neighbors.size());
//            System.out.println("itter = " + itter++);
            if (neighbors.size() >= minPts) {
                Cluster cluster = new Cluster();
                clusters.add(exploreNeighbors(cluster, point, neighbors, allPoints, visitedPoints));
            } else {
                visitedPoints.put(point.toString(), PointStatus.NOISE);
            }
        }

    }


    private Cluster exploreNeighbors(Cluster cluster,
                                     Point point,
                                     List<Point> neighbors,
                                     Collection<Point> allPoints,
                                     Map<String, PointStatus> visited) {
        cluster.addToCluster(point);
        visited.put(point.toString(), PointStatus.CLUSTERED);

        List<Point> startingNeighbors = new ArrayList<>(neighbors);
//        System.out.println("seeds START = " + neighbors.size());
//        seeds.remove(point)
        int index = 0;
        while (index < startingNeighbors.size()) {
//            System.out.println("seeds.size() = " + startingNeighbors.size());
            Point current = startingNeighbors.get(index);
            PointStatus pointStatus = visited.get(current.toString());
            if (pointStatus == null) {
                List<Point> currentNeighbors = getDensityReachableNeighbors(current, allPoints);
                if (currentNeighbors.size() >= minPts) {
                    startingNeighbors = fusion(startingNeighbors, currentNeighbors);
                }
            }

            if (pointStatus != PointStatus.CLUSTERED) {
                visited.put(current.toString(), PointStatus.CLUSTERED);
                cluster.addToCluster(current);
            }
            index++;
        }
        System.out.println("Centroid = " + cluster.getCenterOfCluster());
        return cluster;
    }


    private List<Point> fusion(List<Point> one, List<Point> two) {

        final Set<Point> oneSet = new HashSet<>(one);
        for (Point item : two) {
            if (!oneSet.contains(item)) {
                one.add(item);
            }
        }
        return one;
    }


    private List<Point> getDensityReachableNeighbors(Point point, Collection<Point> points) {
        List<Point> neighbors = new ArrayList<>();
        for (Point neighbor : points) {
            double dst = Point.distance(point, neighbor, 2);
//            System.out.println("dst = " + dst);
            if (point != neighbor && dst <= epsilon) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }


    public double getIntraClassScore() {
        if (clusters.size() == 0)
            return -1;
        double sum = 0;
        for (Cluster cl : clusters) {
            sum += cl.getClassScore();
        }
        return sum;
    }

    public double getInterClassScore() {
        if (clusters == null)
            return -1;

        Cluster allGs = new Cluster();
        for (Cluster cl : clusters) {
            allGs.addToCluster(cl.getCenterOfCluster());
        }

        return allGs.getClassScore();

    }


    public double getEpsilon() {
        return epsilon;
    }

    public double getMinPts() {
        return minPts;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }
}
