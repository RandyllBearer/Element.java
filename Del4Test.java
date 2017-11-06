//Java imports
import java.lang.*;
import java.util.ArrayList;

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
    public void testElementExists() {
		assertNotNull(_e);
    }
	
	
	/**
	*
	BEGIN TESTS
	The twelve required software tests begin here...
	*
	*/

	
	/*
	TEST 1:
	Ensure that a simple valid string returns a non-empty array list
	*/
	
    @Test
    public void testSimpleInputValid() {
		ArrayList<String> t = new ArrayList<String>();
		boolean checkMe;
		
		t = _e.getAbbreviations("Laboon");
		
		checkMe = t.isEmpty();
		
		assertFalse(checkMe);
    }
	
	/*
	TEST 2:
	Ensure that a simple NON-VALID string returns an EMPTY array list
	*/
	
    @Test
    public void testSimpleInputNotValid() {
		ArrayList<String> t = new ArrayList<String>();
		boolean checkMe;
		
		t = _e.getAbbreviations("Bill");
		
		checkMe = t.isEmpty();
		
		assertTrue(checkMe);
    }
	
	
	/*
	TEST 3:
	Ensure that the program is case-insensitive by asserting two identical
	inputs with different cases yield the same abbreviation
	*/
	
    @Test
    public void testNotCaseSensitive() {
		ArrayList<String> t1 = new ArrayList<String>();
		ArrayList<String> t2 = new ArrayList<String>();
		
		t1 = _e.getAbbreviations("Laboon");
		t2 = _e.getAbbreviations("lAbOoN");
		assertEquals(t1, t2);
    }
	
}
