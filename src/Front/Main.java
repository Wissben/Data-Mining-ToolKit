package Front;

import Processing.Apriori.InstanceApriori;
import Processing.Apriori.InstanceReader;
import Processing.Apriori.Searcher;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringStack;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;


//public class Main{
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

//        BufferedReader reader =
//                new BufferedReader(new FileReader("data/iris.arff"));
//        ArffReader arff = new ArffReader(reader);
//        Instances data = arff.getData();
//        data.setClassIndex(data.numAttributes() - 1);
//        System.out.println(data);

//        System.out.println("yes "+data.numInstances());
//        System.out.println("yes "+data.numAttributes());

//        for (int i = 0; i < data.numInstances() ; i++) {
//            Instance instance = data.instance(i);
//            for (int j = 0; j < instance.numAttributes(); j++)
//            {
//                System.out.print(instance.attribute(j).name()+": "); //get Attribute 0 as String
//                System.out.print(instance.value(j)+ " * ");
//            }
//            System.out.println();
//        }

        Parent root = FXMLLoader.load(getClass().getResource("Algorithm.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        launch(args);
//        InstanceApriori ia = InstanceReader.loadInstance(new File("/home/weiss/CODES/TP-DM/dmtp/data/car.data.txt"));
//        ia.printTransactions();
//        Searcher sr = new Searcher(ia,400,0.75);
//        sr.search();
//        TreeMap<String, Double>  r = sr.getAssRules();
//        System.out.println("r = " + r);
//        TreeMap<String,Integer> Ls = sr.Ls;
//        System.out.println("Ls = " + Ls);
//
//        BufferedReader reader =
//                new BufferedReader(new FileReader("/home/weiss/CODES/TP-DM/weka-3-8-3/data/iris.arff"));
//        ArffReader arff = new ArffReader(reader);
//        Instances data = arff.getData();
//        data.setClassIndex(data.numAttributes() - 1);
//        DataCleaner dc = new DataCleaner(data);
//        dc.normalizeAllAttributes();
//        dc.fillInMissingValues();
//        System.out.println(data);
//        System.out.println("new StatisticsRetriever(data).symetry(0) = " + new StatisticsRetriever(data).symetry(0));

    }

}
