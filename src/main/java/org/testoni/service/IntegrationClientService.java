package org.testoni.service;

import org.testoni.dto.UserIntegrationDto;
import org.testoni.exception.FileException;
import org.testoni.model.Order;
import org.testoni.model.Product;
import org.testoni.model.User;
import org.testoni.utils.UtilsParser;
import org.testoni.utils.file.IFileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

public class IntegrationClientService {

    private final IFileReader iFileReader;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static final int LINE_LENGTH = 95;

    public IntegrationClientService(IFileReader iFileReader) {
        this.iFileReader = iFileReader;
    }

    public String getJsonUsersIntegration(String path) {
        List<User> users = getUsersFromIntegration(path);
        return UtilsParser.toJson(users);
    }

    public List<User> getUsersFromIntegration(String path) {
        List<User> users = new ArrayList<>();

        try {
            List<File> files = iFileReader.getFilesFromPath(path);
            List<String> lines = iFileReader.getStringLinesFromFile(files);
            validateLinesContent(lines);
            lines.forEach(line -> insertUserData(users, line));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading files: " + e.getMessage());
        }

        users.sort(Comparator.comparingLong(User::getUserId));
        return users;
    }

    private void validateLinesContent(List<String> lines) throws FileException {
        if (lines.isEmpty()) {
            throw new FileException("There is no data to read");
        }

        int index = IntStream.range(0, lines.size())
                .filter(i -> lines.get(i).length() != LINE_LENGTH)
                .findFirst()
                .orElse(-1);
        if (index != -1) {
            throw new FileException("Integration file contains wrong line info at index " + index);
        }
    }

    private void insertUserData(List<User> users, String line) {
        UserIntegrationDto userIntegrationDto = getUserIntegrationFromFile(line);

        User user = users.stream().filter(u -> u.getUserId().equals(userIntegrationDto.getUserId())).findFirst().orElse(null);
        if (user == null) {
            users.add(new User(userIntegrationDto));
            return;
        }

        Order order = user.getOrders().stream().filter(o -> o.getOrderId().equals(userIntegrationDto.getOrderId())).findFirst().orElse(null);
        if (order == null) {
            user.getOrders().add(new Order(userIntegrationDto));
            Collections.sort(user.getOrders(), Comparator.comparingLong(Order::getOrderId));
            return;
        }

        order.setTotal(order.getTotal() + userIntegrationDto.getValue());
        order.getProducts().add(new Product(userIntegrationDto));
        Collections.sort(order.getProducts(), Comparator.comparingLong(Product::getProductId));
    }

    private UserIntegrationDto getUserIntegrationFromFile(String line) {
        Long userId = UtilsParser.stringToLong(line.substring(0, FieldLengthConstants.USER_ID_LENGTH), true);
        String name = line.substring(FieldLengthConstants.USER_ID_LENGTH, FieldLengthConstants.NAME_LENGTH).trim();
        Long orderId = UtilsParser.stringToLong(line.substring(FieldLengthConstants.NAME_LENGTH, FieldLengthConstants.ORDER_ID_LENGTH), true);
        Long productId = UtilsParser.stringToLong(line.substring(FieldLengthConstants.ORDER_ID_LENGTH, FieldLengthConstants.PRODUCT_ID_LENGTH), true);
        Double value = UtilsParser.stringToDouble(line.substring(FieldLengthConstants.PRODUCT_ID_LENGTH, FieldLengthConstants.VALUE_LENGTH));
        String dateStr = line.substring(FieldLengthConstants.VALUE_LENGTH, FieldLengthConstants.DATE_LENGTH);
        try {
            Date date = this.dateFormat.parse(dateStr);
            dateStr = this.outputDateFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date: " + e.getMessage());
        }
        return new UserIntegrationDto(userId, name, orderId, dateStr, productId, value);
    }
}
