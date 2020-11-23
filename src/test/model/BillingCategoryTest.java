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

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(categoryName, category.getName());
        assertEquals(Double.parseDouble(ratePerHour), category.getRatePerHour());
        assertEquals(client.getName(), category.getClient().getName());
    }

    @Test
    void testBillingCategoryCreationNegativeRateException(){
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "-10.75";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
            fail();
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            //
        } catch (EmptyNameException e) {
            fail();
        }
    }

    @Test
    void testBillingCategoryCreationNumberFormatException(){
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "bad value";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
            fail();
        } catch (NumberFormatException e) {
            //
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
    }


    @Test
    void testBillingCategoryCreationEmptyNameException(){
        String clientName = "Bob";
        String categoryName = "";
        String ratePerHour = "10.75";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
            fail();
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            //
        }
    }

    @Test
    void testSetRatePerHourConversion() {
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "0";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        String newRatePerHour = "20.18";
        try {
            category.setRatePerHour(newRatePerHour);
        } catch (NegativeRateException e) {
            fail();
        }
        assertEquals(Double.parseDouble(newRatePerHour), category.getRatePerHour());
    }

    @Test
    void testSetRatePerHourConversionNegativeRate() {
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "0";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        String newRatePerHour = "-20.18";
        try {
            category.setRatePerHour(newRatePerHour);
        } catch (NegativeRateException e) {
            //
        }
    }

    @Test
    void testSetRatePerHourConversionNumberFormatException() {
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "0";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        String newRatePerHour = "bad input";
        try {
            category.setRatePerHour(newRatePerHour);
        } catch (NumberFormatException e) {
            //
        } catch (NegativeRateException e) {
            fail();
        }
    }

    @Test
    void testSetNameNoExceptions() {
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "0";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        String newName = "New name";
        try {
            category.setName(newName);
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(newName, category.getName());
    }

    @Test
    void testSetNameEmptyNameException() {
        String clientName = "Bob";
        String categoryName = "Coding";
        String ratePerHour = "0";
        client = new Client(clientName);

        try {
            category = new BillingCategory(categoryName, ratePerHour, client);
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        String newName = "";
        try {
            category.setName(newName);
        } catch (EmptyNameException e) {
            //
        }
    }
}