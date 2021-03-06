package Front;

import Algorithms.Apriori.AprioriInstanceReader;
import Algorithms.Apriori.InstanceApriori;
import Front.AprioriUI.AprioriController;
import Processing.DataCleaner;
import Processing.Plotter;
import Processing.StatisticsRetriever;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import javafx.scene.control.cell.MapValueFactory;

import javafx.util.Callback;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable
{

    LinkedList<TableColumn<Map,String>> columns = new LinkedList<>();

    Instances originalData,activeData,activeCleanData;

    File chosen;

    @FXML
    private JFXButton loadFile;

    @FXML
    private TableView<Map> table;

    @FXML
    private TableView<Map> tableOrigin;

    @FXML
    private TableView<Map> valuesTable;

    @FXML
    private Button showBox;


    @FXML
    private Label nbInstances;

    @FXML
    private Label nbAttribut;

    @FXML
    private TableView<Map> cleanedTable;

    @FXML
    private HBox containerHBox;

    @FXML
    private TabPane tabPane;

    @FXML
    private JFXButton launchApriori;

    @FXML
    private JFXButton launchKNN;

    @FXML
    private AnchorPane anchorTop;

    @FXML
    private JFXButton launchDBSCAN;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loadFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            chosen = fileChooser.showOpenDialog(null);
            if(chosen != null)
            {
                BufferedReader reader;
                try
                {
                    reader = new BufferedReader(new FileReader(chosen));
                    ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
                    setActiveData(arff.getData());
                    reader = new BufferedReader(new FileReader(chosen));
                    setActiveCleanData(new ArffLoader.ArffReader(reader).getData());
                    reader = new BufferedReader(new FileReader(chosen));
                    setOriginalData(new ArffLoader.ArffReader(reader).getData());
//                    tabPane.setStyle("");


                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        showBox.setOnAction(actionEvent -> {
            if(activeData!=null)
            {
                Plotter plotter = new Plotter(activeCleanData);
                plotter.plotBox();
            }
        });

        launchApriori.setOnAction(actionEvent->
        {
            if(activeData!=null && chosen != null)
            {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "AprioriUI/AprioriUI.fxml"
                        )
                );

                Stage stage = new Stage(StageStyle.DECORATED);
                try {
                    stage.setScene(
                            new Scene(
                                  loader.load()
                            )
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }

                AprioriController controller =
                        loader.<AprioriController>getController();

                InstanceApriori currentIntance = AprioriInstanceReader.loadInstance(activeCleanData);
                controller.currentIntance=currentIntance;
                controller.currFile=chosen;
                controller.setup();

                stage.show();
            }
        });


        launchKNN.setOnAction(actionEvent->
        {
            if(activeData!=null && chosen != null)
            {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "KNN/KNNUI.fxml"
                        )
                );

                Stage stage = new Stage(StageStyle.DECORATED);
                try {
                    stage.setScene(
                            new Scene(
                                    loader.load()
                            )
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Front.KNN.KNNController controller =loader.<Front.KNN.KNNController>getController();

                controller.currentIntance=activeCleanData;
                controller.currFile=chosen;
                controller.setup();

                stage.show();
            }
        });



        launchDBSCAN.setOnAction(actionEvent->
        {
            if(activeData!=null && chosen != null)
            {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(
                                "DBSCAN/DBSCANUi.fxml"
                        )
                );

                Stage stage = new Stage(StageStyle.DECORATED);
                try {
                    stage.setScene(
                            new Scene(
                                    loader.load()
                            )
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Front.DBSCAN.DBSCANController controller =loader.<Front.DBSCAN.DBSCANController>getController();

                controller.currentIntance=activeData;
                controller.currFile=chosen;
                controller.setup();

                stage.show();
            }
        });

    }

    void setOriginalData(Instances instances)
    {
        originalData = instances;
        originalData.setClassIndex(originalData.numAttributes()-1);
        refreshOriginalTable();
    }

    void setActiveCleanData(Instances instances)
    {
        activeCleanData = instances;
        activeCleanData.setClassIndex(activeCleanData.numAttributes()-1);
        /**
         @Ressaye Uncomment these two lines if you want to normalize then fill in missing values
         **/

        DataCleaner dc = new DataCleaner(activeCleanData);
        dc.fillInMissingValues();
        dc.normalizeAllAttributes();

        refreshCleanTable();
    }

    void setActiveData(Instances instances)
    {
        activeData = instances;
        activeData.setClassIndex(activeData.numAttributes() - 1);
        DataCleaner dc = new DataCleaner(activeData);
        dc.fillInMissingValues();
        refreshTables();
        refreshLabels();
    }

    void refreshLabels()
    {
        nbInstances.setText("Nombre d'instances: "+activeData.numInstances());
        nbAttribut.setText("Nombre d'attributs: "+activeData.numAttributes());
    }

    void refreshTables()
    {
        refreshTable();
        refreshValuesTable();
    }

    void refreshCleanTable()
    {
        cleanedTable.getColumns().clear();
        cleanedTable.setItems(generateDataInMap(activeCleanData));
        Callback<TableColumn<Map, String>, TableCell<Map, String>> factory = getCellFactoryMap();
        for (int i = 0; i < activeCleanData.numAttributes(); i++)
        {
            TableColumn<Map,String> col = new TableColumn<>(activeCleanData.attribute(i).name());
            col.setCellValueFactory(new MapValueFactory<>(activeCleanData.attribute(i).name()));
            col.setCellFactory(factory);
            cleanedTable.getColumns().addAll(col);
        }
    }

    void refreshOriginalTable()
    {
        tableOrigin.getColumns().clear();
        tableOrigin.setItems(generateDataInMap(originalData));
        Callback<TableColumn<Map, String>, TableCell<Map, String>> factory = getCellFactoryMap();
        for (int i = 0; i < originalData.numAttributes(); i++)
        {
            TableColumn<Map,String> col = new TableColumn<>(originalData.attribute(i).name());
            col.setCellValueFactory(new MapValueFactory<>(originalData.attribute(i).name()));
            col.setCellFactory(factory);
            tableOrigin.getColumns().addAll(col);
        }
    }

    void refreshTable()
    {
        table.getColumns().clear();
        table.setItems(generateDataInMap(activeData));
        Callback<TableColumn<Map, String>, TableCell<Map, String>> factory = getCellFactoryMap();
        for (int i = 0; i < activeData.numAttributes(); i++)
        {
            TableColumn<Map,String> col = new TableColumn<>(activeData.attribute(i).name());
            col.setCellValueFactory(new MapValueFactory<>(activeData.attribute(i).name()));
            col.setCellFactory(factory);
            columns.add(col);
            table.getColumns().addAll(col);
        }
    }

    void refreshValuesTable()
    {
        columns.clear();
        valuesTable.getColumns().clear();
        valuesTable.setItems(generateDataValuesInMap());
        Callback<TableColumn<Map, String>, TableCell<Map, String>> factory = getCellFactoryMap();
        String columns[] = {"nom","type","min","max","mediane","Q1","Q3","mode","moyenne","midrange","Symetrique","Mean-Mode=3(Mode-Median)"};
        for (int i = 0; i < columns.length; i++)
        {
            TableColumn<Map,String> col = new TableColumn<>(columns[i]);
            col.setCellValueFactory(new MapValueFactory<>(columns[i]));
            col.setCellFactory(factory);
            valuesTable.getColumns().addAll(col);
        }

        valuesTable.setRowFactory(tv -> {
            TableRow<Map> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    Map clickedRow = row.getItem();

                    int index = getAttributeIndexFromName((String) clickedRow.get("nom"));
                    System.out.println("clicked row: "+ clickedRow.get("nom") + " index " + index);
                    if(index >= 0)
                    {
                        Plotter plotter = new Plotter(activeData);
//                        if(activeData.attribute(index).isNominal())
                            plotter.plotHisto(index);
//                        else
//                            plotter.plotBox(index);
                    }
                }
            });
            return row ;
        });
    }

    private int getAttributeIndexFromName(String name)
    {
        for (int i = 0; i < activeData.numAttributes(); i++)
        {
            if(activeData.attribute(i).name().equals(name))
                return i;
        }
        return -1;
    }

    private ObservableList<Map> generateDataValuesInMap()
    {
        ObservableList<Map> allData = FXCollections.observableArrayList();

        for (int i = 0; i < activeData.numAttributes(); i++)
        {
            Map<String, String> dataRow = new HashMap<>();
            ArrayList<Double> numericValues = new ArrayList<>();
            ArrayList<String> stringValues = new ArrayList<>();
            Attribute a = activeData.attribute(i);
            for (int j = 0; j < activeData.numInstances(); j++)
            {
                if(a.isNumeric())
                    numericValues.add(activeData.instance(j).value(i));
                else
                    stringValues.add(activeData.instance(j).stringValue(i));
            }
            dataRow.put("nom",activeData.attribute(i).name());
            String type = (a.isNumeric())?"Numerique":"Nominale";
            dataRow.put("type",type);
            StatisticsRetriever retriever = new StatisticsRetriever(activeData);

            int sym = retriever.symetry(i);
            String symetry = sym==-1?"Négativement":sym==1?"Positivement":sym==0?"Symétrique":"Non";

            String isSymetric = retriever.isSymetric(i)?"Oui":"Non";

//            String symetry = retriever.symetry(i)?"Oui":"Non";
            if(a.isNumeric())
            {
                dataRow.put("min", String.format("%.2f", retriever.getMin(i)));
                dataRow.put("max", String.format("%.2f", retriever.getMax(i)));
                dataRow.put("mediane", String.format("%.2f", retriever.getMedian(i)));
                dataRow.put("Q1", String.format("%.2f", retriever.getQ1(i)));
                dataRow.put("Q3", String.format("%.2f", retriever.getQ3(i)));
                dataRow.put("moyenne", String.format("%.2f", retriever.getMean(i)));
                dataRow.put("midrange",String.format("%.2f", retriever.getMidRange(i)));
                dataRow.put("Symetrique",symetry);
                dataRow.put("Mean-Mode=3(Mode-Median)",isSymetric);
            }
            else
            {
                dataRow.put("min",  "-");
                dataRow.put("max", "-");
                dataRow.put("mediane", "-");
                dataRow.put("Q1", "-");
                dataRow.put("Q3", "-");
                dataRow.put("moyenne", "-");
                dataRow.put("midrange","-");
                dataRow.put("Symetrique","-");
                dataRow.put("Mean-Mode=3(Mode-Median)","-");
            }
            dataRow.put("mode",retriever.getMode(i)+"");
            allData.add(dataRow);
        }

        return allData;
    }

    private ObservableList<Map> generateDataInMap(Instances activeData) {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 1; i < activeData.numInstances(); i++) {
            Map<String, String> dataRow = new HashMap<>();
            Instance instance = activeData.instance(i);
            for (int j = 0; j < instance.numAttributes(); j++)
            {

                String v = (instance.attribute(j).isNumeric())?""+instance.value(j):instance.stringValue(j);
                dataRow.put(instance.attribute(j).name(),v);
            }
            allData.add(dataRow);
        }
        return allData;
    }

    Callback<TableColumn<Map, String>,TableCell<Map, String>> getCellFactoryMap()
    {
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = p -> new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return t.toString();
                    }
                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });
        return cellFactoryForMap;
    }
}
