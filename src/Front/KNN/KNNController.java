package Front.KNN;

import Algorithms.Apriori.InstanceApriori;
import Algorithms.Apriori.Searcher;
import Algorithms.KNN.KNNClassifier;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import weka.core.Instances;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class KNNController implements Initializable {


    public Instances currentIntance;
    //    Searcher sr = new Searcher(ia,400,0.75);
    public File currFile = null;


    @FXML
    private JFXButton launch;

    @FXML
    private JFXButton loadFile;

    @FXML
    private JFXSlider KSlider;

    @FXML
    private JFXTextField KText;

    @FXML
    private JFXSlider ratioSlider;

    @FXML
    private JFXTextField ratioText;

    @FXML
    private JFXTextArea results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadFile.setDisable(true);
        loadFile.setVisible(false);

        KSlider.setMin(1);
        KSlider.setMax(1000);

        ratioSlider.setMin(0);
        ratioSlider.setMax(100);

        KSlider.setValue((double) (KSlider.getMax() + 1 - KSlider.getMin()) / 2);
        KText.setText(Double.toString(KSlider.getValue()));
        ratioSlider.setValue((double) (ratioSlider.getMax() - ratioSlider.getMin()) / 2);
        ratioText.setText(Double.toString(ratioSlider.getValue()));


        KSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            KSlider.setValue(Math.ceil(newValue.doubleValue()));
            KText.setText(Double.toString(KSlider.getValue()));


        });

        ratioSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            ratioSlider.setValue(Math.ceil(newValue.doubleValue()));
            ratioText.setText(Double.toString(ratioSlider.getValue()));


        });

        KText.textProperty().addListener((observable, oldValue, newValue) ->
        {


            if (Double.valueOf(newValue) >= KSlider.getMin() && Double.valueOf(newValue) <= KSlider.getMax()) {
                KSlider.setValue(Double.valueOf(newValue));
            }
        });


        ratioText.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (Double.valueOf(newValue) >= ratioSlider.getMin() && Double.valueOf(newValue) <= ratioSlider.getMax()) {
                ratioSlider.setValue(Double.valueOf(newValue));
            }
        });


//        loadFile.setOnAction(actionEvent -> {
//            FileChooser fileChooser = new FileChooser();
//            this.currFile= fileChooser.showOpenDialog(null);
//
//            if(currFile != null)
//            {
//                BufferedReader reader;
//                try
//                {
//                    currentIntance = AprioriInstanceReader.loadInstance(currFile);
//                    transactions.setText(String.valueOf(currentIntance));
//                    supMin.setMax(currentIntance.transactions.size());
//
//                } catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        });

        launch.setOnAction(actionEvent -> {
            if (currentIntance != null) {
                KNNClassifier knn = new KNNClassifier(currentIntance, (int) KSlider.getValue(), ratioSlider.getValue());
                TreeMap<String, String> map = knn.classifyTestSet(knn.testInstances);
                String res = "Les résultats de l'algorithme KNN sur le fichier " + currFile.getName() + "\n";
                res += "Le nombre de voisins K : " + KText.getText() + "\n";
                res += "Le ration de division de l'ensemble d'apprentissage est: " + ratioText.getText() + "\n";
                res += "La précision (accuracy) lors de l'évaluation est     " + knn.accuracy + "%\n";
                res += "Les instances de test avec leurs classes prédites sont : \n\n\n";
                res += classificationToString(map) + "\n";
                results.setText(res);


            }
        });

    }


    public void setup() {
        KSlider.setMax(3 * currentIntance.numClasses());
    }

    private String classificationToString(TreeMap<String, String> res) {
        String s = "";

        for (String key : res.keySet()) {
            String[] items = key.split(",");
            s +="Id :"+items[0]+"\n";
            s+= "Classe prédite : \t\t"+res.get(key)+"\n";
            s+="Class réelle : \t\t"+items[1]+"\n\n";
        }
        return s;
    }


}
