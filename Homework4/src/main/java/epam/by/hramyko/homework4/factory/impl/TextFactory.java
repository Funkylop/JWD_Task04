package epam.by.hramyko.homework4.factory.impl;

import epam.by.hramyko.homework4.factory.FileAbstractFactory;
import epam.by.hramyko.homework4.server.service.reader.impl.TextReader;

public class TextFactory implements FileAbstractFactory {

    @Override
        public TextReader getReader() {
            return new TextReader();
        }
    }
