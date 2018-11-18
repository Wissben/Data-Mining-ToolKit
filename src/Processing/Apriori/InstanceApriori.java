package Processing.Apriori;

import java.util.*;

public class InstanceApriori {


    public ArrayList<TreeSet<String>> transactions ;

    public InstanceApriori()
    {
        transactions = new ArrayList<>();

    }

    public void addTransaction(int id, String[] items)
    {
        TreeSet<String> tmp = new TreeSet<>();
        for (int i = 0; i < items.length; i++) {
            tmp.add(items[i]);
        }
        transactions.add(id,tmp);
    }

    public void printTransactions()
    {
        for(TreeSet<String> key : transactions)
        {
            System.out.println(key);
        }
    }



}