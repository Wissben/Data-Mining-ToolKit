package Algorithms.Apriori;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;

public class AprioriInstanceReader {

    File location;
    InstanceApriori instance;
    public AprioriInstanceReader(File path){
        this.instance = new InstanceApriori();
        this.location = path;
    }


    public static InstanceApriori loadInstance(Instances data){
        AprioriInstanceReader ir = new AprioriInstanceReader (null);
        String[] header = new String[data.numAttributes()];
        for (int i = 0; i < header.length; i++) {
            header[i] = data.attribute(i).name();
        }

        for (int i = 0; i < data.numInstances() ; i++) {
            Instance instance = data.instance(i);
            String[] items = new String[instance.numAttributes()];
            for (int k = 0; k < items.length; k++) {
                items[k] = instance.attribute(k).isNominal()?instance.stringValue(k):String.valueOf(instance.value(k));
            }

            ir.instance.addTransaction(i,items);

            for (int j = 0; j < items.length; j++) {
                items[j] =header[j]+" = "+items[j];
            }
            ir.instance.addLabeledTransaction(i,items);
        }
        return ir.instance;

    }


    public static InstanceApriori loadInstance(File path) throws IOException {
        AprioriInstanceReader ir = new AprioriInstanceReader(path);
        BufferedReader br = new BufferedReader(new FileReader(ir.location));
        String line = "";
        int id = 0;
        String[] header = br.readLine().split(",");

        while ((line = br.readLine()) != null) {
            String[] items = line.split(",");
            ir.instance.addTransaction(id,items);

            for (int i = 0; i < items.length; i++) {
                items[i] =header[i]+" = "+items[i];
            }
            ir.instance.addLabeledTransaction(id,items);
            id++;

        }
        return ir.instance;
    }

}
