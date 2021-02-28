package epam.by.hramyko.homework4.server.service.parser.impl;

import epam.by.hramyko.homework4.server.service.parser.Parser;
import epam.by.hramyko.homework4.entity.partOfSentence.PartOfSentence;
import epam.by.hramyko.homework4.entity.Sentence;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserSentence implements Parser {
        public static final String SENTENCE_PATTERN = ".+?[\\?\\!\\.]\\s|Example\\s";
    public Sentence parse(List<PartOfSentence> s) {
        StringBuilder tempS = new StringBuilder();
        for (PartOfSentence word : s) {
            tempS.append(word.getSymbol());
        }
        Pattern wordPattern = Pattern.compile(SENTENCE_PATTERN);
        Matcher matcher = wordPattern.matcher(tempS.toString());
        Sentence sentence = new Sentence();
        if (matcher.find()) {
            sentence.setWords(s);
            return sentence;
        } else {
            return null;
        }
    }
}
