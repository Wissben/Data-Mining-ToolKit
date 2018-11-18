package Front.AprioriUI;

import Processing.Apriori.InstanceApriori;
import Processing.Apriori.InstanceReader;
import Processing.Apriori.Searcher;
import Processing.Plotter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sun.reflect.generics.tree.Tree;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class AprioriUI implements Initializable {


    public InstanceApriori currentIntance;
//    Searcher sr = new Searcher(ia,400,0.75);
    File currFile = null;



    @FXML
    private JFXButton launch;

    @FXML
    private JFXButton loadFile;

    @FXML
    private JFXSlider supMin;

    @FXML
    private JFXTextField supMinText;

    @FXML
    private JFXSlider confMin;

    @FXML
    private JFXTextField confMinText;

    @FXML
    private JFXTextArea transactions;

    @FXML
    private JFXTextArea results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {


        supMin.setMin(1);
        supMin.setMax(1000);

        confMin.setMin(0);
        confMin.setMax(100);

        supMin.setValue((double)(supMin.getMax()+1-supMin.getMin())/2);
        supMinText.setText(Double.toString(supMin.getValue()));
        confMin.setValue((double)(confMin.getMax()-confMin.getMin())/2);
        confMinText.setText(Double.toString(confMin.getValue()));


        supMin.valueProperty().addListener((observable, oldValue, newValue) -> {

            supMinText.setText(Double.toString(newValue.intValue()));


        });

        confMin.valueProperty().addListener((observable, oldValue, newValue) -> {

            confMinText.setText(Double.toString(newValue.intValue()));


        });

        supMinText.textProperty().addListener((observable, oldValue, newValue) ->
        {


            if(Double.valueOf(newValue) >=supMin.getMin() && Double.valueOf(newValue) <= supMin.getMax())
            {
                supMin.setValue(Double.valueOf(newValue));
            }
        });


        confMinText.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if(Double.valueOf(newValue) >=confMin.getMin() && Double.valueOf(newValue) <= confMin.getMax())
            {
                confMin.setValue(Double.valueOf(newValue));
            }
        });


        loadFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            this.currFile= fileChooser.showOpenDialog(null);

            if(currFile != null)
            {
                BufferedReader reader;
                try
                {
                    currentIntance = InstanceReader.loadInstance(currFile);
                    transactions.setText(String.valueOf(currentIntance));

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        launch.setOnAction(actionEvent -> {
            if(currentIntance!=null)
            {
                Searcher sr = new Searcher(currentIntance, (int) supMin.getValue(),confMin.getValue()/100);
                System.out.println("confMin = " + confMin.getValue());
                sr.search();
                System.out.println("sr = " + sr);
                TreeMap<String, Double>  r = sr.rules;
//                System.out.println("r = " + r);
                TreeMap<String,Integer> Ls = sr.Ls;
//                System.out.println("Ls = " + Ls);
//                results.setText(String.valueOf(Ls));
//                System.out.println("Ls = " + Ls);

                String res = "Les résultats de l'algorithme Aapriori sur le fichier "+ currFile.getName()+"\n";
                res+="Le support minimum est : " + supMinText.getText()+"\n";
                res+="La confiance minimum est : " + confMinText.getText()+"\n";
                res+="Les itemsets fréquents sont les suivants : \n";
                res+=freqSetsToString(Ls)+"\n";
                res+="Les règles d'ass sont : \n";
                res+=assRulesToString(r)+"\n";
                results.setText(res);


            }
        });

    }


    private String freqSetsToString(TreeMap<String,Integer> res )
    {
        String s = "";
        for (String key: res.keySet()) {
            s+="{"+key+"} ==> "+res.get(key)+"\n";
        }
        return s;
    }


    private String assRulesToString(TreeMap<String,Double> res )
    {
        String s = "";
        for (String key: res.keySet()) {
            s+="{"+key+"} ==> "+res.get(key)+"\n";
        }
        return s;
    }

}
