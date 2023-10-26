package DictionaryApp.DictionaryCmdLine;

import java.util.Comparator;

public class SortWords implements Comparator<Word> {
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord_Target().compareTo(o2.getWord_Target());
    }
}
