package org.testoni.service;

import org.testoni.dto.UserIntegrationDto;
import org.testoni.exception.FileException;
import org.testoni.model.Order;
import org.testoni.model.Product;
import org.testoni.model.User;
import org.testoni.utils.FileParserBuilder;
import org.testoni.utils.FileReaderBuilder;
import org.testoni.utils.Parser;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

public class IntegrationClientService {

    private final FileReaderBuilder fileReaderBuilder;
    private final FileParserBuilder fileParserBuilder;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public IntegrationClientService(FileReaderBuilder fileReaderBuilder, FileParserBuilder fileParserBuilder) {
        this.fileReaderBuilder = fileReaderBuilder;
        this.fileParserBuilder = fileParserBuilder;
    }

    public String getJsonUsersIntegration(String path) {
        List<User> users = getUsersFromIntegration(path);
        return Parser.toJson(users);
    }

    public List<User> getUsersFromIntegration(String path) {
        List<User> users = new ArrayList<>();

        try {
            File file = fileReaderBuilder.searchFile(path);

            List<String> lines = fileParserBuilder.parseTextFile(file);
            validateLinesContent(lines);

            lines.stream().forEach(line -> insertUserData(users, line));
        } catch (FileException e) {
            e.printStackTrace();
        }

        Collections.sort(users, Comparator.comparingLong(User::getUserId));
        return users;
    }

    private void validateLinesContent(List<String> lines) throws FileException {
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
    }

    private void insertUserData(List<User> users, String line) {
        UserIntegrationDto userIntegrationDto = getUserIntegrationFromFile(line);

        User user = users.stream().filter(u -> u.getUserId().equals(userIntegrationDto.getUserId())).findFirst().orElse(null);
        if (user == null) {
            users.add(new User(userIntegrationDto));
        } else {
            Order order = user.getOrders().stream().filter(o -> o.getOrderId().equals(userIntegrationDto.getOrderId())).findFirst().orElse(null);
            if (order == null) {
                user.getOrders().add(new Order(userIntegrationDto));
                Collections.sort(user.getOrders(), Comparator.comparingLong(Order::getOrderId));
            } else {
                order.setTotal(order.getTotal() + userIntegrationDto.getValue());
                order.getProducts().add(new Product(userIntegrationDto));
                Collections.sort(order.getProducts(), Comparator.comparingLong(Product::getProductId));
            }
        }
    }

    private UserIntegrationDto getUserIntegrationFromFile(String line) {
        Long userId = Parser.StringToLong(line.substring(0, 10).replaceFirst("^0+", ""));
        String name = line.substring(10, 55).trim();
        Long orderId = Parser.StringToLong(line.substring(55, 65).replaceFirst("^0+", ""));
        Long productId = Parser.StringToLong(line.substring(65, 75));
        Double value = Parser.stringToDouble(line.substring(75, 87));
        String dateStr = line.substring(87, 95);
        try {
            Date date = this.dateFormat.parse(dateStr);
            dateStr = this.outputDateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new UserIntegrationDto(userId, name, orderId, dateStr, productId, value);
    }
}
