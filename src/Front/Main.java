package Front;

import Front.AprioriUI.AprioriUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


//public class Main{
public class Main extends Application {


    private AprioriUI controller;


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

        Parent root = FXMLLoader.load(getClass().getResource("AprioriUI/AprioriUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
//        root.getStylesheets().add("/home/weiss/CODES/TP-DM/dmtp/styles/slider.css");
        primaryStage.setResizable(false);
        primaryStage.show();
        Platform.setImplicitExit(true);


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
