//Name:Yangping Zheng
//USC loginid:yangpinz
//CS 455 PA4
//Fall 2015

import java.util.*;
//a class to restore, compare, refresh a prefix
//the prefix if stored in this class in String array
public class Prefix {
	private String[] content;
	
	//constructor of Prefix class
	//prevent from being invalidated
	public Prefix(String[] prefix) {
		content = new String[prefix.length];
		for(int i = 0; i < prefix.length; i++) {
			content[i] = prefix[i];
		}
	}
	
	//get the length of prefix
	public int getLength(){
		return content.length;
	}
	
	//get the content of this prefix
	public String[] getContent() {
		String[] rtn = content.clone();//to prevent invalidation
		return rtn;
	}
	
	//get a new Prefix by appending a selected successor to original prefix
	public Prefix refreshPrefix(String newStr) {
		ArrayList<String> tmpList = new ArrayList<String>(Arrays.asList(content));
		tmpList.add(newStr);
		List<String> rtnList =tmpList.subList(1, tmpList.size()) ;
		String[] rtnStr = new String[rtnList.size()];
		for(int i = 0; i<rtnStr.length; i++) {
			rtnStr[i] = rtnList.get(i);
		}
		return new Prefix(rtnStr);
	}
	
	//override the hashCode method
	public int hashCode() {
		int hashCode = 0;
		for(int i = 0; i < content.length; i++){
			hashCode = hashCode + content[i].hashCode();
		}
		return hashCode;
	}
	//override the equals method
	public boolean equals(Object pre){
		if(!(pre instanceof Prefix)) return false;
		if(this.getLength() != ((Prefix) pre).getLength()) return false;
		
		String[] preStr = ((Prefix) pre).getContent();
		for(int i = 0; i < this.getLength(); i++) {
			if(!content[i].equals(preStr[i])) return false;
		}
		return true;
	}
	//override the toString method
	public String toString() {
		String rtn = "";
		for(String str:content) {
			rtn = rtn + str + " ";
		}
		return rtn;
	}
}
