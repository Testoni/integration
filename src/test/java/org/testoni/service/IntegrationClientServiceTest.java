package org.testoni.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testoni.exception.FileException;
import org.testoni.model.Order;
import org.testoni.model.User;
import org.testoni.utils.file.FileReaderText;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationClientServiceTest {

    private IntegrationClientService integrationClientService;
    private FileReaderText fileReaderText;

    @BeforeEach
    public void setUp() throws IOException {
        fileReaderText = new FileReaderText();
        integrationClientService = new IntegrationClientService(fileReaderText);
    }

    @Test
    public void shouldGetJsonUsersIntegration() throws FileNotFoundException {
        URL url = getClass().getResource("/data_2.txt");
        String result = integrationClientService.getJsonUsersIntegration(url.getPath());
        assertNotNull(result);
    }

    @Test
    public void shouldGetUsersFromIntegration() throws FileNotFoundException {
        URL url = getClass().getResource("/data_1.txt");
        List<User> users = integrationClientService.getUsersFromIntegrationPath(url.getPath());

        assertNotNull(users);
        assertEquals(100, users.size());
        assertEquals(78, users.get(77).getUserId());
        assertEquals("Wade Mraz", users.get(77).getName());
        assertEquals(15, users.get(77).getOrders().size());
        assertEquals(855, users.get(77).getOrders().get(6).getOrderId());
        assertEquals(1191.54, users.get(77).getOrders().get(6).getTotal());
        assertEquals("2021-06-28", users.get(77).getOrders().get(6).getDate());
        assertEquals(2, users.get(77).getOrders().get(6).getProducts().size());
        assertEquals(3, users.get(77).getOrders().get(6).getProducts().get(1).getProductId());
        assertEquals(399.57, users.get(77).getOrders().get(6).getProducts().get(1).getValue());

        Double totalValue = users.stream().flatMap(user -> user.getOrders().stream()).mapToDouble(Order::getTotal).sum();
        assertEquals(2328410.63, totalValue);
    }

    @Test
    public void shouldThrowRuntimeExceptionOnFileNotFoundException() {
        String strNonExistentPath = "nonExistentPath";
        RuntimeException nonExistentPath = assertThrows(RuntimeException.class, () -> {
            integrationClientService.getUsersFromIntegrationPath(strNonExistentPath);
        });
        assertEquals("File or directory not found: " + strNonExistentPath, nonExistentPath.getMessage());
    }

    @Test
    public void shouldThrowFileExceptionOnInvalidLines() {
        URL url = getClass().getResource("/invalid/invalid_line.txt");
        FileException exception = assertThrows(FileException.class, () -> {
            integrationClientService.getUsersFromIntegrationPath(url.getPath());
        });

        assertEquals("Integration file contains wrong line info at index 0", exception.getMessage());
    }

    @Test
    public void shouldThrowFileExceptionOnNoDataToRead() {
        URL url = getClass().getResource("/invalid/empty_file.txt");
        FileException exception = assertThrows(FileException.class, () -> {
            integrationClientService.getUsersFromIntegrationPath(url.getPath());
        });

        assertEquals("There is no data to read", exception.getMessage());
    }
}
