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
        new IntegrationClientService(new FileReaderBuilder(), new FileParserBuilder()).getUsersFromIntegration(path1.getPath());
    }
}
