import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try {	
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("haha.txt.C"));

			int binCharLength = inputStream.readInt();
			int decFileLength = inputStream.readInt();
			int bitRequested = inputStream.readInt();

			char[] character = new char[binCharLength];
			String[] binChar = new String[binCharLength];

			for(int i = 0; i < binCharLength; i++) {
				character[i] = inputStream.readChar();
				binChar[i] = inputStream.readUTF();
			}			 
		
			byte[] decFile = new byte[decFileLength];

			for(int i = 0; i < decFileLength; i++)
				decFile[i] = inputStream.readByte();

			System.out.println(binCharLength);
			System.out.println(decFileLength);
			System.out.println(bitRequested);

			for(char a : character)
				System.out.println(a);
			for(String a : binChar)
				System.out.println(a);
			//for(byte a : decFile)
			//	System.out.println(a);

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
}