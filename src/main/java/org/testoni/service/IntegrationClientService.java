package org.testoni.service;

import org.testoni.dto.UserIntegrationDto;
import org.testoni.exception.FileException;
import org.testoni.utils.FileParserBuilder;
import org.testoni.utils.FileReaderBuilder;
import org.testoni.model.User;
import org.testoni.utils.Parser;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class IntegrationClientService {

    private final FileReaderBuilder fileReaderBuilder;

    private final FileParserBuilder fileParserBuilder;

    public IntegrationClientService(FileReaderBuilder fileReaderBuilder, FileParserBuilder fileParserBuilder) {
        this.fileReaderBuilder = fileReaderBuilder;
        this.fileParserBuilder = fileParserBuilder;
    }

    public List<User> getUsersFromIntegration(String path) {
        List<User> users = new ArrayList<>();

        try {
            File file = fileReaderBuilder.searchFile(path);

            List<String> lines = fileParserBuilder.parseTextFile(file);
            if (lines.isEmpty()) {
                throw new FileException("There is no lines in the file to read");
            }

            int index = IntStream.range(0, lines.size())
                    .filter(i -> lines.get(i).length() != 95)
                    .findFirst()
                    .orElse(-1);
            if (index != -1) {
                throw new FileException("Integration file contains wrong line info at index " + index); // list
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

            // create a list of validations
            List<UserIntegrationDto> userIntegrationDtos = new ArrayList<>();
            lines.stream().forEach(line -> {
                Long userId = Parser.parseToLong(line.substring(0, 10).replaceFirst("^0+", ""));
                String name = line.substring(10, 55).trim();
                Long orderId = Parser.parseToLong(line.substring(55, 65).replaceFirst("^0+", ""));
                Long productId = Parser.parseToLong(line.substring(65, 75));
                Double value = Parser.parseToDouble(line.substring(75, 87));
                String dateStr = line.substring(87, 95);
                try {
                    dateFormat.parse(dateStr);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                userIntegrationDtos.add(new UserIntegrationDto(userId, name, orderId, dateStr, productId, value));
            });

            System.out.println("");
        } catch (FileException e) {
            e.printStackTrace();
        }

        return users;
    }
}
