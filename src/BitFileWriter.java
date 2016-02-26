import java.io.FileOutputStream;
import java.io.IOException;


public class BitFileWriter {
	
	private FileOutputStream file;
	private boolean[] bits = new boolean[8];
	private int writtenBits = 0;
	private int writtenBytesToFile = 0;
	
	
	/**
	 * 
	 * @param fileN
	 */
	
	public BitFileWriter(String fileN){
		try{
			file = new FileOutputStream(fileN);
		}catch(IOException e){
			System.out.println("Error when opening file.");
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param b
	 */
	public void writeBit(boolean b){
		bits[writtenBits] = b;
		writtenBits++;
		
		if(writtenBits == 8){
			try {
				writeByteToFileFromArray();
			} catch (IOException e) {
				System.out.println("Error while writing byte #" + writtenBytesToFile + " to file.");
				e.printStackTrace();
			}
			writtenBits = 0;
		}
	}
	
	
	public void writeByte(byte b){
		try{
			file.write(b);
		}catch(IOException e){
			System.out.println("Failed to write byte to file");
			System.exit(0);
		}
	}
		
	
	/**
	 * 
	 * @throws IOException
	 */
	public void writeByteToFileFromArray() throws IOException{
		int copyOfBitArray = 0;
		
		for(int i = 0; i < 8; i++){
			if(bits[7-i]){
				copyOfBitArray = copyOfBitArray|(1<<i);
			}else{
				copyOfBitArray = copyOfBitArray|(0<<i);
			}		
		}
		
		byte outputByte = (byte)copyOfBitArray;
		
		file.write(outputByte);
		writtenBytesToFile++;	
	}
	/**
	 * 
	 */
	public void closeFileWriter(){
		try {
			file.close();
		} catch (IOException e) {
			System.out.println("Error while closing file");
		}
	}

	public int writeFinalBit(Boolean bool) {
		if(writtenBits == 7)
			writeBit(bool);
		else {
			writeBit(bool);
			//Fill with trailing zeroes
			for(int i = 7; i > writtenBits-1; i--) {
				bits[i] = false;
			}
			int temp=0;
			for(int i = 0; i < 8; i++) {
				if(bits[7-i]==true)
					temp=temp|(1<<i);
				else 
					temp=temp|(0<<i);
			}
			byte b = (byte)temp;
			try {
				file.write(b);
			} catch(IOException e) {
				System.out.println("Failed to write to file");
			}
		}
		
		
		if(writtenBits == 0) 
			writtenBits = 8;
		
		return writtenBits;
	}
}
	

	
	
		



