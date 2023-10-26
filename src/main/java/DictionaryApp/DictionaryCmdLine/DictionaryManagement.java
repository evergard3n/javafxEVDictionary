package DictionaryApp.DictionaryCmdLine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static DictionaryApp.DictionaryCmdLine.Dictionary.dictionary;

public class DictionaryManagement  {

//    public String wordSearch(String word_Target) {
//        if (DictionaryUtils.dict.containsKey(word_Target)) {
//            return dictionary.get(word_Target).getWord_explain();
//        } else {
//            return null;
//        }
//    }
    /**Scanner ini.*/
    public static Scanner scn = new Scanner(System.in);

    public static void closeScanner() {
        scn.close();
    }
    /**Look up*/
//    public static String dictionaryLookUp(String target) {
//        if(Dictionary.dictionary.containsKey(target)) {
//            Word dest  = Dictionary.dictionary.get(target);
//            return dest.toString();
//        }
//        else {
//            return "Not found!";
//        }
//    }

    public static Word dictionaryLookUp(String target) {
        Word temp = null;
        try {
            dictionary.sort(new SortWords());
            int left = 0;
            int right = dictionary.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = dictionary.get(mid).getWord_Target().compareTo(target);
                if (res == 0) {
                    temp = dictionary.get(mid);
                }
                if (res <= 0) {
                    left = mid + 1;

                } else {
                    right = mid - 1;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
        return temp;
    }
    public static int searchForIndex(String target) {
        try {
            dictionary.sort(new SortWords());
            int left = 0;
            int right = dictionary.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int res = dictionary.get(mid).getWord_Target().compareTo(target);
                if (res == 0) {
                    return mid;
                }
                if (res <= 0) {
                    left = mid + 1;

                } else {
                    right = mid - 1;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Null Exception.");
        }
        return -1;
    }
    /**Insert from cmd line.*/
    public static void insertFromCmdline() {
        System.out.println("Insert number of words");

        int N = scn.nextInt();
        System.out.println("Insert words and their definations");
        for (int i = 1; i <= N; i++) {
            String wordTarget = scn.next();
            scn.nextLine();
            String wordDef = scn.nextLine();
            dictionary.add( new Word(wordTarget, wordDef));
        }
        dictionary.sort(new SortWords());

    }
//    public static void insertFromSql() {
//        try {
//            Connection conn = JBDCUtil.getConnection();
//
//        }
//        catch(SQLException e) {
//            e.printStackTrace();
//        }
//    }
    //delete
    public static void deleteWord(String target) {
        Word w = dictionaryLookUp(target);
        if(w!=null) {
            dictionary.remove(w);
        }
        else {
            System.out.println("No such word exists!");
        }
    }
    public static void deleteWordNP(int index, String src) {
        dictionary.remove(index);
        exportToFile(src);
    }
    //update
    public static void updateWord(String target) {
        Word w = dictionaryLookUp(target);
        if(w!=null) {
            System.out.println("Enter new definition: ");
            String def = scn.nextLine();
            w.setWord_explain(def);
            scn.nextLine();
        }
        else {
            System.out.println("No such word exists!");
        }
    }
    public static void updateWordNP(int index, String newExp, String src ) {
        dictionary.get(index).setWord_explain(newExp);
        exportToFile(src);
    }


    /**Insert from file*/
    public static void insertFromFile(String src) {
        try {
            File file = new File(src);
            Scanner scnF = new Scanner(file);
            while (scnF.hasNextLine()) {
                String line = scnF.nextLine();

                String[] stack = line.split("\t");
                if(stack.length == 2) {
                    Word newWord = new Word(stack[0],stack[1]);
                    dictionary.add(newWord);
                }
                else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            scnF.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void setTimeout( Runnable runnable , int delay ) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
    public static void exportToFile(String src)  {
        try {
            FileWriter fw = new FileWriter(src);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Word w: dictionary) {
                String res = w.toString() +"\n";
                bw.write(res);
            }
            bw.flush();
            bw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
    public static javafx.collections.ObservableList<String> lookUpWord (String key) {
        ObservableList<String> list = FXCollections.observableArrayList();
        dictionary.sort(new SortWords());
        for(Word w: dictionary) {
            String target = w.getWord_Target();
            if(target.length()<key.length()) {
                if(target.substring(0,key.length()-1).equals(key)) {
                    list.add(w.getWord_Target());
                }
            }
        }
        return list;

    }
    public static void EXIT() {
        closeScanner();

        System.exit(1);
    }




}
