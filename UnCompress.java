import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class UnCompress {
	private int binCharLength;
	private int decArrLength;
	private int bitRequested;
	private int binFileLength;
	private char[] character;
	private String[] binChar;
	private byte[] decArr;

	public UnCompress(String fileInputName) {
		try {	
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileInputName));

			this.binCharLength = inputStream.readInt();
			this.binFileLength = inputStream.readInt();
			this.decArrLength = inputStream.readInt();
			this.bitRequested = inputStream.readInt();

			this.character = new char[binCharLength];
			this.binChar = new String[binCharLength];

			for(int i = 0; i < binCharLength; i++) {
				character[i] = inputStream.readChar();
				binChar[i] = inputStream.readUTF();
			}			 
		
			this.decArr = new byte[decArrLength];

			for(int i = 0; i < decArrLength; i++)
				decArr[i] = inputStream.readByte();       

			inputStream.close();

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	public void unCompress() {
		String binaryFile = getBinaryFile();

		String[] temp = new String[binaryFile.length() / bitRequested];

		for(int i = 0; i < temp.length; i++)
			temp[i] = binaryFile.substring(bitRequested * i,bitRequested * (i+1));
		
		String unCompressedFile = "";

		for(int i = 0; i < temp.length; i++) {
			for(int j = 0; j < binCharLength; j++)
				if(binChar[j].equals(temp[i]))
					unCompressedFile += character[j];
		}
	}

	private String getBinaryFile() {
		int[] decIntArr = new int[decArrLength];

		for(int i = 0; i < decArr.length; i++)
			if(decArr[i] < 0)
				decIntArr[i] = (int)decArr[i] + 256;
			else
				decIntArr[i] = (int)decArr[i];

		String binaryFile = "";

		for(int i = 0; i < decIntArr.length; i++) {
			String temp = Integer.toBinaryString(decIntArr[i]);

			if((i == decIntArr.length - 1) && (binFileLength % 8 != 0)) {
				for(int j = temp.length(); j < binFileLength % 8; j++)
					temp = "0" + temp;
				binaryFile += temp;
				break; 
			}

			for(int j = temp.length(); j < 8; j++)
				temp = "0" + temp;
		
			binaryFile += temp;
		}

		return binaryFile;
	}


	public static void main(String[] args) {
		UnCompress hah = new UnCompress("haha.txt.C");
		hah.unCompress();
	}
}