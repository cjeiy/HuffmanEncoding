import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class EncodeFile {
	public Node rootOfHuffmanTree;
	public BitFileWriter encodedFile;
	public BitFileReader fileToEncode;
	
	/**
	 * 
	 * @param args
	 * 
	 */
	
	public static void main(String[] args){
		if(args.length != 2){
			System.out.println("Woops! Wrong amount of arguments");			
		}else{
			new EncodeFile(args);
		}
	}
	
	public EncodeFile(String[] args){
		HuffmanTreeCreator tree = new HuffmanTreeCreator(args[0]);
		rootOfHuffmanTree = tree.getRoot();
		encodedFile = new BitFileWriter(args[1]);
		createEncodedFile(tree);
		saveHuffmanTree();
	
	}
	
	private void createEncodedFile(HuffmanTreeCreator tree){
		ArrayList<Byte> bytes = tree.getBytes();

		ArrayList<Boolean> code = new ArrayList();
		
		int numberOfBytes = bytes.size();
		int lastIndexBytes = numberOfBytes -1;
		
		for(int a = 0; a < numberOfBytes; a++){
			code = rootOfHuffmanTree.getBitCodeOfLeaf(bytes.get(a));
			int sizeOfCode = code.size();
			
			if(a == lastIndexBytes){
				for(int b = 0; b < sizeOfCode; b++){
					if(b == sizeOfCode-1){
						rootOfHuffmanTree.setNumberOfBitsInFinalByte(encodedFile.writeFinalBit(code.get(b)));
					}else{
						encodedFile.writeBit(code.get(b));
					}	
				}
			}else{
				for(int b = 0; b < sizeOfCode; b++){
					encodedFile.writeBit(code.get(b));
				}
			}
		}
		
		
		encodedFile.closeFileWriter();
		
		
		/**
		for(byte b : bytes){
			ArrayList<Boolean> code = rootOfHuffmanTree.getBitCodeOfLeaf(b);
			for(Boolean bool : code){
				if((bytes.indexOf(b) == bytes.size() -1) && (code.indexOf(bool) == code.size()-1)){
					rootOfHuffmanTree.setNumberOfBitsInFinalByte(encodedFile.writeFinalBit(bool));
				}else{
					encodedFile.writeBit(bool);	
				}
			}	
		}
		encodedFile.closeFileWriter();
		
		**/
	}
	
	public void saveHuffmanTree(){
		try{
			FileOutputStream fileOut = new FileOutputStream("encodedFile.data");
			ObjectOutputStream object = new ObjectOutputStream(fileOut);
			object.writeObject(rootOfHuffmanTree);
		}catch(IOException e){
			System.out.println("Failed to open tree");
		}	
	}
}
