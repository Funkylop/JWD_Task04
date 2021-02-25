package epam.by.hramyko.homework4.entity;

import epam.by.hramyko.homework4.entity.partOfSentence.PartOfSentence;
import epam.by.hramyko.homework4.entity.partOfSentence.impl.Word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sentence implements Serializable, Comparable<Sentence> {
    private List<PartOfSentence> words;
    public Sentence() {
    }

    public Sentence(List<PartOfSentence> words) {
        this.words = words;
    }

    public List<PartOfSentence> getWords() {
        return words;
    }

    public void setWords(List<PartOfSentence> words) {
        this.words = words;
    }

    public void setStrings(List<String> words)
    {
        List<PartOfSentence> tempWords = new ArrayList<>();
        for(String word : words){
            Word temp = new Word();
            temp.setSymbol(word);
            tempWords.add(temp);
        }
        this.words = tempWords;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + words.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PartOfSentence word : words){
            sb.append(word.getSymbol());
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Sentence o) {
        return Integer.compare(this.getWords().size(), o.getWords().size());
    }
}

