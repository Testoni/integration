package org.testoni.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileParserBuilder {

    public List<String> parseTextFile(List<File> files) {
        List<String> lines = new ArrayList<>();

        files.stream().forEach(file -> {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return lines;
    }
}
