package epam.by.hramyko.homework4.server.reader;

import epam.by.hramyko.homework4.entity.Text;

public interface Reader {
    Text create(String path);
}
