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
	public static HashMap<String, String> getElements(BufferedReader inFile){
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
			System.exit(2);
		}
		
		return hmap;
	}
	
	//Reads in a string from user input, attempts to parse all the individual characters and return that array
	public static void run(BufferedReader inElements, BufferedReader inFile){
		HashMap<String, String> hmap = new HashMap<String, String>();
		String line;
		String testLine;
		String result;
		ArrayList<String> abbreviations;
		ArrayList<String> elements;
		int startIndex;
		int endIndex;
		
		//Parse from elements.txt - updates hmap
		hmap = getElements(inElements);
		
		try{
			while((line = inFile.readLine()) != null){
				abbreviations = new ArrayList<String>();
				elements = new ArrayList<String>();
				int flag = 0;
				
				//get rid of all non-alphabetical characters + case-insensitive
				line = line.replaceAll("[^A-Za-z]+","");	
				line = line.toUpperCase();	
				
				System.out.println(line);
				
				//parse line for matching abbreviations using substring()
				startIndex = 0;
				endIndex = 1;
				while(startIndex < line.length()){
					testLine = line.substring(startIndex, endIndex);
					result = hmap.get(testLine);
					
					if(result != null){
						abbreviations.add(testLine);
						elements.add(result);
						startIndex = endIndex;
					}else{
						if(endIndex - startIndex == 2){	//there is no abbreviation
							System.out.println("\nCould not create name '"+line+"' out of elements.");
							flag = 1;	//don't print out a partial attempt
							break;
						}else{	//try a 2 letter combination
							endIndex = endIndex + 1;
						}
					}
				}
				
				//Print out abbreviations line / I - N - AC
				if(flag == 0){
					int i = 0;
					while(i < abbreviations.size()){ 
						if(i == 0){
							System.out.print("\n" + abbreviations.get(0));
						}else{
							System.out.print(" - " + abbreviations.get(i));
						}
						i = i + 1;
					}
					
					//Print out elements line / Iodine - Nitrogen - Actinium
					i = 0;
					while(i < elements.size()){
						if(i == 0){
							System.out.print("\n" + elements.get(0));
						}else{
							System.out.print(" - " + elements.get(i));
						}
						i = i + 1;
					}
				}
				
				
				System.out.println("");	//Just for cleaner formatting
			}
		}catch(IOException ioe){
			System.out.println("ERROR: Element.java could not read user input file");
			System.exit(2);
		}
		
		System.out.println("TEST: HMAP SIZE = " + hmap.size());	//TEST should be 118 from elements.txt

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
		BufferedReader inElements = openFile("elements.txt");
		BufferedReader inUser = openFile(filePath); 
		
		//Parse input from user file
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