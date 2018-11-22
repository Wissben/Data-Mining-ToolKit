package Front;

import Algorithms.KNN.KNNClassifier;
import Front.AprioriUI.AprioriUI;
import Processing.DataCleaner;
import com.sun.corba.se.spi.ior.ObjectKey;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Enumeration;


public class Main{
//public class Main extends Application {


//    private AprioriUI controller;
//
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{

//        BufferedReader reader =
//                new BufferedReader(new FileReader("/home/weiss/CODES/TP-DM/dmtp/data/iris.arff"));
//        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
//        Instances data = arff.getData();
//        data.setClassIndex(data.numAttributes() - 1);
//        System.out.println(data);
//        System.out.println("data = " + data.instance(0).attribute(1).enumerateValues());

//        for (int i = 0; i < data.numInstances() ; i++) {
//            Instance instance = data.instance(i);
//            for (int j = 0; j < instance.numAttributes(); j++)
//            {
//                System.out.print(instance.attribute(j).name()+": "); //get Attribute 0 as String
//                System.out.print(instance.value(j)+ " * ");
//            }
//            System.out.println();
//        }

//        Parent root = FXMLLoader.load(getClass().getResource("AprioriUI/AprioriUI.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root));
////        root.getStylesheets().add("/home/weiss/CODES/TP-DM/dmtp/styles/slider.css");
//        primaryStage.setResizable(false);
//        primaryStage.show();
//        Platform.setImplicitExit(true);


//    }


    public static void main(String[] args) throws IOException {
//        launch(args);

        BufferedReader reader =
                new BufferedReader(new FileReader("/home/weiss/CODES/TP-DM/dmtp/data/breast-cancer.arff"));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();
        data.setClassIndex(data.numAttributes() - 1);
        DataCleaner dc = new DataCleaner(data);
        dc.normalizeAllAttributes();
        dc.fillInMissingValues();
        KNNClassifier knn = new KNNClassifier(data,5);
        System.out.println("data = " + data.instance(45));
        String cl = knn.classify(data.instance(45));
        System.out.println("cl = " + cl);

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

}
