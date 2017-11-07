//Java imports
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;

//JUnit and Mockito imports
import org.junit.*;
import static org.junit.Assert.*;
import org.mockito.*;

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
	
	Note: the program returns empty array lists for invalid inputs
	any line that returns an empty list is handled and printed as "Could not create... etc."
	
	Note: the program logic is set up to attempt to build the string in the forward
	direction of steps of two, then one.  If this fails, then it is possible that a permutation
	of element combinations was missed.  So, it is then attempted to build the string backwords.
	*
	*/

	
	/*
	TEST 1:
	Ensure that a simple valid string returns a non-empty array list
	The array list is then checked for the correct value
	*/
	
    @Test
    public void testSimpleInputValidFwd() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		ArrayList<String> test = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Mock the hash map values used in this case
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Simulate the getAbbreviations method
		t = _e.getAbbreviations("Laboon", hmap, true);
		
		// Create the test list to check against
		test.add("LA");
		test.add("B");
		test.add("O");
		test.add("O");
		test.add("N");
		
		// Ensure that the array is not empty and that it equals
		// the desired output.
		checkMe = t.isEmpty();
		assertFalse(checkMe);
		assertEquals(t, test);
    }
	
	
	/*
	TEST 2:
	Ensure that a simple NON-VALID string returns an EMPTY array list
	*/
	
    @Test
    public void testSimpleInputNotValid() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		//Mock of the hash map value that SHOULD be valid
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		
		// Testing of the function, for which get() should return null
		// when the hash map is called
		t = _e.getAbbreviations("Bill", hmap, true);
		
		// Ensure that an empty array is returned for this invalid input
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
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Mocking the required hashmap
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Simulation using indentical strings with different cases
		t1 = _e.getAbbreviations("Laboon", hmap, true);
		t2 = _e.getAbbreviations("lAbOoN", hmap, true);
		
		// Assert that both strings return a value and that the values are equal
		assertFalse(t1.isEmpty());
		assertFalse(t2.isEmpty());
		assertEquals(t1, t2);
    }
	
	
	/*
	TEST 4:
	Ensure that if a file has no content, or a line is empty,
	that an empty array list is generated
	*/
	@Test
    public void testSimpleInputEmpty() {
		boolean checkMe;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Adding some values so the map is not empty
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with an empty string
		t = _e.getAbbreviations("", hmap, true);
		
		// Ensure that an empty array is returned for this invalid input
		checkMe = t.isEmpty();
		assertTrue(checkMe);
    }

	
	/*
	TEST 5:
	Given an appropriate list of element abbreviations, ensure
	that the correct string is built and returned from buildAbbreviationString()
	*/
	@Test
    public void testBuildAbbreviationsValid() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		
		// Adding some values to the array list
		t.add("LA");
		t.add("B");
		t.add("O");
		t.add("O");
		t.add("N");
		
		// Testing of the function with a valid array list
		testStr = _e.buildAbbreviationString(t);
		
		// Ensure that an empty array is returned for this invalid input
		assertEquals(testStr, "\nLA - B - O - O - N");
    }
	
	/*
	TEST 5:
	Given an appropriate list of element abbreviations, ensure
	that the correct string is built and returned from buildElementString()
	*/
	@Test
    public void testBuildElementsValid() {
		String testStr;
		ArrayList<String> t = new ArrayList<String>();
		HashMap<String, String> hmap = Mockito.mock(HashMap.class);
		
		// Adding some values to the array list
		t.add("LA");
		t.add("B");
		t.add("O");
		t.add("O");
		t.add("N");
		
		// Add the appropriate values to the hashmap
		Mockito.when(hmap.get("LA")).thenReturn("Lanthanum");
		Mockito.when(hmap.get("B")).thenReturn("Boron");
		Mockito.when(hmap.get("O")).thenReturn("Oxygen");
		Mockito.when(hmap.get("N")).thenReturn("Nitrogen");
		
		// Testing of the function with a valid array list
		testStr = _e.buildElementString(t, hmap);
		
		// Ensure that an empty array is returned for this invalid input
		assertEquals(testStr, "\nLanthanum - Boron - Oxygen - Oxygen - Nitrogen");
    }
	
}
