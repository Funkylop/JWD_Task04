package epam.by.hramyko.homework4.view.impl;

import epam.by.hramyko.homework4.entity.Text;
import epam.by.hramyko.homework4.view.Viewer;

public class FileViewer implements Viewer {
    public static void printMessage(Object obj) {
        System.out.println(obj);
    }
    public static void printMessage(Text text, String s){
        System.out.println(s);
        TextViewer.printText(text);
    }
}
