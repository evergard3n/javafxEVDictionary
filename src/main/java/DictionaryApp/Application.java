package DictionaryApp;

import DictionaryApp.DictionaryCmdLine.DictionaryManagement;
import DictionaryApp.DictionaryCmdLine.DictionaryUtils;

public class Application {
    public static void main(String[] args) {
        DictionaryManagement.insertFromFile("src/dictionaries.txt");
        DictionaryUtils.dictionaryAdvanced();
    }
}
