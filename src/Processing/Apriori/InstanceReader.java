package Processing.Apriori;

import java.io.*;

public class InstanceReader {

    File location;
    InstanceApriori instance;
    public InstanceReader(File path){
        this.instance = new InstanceApriori();
        this.location = path;
    }


    public static InstanceApriori loadInstance(File path) throws IOException {
        InstanceReader ir = new InstanceReader(path);
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
