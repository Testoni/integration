package org.testoni.utils;

import org.testoni.exception.FileException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReaderBuilder {

    public List<File> searchFile(String path) throws FileException {
        List<File> files = new ArrayList<>();

        File file = new File(path);
        validateFile(path, file);

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (isTextFile(f)) {
                    files.add(f);
                }
            }
            return files;
        } else {
            return Arrays.asList(file);
        }
    }

    private void validateFile(String path, File file) throws FileException {
        if (!file.exists()) {
            throw new FileException("File or directory not found: " + path);
        }
        if (file.isFile() && !isTextFile(file)) {
            throw new FileException("File is not a valid text file: " + path);
        }
    }

    private boolean isTextFile(File file) {
        return file.getName().toLowerCase().endsWith(".txt");
    }

}
