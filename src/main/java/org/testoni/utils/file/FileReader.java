package org.testoni.utils.file;

import org.testoni.exception.FileException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FileReader {
    public List<String> getStringLinesFromFile(List<File> files) throws FileException {
        List<String> lines = new ArrayList<>();

        files.stream().forEach(file -> {
            try (BufferedReader br = new BufferedReader(new java.io.FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                throw new FileException("Error reading file: " + file.getAbsolutePath(), e);
            }
        });
        return lines;
    }

    protected void validateFile(String path, File file) throws FileException {
        if (!file.exists()) {
            throw new FileException("File or directory not found: " + path);
        }
    }
}
