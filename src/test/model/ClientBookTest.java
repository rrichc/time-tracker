package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientBookTest {
    private Client client;
    private ClientBook clientBook;

    @BeforeEach
    void runBefore() {
        clientBook = new ClientBook();
    }

    @Test
    void testClientBookCreation() {
        assertEquals(0, clientBook.getClients().size());
    }

    @Test
    void testDuplicateNameCheckNoDupes() {
        String differentName1 = "Different Name 1";
        String differentName2 = "Different Name 2";
        clientBook.createClient(differentName1);
        boolean nameCheck = clientBook.duplicateNameCheck(differentName2);
        assertFalse(nameCheck);
    }

    @Test
    void testDuplicateNameCheckDupeExists() {
        String sameName1 = "Same Name";
        String sameName2 = "Same Name";
        clientBook.createClient(sameName1);
        boolean nameCheck = clientBook.duplicateNameCheck(sameName2);
        assertTrue(nameCheck);
    }

    @Test
    void createClientNoDupe() {
        String differentName1 = "Different Name 1";
        String differentName2 = "Different Name 2";
        clientBook.createClient(differentName1);
        boolean creationSuccess = clientBook.createClient(differentName2);
        assertTrue(creationSuccess);
        assertEquals(2, clientBook.getClients().size());
        assertEquals(differentName1, clientBook.getClients().get(0).getName());
        assertEquals(differentName2, clientBook.getClients().get(1).getName());
    }

    @Test
    void createClientDupeExists() {
        String sameName1 = "Same Name";
        String sameName2 = "Same Name";
        clientBook.createClient(sameName1);
        boolean creationSuccess = clientBook.createClient(sameName2);
        assertFalse(creationSuccess);
        assertEquals(1, clientBook.getClients().size());
        assertEquals(sameName1, clientBook.getClients().get(0).getName());
    }

    @Test
    void removeClientNotInCollection() {
        String clientName = "Bob";
        String clientNameToRemove = "Sandy";
        clientBook.createClient(clientName);
        boolean removeSuccess = clientBook.removeClient(clientNameToRemove);
        assertFalse(removeSuccess);
        assertEquals(1, clientBook.getClients().size());
    }

    @Test
    void removeClientInCollection() {
        String clientName = "Bob";
        String clientNameToRemove = "Bob";
        clientBook.createClient(clientName);
        boolean removeSuccess = clientBook.removeClient(clientNameToRemove);
        assertTrue(removeSuccess);
        assertEquals(0, clientBook.getClients().size());
    }

    @Test
    void editClientNoDupe() {
        String differentName1 = "Different Name 1";
        String differentName2 = "Different Name 2";
        String newName = "Bob";
        clientBook.createClient(differentName1);
        clientBook.createClient(differentName2);
        boolean editSuccess = clientBook.editClient(differentName2, newName);
        assertTrue(editSuccess);
        assertEquals(differentName1, clientBook.getClients().get(0).getName());
        assertEquals(newName, clientBook.getClients().get(1).getName());
    }

    @Test
    void editClientDupeExists() {
        String differentName1 = "Different Name 1";
        String differentName2 = "Different Name 2";
        String newName = "Different Name 2";
        clientBook.createClient(differentName1);
        clientBook.createClient(differentName2);
        boolean editSuccess = clientBook.editClient(differentName2, newName);
        assertFalse(editSuccess);
        assertEquals(differentName1, clientBook.getClients().get(0).getName());
        assertEquals(differentName2, clientBook.getClients().get(1).getName());
    }

    @Test
    void getClientMatchExists() {
        String differentName1 = "Different Name 1";
        String differentName2 = "Different Name 2";
        clientBook.createClient(differentName1);
        clientBook.createClient(differentName2);
        Client client1 = clientBook.getAClient(differentName1);
        assertEquals(differentName1, client1.getName());
    }

    @Test
    void getClientNoMatchExists() {
        String differentName1 = "Different Name 1";
        String differentName2 = "Different Name 2";
        String nameNotInList = "Really Different";
        clientBook.createClient(differentName1);
        clientBook.createClient(differentName2);
        Client client1 = clientBook.getAClient(nameNotInList);
        assertEquals(null, client1);
    }
}