package epam.by.hramyko.homework4.configuration;

import epam.by.hramyko.homework4.factory.FileAbstractFactory;
import epam.by.hramyko.homework4.factory.factories.TextFactory;
import epam.by.hramyko.homework4.server.reader.Reader;

public class FileConfiguration {

    private Reader fileDataReader;
    private String filePath;
    public FileConfiguration(){}

    public FileConfiguration(FileAbstractFactory factory, String fileName) {
        filePath = getClass().getResource("/" + fileName).getPath();
        fileDataReader = factory.getReader();
    }

    public Reader getFileDataReader() {
        return fileDataReader;
    }

    public void setFileDataReader(Reader fileDataReader) {
        this.fileDataReader = fileDataReader;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public static FileConfiguration configurationFile(String path){
        FileAbstractFactory factory;
        FileConfiguration fileApplication = null;
            if (path.contains(".txt")){
                factory = new TextFactory();
                fileApplication = new FileConfiguration(factory, path);
            }
        return fileApplication;
    }
}
