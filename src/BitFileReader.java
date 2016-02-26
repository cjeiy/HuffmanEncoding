import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class BitFileReader {
	
	
	private ArrayList<Byte> bytes = new ArrayList<Byte>();
	
	private long bitsInFile;
	
	private int nBits;
	
	//Current bit position, starts at the end of the file. 
	private int currentBit = 0;
	
	
	public BitFileReader(String fileN){
		try {
			fileToArray(fileN);
		} catch (FileNotFoundException fnfe) {
			System.out.println("error when opening file");
			fnfe.printStackTrace();
			System.exit(0);
		} catch(IOException ioe){
			System.out.println("error while reading bytes");
			ioe.printStackTrace();
			System.exit(0);	
		}
	}
	
	private void fileToArray(String fileN) throws FileNotFoundException, IOException {
		RandomAccessFile file = null;
		
		//RandomAccessFile can read all types of files.
		file = new RandomAccessFile(fileN, "r");
		
		while(true){
			try{
				bytes.add(file.readByte());
				bitsInFile+=8;
			}catch(EOFException eof){
				//Last byte has been read, we're done.
				break;
			}
	
		}
		
		//last bit's position
		currentBit = (int) (bitsInFile - 1);
		nBits = currentBit;
		
		
		file.close();
	}
	
	
	public long fileBitLength(){
		return bitsInFile;
	}
	
	
	public boolean nextBit(){
		int bitPositionInByte = currentBit%8;
		int bytePosInList = (int)(nBits  - (currentBit - bitPositionInByte))/8; 
		currentBit--;
		
		//int bitValue = (int)(bytes.get(bytePosInList)&(1<<bitPositionInByte));
		
		if(((int)bytes.get(bytePosInList)&(1<<bitPositionInByte))!=0){
			return true;
		}else{
			return false;
		}
		
		/**
		switch(bitValue){
			case 0:
				return false;
			default:
				return true;
		}		
		 **/

	}
	
	
	public boolean hasNextBit(){
		switch(currentBit){
			case -1:
				return false;
			default:
				return true;
		}
		
	}
}
