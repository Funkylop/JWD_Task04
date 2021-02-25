package epam.by.hramyko.homework4.entity;

import epam.by.hramyko.homework4.entity.partOfSentence.PartOfSentence;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Code implements Serializable {
    private List<PartOfSentence> blocksOfCode;


    public Code() {
    }

    public Code(List<PartOfSentence> blocksOfCode) {
        this.blocksOfCode = blocksOfCode;
    }

    public List<PartOfSentence> getBlocksOfCode() {
        return blocksOfCode;
    }

    public void setBlocksOfCode(List<PartOfSentence> blocksOfCode) {
        this.blocksOfCode = blocksOfCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return Objects.equals(blocksOfCode, code.blocksOfCode);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + blocksOfCode.hashCode();
        return result;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PartOfSentence code : blocksOfCode){
            sb.append(code.getSymbol());
        }
        return sb.toString();
    }
}
