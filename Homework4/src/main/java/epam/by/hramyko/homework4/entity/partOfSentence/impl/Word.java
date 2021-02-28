package epam.by.hramyko.homework4.entity.partOfSentence.impl;

import epam.by.hramyko.homework4.entity.partOfSentence.PartOfSentence;

import java.io.Serializable;

public class Word implements PartOfSentence, Serializable {
    private String word;

    public String getSymbol() {
        return word;
    }

    public void setSymbol(String word) { this.word = word; }

    @Override
    public int compareTo(PartOfSentence o) {
        return Integer.compare(this.getSymbol().length(), o.getSymbol().length());
    }
}
