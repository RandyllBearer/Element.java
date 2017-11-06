//Java imports
import java.lang.*;
import java.util.logging.*;
import java.util.concurrent.TimeUnit;
import java.util.List;

//JUnit imports
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * This is the test class for Deliverable 4, CS1632 Fall 2017
 * Authors: Randyll Bearer (github: rlb97) and Branden Keck (github: BrandenKeck)
 *
 */

public class Del4Test {
	//Declare reusable element object
	Element _e;
	
	// Create a new Element instance before testing
    @Before
    public void setup() {
		_e = new Element();
    }
    
    // Initial test to ensure that the new Element instance isn't null
    @Test
    public void testLaboonCoinExists() {
		assertNotNull(_l);
    }
	
}
