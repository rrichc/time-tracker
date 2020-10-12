package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BillingCategoryTest {
    private Client client;
    private BillingCategory category;

    @Test
    void testBillingCategoryCreation(){
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "10.75";
        client = new Client(clientName);

        category = new BillingCategory(categoryName, ratePerHour, client);
        assertEquals(categoryName, category.getName());
        assertEquals(Double.parseDouble(ratePerHour), category.getRatePerHour());
        assertEquals(client.getName(), category.getClient().getName());
    }

    @Test
    void testSetRatePerHourConversion() {
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "0";
        client = new Client(clientName);

        category = new BillingCategory(categoryName, ratePerHour, client);
        String newRatePerHour = "20.18";
        category.setRatePerHour(newRatePerHour);
        assertEquals(Double.parseDouble(newRatePerHour), category.getRatePerHour());
    }
}