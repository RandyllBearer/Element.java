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
	// Unacceptable in
	public static ArrayList<String> getAbbreviations(String _line, HashMap<String, String> hmap){
		ArrayList<String> _a = new ArrayList<String>();
		String testLine, result;
		int startIndex = 0;
		int endIndex = 2;
		
		// removes of all non-alphabetical characters
		// eliminates case sensitivity
		_line = _line.replaceAll("[^A-Za-z]+","");	
		_line = _line.toUpperCase();
		
		//parse line for matching abbreviations using substring()
		while(startIndex < _line.length()){
			if(endIndex > _line.length()){endIndex=_line.length();}
			testLine = _line.substring(startIndex, endIndex);
			result = hmap.get(testLine);
			
			if(result != null){
				_a.add(testLine);
				startIndex = endIndex;
				endIndex = startIndex + 2;
			}else{
				// if there is no abbreviation, return an empty list
				if(endIndex - startIndex == 1){	
					_a.clear();
					return _a;
				}else{
					//try a 1 letter combination
					endIndex = endIndex - 1;
				}
			}
		}
		
		return _a;
	}
	
	public static String buildAbbreviationString(ArrayList<String> _a){
		String _ba = "";
		
		_ba += "\n" + _a.get(0);
		for (int i = 1; i < _a.size(); i++) {
			_ba += " - " + _a.get(i);
		}
		
		return _ba;
	}
	
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
				abbreviations = getAbbreviations(line, hmap);
				
				// Do the following if getAbbreviations determines the line is valid
				// Prints a line of abbreviations then a line of actual elements
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