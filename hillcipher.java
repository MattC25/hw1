package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*	
 *   Assignment:  HW 01 –Encrypting a plaintext file using the Hill cipher in the key file
 *   Author:  Matt Cunningham
 *   Language:  Java
 *   To Compile:  javac Hw01.java
 *   To Execute:  	java Hw01 hillcipherkey.txtplaintextfile.txt
 *   				where the files in the command line are in the current directory.
 *   				The key text containsa single digit on the first line defining the size of the key 
 *   				while the remaining linesdefine the key, for example:
 *   				3
 *   				1 2 3
 *   				4 5 6
 *   				7 8 9
 *   				The plain text file containsthe plain text in mixed case with spaces & punctuation.
 *   
 *   Class:  CIS3360-Security in Computing-Fall2020
 *   Instructor:  McAlpin
 *   Due Date:  10/11/2020
 */

public class hillcipher {
	
	static int size;
	int[][] key;
	ArrayList<Character> text;
	
	public hillcipher(int[][] key, ArrayList<Character> text) {
		this.key = key;
		this.text = text;
	}
	
	public static int[][] readKey(String keyFile) throws NumberFormatException, IOException {
		
		//Take input keyfile and store as 2d array to return
		int[][] key;
		BufferedReader br = new BufferedReader(new FileReader(keyFile));
		size = Integer.parseInt(br.readLine());
		key = new int[size][size];
		
		for(int i = 0; i < size; i++) {
			
			String[] newLine = br.readLine().split(" ");
			
			for(int j = 0; j < size; j++) {
				
				key[i][j] = Integer.parseInt(newLine[j]);
				
			}
			
		}
		
		//Ouput key to console
		System.out.println("Key Matrix: \n");
		for(int i = 0; i < size; i++) {
			
			for(int j = 0; j < size; j++) {
				
				System.out.print(key[i][j] + " ");
				
			}
			
			System.out.println();
			
		}
		
		System.out.println("\n");
		
		return key;
	}
	
	public static ArrayList<Character> readText(String textFile) throws IOException{
		
		//Get input text from file and store in Arraylist to return
		ArrayList<Character> plainText = new ArrayList<Character>();
		
		BufferedReader br = new BufferedReader(new FileReader(textFile));
		String input = "";
		
		while((input=br.readLine()) != null) {
			
			for(Character c : input.toCharArray()) {
				
				if(Character.isAlphabetic(c)) {
					
					plainText.add(Character.toLowerCase(c));
					
				}
				
			}
			
		}
		
		//Output to Console
		System.out.println("Plaintext: \n");
		int i = 1;
		for(Character c : plainText) {
			
			if(i < 80) {
				
				System.out.print(c);
				
			}
			else {
				
				System.out.println(c);
				i = 0;
				
			}
			i++;
		}
		
		System.out.println("\n");
		
		return plainText;
		
	}
	
	public List<Character> encryptChars(List<Character> characters) {
		
		while (characters.size() != size)
            characters.add('x');
		
		ArrayList<Character> ciphers = new ArrayList<>();
		
		for(int i = 0; i < size; i++) {
			
			int sum = 0;
			for(int j = 0; j < size; j++) {
				
				sum += key[i][j] * (Character.getNumericValue(characters.get(j)) - 10);
				
			}
			
			ciphers.add(Character.forDigit((sum % 26) + 10, Character.MAX_RADIX));
			
		}
		
		return ciphers;
		
	}
	
	public ArrayList<Character> encrypt(){
		
		//
		ArrayList<Character> plainText = this.text;
		ArrayList<Character> cipherText = new ArrayList<>();
		
		while (!plainText.isEmpty()) {
            List<Character> subplainText;
            if (plainText.size() >= size)
                subplainText = plainText.subList(0, size);
            else {
                subplainText = plainText.subList(0, plainText.size());
            }
            cipherText.addAll(encryptChars(subplainText));
            for (Character c : subplainText.toArray(new Character[subplainText.size()])) {
                plainText.remove(c);
            }
        }
		
		return cipherText;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		String keyFile = args[0];
		String textFile = args[1];
		
		int[][] key = readKey(keyFile);
		ArrayList<Character> text = readText(textFile);
		
		hillcipher cypher = new hillcipher(key, text);
		
		ArrayList<Character> cipher = cypher.encrypt();
		
		System.out.println("Ciphertext: \n");
		int i = 1;
		for(Character c : cipher) {
			
			if (i < 80)
				
                System.out.print(c);
			
            else {
            	
                System.out.println(c);
                i = 0;
                
            }
			
            i++;
            
		}
		
		
	}
	
}

/*
 *     I [your name] ([your NID]) affirm that this program is  
 *     entirely my own work and that I have neither developed my code together with 
 *     any another person, nor copied any code from any other person, nor permitted 
 *     my code to be copied  or otherwise used by any other person, nor have I  
 *     copied, modified, or otherwise used programs created by others. I acknowledge 
 *     that any violation of the above terms will be treated as academic dishonesty.
 */
