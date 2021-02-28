package epam.by.hramyko.homework4.server.service.reader;

import epam.by.hramyko.homework4.entity.Text;

public interface Reader {
    Text createText(String path);
}
