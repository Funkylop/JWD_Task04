package epam.by.hramyko.homework4.server.service;

import epam.by.hramyko.homework4.entity.Sentence;
import epam.by.hramyko.homework4.entity.Text;
import epam.by.hramyko.homework4.entity.partOfSentence.*;
import epam.by.hramyko.homework4.entity.partOfSentence.impl.Punctuation;
import epam.by.hramyko.homework4.entity.partOfSentence.impl.Word;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Operation {
        public static Text taskSelection (Text text, int taskNumber, ObjectInputStream in, ObjectOutputStream out)
                    throws IOException, ClassNotFoundException {

            switch (taskNumber) {
                case 1 -> {
                    return findSentenceWithSameWords(text);
                }
                case 2 -> {
                    return SortSentenceByWords(text);
                }
                case 3 -> {
                    return findWordThatIsInFirstSentenceButNotOther(text);
                }
                case 4 -> {
                    out.writeObject("Enter the size of words you want to search for: ");
                    out.flush();
                    int lengthOfWords = Integer.parseInt((String) in.readObject());
                    return findWordsOfGivenLengthInQuestionSentence(text, lengthOfWords);
                }
                case 5 -> {
                    return replaceFirstWordOfSentenceWithLastWord(text);
                }
                case 6 -> {
                    return printWordsInAlphabeticalOrderByFirstLetter(text);
                }
                case 7 -> {
                    return SortWordsByIncreasingProportionOfVowels(text);
                }
                case 8 -> {
                    return WordsStartingWithVowelsSortedAlphabeticallyByFirstConsonantLetterOfWord(text);
                }
                case 9 -> {
                    out.writeObject("Enter the character: ");
                    out.flush();
                    char character = in.readObject().toString().charAt(0);
                    return sortWordsByIncreasingNumberLetterInWord(text, character);
                }
                case 10 -> {
                    List<String> strings = new ArrayList<>();
                    String word;
                    do {
                        out.writeObject("Enter list: ");
                        out.flush();
                        word = (String) in.readObject();
                        strings.add(word);
                        out.writeObject("Continue: y/n");
                        out.flush();
                    } while ("y".equals(in.readObject()));
                    return forEachWordFromListFindHowManyTimesItOccursInSentencesAndSortIt(text, strings);
                }
                case 11 -> {
                    out.writeObject("Enter the first letter of the word: ");
                    out.flush();
                    char firstCharacter = in.readObject().toString().charAt(0);
                    out.writeObject("Enter the last letter of the word: ");
                    out.flush();
                    char lastCharacter = in.readObject().toString().charAt(0);
                    return removeSubStringMaxLengthInEverySentence(text, firstCharacter, lastCharacter);
                }
                case 12 -> {
                    out.writeObject("Enter the size of the words you want to delete for: ");
                    out.flush();
                    int lengthOfWord = Integer.parseInt((String) in.readObject());
                    return deleteWordsOfGivenLengthThatStartWithConsonantLetter(text, lengthOfWord);
                }
                case 13 -> {
                    out.writeObject("Enter one letter: ");
                    out.flush();
                    char character = in.readObject().toString().charAt(0);
                    return SortWordsDescendingOrderOfNumberOfOccurrencesOfGivenCharacter(text, character);
                }
                case 14 -> {
                    return findSubStringMaxLengthThatIsPalindrome(text);
                }
                case 15 -> {
                    return convertWordsByRemovingFirstAndLastLetterOccurrences(text);
                }
                case 16 -> {
                    out.writeObject("Enter a substring: ");
                    out.flush();
                    String subString = (String) in.readObject();
                    out.writeObject("Enter a length word: ");
                    out.flush();
                    int length = Integer.parseInt((String) in.readObject());
                    return replaceWordsOfGivenLengthSubString(text, subString, length);
                }
                default -> System.out.println("This task never exist");

            }
            return null;
        }

        public static Text findSentenceWithSameWords (Text text){
            List<Sentence> sentences = text.getSentence();
            ArrayList<Sentence> foundSentence = new ArrayList<>();
            for (Sentence sentence : sentences) {
                List<String> words = new ArrayList<>();
                for (PartOfSentence word : sentence.getWords()) {
                    words.add(word.getSymbol());
                }
                boolean isWordRepeated = false;
                for (PartOfSentence word : sentence.getWords()) {
                    words.remove(word.getSymbol());
                    if (words.contains(word.getSymbol()) && !(word instanceof Punctuation)) {
                        isWordRepeated = true;
                        break;
                    }
                }

                if (isWordRepeated) {
                    foundSentence.add(sentence);
                }
            }
            text.setSentence(foundSentence);
            return text;
        }

        public static Text SortSentenceByWords (Text text){
            List<Sentence> sentences = text.getSentence();
            Collections.sort(sentences);
            return text;
        }

        public static Text findWordThatIsInFirstSentenceButNotOther (Text text){
            Sentence firstSentence = text.getSentence().get(0);
            List<PartOfSentence> words = new ArrayList<>();
            List<PartOfSentence> foundWords = new ArrayList<>();
            for (PartOfSentence wordInFirstSentence : firstSentence.getWords()) {
                boolean isFoundWord = true;
                if(!(wordInFirstSentence instanceof Punctuation)) {
                    isFoundWord = false;
                    for (Sentence sentence : text.getSentence()) {
                        if (!sentence.equals(firstSentence)) {
                            words.addAll(sentence.getWords());
                            for (PartOfSentence wordOtherSentences : words) {
                                if (wordOtherSentences.getSymbol().equals(wordInFirstSentence.getSymbol())
                                        && !(wordOtherSentences instanceof Punctuation)) {
                                    isFoundWord = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!isFoundWord) {
                    foundWords.add(wordInFirstSentence);
                }
            }
            if(foundWords.size()!=0) {
                text.getSentence().clear();
                text.setSentence(new ArrayList<>());
                text.getSentence().add(new Sentence());
                text.getSentence().get(0).setWords(foundWords);
            }
            return text;
        }

        public static Text findWordsOfGivenLengthInQuestionSentence (Text text,int length){
            List<PartOfSentence> words = new ArrayList<>();
            for (Sentence sentence : text.getSentence()) {
                if (sentence.toString().contains("?")) {
                    for (PartOfSentence word : sentence.getWords()) {
                        if (word.getSymbol().length() == length && !(word instanceof Punctuation)) {
                            if (!words.contains(word)) {
                                words.add(word);
                            }
                        }
                    }
                }
            }
            text.getSentence().clear();
            text.setSentence(new ArrayList<>());
            text.getSentence().add(new Sentence());
            text.getSentence().get(0).setWords(words);
            return text;
        }

        public static Text replaceFirstWordOfSentenceWithLastWord (Text text){
            for (Sentence sentence : text.getSentence()) {
                PartOfSentence firstWord = sentence.getWords().get(1);
                PartOfSentence lastWord = sentence.getWords().get(sentence.getWords().size() - 2);

                sentence.getWords().remove(1);
                sentence.getWords().add(1, lastWord);

                sentence.getWords().remove(sentence.getWords().size() - 2);
                sentence.getWords().add(sentence.getWords().size()-1, firstWord);

            }
            return text;
        }

        public static Text printWordsInAlphabeticalOrderByFirstLetter (Text text){
            List<String> words = new ArrayList<>();
            for (Sentence sentence : text.getSentence()) {
                for (PartOfSentence word : sentence.getWords()) {
                    if(!(word instanceof Punctuation)) {
                        word.setSymbol(word.getSymbol().toLowerCase());
                        words.add(word.getSymbol());
                    }
                }
            }
            Collections.sort(words);
            for (int i = 1; i < words.size(); i++) {
                if (words.get(i - 1).charAt(0) == words.get(i).charAt(0)) {
                    words.set(i-1, "\t " + words.get(i - 1));
                } else {
                    words.set(i - 1,"\t " + words.get(i - 1) + "\n");
                }
            }
            text.getSentence().clear();
            ArrayList<Sentence> sentence = new ArrayList<>();
            sentence.add(new Sentence());
            sentence.get(0).setStrings(words);
            text.setSentence(sentence);
            return text;
        }

        public static Text SortWordsByIncreasingProportionOfVowels (Text text){
            List<PartOfSentence> words = new ArrayList<>();
            String vowels = "a e i o u y";
            Map<String, Double> vowelsProportion = new HashMap<>();
            for (Sentence sentence : text.getSentence()) {
                words.addAll(sentence.getWords());
            }
            for (PartOfSentence word : words) {
                if (!(word instanceof Punctuation)) {
                    double countVowelsLetter = 0;
                    double countLetterInWord = word.getSymbol().length();
                    for (int i = 1; i <= countLetterInWord; i++) {
                        String letter = word.getSymbol().toLowerCase().substring(i - 1, i);
                        if (vowels.contains(letter)) {
                            countVowelsLetter++;
                        }
                    }
                    double proportionOfVowels = countVowelsLetter / countLetterInWord;
                    vowelsProportion.put(word.getSymbol(), proportionOfVowels);
                }
            }

            words.clear();
            double valueOfProportion = 0;
            String nameOfWord = null;
            for (int i = 0; i < vowelsProportion.size(); i++) {
                for (Map.Entry<String, Double> word : vowelsProportion.entrySet()) {
                    if (valueOfProportion > word.getValue() || valueOfProportion == 0) {
                        nameOfWord = word.getKey();
                        valueOfProportion = word.getValue();
                    }
                }
                words.add(new Word());
                words.get(i).setSymbol(nameOfWord);
                vowelsProportion.remove(nameOfWord);
                valueOfProportion = 0;
            }
            text.getSentence().clear();
            text.setSentence(new ArrayList<>());
            text.getSentence().add(new Sentence());
            text.getSentence().get(0).setWords(words);

            return text;
        }

        public static Text WordsStartingWithVowelsSortedAlphabeticallyByFirstConsonantLetterOfWord (Text text){
            String vowels = "a e i o u y";
            Character[] consonantLetters =
                    {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x', 'z', 'w'};

            List<PartOfSentence> words = new ArrayList<>();
            for (Sentence sentence : text.getSentence()) {
                for(PartOfSentence sentence1 : sentence.getWords()){
                   if(!(sentence1 instanceof Punctuation)){
                       words.add(sentence1);
                   }
                }
            }
            List<PartOfSentence> word = new ArrayList<>();
            for (int i = 0; i < words.size(); i++) {
                int minPositionOfWord = 0;
                int position = 0;
                char character = 0;
                char minCharacter = 0;
                for (int j = i; j < words.size(); j++) {
                    boolean isFoundCharacter = false;
                    String nameOfWord = words.get(j).getSymbol().toLowerCase();
                    if (vowels.contains(nameOfWord.substring(0, 1))) {
                        for (int k = 1; k < nameOfWord.length(); k++) {
                            for (Character consonantCharacter : consonantLetters) {
                                if (nameOfWord.charAt(k) == consonantCharacter && minCharacter == 0) {
                                    minCharacter = consonantCharacter;
                                    minPositionOfWord = j;
                                    isFoundCharacter = true;
                                    break;
                                } else if (nameOfWord.charAt(k) == consonantCharacter) {
                                    character = consonantCharacter;
                                    position = j;
                                    isFoundCharacter = true;
                                    break;
                                }
                            }
                            if (isFoundCharacter) {
                                break;
                            }
                        }
                    }
                    if (minCharacter > character && character != 0) {
                        minPositionOfWord = position;
                    }
                }
                if (position != 0 && minPositionOfWord != 0) {
                    String temp = words.get(i).getSymbol();
                    words.get(i).setSymbol(words.get(minPositionOfWord).getSymbol() + "\n");
                    words.get(minPositionOfWord).setSymbol(temp + "\n");
                }
            }
            for (PartOfSentence partOfSentence : words) {
                String nameOfWord = partOfSentence.getSymbol().toLowerCase();
                if (vowels.contains(nameOfWord.substring(0, 1))) {
                    word.add(partOfSentence);
                }
            }
            text.getSentence().clear();
            text.setSentence(new ArrayList<>());
            text.getSentence().add(new Sentence());
            text.getSentence().get(0).setWords(word);
            return text;
        }

        public static Text sortWordsByIncreasingNumberLetterInWord (Text text,char character){

            List<PartOfSentence> words = new ArrayList<>();
            for (Sentence sentence : text.getSentence()) {
                for(PartOfSentence sentence1 : sentence.getWords()){
                    if(!(sentence1 instanceof Punctuation)){
                        words.add(sentence1);
                    }
                }
            }

            for (int i = 0; i < words.size(); i++) {
                int positionWordsWithMinMatches = i;
                int minCountOfMatchesWithCharacter = 0;
                int countOfMatchesWithCharacter;
                for (int j = 0; j < words.size() - i; j++) {
                    countOfMatchesWithCharacter = 0;
                    String nameOfWord = words.get(j).getSymbol();
                    for (int k = 0; k < nameOfWord.length(); k++) {
                        if (nameOfWord.charAt(k) == character) {
                            countOfMatchesWithCharacter++;
                        }
                    }
                    if (countOfMatchesWithCharacter > minCountOfMatchesWithCharacter) {
                        positionWordsWithMinMatches = j;
                        minCountOfMatchesWithCharacter = countOfMatchesWithCharacter;
                    }
                }
                String name = words.get(words.size() - 1 - i).getSymbol();
                words.get(words.size() - 1 - i).setSymbol(words.get(positionWordsWithMinMatches).getSymbol());
                words.get(positionWordsWithMinMatches).setSymbol(name);

            }
            return text;
        }

        public static Text forEachWordFromListFindHowManyTimesItOccursInSentencesAndSortIt (Text
        text, List < String > listWords){

            List<Sentence> sentences = text.getSentence();
            for (int i = 0; i < sentences.size(); i++) {
                int positionSentenceWithMaxMatches = i;
                int maxCountOfMatchesWithList = 0;

                for (int j = i + 1; j < sentences.size(); j++) {
                    int countOfMatchesWithList = 0;
                    for (PartOfSentence word : sentences.get(j - 1).getWords()) {
                        if (!(word instanceof Punctuation)) {
                            if (listWords.contains(word.getSymbol())) {
                                countOfMatchesWithList++;
                            }
                        }
                    }
                    if (maxCountOfMatchesWithList < countOfMatchesWithList) {
                        positionSentenceWithMaxMatches = j - 1;
                        maxCountOfMatchesWithList = countOfMatchesWithList;
                    }
                }
                List<PartOfSentence> wordsOfSentence = sentences.get(i).getWords();
                sentences.get(i).setWords(sentences.get(positionSentenceWithMaxMatches).getWords());
                sentences.get(positionSentenceWithMaxMatches).setWords(wordsOfSentence);

            }
            return text;
        }

        public static Text removeSubStringMaxLengthInEverySentence (Text text,char firstLetter, char lastLetter){
            for (Sentence sentence : text.getSentence()) {
                int positionWordWithMaxLength = 0;
                int wordWithMaxLength = -1;
                List<PartOfSentence> words = sentence.getWords();
                for (int i = 0; i < words.size(); i++) {
                    if (!(words.get(i) instanceof Punctuation)) {
                        int start = words.get(i).toString().indexOf(firstLetter);
                        int end = words.get(i).toString().lastIndexOf(lastLetter);
                        int length = end - start;

                        if (start >= 0 && end > 0 && length >= 0) {
                            if (wordWithMaxLength < length) {
                                wordWithMaxLength = length;
                                positionWordWithMaxLength = i;
                            }
                        }
                    }
                }
                if (positionWordWithMaxLength != 0) {
                    words.remove(positionWordWithMaxLength);
                }
            }
            return text;
        }

        public static Text deleteWordsOfGivenLengthThatStartWithConsonantLetter (Text text,int length){
            String consonantLetters = "b c d f g h j k l m n p q r s t v x y z w";
            for (Sentence sentence : text.getSentence()) {
                List<PartOfSentence> words = sentence.getWords();
                for (int i = 0; i < words.size(); i++) {
                    if (!(words.get(i) instanceof Punctuation)) {
                        if (words.get(i).getSymbol().length() == length
                                && consonantLetters.contains(words.get(i).getSymbol().toLowerCase().substring(0, 1))) {
                            words.remove(i--);
                        }
                    }
                }
            }
            return text;
        }

        public static Text SortWordsDescendingOrderOfNumberOfOccurrencesOfGivenCharacter (Text text,char character){

            List<PartOfSentence> words = new ArrayList<>();
            for (Sentence sentence : text.getSentence()) {
                for(PartOfSentence sentence1 : sentence.getWords()){
                    if(!(sentence1 instanceof Punctuation)){
                        words.add(sentence1);
                    }
                }
            }
            int positionOfWordWithMaxCharacter = -1;
            for (int i = 0; i < words.size(); i++) {
                int countMatchesCharInPreviousFoundWord = 0;
                for (int j = i; j < words.size(); j++) {
                    int numberOfMatchesOfSpecifiedCharacter = 0;
                    String name = words.get(j).getSymbol();
                    while (!name.isEmpty()) {
                        int index;
                        if ((index = name.indexOf(character)) != -1) {
                            name = name.substring(index + 1);
                            numberOfMatchesOfSpecifiedCharacter++;
                        } else {
                            name = "";
                        }
                    }
                    if (countMatchesCharInPreviousFoundWord < numberOfMatchesOfSpecifiedCharacter) {
                        positionOfWordWithMaxCharacter = j;
                        countMatchesCharInPreviousFoundWord = numberOfMatchesOfSpecifiedCharacter;
                    } else if (countMatchesCharInPreviousFoundWord == numberOfMatchesOfSpecifiedCharacter) {
                        if (words.get(j).compareTo(words.get(positionOfWordWithMaxCharacter)) < 0) {
                            String nameWord = words.get(j).getSymbol();
                            words.get(j).setSymbol(words.get(positionOfWordWithMaxCharacter).getSymbol());
                            words.get(positionOfWordWithMaxCharacter).setSymbol(nameWord);
                        }
                    }
                }
                String nameWord = words.get(i).getSymbol();
                words.get(i).setSymbol(words.get(positionOfWordWithMaxCharacter).getSymbol());
                words.get(positionOfWordWithMaxCharacter).setSymbol(nameWord);
            }

            text.getSentence().clear();
            text.setSentence(new ArrayList<>());
            text.getSentence().add(new Sentence());
            text.getSentence().get(0).setWords(words);
            return text;
        }

        public static Text findSubStringMaxLengthThatIsPalindrome (Text text){
            int lengthPalindromeWord = 0;
            Map<String, Integer> palindromeInfo = new HashMap<>();
            List<PartOfSentence> words = new ArrayList<>();
            for (Sentence sentence : text.getSentence()) {
                words = sentence.getWords();
                for (PartOfSentence word : words) {
                    if (!(word instanceof Punctuation)) {
                        String nameOfWord = word.getSymbol();
                        for (int j = 0; j < nameOfWord.length(); j++) {
                            int lastCharacter = nameOfWord.length() - 1;
                            for (int k = lastCharacter; k > j; k--) {

                                String start = nameOfWord.substring(j, j + 1);
                                String last = nameOfWord.substring(k, k + 1);
                                if (start.equals(last)) {
                                    if (lengthPalindromeWord == 0) {
                                        lengthPalindromeWord = k - j + 1;
                                    }
                                    j++;
                                } else {
                                    lengthPalindromeWord = 0;
                                }
                            }
                            if (lengthPalindromeWord != 0) {
                                palindromeInfo.put(word.getSymbol(), lengthPalindromeWord);
                                lengthPalindromeWord = 0;
                            }

                        }
                    }
                }
            }

            String wordPalindrome = null;
            lengthPalindromeWord = 0;
            for (Map.Entry<String, Integer> palindrome : palindromeInfo.entrySet()) {
                if (lengthPalindromeWord < palindrome.getValue() || lengthPalindromeWord == 0) {
                    wordPalindrome = palindrome.getKey();
                    lengthPalindromeWord = palindrome.getValue();
                }
            }

            words.clear();
            Word word = new Word();
            word.setSymbol(wordPalindrome);
            words.add(word);

            text.getSentence().clear();
            text.setSentence(new ArrayList<>());
            text.getSentence().add(new Sentence());
            text.getSentence().get(0).setWords(words);
            return text;
        }

        public static Text convertWordsByRemovingFirstAndLastLetterOccurrences (Text text){
            for (Sentence sentence : text.getSentence()) {
                for (PartOfSentence word : sentence.getWords()) {
                    if (!(word instanceof Punctuation)) {
                        String firstLetter = word.getSymbol().substring(0, 1);
                        String lastLetter = word.getSymbol().substring(word.getSymbol().length() - 1);
                        if ("?".equals(lastLetter) || "?".equals(firstLetter)) {
                            lastLetter = "\\?";
                        }
                        word.setSymbol(word.getSymbol().replaceAll(firstLetter, "").replaceAll(lastLetter, ""));
                    }
                }
            }
            return text;
        }

        public static Text replaceWordsOfGivenLengthSubString (Text text, String subString,int length){
            for (Sentence sentence : text.getSentence()) {
                for (PartOfSentence word : sentence.getWords()) {
                    if (!(word instanceof Punctuation)) {
                        if (word.getSymbol().length() == length) {
                            word.setSymbol(subString);
                        }
                    }
                }
            }
            return text;
        }

}