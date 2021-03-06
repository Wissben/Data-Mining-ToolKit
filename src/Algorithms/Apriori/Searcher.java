package Algorithms.Apriori;

import Algorithms.Apriori.InstanceApriori;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Searcher {


    InstanceApriori instance;
    public TreeMap<String, Double> rules = new TreeMap<>();
    private final int supMin;
    private final double confMin;
    public TreeMap<String, Integer> Ls = new TreeMap<>();

    public Searcher(InstanceApriori instance, int supMin, double confMin) {
        this.instance = instance;
        this.supMin = supMin;
        this.confMin = confMin;
    }


    public int getFreqOfItemSet(TreeSet<String> itemset) {
        int freq = 0;
        for (TreeSet<String> key : instance.labeledTransactions) {
            if (key.containsAll(itemset)) {
                freq++;
            }
        }
        return freq;
    }

    public TreeMap<String, Integer> getFreqSets(ArrayList<TreeSet<String>> C) {
        TreeMap<String, Integer> res = new TreeMap<>(new ItemComaparator());
        for (int i = 0; i < C.size(); i++) {
            int freq = getFreqOfItemSet(C.get(i));
            if (freq >= supMin) {
                res.put(setToString(C.get(i)), freq);
            }
        }
        return res;
    }


    public ArrayList<TreeSet<String>> generateCandidates(ArrayList<TreeSet<String>> prev) {
        ArrayList<TreeSet<String>> res = new ArrayList<>();
        if (prev == null) {

            TreeSet<String> union = new TreeSet<>();
            for (TreeSet<String> key : instance.labeledTransactions) {
                union.addAll(key);
            }
            for (String s : union) {
                res.add(new TreeSet<>(Collections.singleton(s)));
            }
            return res;
        }
        for (int i = 0; i < prev.size(); i++) {

            for (int j = i + 1; j < prev.size(); j++) {
                TreeSet<String> union = (TreeSet<String>) prev.get(i).clone();
                union.addAll(prev.get(j));
                if (getFreqOfItemSet(union) >= supMin)
                    res.add(union);
            }

        }

        return res;
    }


    public void search() {
        ArrayList<TreeSet<String>> prev = null;
        ArrayList<TreeSet<String>> L = generateCandidates(prev);
        TreeMap<String, Integer> fr;
        while (true) {
            fr = getFreqSets(L);
            Ls.putAll(fr);
            prev = L;
            L = generateCandidates(prev);
            if (L.containsAll(prev) && prev.containsAll(L))
                break;
        }

        TreeMap<String, Double> unsrt = getAssRules();
        this.rules = new TreeMap<>(new rulesComparator(unsrt));
        this.rules.putAll(unsrt);
//        System.out.println("rules = " + rules);

    }


    public TreeMap<String, Double> getAssRules() {
        TreeMap<String, Double> res = new TreeMap<>();
        for (String itemSetString : Ls.keySet()) {

            TreeSet<String> itemSet = stringToSet(itemSetString);
            ArrayList<TreeSet<String>> subs = getSubSets(itemSet);
            subs.remove(0);
            subs.remove(subs.size() - 1);

            for (int i = 0; i < subs.size(); i++) {
                for (int j = i + 1; j < subs.size(); j++) {
                    TreeSet<String> inters = (TreeSet<String>) subs.get(i).clone();
                    inters.retainAll(subs.get(j));
                    if (inters.isEmpty()) {
//                        System.out.println("inters = " + inters);
//                        System.out.println("subs i = " + subs.get(i));
//                        System.out.println("subs j = " + subs.get(j));

                        TreeSet<String> union = (TreeSet<String>) subs.get(i).clone();
                        union.addAll(subs.get(j));
                        double conf = (double) getFreqOfItemSet(union) / (double) getFreqOfItemSet(subs.get(i));
                        String rule = setToString(subs.get(i));
                        if (conf >= confMin) {
                            rule += ",--->" + setToString(subs.get(j));
                            res.put(rule, conf);
                        }

                        conf = (double) getFreqOfItemSet(union) / (double) getFreqOfItemSet(subs.get(j));
                        rule = setToString(subs.get(j));
                        if (conf > confMin) {
                            rule += ",--->" + setToString(subs.get(i));

                            res.put(rule, conf);

                        }


                    }
                }
            }


        }
        return res;
    }


    static String setToString(TreeSet<String> set) {
        String[] tmp = set.toArray(new String[set.size()]);
        String s = "";
        for (int i = 0; i < tmp.length; i++) {
            s += tmp[i] + ",";
        }
        s = s.substring(0, s.length() - 1);
//        s+="}";
        return s;
    }

    static TreeSet<String> stringToSet(String s) {
        String[] tmp = s.split(",");
        TreeSet<String> res = new TreeSet<>(Arrays.asList(tmp));
        return res;
    }


    private class ItemComaparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            if (str1.length() == str2.length())
                return str1.compareTo(str2);
            if (str1.length() < str2.length())
                return -1;
            return 1;
        }

    }

    private class rulesComparator implements Comparator<String> {

        TreeMap<String, Double> map;

        public rulesComparator(Map<String, Double> map) {
            this.map = (TreeMap<String, Double>) map;
        }

        @Override
        public int compare(String a, String b) {
            return -map.get(a).compareTo(map.get(b));
        }

    }

    public ArrayList<TreeSet<String>> getSubSets(TreeSet<String> set) {
        ArrayList<TreeSet<String>> subs = new ArrayList<>();
        String[] setArr = set.stream().toArray(String[]::new);
        int n = setArr.length;
        for (int i = 0; i < (1 << n); i++) {
            TreeSet<String> tmp = new TreeSet<>();
            for (int j = 0; j < n; j++)
                if ((i & (1 << j)) > 0) {
                    tmp.add(setArr[j]);
                }
            subs.add(tmp);
        }
        return subs;
    }


}
