package epam.by.hramyko.homework4.client;

import epam.by.hramyko.homework4.controller.FileController;

import java.io.*;


public class Client {
    public static void main(String[] args){
        FileController fileController = new FileController();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int taskNumber = Integer.parseInt(reader.readLine());
            fileController.request(taskNumber);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }
}
