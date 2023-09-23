package org.testoni.utils;

import org.testoni.exception.FileException;

import java.io.File;

public class FileReaderBuilder {

    public File searchFile(String path) throws FileException {
        File file = new File(path);
        validateFile(path, file);
        return file;
    }

    private void validateFile(String path, File file) throws FileException {
        if (!file.exists()) {
            throw new FileException("File or directory not found: " + path);
        }
        if (file.isDirectory()) {
            throw new FileException("Please selected the file not directory");
        }
        if (file.isFile() && !isTextFile(file)) {
            throw new FileException("File is not a valid text file: " + path);
        }
    }

    private boolean isTextFile(File file) {
        return file.getName().toLowerCase().endsWith(".txt");
    }

}
