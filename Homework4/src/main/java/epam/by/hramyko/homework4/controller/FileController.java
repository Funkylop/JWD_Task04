package epam.by.hramyko.homework4.controller;

import epam.by.hramyko.homework4.entity.Text;
import epam.by.hramyko.homework4.view.impl.FileViewer;

import java.io.*;
import java.net.Socket;

public class FileController {

    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public void request(int taskNumber) {
        Socket client = null;
        try {
            client = new Socket("localhost", 4004);
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                OpenInputOutputStreams(client);
                out.writeInt(taskNumber);
                out.flush();
                Text text = null;
                while (text==null) {
                    Object obj = in.readObject();
                    if (obj instanceof Text) {
                        text = (Text) obj;
                        FileViewer.printMessage(text, "Result:");
                    } else {
                        FileViewer.printMessage(obj);
                        out.writeObject(reader.readLine());
                        out.flush();
                    }
                }
            }catch (IOException exception){
                exception.printStackTrace();
                System.exit(-1);
            }catch (ClassNotFoundException exception){
                exception.printStackTrace();
                System.exit(2);
            }finally {
                try {
                    in.close();
                    out.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                    System.exit(-1);
                }
            }
        }catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }finally {
            try {
                if (client!=null) {
                    client.close();
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
