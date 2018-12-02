import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankTest {
    
    public BankTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of addAccount method, of class Bank.
     */
    @Test
    public void testAddAccount() {
        System.out.println("addAccount");
        
        // Arrange
        String acctName = "test";
        Bank instance = new Bank();
        final String expected = "test";
        
        // Act
        instance.addAccount(acctName);        
        
        // Assert
        assertNotNull(Bank.accounts.get(expected));
    }

    /**
     * Test of showMoney method, of class Bank.
     */
    @Test
    public void testShowMoney() {
        System.out.println("showMoney");
        
        // Arrange
        String acctName = "test";
        Bank instance = new Bank();
        final int expected = 20;
        
        // Act
        instance.addAccount(acctName);                        
        final int actual = instance.showMoney(acctName);
        
        // Assert
        assertEquals(actual, expected);        
    }

    /**
     * Test of withdrawMoney method, of class Bank.
     */
    @Test
    public void testWithdrawMoney() {
        System.out.println("withdrawMoney");
        
        // Arrange
        String acctName = "test";
        Bank instance = new Bank();
        final int expected = 0;
        
        // Act
        instance.addAccount(acctName);                        
        instance.withdrawMoney(acctName, 20);
        final int actual = instance.showMoney(acctName);
        
        // Assert
        assertEquals(actual, expected);               
    }
    
}
