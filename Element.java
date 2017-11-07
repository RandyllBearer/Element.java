//TO DO
// 1. Better implement printing out results, kind of a mess right now
// 2. Better implement a message which says that we cannot parse a certain input word like "John"
// 3. Profile the program
// 4. Write the Paper
// 5. Better exception handling


//Imports
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;	//https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html

public class Element{
	//Initiate a FileReader and BufferedReader object using the filePath argument
	public static BufferedReader openFile(String path){
		BufferedReader inFile = null;
		try{
			inFile = new BufferedReader(new FileReader(path));
		}catch(FileNotFoundException fne){
			System.out.println("ERROR: Element.java could not find the following file: " + path);
			System.exit(2);
		}
		return inFile;
		
	}
	
	
	//Fills hmap with the contents of elements.txt
	public static HashMap<String, String> getElementMap(BufferedReader inFile){
		HashMap<String, String> hmap = new HashMap<String, String>();
		String line;
		
		try{
			while((line = inFile.readLine()) != null){
				//Regular Expression: words[1] holds abbreviation / words[2] holds element name
				String[] words = line.split("[^\\w']+");
				words[1] = words[1].toUpperCase();	//must also make our input string toUpperCase()
				hmap.put(words[1], words[2]);
			}
		}catch(IOException ioe){
			System.out.println("ERROR: Element.java could not read elements.txt");
			System.exit(3);
		}
		
		return hmap;
	}
	
	
	// Creates the array list of atom abbreviations
	// Returns the list for use in unit testing
	// The list is attempted to be built forward using the stepping algorithm
	// If and only if this fails, the list is attempted to be built backwards
	// The direction depends on the 'forward' flag passed to this function
	public static ArrayList<String> getAbbreviations(String _line, HashMap<String, String> hmap, boolean forward){
		ArrayList<String> _a = new ArrayList<String>();
		String testLine, result;
		
		// removes of all non-alphabetical characters
		// eliminates case sensitivity
		_line = _line.replaceAll("[^A-Za-z]+","");	
		_line = _line.toUpperCase();
		
		// starting point chosen based on direction
		int startIndex = 0;
		int endIndex = 2;
		if(!forward){
			startIndex = _line.length()-2;
			endIndex = _line.length();
		}
		
		// Choose to apply the algorithm in the forward or backward
		// direction.  Sometimes only one will work, so it has to be
		// possible to check both using this function
		if(forward){
			while(startIndex < _line.length()){
				// get a group of one or two letters to test
				testLine = _line.substring(startIndex, endIndex);
				result = hmap.get(testLine);
				
				if(result != null){
					// add element to list if it exists
					_a.add(testLine);
					
					// set new indices
					startIndex = endIndex;
					endIndex = startIndex + 2;
					if(endIndex > _line.length()){endIndex=_line.length();}
				}else{
					// if there is no abbreviation, return an empty list
					if(endIndex - startIndex == 1){	
						_a.clear();
						return _a;
					}else{
						//try a 1 letter combination if the 2 letter doesn't exist
						endIndex = endIndex - 1;
					}
				}
			}
		}else{
			while(startIndex >= 0){
				// get a group of one or two letters to test
				testLine = _line.substring(startIndex, endIndex);
				result = hmap.get(testLine);
				
				if(result != null){
					// add element to list if it exists
					_a.add(0,testLine);
					
					// set new indices
					endIndex = startIndex;
					if(startIndex == 1){startIndex-=1;}
					else{startIndex-=2;}
				}else{
					// if there is no abbreviation, return an empty list
					if(endIndex - startIndex == 1){	
						_a.clear();
						return _a;
					}else{
						//try a 1 letter combination if the 2 letter doesn't exist
						startIndex = startIndex + 1;
					}
				}
			}
		}
		
		// if the abbreviations exist, return the final array list
		return _a;
	}
	
	
	// Method used a valid abbreviation list to built a string output for the abbreviations
	public static String buildAbbreviationString(ArrayList<String> _a){
		String _ba = "";
		
		for(int i = 0; i < _a.size(); i++) {
			String toAdd = _a.get(i);
			if(i==0){
				if(toAdd.length() == 2){
					String partA = toAdd.substring(0,1);
					String partB = toAdd.substring(1,2);
					partB = partB.toLowerCase();
					_ba += partA+partB;
				}else{
					_ba += toAdd;
				}
			}else{
				toAdd = _a.get(i);
				if(toAdd.length() == 2){
					String partA = toAdd.substring(0,1);
					String partB = toAdd.substring(1,2);
					partB = partB.toLowerCase();
					_ba += " - " + partA+partB;
				}else{
					_ba += " - " + toAdd;
				}
			}
		}
		
		return _ba;
	}
	
	
	// Method used a valid abbreviation list to built a string output for the elements
	public static String buildElementString(ArrayList<String> _a, HashMap<String, String> _h){
		String _be = "";
		
		_be += "\n" + _h.get(_a.get(0));
		for (int i = 1; i < _a.size(); i++) {
			_be += " - " + _h.get(_a.get(i));
		}
		
		return _be;
	}
	
	
	//Reads in a string from user input, attempts to parse all the individual characters and return that array
	public static void run(BufferedReader inElements, BufferedReader inFile){
		HashMap<String, String> hmap = new HashMap<String, String>();
		ArrayList<String> abbreviations = new ArrayList<String>();
		String line;
		
		// uses the imported element data to create a hashmap
		hmap = getElementMap(inElements);
		
		try{
			while((line = inFile.readLine()) != null){
				System.out.println("\n");	//Just for cleaner formatting
				
				// get the abbreviations and reset elements list
				abbreviations = getAbbreviations(line, hmap, true);
				if(abbreviations.isEmpty()){
					abbreviations = getAbbreviations(line, hmap, false);
				}
				
				// If the line returns valid abbreviations, print
				// Else, 
				if(!abbreviations.isEmpty()){
					String p1 = buildAbbreviationString(abbreviations);
					System.out.println(p1);
					
					String p2 = buildElementString(abbreviations, hmap);
					System.out.println(p2);
				}else{
					System.out.println("\nCould not create name '"+line+"' out of elements.");
				}
			}
		}catch(IOException ioe){
			System.out.println("ERROR: Element.java could not read user input file");
			System.exit(2);
		}
	}
	
	public static void main(String[] args){
		String filePath = "";
		
		//Get Arguments
		if(args.length != 1){
			System.out.println("ERROR: Element.java can only accept one String argument defining file path.");
			System.exit(1);
		}
		try{
			filePath = args[0];
		}catch(Exception e){
			System.out.println("ERROR: Element.java cannot recognize that String file path");
			System.exit(2);
		}
		
		//Open both the elements file and the user specified input
		BufferedReader inUser = openFile(filePath);
		BufferedReader inElements = openFile("elements.txt");
		
		//runs the program using the user input
		run(inElements, inUser);
		
		//Cleanup
		try{
			inElements.close();
			inUser.close();
		}catch(IOException ioe){
			System.out.println("ERROR: Element.java could not close FileReader");
			System.exit(3);
		}
		
		System.exit(0);
	}
	
}