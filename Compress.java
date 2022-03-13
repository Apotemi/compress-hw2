/**
	14.03.2022
	Bil211 Homework-2

	Yusuf Aydin 211101019
	yusufaydin@etu.edu.tr
*/

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Compress {
	private ArrayList<Character> character;
	private String[] binaryCharacter;
	private String inputFile = "";	
	private String binaryFile = "";
	private byte decimalFile[];
	private int bitRequested;

	/**
		Compresses given text file and creates binary file
		with less memory usage.
	*/
	public void compress(String fileInputName) {
		getCharacters(fileInputName);
		generateBinaryCharacter();

		for(int i = 0; i < inputFile.length(); i++)
			for(int j = 0; j < character.size(); j++)
				if(character.get(j) == inputFile.charAt(i)) {
					binaryFile += binaryCharacter[j];
					break;
				}

		generateDecimalString();
		generateDecimalFile(fileInputName);
	}

	/**
		Reads and gets every diffrent character from textfile "fileInputName"
		and stores them in "character" ArrayList
	*/
	private void getCharacters(String fileInputName) {
		character = new ArrayList<>();

		Scanner inputStream = null;

		try {
			inputStream = new Scanner(new File(fileInputName));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		while(inputStream.hasNextLine()) {
			inputFile += inputStream.nextLine();
		}
		inputStream.close();

		String temp = inputFile;

		while(!temp.equals("")) {
			character.add(temp.charAt(0));
			temp = temp.replace(Character.toString(temp.charAt(0)), "");
		}
	}

	/**
		Generates Binary numbers with "generateBinary" method for every 
		corresponding character in "character" ArrayList and stores them in
		"binaryCharacter" array.
	*/
	private void generateBinaryCharacter() {
		binaryCharacter = new String[character.size()];
	
		for(int i = 0; i < binaryCharacter.length; i++) {
			binaryCharacter[i] = generateBinary(i, 2);
		}
	}
	
	/**
		Computes requested bit size for characters and stores it in "bitRequested",
		then creates binary numbers for each of characters.
	*/
	private String generateBinary(int i, int base) {
		String temp = "";
		String tempReversed = "";
		bitRequested = (int)Math.ceil((Math.log(character.size()) / Math.log(base)));

		if(i == 0)
			for(int m = 0; m < bitRequested; m++)
				temp += "0";

		while(i > 0) {
			temp += (i % base);
			i = i / base;
		}
		
		for(int j = temp.length(); j < bitRequested; j++)
			temp += "0";

		for(int j = temp.length() - 1; j >= 0; j--) {
			tempReversed += Character.toString(temp.charAt(j));
		}
		
		return tempReversed;
	}

	/**
		Splits binary string into 8 digit substrings and converts each of them
		from binary to decimal, then stores them(every 8bits) in "decimalFile"
		byte Array.
	*/
	private void generateDecimalString() {
		String[] temp = new String[(int)Math.ceil(binaryFile.length()/8.0)];
		int i = 0;
		String tempBinaryFile = binaryFile;

		while(!tempBinaryFile.equals("")) {
			if(tempBinaryFile.length() >= 8)
				temp[i] = tempBinaryFile.substring(0,8);
			else {
				temp[i] = tempBinaryFile.substring(0,tempBinaryFile.length());
				break;
			}

			tempBinaryFile = tempBinaryFile.substring(8,tempBinaryFile.length());
			i++;
		}

		decimalFile = new byte[temp.length];

		for(int n = 0; n < temp.length; n++) {
			int k = 0;

			for(int j = 0; j < temp[n].length(); j++)
				k += Character.getNumericValue(temp[n].charAt(j)) * (int)Math.pow(2, temp[n].length() - 1 - j);
			decimalFile[n] = (byte)k;
		}
	}
	/**
		Creates new binary file and writes
		-Binary Character Array's length to know how many diffrent characters that have used,
		-Binary File length to know the size of a file,
		-Decimal File length to know how many byte's it is going to write;
		-Requested Bit size to know how many digits every binary number have,
		-Characters and corresponding Binary Numbers,
		-Byte's Array.
	*/
	private void generateDecimalFile(String fileOutputName) {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileOutputName + ".C"));

			outputStream.writeInt(binaryCharacter.length);
			outputStream.writeInt(binaryFile.length());
			outputStream.writeInt(decimalFile.length);
			outputStream.writeInt(bitRequested);

			for(int i = 0; i < character.size(); i++) {
				outputStream.writeChar(character.get(i));
				outputStream.writeUTF(binaryCharacter[i]);
			}

			for(byte a : decimalFile)
				outputStream.writeByte(a);

			outputStream.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
}