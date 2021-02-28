package epam.by.hramyko.homework4.server.service.parser.impl;

import epam.by.hramyko.homework4.server.service.parser.Parser;
import epam.by.hramyko.homework4.entity.partOfSentence.impl.Punctuation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserPunctuation implements Parser {
    public static final String PATTERN_PUNCTUATION = "([^a-zA-z0-9]*[\\[][\\]])|[\\\\]*[^a-zA-Z0-9]+";
    public List<Punctuation> parsePunctuation(String s){
        List<Punctuation> punctuations = new ArrayList<>();
        Pattern punctuationPattern = Pattern.compile(PATTERN_PUNCTUATION);
        Matcher matcher = punctuationPattern.matcher(s);
        while(matcher.find()){
            Punctuation punctuation = new Punctuation();
            punctuation.setSymbol(matcher.group());
            punctuations.add(punctuation);
        }
        return punctuations;
    }
}
