package epam.by.hramyko.homework4.server;

import epam.by.hramyko.homework4.configuration.FileConfiguration;
import epam.by.hramyko.homework4.entity.Operation;
import epam.by.hramyko.homework4.entity.Text;
import epam.by.hramyko.homework4.view.impl.TextViewer;
import epam.by.hramyko.homework4.server.reader.Reader;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {
        ObjectOutputStream out;
        ObjectInputStream in;
        String path = "text.txt";
        try {
            ServerSocket serverSocket = new ServerSocket(4004);
            Socket clientSocket = serverSocket.accept();
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            Text text;
            FileConfiguration configuration = FileConfiguration.configurationFile(path);
            Reader fileDataReader = configuration.getFileDataReader();
            text = fileDataReader.create(path);
            TextViewer.printText(text);
            int code = in.read();

            text = Operation.taskSelection(text, code, in, out);
            TextViewer.printText(text);
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
