package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//Класс отвечающий за запись рейтинга в txt
public class MyFileWriter {
    private String filename;
    public MyFileWriter(String filename) {
        this.filename = filename;
    }

    public void writeToFile(String text) {
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            fileWriter.write(text);
            fileWriter.write(System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}