//Name:Yangping Zheng
//USC loginid:yangpinz
//CS 455 PA4
//Fall 2015

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;



public class RandomTextGenerator {
	
	private static final int LINE_LENGTH = 80;
	
	private int prefixLength;
	private boolean debugFlag;
	private Prefix initPrefix;
//	private int numWords;
	private Map<Prefix, ArrayList<String>> coreMap;
	private Random rand;
	public RandomTextGenerator(boolean debugFlag, int prefixLength, Scanner in)throws IllegalArgumentException{
		this.debugFlag = debugFlag;
		this.prefixLength = prefixLength;
//		this.numWords = numWords;
		this.coreMap = mapGenerator(prefixLength, in, debugFlag);
		if(debugFlag) rand = new Random(12345l);
		else rand = new Random();
		this.initPrefix = getRandomPre();
	}
	//generate the content of output text of numWords length, in terms of String array
	public String[] generateText(int numWords) {
		ArrayList<String> text = new ArrayList<String>(numWords);  //temperately restore the text
		Prefix prefix = new Prefix(initPrefix.getContent());    //get the initial prefix and prevent it from invalidation
		
		if(debugFlag){
			System.out.println("DEBUG: chose a new initial prefix: " + prefix);
		}
		while(text.size() < numWords) {
			if(coreMap.get(prefix)!=null) {
				
				if(debugFlag){
					System.out.println("DEBUG: prefix: " + prefix);
				}
				
				Object[] selectStrArr = coreMap.get(prefix).toArray();  //temperately restore the value (String ArrayList). And transmit into array to prepare for random processing 
				if(debugFlag){
					System.out.print("DEBUG: successors: ");
					for(Object obj : selectStrArr) System.out.print(obj + " ");
					System.out.println();
				}
				
				String nextStr = (String)selectStrArr[rand.nextInt(selectStrArr.length)];  //randomly choose a successor based on its occurrance frequency
				                                                                
				if(nextStr==null) {   //if the successor is the end of sample text
					if(debugFlag){
						System.out.println("DEBUG: successors: <END OF FILE>");
					}
					prefix = getRandomPre();   //get a new prefix 
					while(prefix == initPrefix) {
						prefix = getRandomPre();
					}
					if(debugFlag){
						System.out.println("DEBUG: chose a new initial prefix: " + prefix);
					}
					initPrefix = prefix;
					continue;
				}
				text.add(nextStr);
				
				if(debugFlag){
					System.out.println("DEBUG: word generated: " + nextStr);
				}
				
				prefix = prefix.refreshPrefix(nextStr);
			}
			else {
				if(debugFlag){
					System.out.println("DEBUG: successors: <END OF FILE>");
				}
				
				prefix = initPrefix;
				while(prefix == initPrefix) {
					prefix = getRandomPre();
				}
				initPrefix = prefix;
				
				if(debugFlag){
					System.out.println("DEBUG: chose a new initial prefix: " + prefix);
				}
			}
		}
		return (String[])text.toArray(new String[text.size()]);
	}
	
	private Prefix getRandomPre() {  //method to generate a prefix randomly
		if(coreMap.size() < 1) throw new IllegalArgumentException("Exception: Sample text size mistake");
		Object[] tmpArr = coreMap.keySet().toArray();		
		return (Prefix)tmpArr[rand.nextInt(coreMap.size())];
	}
	
	private ArrayList<String> toStrList(Scanner in, boolean debugFlag) {  //turn the sample text into an Arraylist to prepare it for processing
		ArrayList<String> wholeText = new ArrayList<String>();
		while(in.hasNext()){
			wholeText.add(in.next());
		}
//		String[] rtn = wholeText.toString().split(" ");
		if(wholeText.size() == prefixLength) throw new IllegalArgumentException("Exception: Sample text size mistake");
		return wholeText;
	}
	//get the HashMap
	//Key: prefix
	//Value: an arraylist of successor words following the specific prefix
	private HashMap<Prefix, ArrayList<String>> mapGenerator(int prefixLength, Scanner in, boolean debugFlag) {
		HashMap<Prefix, ArrayList<String>> coreMap = new HashMap<Prefix, ArrayList<String>>();
		ArrayList<String> wholeText = toStrList(in, debugFlag);
		for(int i = 0; i < wholeText.size()-prefixLength+1; i++) {
			//get the key, which is a Prefix
			String[] keyStr = new String[prefixLength];
			int keyStrIndex = 0;
			for(int index = i; index < i + prefixLength; index++) {
				keyStr[keyStrIndex] = wholeText.get(index);
				keyStrIndex++;
			}		
			Prefix key = new Prefix(keyStr);
			
			//get value for each new entry, which is a string array
			ArrayList<String> value = new ArrayList<String>();
			if(i < wholeText.size()-prefixLength) {
				value.add(wholeText.get(i+prefixLength));
			}
			else {
				value.add(null);
			}
			
			if(coreMap.containsKey(key)) {
				if(debugFlag) {
					System.out.print("DEBUG:");
					System.out.println("Found key: " + key);
				}
				ArrayList<String> prevValue = coreMap.get(key);
				prevValue.addAll(value);
				coreMap.put(key, prevValue);
			}
			else {
				if(debugFlag) {
					System.out.print("DEBUG:");
					System.out.println("Put a new entry: " + key);
				}
				coreMap.put(key, value);
			}
			
//			System.out.print("key:");
//			for(String str:keyStr) System.out.print(str + " ");
//			System.out.println("  value:"+value);
			
//			System.out.println();
			
//			Prefix key = new Prefix(wholeText.subList(i, i+prefixLength).toArray());
//			System.out.println(key);
		}
		if(debugFlag) {
			
			for(Map.Entry<Prefix, ArrayList<String>> entry : coreMap.entrySet()) {
				System.out.print("DEBUG:");
				System.out.print("key:");
				for(String str:entry.getKey().getContent()) System.out.print(str + " ");
//				System.out.print("  "+entry.getKey().hashCode()+"  ");
				System.out.println("  value:"+entry.getValue());
		    }
		}

//		toStrList(in, debugFlag);
		return coreMap;		
	}
	
	//write the text we generate into a file in specific format
	public static void formatOutput(String[] text, String dir) throws IOException {
		FileWriter writer = new FileWriter(dir);
		int charCounter = 0;
		for(String str : text) {
			charCounter += str.length();
			if(charCounter > LINE_LENGTH) {
				writer.write("\n");
				charCounter = str.length();
			}
			writer.write(str);
			writer.write(" ");
			charCounter++;
		}
		writer.close();
	}
	
}
