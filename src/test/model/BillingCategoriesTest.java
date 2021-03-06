package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BillingCategoriesTest {
    String clientName1 = "Client Name 1";
    String clientName2 = "Client Name 2";
    Client client1;
    Client client2;
    private ClientBook clientBook;
    private BillingCategories billingCategories;

    @BeforeEach
    void runBefore() {
        clientBook = new ClientBook();
        billingCategories= new BillingCategories();
        clientBook.createClient(clientName1);
        clientBook.createClient(clientName2);
        client1 = clientBook.getAClient(clientName1);
        client2 = clientBook.getAClient(clientName2);
    }

    @Test
    void testBillingCategoriesCreation() {
        assertEquals(0, billingCategories.getAllBillingCategories().size());
    }

    @Test
    void testDuplicateNameCheckNoDupes() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 2";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        boolean nameCheck = billingCategories.duplicateNameCheck(categoryName2, client1);
        assertFalse(nameCheck);
    }

    @Test
    void testDuplicateNameCheckDupeExists() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 1";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        boolean nameCheck = billingCategories.duplicateNameCheck(categoryName2, client1);
        assertTrue(nameCheck);
    }

    @Test
    void createBillingCategoryNoDupe() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 2";
        String ratePerHour1 = "3.50";
        String ratePerHour2 = "100";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
            boolean creationSuccess = billingCategories.createBillingCategory(categoryName2, ratePerHour2, client1);
            assertTrue(creationSuccess);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(2, billingCategories.getBillingCategoriesForClient(client1).size());
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
        assertEquals(categoryName2, billingCategories.getBillingCategoriesForClient(client1).get(1).getName());
    }

    @Test
    void createBillingCategoryDupeExists() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 1";
        String ratePerHour1 = "3.50";
        String ratePerHour2 = "100";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
            boolean creationSuccess = billingCategories.createBillingCategory(categoryName2, ratePerHour2, client1);
            assertFalse(creationSuccess);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(1, billingCategories.getBillingCategoriesForClient(client1).size());
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    //1
    //Category names don't match & the Category is for that client
    @Test
    void removeBillingCategoryCatNameNoMatchSameClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 2";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        boolean removalSuccess = billingCategories.removeBillingCategory(categoryName2, client1);
        assertFalse(removalSuccess);
        assertEquals(1, billingCategories.getBillingCategoriesForClient(client1).size());
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    //2
    //Category names match & the Category is for that client
    @Test
    void removeBillingCategoryCatNameMatchSameClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 1";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        boolean removalSuccess = billingCategories.removeBillingCategory(categoryName2, client1);
        assertTrue(removalSuccess);
        assertEquals(0, billingCategories.getBillingCategoriesForClient(client1).size());
    }

    //3
    //Category names don't match & the category is for a different client
    @Test
    void removeBillingCategoryCatNameNoMatchDifferentClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 2";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        boolean removalSuccess = billingCategories.removeBillingCategory(categoryName2, client2);
        assertFalse(removalSuccess);
        assertEquals(1, billingCategories.getBillingCategoriesForClient(client1).size());
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    //4
    //Category names match & the category is for a different client
    @Test
    void removeBillingCategoryCatNameMatchDifferentClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 1";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        boolean removalSuccess = billingCategories.removeBillingCategory(categoryName2, client2);
        assertFalse(removalSuccess);
        assertEquals(1, billingCategories.getBillingCategoriesForClient(client1).size());
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    //1
    //Category names don't match & the Category is for that client
    @Test
    void editBillingCategoryNoDupeCatNameNoMatchSameClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 2";
        String newName = "New Name";
        String ratePerHour1 = "3.50";
        String ratePerHour2 = "7.00";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
            boolean editSuccess = billingCategories.editBillingCategory(categoryName2, newName, ratePerHour2 ,client1);
            assertFalse(editSuccess);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    //2
    //Category names match & the Category is for that client
    @Test
    void editBillingCategoryNoDupeCatNameMatchSameClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 1";
        String newName = "New Name";
        String ratePerHour1 = "3.50";
        String ratePerHour2 = "7.00";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
            boolean editSuccess = billingCategories.editBillingCategory(categoryName2, newName, ratePerHour2 ,client1);
            assertTrue(editSuccess);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(newName, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    //3
    //Category names don't match & the Category is different client
    @Test
    void editBillingCategoryNoDupeCatNameNoMatchDifferentClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 2";
        String newName = "New Name";
        String ratePerHour1 = "3.50";
        String ratePerHour2 = "7.00";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
            boolean editSuccess = billingCategories.editBillingCategory(categoryName2, newName, ratePerHour2 ,client2);
            assertFalse(editSuccess);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    //4
    //Category names match & the Category is different client
    @Test
    void editBillingCategoryNoDupeCatNameMatchDifferentClient() {
        String categoryName1 = "Name 1";
        String categoryName2 = "Name 1";
        String newName = "New Name";
        String ratePerHour1 = "3.50";
        String ratePerHour2 = "7.00";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
            boolean editSuccess = billingCategories.editBillingCategory(categoryName2, newName, ratePerHour2 ,client2);
            assertFalse(editSuccess);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    @Test
    void editBillingCategoryDupeExists() {
        String categoryName1 = "Name 1";
        String newName = "Name 1";
        String ratePerHour1 = "3.50";
        String ratePerHour2 = "7.00";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
            boolean editSuccess = billingCategories.editBillingCategory(categoryName1, newName, ratePerHour2 ,client1);
            assertFalse(editSuccess);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        assertEquals(categoryName1, billingCategories.getBillingCategoriesForClient(client1).get(0).getName());
    }

    @Test
    void getBillingCategoryForClientMatchExists() {
        String categoryName1 = "Name 1";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        ArrayList<BillingCategory> categoriesForClient = billingCategories.getBillingCategoriesForClient(client1);
        assertEquals(1, categoriesForClient.size());
        assertEquals(categoryName1, categoriesForClient.get(0).getName());
    }

    @Test
    void getBillingCategoryForClientNoMatchExists() {
        String categoryName1 = "Name 1";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        ArrayList<BillingCategory> categoriesForClient = billingCategories.getBillingCategoriesForClient(client2);
        assertEquals(0, categoriesForClient.size());
    }

    @Test
    void getBillingACategoryMatchExists() {
        String categoryName1 = "Name 1";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        BillingCategory category = billingCategories.getABillingCategory(categoryName1, client1);
        assertEquals(categoryName1, category.getName());
    }

    @Test
    void getBillingACategoryNoMatchExistsNotANameMatch() {
        String categoryName1 = "Name 1";
        String noMatch = "Not a matching name";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        BillingCategory category = billingCategories.getABillingCategory(noMatch, client1);
        assertEquals(null, category);
    }

    @Test
    void getBillingACategoryNoMatchExistsNotAClientMatch() {
        String categoryName1 = "Name 1";
        String ratePerHour1 = "3.50";
        try {
            billingCategories.createBillingCategory(categoryName1, ratePerHour1, client1);
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
        BillingCategory category = billingCategories.getABillingCategory(categoryName1, client2);
        assertEquals(null, category);
    }

    @Test
    void testSetBillingCategories() {
        Client client1 = new Client("Client1");
        Client client2 = new Client("Client2");
        BillingCategory category1 = null;
        try {
            category1 = new BillingCategory("Cat1", "14", client1);
            BillingCategory category2 = new BillingCategory("Cat2", "17", client2);
            ArrayList<BillingCategory> newBillingCategories = new ArrayList<>();
            newBillingCategories.add(category1);
            newBillingCategories.add(category2);
            assertEquals(0, billingCategories.getAllBillingCategories().size());
            billingCategories.setBillingCategories(newBillingCategories);
            assertEquals(2, billingCategories.getAllBillingCategories().size());
        } catch (NumberFormatException e) {
            fail();
        } catch (NegativeRateException e) {
            fail();
        } catch (EmptyNameException e) {
            fail();
        }
    }
}