package org.testoni.service;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.testoni.utils.FileParserBuilder;
import org.testoni.utils.FileReaderBuilder;

import java.io.IOException;
import java.net.URL;

public class IntegrationClientServiceTest {
    private IntegrationClientService integrationClientService = null;

    @BeforeEach
    public void setUp() throws IOException {
    }

    @Test
    public void testProcessLineValid() {
        URL path1 = getClass().getResource("/data_1.txt");
        String jsonUsersIntegration = new IntegrationClientService(new FileReaderBuilder(), new FileParserBuilder()).getJsonUsersIntegration(path1.getPath());
        System.out.println(jsonUsersIntegration);
    }
}
