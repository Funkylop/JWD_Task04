package epam.by.hramyko.homework4.entity;

import java.io.*;
import java.util.ArrayList;

public class Text implements Serializable {
    private ArrayList<Sentence> sentence = new ArrayList<>();
    private ArrayList<Code> code = new ArrayList<>();

    public void addSentence(Sentence s) {
        if(s!=null) {
            sentence.add(s);
        }
    }

    public void addCode(Code c) {
        if(c!=null){
            code.add(c);
        }
    }

    public ArrayList<Sentence> getSentence() {
        return sentence;
    }

    public void setSentence(ArrayList<Sentence> sentence) {
        this.sentence = sentence;
    }

    public ArrayList<Code> getCode() {
        return code;
    }

    public void setCode(ArrayList<Code> code) {
        this.code = code;
    }

}



