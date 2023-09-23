package org.testoni.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testoni.utils.FileParserBuilder;
import org.testoni.utils.FileReaderBuilder;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationClientServiceTest {
    private IntegrationClientService integrationClientService = null;

    @BeforeEach
    public void setUp() throws IOException {
    }

    @Test
    public void testProcessLineValid() {
        //URL path1 = getClass().getResource("/");
        URL path1 = getClass().getResource("/data_2.txt");
        String jsonUsersIntegration = new IntegrationClientService(new FileReaderBuilder(), new FileParserBuilder()).getJsonUsersIntegration(path1.getPath());
        //System.out.println(jsonUsersIntegration);
        assertTrue(true);
    }
}
