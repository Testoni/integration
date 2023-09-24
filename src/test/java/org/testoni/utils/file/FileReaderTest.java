package org.testoni.utils.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testoni.exception.FileException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReaderTest {

    private FileReaderText fileReaderText;
    private String absolutePath;

    @BeforeEach
    public void setUp() {
        fileReaderText = new FileReaderText();

        ClassLoader classLoader = FileReaderTest.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("data_1.txt");
        absolutePath = new File(resourceUrl.getFile()).getAbsolutePath();
    }

    @Test
    public void shouldGetOneTextFileFromPath() throws FileException, IOException {
        List<File> textFiles = fileReaderText.getFilesFromPath(absolutePath);
        assertEquals(1, textFiles.size());
    }

    @Test
    public void shouldGetTextFileListFromPath() throws FileException, IOException {
        ClassLoader classLoader = FileReaderTest.class.getClassLoader();
        File resourcesDirectory = new File(classLoader.getResource("").getFile());
        List<File> textFiles = fileReaderText.getFilesFromPath(resourcesDirectory.getAbsolutePath());
        assertEquals(2, textFiles.size());
    }

    @Test
    public void shouldThrowFileExceptionWhenPathNotFound() {
        String nonExistentPath = "non_existent_path";
        FileException exception = assertThrows(FileException.class, () -> {
            fileReaderText.getFilesFromPath(nonExistentPath);
        });
        assertEquals("File or directory not found: " + nonExistentPath, exception.getMessage());
    }

    @Test
    public void shouldThrowFileExceptionWhenFileIsNotText() throws IOException {
        ClassLoader classLoader = FileReaderTest.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("invalid/data.csv");
        absolutePath = new File(resourceUrl.getFile()).getAbsolutePath();

        FileException exception = assertThrows(FileException.class, () -> {
            fileReaderText.getFilesFromPath(absolutePath);
        });

        assertEquals("File is not a valid text file: " + absolutePath, exception.getMessage());
    }

    @Test
    public void shouldThrowFileExceptionOnIOException() {
        File invalidFile = new File("non_existent_file.txt");
        List<File> files = Collections.singletonList(invalidFile);

        FileException exception = assertThrows(FileException.class, () -> {
            fileReaderText.getStringLinesFromFile(files);
        });

        assertEquals("Error reading file: " + invalidFile.getAbsolutePath(), exception.getMessage());
    }

}
