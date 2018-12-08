package Front;

import Algorithms.DBScan.Cluster;
import Algorithms.DBScan.DBSCANClusterer;
import Algorithms.DBScan.Point;
import Algorithms.KNN.KNNClassifier;
import Front.AprioriUI.AprioriController;
import Processing.DataCleaner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


//public class Main {
public class Main extends Application {


    private AprioriController controller;
//
//
    @Override
    public void start(Stage primaryStage) throws Exception{

//        BufferedReader reader =
//                new BufferedReader(new FileReader("/home/weiss/CODES/TP-DM/dmtp/car.arff"));
//        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
//        Instances data = arff.getData();
//        data.setClassIndex(data.numAttributes() - 1);
////        System.out.println(data);
////        System.out.println("data = " + data.instance(0).attribute(1).enumerateValues());
//        InstanceApriori ia = AprioriInstanceReader.loadInstance(data);
//        Searcher sr = new Searcher(ia,500,075);
//        sr.search();
//        System.out.println("sr.rules = " + sr.rules);
//        System.out.println("sr.Ls = " + sr.Ls);
//        for (int i = 0; i < data.numInstances() ; i++) {
//            Instance instance = data.instance(i);
//            for (int j = 0; j < instance.numAttributes(); j++)
//            {
//                System.out.print(instance.attribute(j).name()+": "); //get Attribute 0 as String
//                System.out.print(instance.value(j)+ " * ");
//            }
//            System.out.println();
////        }
////
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("DM Toolkit");
        primaryStage.setScene(new Scene(root));
//        root.getStylesheets().add("/home/weiss/CODES/TP-DM/dmtp/styles/slider.css");
        primaryStage.setResizable(false);
        primaryStage.show();
        Platform.setImplicitExit(true);



    }


    public static void main(String[] args) throws IOException {
        launch(args);
//        BufferedReader reader =
//                new BufferedReader(new FileReader("/home/weiss/CODES/TP-DM/dmtp/car.arff"));
//        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
//        Instances data = arff.getData();
//        data.setClassIndex(data.numAttributes() - 1);
////        System.out.println(data);
////        System.out.println("data = " + data.instance(0).attribute(1).enumerateValues());
//        InstanceApriori ia = AprioriInstanceReader.loadInstance(data);
//        Searcher sr = new Searcher(ia,200,0.75);
//        sr.search();
//        System.out.println("sr.rules = " + sr.rules);
//        System.out.println("sr.Ls = " + sr.Ls);


//
//        BufferedReader reader =
//                new BufferedReader(new FileReader("/home/weiss/CODES/TP-DM/dmtp/data/labor.arff"));
//        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
//        Instances data = arff.getData();
//        data.setClassIndex(data.numAttributes() - 1);
//        DataCleaner dc = new DataCleaner(data);
//        dc.normalizeAllAttributes();
//        dc.fillInMissingValues();
////        System.out.println("dc.getData() = " + dc.getData());
//        DBSCANClusterer dbscan = new DBSCANClusterer(05,12,data);
//        dbscan.start();
//        ArrayList<Cluster> clusters = (ArrayList<Cluster>) dbscan.getClusters();
//
//        String res = "Le nombre de clusters est : " + clusters.size() +"\n";
//        res += "L'inertie intra-classes est : " + dbscan.getIntraClassScore() +"\n";
//        res += "L'inertie inter-classes est : " + dbscan.getInterClassScore() +"\n";
//        res += "Les clusters construit sont : \n\n\n";
////        res += clustersToString(clusters) + "\n";
//        System.out.println("res = " + res);


//        System.out.println("dbscan = " + dbscan.getInterClassScore());
//        System.out.println("dc = " + dc.getData().size());
//        System.out.println("dbscan = " + dbscan.getClusters().size());
//        for (Cluster cl : dbscan.getClusters())
//        {
//            System.out.println("cl.getElements().size() = " + cl.getElements().size());
//            System.out.println("cl = " + cl);
//        }

//        System.out.println("dbscan.getClusters() = " + dbscan.getClusters());
//        
//        KNNClassifier knn = new KNNClassifier(data,5,0.75);
//        knn.classifyTestSet(knn.testInstances);
//        System.out.println("knn = "+  knn.classifyTestSet(knn.testInstances));
//        System.out.println("data = " + data.instance(45));
//        String cl = knn.classify(data.instance(45));
//        System.out.println("cl = " + cl);

//        System.out.println(data);
//        Enumeration a = data.attribute(1).enumerateValues();
//        while (a.hasMoreElements())
//        {
//            System.out.println("a.nextElement() = " + a.nextElement());
//        }

//        InstanceApriori instanceApriori = KNNInstanceReader.loadInstance(new File("path to csv file "));
//        Searcher sr = new Searcher(instanceApriori,400,0.7);
//        sr.search();
//        //FreqSets :
//        TreeMap<String,Integer> freqSets = sr.Ls;
//        TreeMap<String,Double> rules = sr.rules;

    }

    private static String clustersToString(ArrayList<Cluster> res) {
        String s = "";
        int count = 1;
        for (Cluster c : res) {
            s += "Cluster : " + count + "\n Nombre d'éléments : " + c.getElements().size() + "\n Distance moyenne dans le cluster: " + c.getCenterOfCluster() + " \n[";
            for (Point p : c.getElements()) {

                s += "\t\t" + p + "\n";
            }
            s += "]\n";
            count++;
        }
        return s;
    }

}
