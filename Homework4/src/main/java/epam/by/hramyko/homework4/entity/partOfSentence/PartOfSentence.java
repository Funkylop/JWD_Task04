package epam.by.hramyko.homework4.entity.partOfSentence;

public interface PartOfSentence extends Comparable<PartOfSentence> {
    String getSymbol();
    void setSymbol(String s);
}
