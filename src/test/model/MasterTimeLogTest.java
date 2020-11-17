package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MasterTimeLogTest {
    private MasterTimeLog masterTimeLog;
    private Client client;
    private String clientName = "Bob";

    @BeforeEach
    void runBefore() {
        masterTimeLog = new MasterTimeLog();
        client = new Client(clientName);
    }

    @Test
    void testMasterTimeLogCreation(){
        assertEquals(0, masterTimeLog.getMasterTimeLog().size());
    }

    @Test
    void testCreateTimeLog(){
        masterTimeLog.createTimeLog(client);
        assertEquals(1, masterTimeLog.getMasterTimeLog().size());
        assertEquals(clientName, masterTimeLog.getMasterTimeLog().get(0).getClient().getName());
    }

    @Test
    void testRemoveTimeLogNameMatch() {
        masterTimeLog.createTimeLog(client);
        masterTimeLog.removeTimeLog(clientName);
        assertEquals(0, masterTimeLog.getMasterTimeLog().size());
    }

    @Test
    void testRemoveTimeLogNoNameMatch() {
        String client2Name = "Sally";
        masterTimeLog.createTimeLog(client);
        masterTimeLog.removeTimeLog(client2Name);
        assertEquals(1, masterTimeLog.getMasterTimeLog().size());
    }

    @Test
    void testGetTimeLogForClientMatch() {
        String clientName2 = "Sally";
        Client client2 = new Client(clientName2);
        masterTimeLog.createTimeLog(client);
        masterTimeLog.createTimeLog(client2);
        TimeLog returnedLog = masterTimeLog.getTimeLogForClient(client2);
        assertEquals(clientName2, returnedLog.getClient().getName());
    }

    @Test
    void testGetTimeLogForClientNoMatch() {
        String clientName2 = "Sally";
        Client client2 = new Client(clientName2);
        masterTimeLog.createTimeLog(client);
        TimeLog returnedLog = masterTimeLog.getTimeLogForClient(client2);
        assertEquals("", returnedLog.getClient().getName());
    }
}