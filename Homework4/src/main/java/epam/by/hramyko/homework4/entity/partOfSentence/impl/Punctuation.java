package epam.by.hramyko.homework4.entity.partOfSentence.impl;

import epam.by.hramyko.homework4.entity.partOfSentence.PartOfSentence;

import java.io.Serializable;

public class Punctuation implements PartOfSentence, Serializable {
    private String punctuation;

    public String getSymbol() {
        return punctuation;
    }

    public void setSymbol(String punctuation) { this.punctuation = punctuation; }

    public int compareTo(PartOfSentence o) {
        return Integer.compare(this.getSymbol().length(), o.getSymbol().length());
    }
}
