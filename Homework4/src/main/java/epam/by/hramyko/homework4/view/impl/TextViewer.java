package epam.by.hramyko.homework4.view.impl;

import epam.by.hramyko.homework4.entity.Code;
import epam.by.hramyko.homework4.entity.Sentence;
import epam.by.hramyko.homework4.entity.Text;
import epam.by.hramyko.homework4.view.Viewer;

import java.util.List;

public class TextViewer implements Viewer {
    public static void printText(Text text) {
        int k = 0;
        List<Code> codeList = text.getCode();
        List<Sentence> sentences = text.getSentence();
        for (Sentence sentence : sentences) {
            System.out.println(sentence.toString());
            if ((sentence.toString().contains("Example"))) {
                for (int i = k; i < codeList.size(); i++) {
                    k = i;
                    if (!codeList.get(i).toString().contains("End")) {
                        System.out.println(codeList.get(i).toString());
                    } else {
                        System.out.println(codeList.get(i).toString());
                        if(k!=codeList.size()-1) {
                            k++;
                        }
                        break;
                    }
                }
            }
        }
    }
}
