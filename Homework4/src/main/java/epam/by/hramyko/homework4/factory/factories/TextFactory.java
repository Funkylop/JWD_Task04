package epam.by.hramyko.homework4.factory.factories;

import epam.by.hramyko.homework4.factory.FileAbstractFactory;
import epam.by.hramyko.homework4.server.reader.impl.TextReader;

public class TextFactory implements FileAbstractFactory {

    @Override
        public TextReader getReader() {
            return new TextReader();
        }
    }
