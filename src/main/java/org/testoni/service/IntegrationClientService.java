package org.testoni.service;

import org.testoni.dto.UserIntegrationDto;
import org.testoni.exception.FileException;
import org.testoni.model.Order;
import org.testoni.model.Product;
import org.testoni.model.User;
import org.testoni.utils.UtilsParser;
import org.testoni.utils.file.IFileReader;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class IntegrationClientService {

    private final IFileReader iFileReader;
    static final int LINE_LENGTH = 95;

    public IntegrationClientService(IFileReader iFileReader) {
        this.iFileReader = iFileReader;
    }

    public String getJsonUsersIntegration(String path) throws FileNotFoundException {
        List<User> users = getUsersFromIntegrationPath(path);
        return UtilsParser.toJson(users);
    }

    public List<User> getUsersFromIntegrationPath(String path) throws FileNotFoundException {
        List<String> lines = iFileReader.getStringLinesFromFile(iFileReader.getFilesFromPath(path));
        validateLinesContent(lines);
        return getUserListFromLines(lines);
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

    private List<User> getUserListFromLines(List<String> lines) {
        HashMap<Long, User> usersHash = new HashMap<>();
        lines.forEach(line -> {
            UserIntegrationDto userIntegrationDto = getUserIntegrationFromFile(line);
            Long userId = userIntegrationDto.getUserId();

            User user = usersHash.get(userId);
            if (user == null) {
                usersHash.put(userId, new User(userIntegrationDto));
                return;
            }

            Order order = user.getOrderById(userIntegrationDto.getOrderId());
            if (order == null) {
                user.addOrder(new Order(userIntegrationDto));
                return;
            }

            order.setTotal(order.getTotal() + userIntegrationDto.getValue());
            order.addProduct(new Product(userIntegrationDto));
        });

        List<User> users = new ArrayList<>(usersHash.values());
        users.sort(Comparator.comparingLong(User::getUserId));
        return users;
    }

    private UserIntegrationDto getUserIntegrationFromFile(String line) {
        Long userId = UtilsParser.stringToLong(line.substring(0, FieldLengthConstants.USER_ID_LENGTH), true);
        String name = line.substring(FieldLengthConstants.USER_ID_LENGTH, FieldLengthConstants.NAME_LENGTH).trim();
        Long orderId = UtilsParser.stringToLong(line.substring(FieldLengthConstants.NAME_LENGTH, FieldLengthConstants.ORDER_ID_LENGTH), true);
        Long productId = UtilsParser.stringToLong(line.substring(FieldLengthConstants.ORDER_ID_LENGTH, FieldLengthConstants.PRODUCT_ID_LENGTH), true);
        Double value = UtilsParser.stringToDouble(line.substring(FieldLengthConstants.PRODUCT_ID_LENGTH, FieldLengthConstants.VALUE_LENGTH));
        LocalDate date = UtilsParser.stringToLocalDate(line.substring(FieldLengthConstants.VALUE_LENGTH, FieldLengthConstants.DATE_LENGTH), "yyyyMMdd");
        String dateStr = UtilsParser.dateToString(date, "yyyy-MM-dd");
        return new UserIntegrationDto(userId, name, orderId, dateStr, productId, value);
    }
}
