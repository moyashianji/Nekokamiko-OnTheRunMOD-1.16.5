package com.example.nekokamiko.config;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FileVariable {

    public void FileVariable() {
    }
    public int

    public void valuable(){

    String filePath = "ファイルのパス"; // テキストファイルのパスを指定してください

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        int lineNumber = 1;

        while ((line = br.readLine()) != null) {
            if (lineNumber == 2) {
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    double aa = Double.parseDouble(matcher.group());
                    System.out.println("二行目の数字: " + aa);
                }

                break;
            }
            lineNumber++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
