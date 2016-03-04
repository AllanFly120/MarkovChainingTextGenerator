//Name:Yangping Zheng
//USC loginid:yangpinz
//CS 455 PA4
//Fall 2015

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class GenText {
	
// java GenText [-d] prefixLength numWords sourceFile outFile
	public static void main(String[] args) {
		boolean debugFlag = false;
		int prefixLength = 0;
		int numWords = 0;
		String sourceFile = "";
		String outFile = "";
		try{
			if(args[0].equals("-d")&&args.length==5){
				debugFlag = true;
				prefixLength = Integer.parseInt(args[1]);
				numWords = Integer.parseInt(args[2]);
				if(prefixLength < 1 || numWords < 0) throw new NumberFormatException("prefixLength OR numWords range mistake.");
				sourceFile = args[3];
				outFile = args[4];
			}
			else if(!args[0].equals("-d")&&args.length==4){
				debugFlag = false;
				prefixLength = Integer.parseInt(args[0]);
				numWords = Integer.parseInt(args[1]);
				if(prefixLength < 1 || numWords < 0) throw new NumberFormatException("prefixLength OR numWords range mistake.");
				sourceFile = args[2];
				outFile = args[3];
			}
			else throw new ArrayIndexOutOfBoundsException();
			if(debugFlag) {
				System.out.println("debug:"+debugFlag+" prefixLength:"+prefixLength + " numWords:" +numWords+ 
						" sourceFile:" +sourceFile+ " outFile:" +outFile );
			}
						
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("function:java GenText [-d] prefixLength numWords sourceFile outFile");
			System.out.println("-d:            debugging mode (optional)");			
			System.out.println("-prefixLength: the length of prefix");
			System.out.println("-numWords:     the length of expected output");
			System.out.println("-sourceFile:   the sample text");
			System.out.println("-outFile:      the direction of output file");
			System.exit(0);
		}catch(NumberFormatException e) {
			System.out.println("Wrong variable format!");
			System.out.println(e.getMessage());
			System.exit(0);
		}
		Scanner in = null;
		try{
			in = new Scanner(new File(sourceFile));
		}catch(FileNotFoundException e) {
			System.out.println("Input file is not found.");
			System.exit(0);
		}
		String[] output = {"null"};
		try{
			RandomTextGenerator gen = new RandomTextGenerator(debugFlag, prefixLength, in);
			output = gen.generateText(numWords);
			if(debugFlag) for(String str : output) System.out.print(str + " ");
		}catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		try{
			RandomTextGenerator.formatOutput(output, outFile);
		}catch(IOException e) {
			System.out.println("Exception: cannot write output file");
			System.exit(1);
		}
//		Random rand = new Random(12345l);
//		for(int i=0;i<20;i++)System.out.print(rand.nextInt(1)+ " ");

//		System.out.println(RandomTextGenerator.toStrArr(in, debugFlag).size());
//		for(String str : RandomTextGenerator.toStrArr(1, in, debugFlag)) {
//			System.out.print(str + " ");	
//		}
//		String[] prefixStr = {"aaa","bbb","cba"};
//		Prefix pre = new Prefix(prefixStr);
//		String[] prefixStr2 = {"aaa","bbb","cba"};
//		Prefix pre2 = new Prefix(prefixStr2);
//		System.out.println(pre.hashCode() + " " + pre2.hashCode());
//		System.out.println(pre.equals(pre2));
//		Map<Prefix, Integer> amap = new HashMap<Prefix, Integer>();
//		amap.put(pre, 1);
//		amap.put(pre2, 2);
//		for(Map.Entry<Prefix, Integer> entry : amap.entrySet()) {
//			System.out.println(entry.getValue());
//		}
		
//		String[] now = pre.getContent();
//		System.out.println(pre);
//		now[0] = "111";
////		String[] now2 = pre.getContent();
//		System.out.println(pre);
	}

}
