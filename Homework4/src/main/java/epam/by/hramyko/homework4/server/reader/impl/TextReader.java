package epam.by.hramyko.homework4.server.reader.impl;

import epam.by.hramyko.homework4.entity.Text;
import epam.by.hramyko.homework4.entity.partOfSentence.PartOfSentence;
import epam.by.hramyko.homework4.entity.partOfSentence.impl.Punctuation;
import epam.by.hramyko.homework4.entity.partOfSentence.impl.Word;
import epam.by.hramyko.homework4.server.parser.impl.ParserCode;
import epam.by.hramyko.homework4.server.parser.impl.ParserPunctuation;
import epam.by.hramyko.homework4.server.parser.impl.ParserSentence;
import epam.by.hramyko.homework4.server.parser.impl.ParserWord;
import epam.by.hramyko.homework4.server.reader.Reader;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TextReader implements Reader {

    @Override
    public Text create(String path) {
    ParserCode pc = new ParserCode();
    ParserSentence ps = new ParserSentence();
    ParserPunctuation pp = new ParserPunctuation();
    ParserWord pw = new ParserWord();
    StringBuilder tempS = new StringBuilder();
    Text text = new Text();
    List<String> string = new ArrayList<>();
    List<Word> words;
    List<Punctuation> punctuationList;
    List<PartOfSentence> sentences = new ArrayList<>();
    InputStream fileInputStream;
    try {
        fileInputStream = new BufferedInputStream(
                new FileInputStream(getClass().getResource("/" + path).getPath()));
        int charXml;
        while ((charXml = fileInputStream.read()) != -1) {
            tempS.append((char) charXml);
            if((char) charXml=='\r'){
                string.add(tempS.toString());
                tempS = new StringBuilder();
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        System.exit(-1);
    }

    int k = 0;
    for(String s: string){
        int i;
        words = pw.parseWord(s);
        punctuationList = pp.parsePunctuation(s);
        k++;
        if(words.size()==0)
        {
            for(i = 0; i< punctuationList.size(); i++) {
                sentences.add(punctuationList.get(i));
            }
        }
        if(k==1) {
            for(i = 0;i < words.size(); i++) {
                sentences.add(words.get(i));
                sentences.add(punctuationList.get(i));
            }
        }
        else{
            for(i = 0;i < words.size(); i++) {
                sentences.add(punctuationList.get(i));
                sentences.add(words.get(i));
            }
            if(words.size()!=0){
                sentences.add(punctuationList.get(punctuationList.size()-1));
            }
        }
        text.addSentence(ps.parse(sentences));
        text.addCode(pc.parse(sentences));
        sentences = new ArrayList<>();
    }
    return text;
}
}
