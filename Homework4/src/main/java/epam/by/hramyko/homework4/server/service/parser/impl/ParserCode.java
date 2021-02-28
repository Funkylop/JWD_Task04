package epam.by.hramyko.homework4.server.service.parser.impl;

import epam.by.hramyko.homework4.server.service.parser.Parser;
import epam.by.hramyko.homework4.entity.Code;
import epam.by.hramyko.homework4.entity.partOfSentence.PartOfSentence;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserCode implements Parser {
    public static final String CODE_PATTERN = ".*([;}{][^a-zA-Z {};)(\\d])|@\\w+|End\\s";
    public Code parse(List<PartOfSentence> c){
        StringBuilder s = new StringBuilder();
        Pattern wordPattern = Pattern.compile(CODE_PATTERN);
        for(PartOfSentence word: c){
            s.append(word.getSymbol());
        };
        Matcher matcher = wordPattern.matcher(s.toString());
        Code code = new Code();
        if(matcher.find()){
            code.setBlocksOfCode(c);
            return code;
        }
        else {
            return null;
        }
    }
}
