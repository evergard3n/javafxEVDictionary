package DictionaryApp.DictionaryCmdLine;

import java.util.Objects;

public class Word {
    private String word_Target;
    private String word_explain;

    public String getWord_Target() {
        return word_Target;
    }

    public void setWord_Target(String word_Target) {
        this.word_Target = word_Target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public Word(String word_Target, String word_explain) {
        this.word_Target = word_Target;
        this.word_explain = word_explain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word word)) return false;
        return Objects.equals(word_Target, word.word_Target) && Objects.equals(word_explain, word.word_explain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word_Target, word_explain);
    }

    @Override
    public String toString() {
        return word_Target
                +"  "
                + word_explain;
    }
}
