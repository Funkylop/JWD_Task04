package epam.by.hramyko.homework4.client;

import java.io.*;
import java.net.Socket;


public class Client {
    public static void main(String[] args){

        Socket clientSocket;
        ObjectOutputStream out;
        ObjectInputStream in;
        try{
            clientSocket = new Socket("127.0.0.1", 4004);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            int codeOfOperation = 15;
            int sizeOfTheWords = 4;
            out.write(codeOfOperation);
            out.flush();
            if(codeOfOperation == 4) {
                System.out.println("" + in.readObject() + sizeOfTheWords);
                out.write(sizeOfTheWords);
                out.flush();
            }
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
