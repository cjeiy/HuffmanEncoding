import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class HuffmanTreeCreator {
	
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Byte> bytes = new ArrayList<Byte>();
	
	public HuffmanTreeCreator(String fileName){
		BytesFromFile(fileName);
		fillNodesFromBytes();
		sortNodesWithRespectToFrequency();
		makeHuffmanNode();
	}
	
	public Node getRoot(){
		return nodes.get(0);
	}
	
	/**
	 * 
	 * @param fileName
	 */
	private void BytesFromFile(String fileName){
		RandomAccessFile file = openFile(fileName);
		fillBytes(file, fileName);
	}
	
	/**
	 * 
	 * @param file
	 * @param fileName
	 */
	private void fillBytes(RandomAccessFile file, String fileName){
		while(true){
			try{
				bytes.add(file.readByte());
			}catch(EOFException e){
				/**
				 * End of file, bytes now contains all data from file.
				 */
				break;
			}catch(IOException e){
				System.out.println("Failed to read byte from file: " + fileName);
				System.exit(0);
			}
		}
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private RandomAccessFile openFile(String fileName){
		RandomAccessFile file = null;
		try{
			file = new RandomAccessFile(fileName, "r");
				
		}catch(IOException e){
			System.out.println("Failed to open file: " + fileName);
			System.exit(0);
		}
		return file;	
	}
	
	private void fillNodesFromBytes(){
		for(Byte b : bytes){
			Node nodeWithCurrentByte = findNodeWithByte(b);
			if(nodeWithCurrentByte != null){
				nodeWithCurrentByte.incrementFrequency();
			}else{
				nodes.add(new Node(b));
			}
		}
	}
	
	private Node findNodeWithByte(Byte b){
		for(Node node : nodes){
			if(node.getValue() == b){
				return node;
			}	
		}
		return null;
	}
	
	private void sortNodesWithRespectToFrequency(){
		int numberOfNodes = nodes.size();
		/**
		 * Bubble sort algorithm.
		 * 
		 * Highest frequency -> lowest index.
		 * 
		 */
		for(int a = 0; a < numberOfNodes-1; a++) {
			for(int b = 0; b < numberOfNodes-1-a; b++) {
				if(nodes.get(b).getFrequency() < nodes.get(b+1).getFrequency()) {
					Node temp = nodes.get(b);
					nodes.set(b, nodes.get(b+1));
					nodes.set(b+1, temp);
				}
			}
		}
	}
	
	private void makeHuffmanNode(){
		while(true){
			int currentSize = nodes.size();
			if(currentSize > 1){
				/**
				 * Fuse the nodes with lowest frequency
				 */
				
				nodes.set(currentSize-2, new Node(nodes.get(currentSize-2),nodes.get(currentSize-1)));
				nodes.remove(currentSize - 1);
			}else{
				break;
			}	
		}
	}

	public ArrayList<Byte> getBytes() {
		return bytes;
	}
	
}
