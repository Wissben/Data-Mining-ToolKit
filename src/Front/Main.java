package Front;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        BufferedReader reader =
                new BufferedReader(new FileReader("data/iris.arff"));
        ArffReader arff = new ArffReader(reader);
        Instances data = arff.getData();
        data.setClassIndex(data.numAttributes() - 1);
        System.out.println(data);

//        System.out.println("yes "+data.numInstances());
//        System.out.println("yes "+data.numAttributes());

        for (int i = 0; i < data.numInstances() ; i++) {
            Instance instance = data.instance(i);
            for (int j = 0; j < instance.numAttributes(); j++)
            {
                System.out.print(instance.attribute(j).name()+": "); //get Attribute 0 as String
                System.out.print(instance.value(j)+ " * ");
            }
            System.out.println();
        }

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
