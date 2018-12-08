package Front.DBSCAN;

import Algorithms.DBScan.Cluster;
import Algorithms.DBScan.DBSCANClusterer;
import Algorithms.DBScan.Point;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import weka.core.Instances;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DBSCANController implements Initializable {

    public Instances currentIntance;
    //    Searcher sr = new Searcher(ia,400,0.75);
    public File currFile = null;


    @FXML
    private JFXButton launch;

    @FXML
    private JFXButton loadFile;

    @FXML
    private JFXSlider minPtsSlider;

    @FXML
    private JFXTextField minPtsText;

    @FXML
    private JFXSlider epsilonSlider;

    @FXML
    private JFXTextField epsilonText;

    @FXML
    private JFXTextArea results;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadFile.setDisable(true);
        loadFile.setVisible(false);

        minPtsSlider.setMin(3);
        minPtsSlider.setMax(1000);

        epsilonSlider.setMin(0.01);
        epsilonSlider.setMax(5);

        minPtsSlider.setValue((double) (minPtsSlider.getMax() + 1 - minPtsSlider.getMin()) / 2);
        minPtsText.setText(Double.toString(minPtsSlider.getValue()));
        epsilonSlider.setValue((double) (epsilonSlider.getMax() - epsilonSlider.getMin()) / 2);
        epsilonText.setText(Double.toString(epsilonSlider.getValue()));


        minPtsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            minPtsSlider.setValue(Math.ceil(newValue.doubleValue()));
            minPtsText.setText(Double.toString(minPtsSlider.getValue()));


        });

        epsilonSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            epsilonText.setText(Double.toString(epsilonSlider.getValue()));


        });

        minPtsText.textProperty().addListener((observable, oldValue, newValue) ->
        {


            if (Double.valueOf(newValue) >= minPtsSlider.getMin() && Double.valueOf(newValue) <= minPtsSlider.getMax()) {
                minPtsSlider.setValue(Double.valueOf(newValue));
            }
        });


        epsilonText.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (Double.valueOf(newValue) >= epsilonSlider.getMin() && Double.valueOf(newValue) <= epsilonSlider.getMax()) {
                epsilonSlider.setValue(Double.valueOf(newValue));
            }
        });



        launch.setOnAction(actionEvent -> {
            if (currentIntance != null) {

                DBSCANClusterer dbscan = new DBSCANClusterer(epsilonSlider.getValue(),minPtsSlider.getValue(),currentIntance);
                dbscan.start();
                ArrayList<Cluster> clusters = (ArrayList<Cluster>) dbscan.getClusters();

                String res = "Les résultats de l'algorithme DBSCAN sur le fichier " + currFile.getName() + "\n";
                res += "La distance minimale de densité Epsilon : " + epsilonText.getText() + "\n";
                res += "Le nombre de points  de clusterisation minimum est: " + minPtsText.getText() + "\n";
                res += "Le nombre de clusters est : " + clusters.size() +"\n";
                res += "L'inertie intra-classes est : " + dbscan.getIntraClassScore() +"\n";
                res += "L'inertie inter-classes est : " + dbscan.getInterClassScore() +"\n";
                res += "Les clusters construit sont : \n\n\n";
                res += clustersToString(clusters) + "\n";
                results.setText(res);


            }
        });
    }




    public void setup() {
        minPtsSlider.setValue(currentIntance.numAttributes()+1);
    }

    private String clustersToString(ArrayList<Cluster> res) {
        String s = "";
        int count = 1;
        for (Cluster c : res) {
            s +="Cluster : "+count+"\n Nombre d'éléments : "+c.getElements().size()+"\n Distance moyenne dans le cluster: " +c.getCenterOfCluster()+" \n[";
            for (Point p : c.getElements()) {

                s+= "\t\t"+p+"\n";
            }
            s+="]\n";
            count++;
        }
        return s;
    }
}
