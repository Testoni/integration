package org.testoni.utils.file;

import org.testoni.exception.FileException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface IFileReader {
    List<String> getStringLinesFromFile(List<File> files) throws FileException, FileNotFoundException;
    List<File> getFilesFromPath(String path) throws FileException;
}
