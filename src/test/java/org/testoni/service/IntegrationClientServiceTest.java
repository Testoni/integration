package org.testoni.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testoni.exception.FileException;
import org.testoni.model.User;
import org.testoni.utils.file.FileReaderText;

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
    public void shouldGetJsonUsersIntegration() {
        URL url = getClass().getResource("/data_2.txt");
        String result = integrationClientService.getJsonUsersIntegration(url.getPath());
        assertNotNull(result);
    }

    @Test
    public void shouldGetUsersFromIntegration() {
        URL url = getClass().getResource("/");
        List<User> users = integrationClientService.getUsersFromIntegration(url.getPath());
        assertNotNull(users);
        assertEquals(200, users.size());
        assertEquals(78, users.get(77).getUserId());
        assertEquals("Wade Mraz", users.get(77).getName());
        assertEquals(18, users.get(77).getOrders().size());
        assertEquals(852, users.get(77).getOrders().get(6).getOrderId());
        assertEquals(1068.27, users.get(77).getOrders().get(6).getTotal());
        assertEquals("2021-05-20", users.get(77).getOrders().get(6).getDate());
        assertEquals(2, users.get(77).getOrders().get(6).getProducts().size());
        assertEquals(2, users.get(77).getOrders().get(6).getProducts().get(1).getProductId());
        assertEquals(798.96, users.get(77).getOrders().get(6).getProducts().get(1).getValue());
    }

    @Test
    public void shouldThrowRuntimeExceptionOnFileNotFoundException() {
        String strNonExistentPath = "nonExistentPath";
        RuntimeException nonExistentPath = assertThrows(RuntimeException.class, () -> {
            integrationClientService.getUsersFromIntegration(strNonExistentPath);
        });
        assertEquals("File or directory not found: " + strNonExistentPath, nonExistentPath.getMessage());
    }

    @Test
    public void shouldThrowFileExceptionOnInvalidLines() {
        URL url = getClass().getResource("/invalid/invalid_line.txt");
        FileException exception = assertThrows(FileException.class, () -> {
            integrationClientService.getUsersFromIntegration(url.getPath());
        });

        assertEquals("Integration file contains wrong line info at index 0", exception.getMessage());
    }
}
