import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TerritoryListTest {
    
    public TerritoryListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of addTerritory method, of class TerritoryList.
     */
    @Test
    public void testAddTerritory() {
        System.out.println("addTerritory");
        String c = "";
        String t = "";
        TerritoryList instance = new TerritoryList();
        instance.addTerritory(c, t);
        
        
    }

    /**
     * Test of removeTerritory method, of class TerritoryList.
     */
    @Test
    public void testRemoveTerritory() {
        System.out.println("removeTerritory");
        String c = "";
        String t = "";
        TerritoryList instance = new TerritoryList();
        instance.removeTerritory(c, t);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of numTerritoriesCaptured method, of class TerritoryList.
     */
    @Test
    public void testNumTerritoriesCaptured() {
        System.out.println("numTerritoriesCaptured");
        TerritoryList instance = new TerritoryList();
        int expResult = 0;
        int result = instance.numTerritoriesCaptured();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasTerritory method, of class TerritoryList.
     */
    @Test
    public void testHasTerritory() {
        System.out.println("hasTerritory");
        String t = "";
        TerritoryList instance = new TerritoryList();
        boolean expResult = false;
        boolean result = instance.hasTerritory(t);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerritoryCount method, of class TerritoryList.
     */
    @Test
    public void testGetTerritoryCount() {
        System.out.println("getTerritoryCount");
        TerritoryList instance = new TerritoryList();
        int expResult = 0;
        int result = instance.getTerritoryCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of coninentValues method, of class TerritoryList.
     */
    @Test
    public void testConinentValues() {
        System.out.println("coninentValues");
        TerritoryList instance = new TerritoryList();
        int expResult = 0;
        int result = instance.coninentValues();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printConts method, of class TerritoryList.
     */
    @Test
    public void testPrintConts() {
        System.out.println("printConts");
        TerritoryList instance = new TerritoryList();
        instance.printConts();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
