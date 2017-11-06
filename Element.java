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
	//Declare globally used hashmap
	static HashMap<String, String> hmap = new HashMap<String, String>();
	
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
	public static void fillGlobalElementMap(BufferedReader inFile){
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
	}
	
	// Creates the array list of atom abbreviations
	// Returns the list for use in unit testing
	public static ArrayList<String> getAbbreviations(String _line){
		ArrayList<String> _a = new ArrayList<String>();
		String testLine, result;
		int startIndex = 0;
		int endIndex = 2;
		
		// prints the current line
		System.out.println(_line);
		
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
	
	// Gets the element associated with a specific abbreviation
	public static String getElement(String _e){
			return hmap.get(_e);
	}
	
	//Reads in a string from user input, attempts to parse all the individual characters and return that array
	public static void run(BufferedReader inFile){
		ArrayList<String> abbreviations = new ArrayList<String>();
		ArrayList<String> elements = new ArrayList<String>();
		String line;
		
		try{
			while((line = inFile.readLine()) != null){
				// removes of all non-alphabetical characters
				// eliminates case sensitivity
				line = line.replaceAll("[^A-Za-z]+","");	
				line = line.toUpperCase();
				
				// get the abbreviations and reset elements list
				abbreviations = getAbbreviations(line);
				elements.clear();
				
				//Print out abbreviations line / I - N - AC
				if(!abbreviations.isEmpty()){
					System.out.print("\n" + abbreviations.get(0));
					elements.add(getElement(abbreviations.get(0)));
					
					for (int i = 1; i < abbreviations.size(); i++) {
						System.out.print(" - " + abbreviations.get(i));
						elements.add(getElement(abbreviations.get(i)));
					}
					
					System.out.print("\n" + elements.get(0));
					for (int i = 1; i < elements.size(); i++) {
						System.out.print(" - " + elements.get(i));
					}
					
				}else{
					System.out.println("\nCould not create name '"+line+"' out of elements.");
				}
				
				
				System.out.println("");	//Just for cleaner formatting
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
		
		// uses the imported element data to create a hashmap
		fillGlobalElementMap(inElements);
		
		//runs the program using the user input
		run(inUser);
		
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