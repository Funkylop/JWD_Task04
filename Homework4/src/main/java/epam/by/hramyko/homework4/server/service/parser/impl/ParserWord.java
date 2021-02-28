package epam.by.hramyko.homework4.server.service.parser.impl;


import epam.by.hramyko.homework4.server.service.parser.Parser;
import epam.by.hramyko.homework4.entity.partOfSentence.impl.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserWord implements Parser {
    public static final String PATTERN_WORD = "([A-Za-z0-9])+";
    public List<Word> parseWord(String s){
        List<Word> words = new ArrayList<>();
        Pattern wordPattern = Pattern.compile(PATTERN_WORD);
        Matcher matcher = wordPattern.matcher(s);
        while(matcher.find()){
            Word word = new Word();
            word.setSymbol(matcher.group());
            words.add(word);
        }
        return words;
    }
}
