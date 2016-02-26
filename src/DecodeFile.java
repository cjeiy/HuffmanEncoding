import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class DecodeFile {
	public Node rootOfHuffmanTree;
	public BitFileWriter decodedFile;
	public BitFileReader fileToDecode;
	
	public static void main(String[] args){
		new DecodeFile(args);
	}
	
	public DecodeFile(String[] args){
		
		//Öppnar filerna 
		decodedFile = new BitFileWriter(args[0]);
		fileToDecode = new BitFileReader(args[1]);
		openHuffmanTreeForEncodedFile();
		decodeHuffmanTree();
	}
	
	public void openHuffmanTreeForEncodedFile(){
		try{
			
			//Läser in .data file och castar till node
			FileInputStream dataFromEncodedFile = new FileInputStream("encodedFile.data");
			ObjectInputStream objectInData = new ObjectInputStream(dataFromEncodedFile);
			Object root = objectInData.readObject();
			if(root instanceof Node){
				rootOfHuffmanTree = (Node)root;
			}
			
		}catch(IOException e){
			System.out.println("Failed to open encoded file");
		}catch(ClassNotFoundException e){
			System.out.println("Failed to fetch huffman tree");
		}
	}
	
	public void decodeHuffmanTree(){
		
		
		// Utgår från inläst node som "traveler" och letar efter Byte i trädet.
		Node travelerNode = rootOfHuffmanTree;
		
		ArrayList<Byte> bytes = new ArrayList<Byte>();
		long numberOfBits = fileToDecode.fileBitLength() - (8-rootOfHuffmanTree.getNumberOfBitsInFinalByte());
		//System.out.println(numberOfBits);
		
		for(int i = 0; i < numberOfBits; i++){
			boolean currentBit = fileToDecode.nextBit();
			travelerNode = travelerNode.goDownInBranch(!currentBit);
			//System.out.println("currentBit: " + currentBit);
			if(travelerNode.isLeafNode()){
				
				//System.out.println("Character: "+ (char)travelerNode.getValue() + "\n");
				
				bytes.add(travelerNode.getValue());
				travelerNode = rootOfHuffmanTree;
			}
		}
		
		for(byte b:bytes){
			decodedFile.writeByte(b);
		}	
	}
}
