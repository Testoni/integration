package org.testoni.utils.file;

import org.testoni.exception.FileException;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileReaderText extends FileReader implements IFileReader {
    @Override
    public List<File> getFilesFromPath(String path) throws FileException {
        File file = new File(path);
        validateFile(path, file);

        if (file.isDirectory()) {
            return Arrays.stream(file.listFiles())
                    .filter(this::isTextFile)
                    .collect(Collectors.toList());
        } else {
            return Arrays.asList(file);
        }
    }

    protected void validateFile(String path, File file) throws FileException {
        super.validateFile(path, file);
        if (file.isFile() && !isTextFile(file)) {
            throw new FileException("File is not a valid text file: " + path);
        }
    }

    private boolean isTextFile(File file) {
        return file.getName().toLowerCase().endsWith(".txt");
    }
}
