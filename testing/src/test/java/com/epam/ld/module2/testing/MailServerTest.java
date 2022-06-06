package com.epam.ld.module2.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MailServerTest {
    @Mock
    MailServer server;

    @BeforeEach
    public void preTestInit() {
        server = mock(MailServer.class);
    }

    @Test
    public void validInfoTest() {
        server.send("address@address.com", "Message and some other values");
        verify(server).send("address@address.com", "Message and some other values");
    }

}
