package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {
    private Client client;

    @Test
    void testClientCreation(){
        String clientName = "Bob";
        client = new Client(clientName);
        assertEquals(clientName, client.getName());
    }
}