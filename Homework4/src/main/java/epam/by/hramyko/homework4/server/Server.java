package epam.by.hramyko.homework4.server;

import epam.by.hramyko.homework4.configuration.FileConfiguration;
import epam.by.hramyko.homework4.server.service.Operation;
import epam.by.hramyko.homework4.entity.Text;
import epam.by.hramyko.homework4.view.impl.TextViewer;
import epam.by.hramyko.homework4.server.service.reader.Reader;

import java.net.*;
import java.io.*;

public class Server {
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) {
        String path = "text.txt";
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4004);
            try {
                clientSocket = serverSocket.accept();
                Server.OpenInputOutputStreams(clientSocket);
                Text text;
                FileConfiguration configuration = FileConfiguration.configurationFile(path);
                Reader fileDataReader = configuration.getFileDataReader();
                text = fileDataReader.createText(path);
                TextViewer.printText(text);
                int codeOfOperation = in.readInt();
                text = Operation.taskSelection(text, codeOfOperation, in, out);
                out.writeObject(text);
                out.flush();
            } catch (IOException exception) {
                exception.printStackTrace();
                System.exit(-1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(2);
            } finally {
                try {
                    out.close();
                    in.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    System.exit(1);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                System.exit(-1);
            }
        }
    }
    private static void OpenInputOutputStreams(Socket client){
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }
}
