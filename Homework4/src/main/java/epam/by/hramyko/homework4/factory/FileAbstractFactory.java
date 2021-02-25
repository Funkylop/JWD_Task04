package epam.by.hramyko.homework4.factory;

import epam.by.hramyko.homework4.server.reader.Reader;

public interface FileAbstractFactory {
    Reader getReader();
}
