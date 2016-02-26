import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class Node implements Serializable{
	
	private Node higherChild = null;
	private Node lowerChild = null;
	private int frequency = 1;
	private byte value;
	private boolean isLeafNode;
	
	private ArrayList<Boolean> bitcode = new ArrayList<Boolean>();
	
	/**
	 * Only used for root when loading a saved tree
	 * 
	 */
	
	private int numberOfBitsInFinalByte;
	
	
	/**
	 * 
	 * @param higher
	 * @param lower
	 */
	public Node(Node higher, Node lower){
		
		higherChild = higher;
		lowerChild = lower;
		
		frequency = higherChild.getFrequency() + lowerChild.getFrequency();
		
		isLeafNode = false;
		
	}
	/**
	 * 
	 * @param finalValue
	 */
	public Node(byte finalValue){
		value = finalValue;
		isLeafNode = true;
		
	}
	
	public boolean isLeafNode(){
		//System.out.println("Leaf Node: " + isLeafNode);
		return this.isLeafNode;
	}
	
	
	public int getFrequency(){
		return frequency;
		
	}
	
	public byte getValue(){
		return value;
	}
	
	public void incrementFrequency(){
		frequency++;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public ArrayList<Boolean> getBitCodeOfLeaf(byte value){
		bitcode.clear();
		fillBitCode(bitcode, value);
		Collections.reverse(bitcode);
		
		//System.out.println("Value, code: " + "( " + value + "\n"+ bitCode.toString());
		
		return bitcode;
	}
	
	
	/**
	 * Recursive method that search for the leaf corresponding to value and filling the bit-code.
	 * @param bitCode
	 * @param value
	 * @return
	 */
	public boolean fillBitCode(ArrayList<Boolean> bitCode, byte value){
		
		if(!this.isLeafNode){
			if(higherChild.fillBitCode(bitCode, value)){
				bitCode.add(false);
				return true;
			}else if(lowerChild.fillBitCode(bitCode,value)){
				bitCode.add(true);
				return true;	
			}	
			
		}else{
			if(this.value == value){
				return true;
			}
			
		}
		
		return false;
	}
	
	
	
	
	
	/**
	 * Only used for root node.
	 * 
	 */
	
	public Node goDownInBranch(boolean bit){
		if(this.isLeafNode){
			return this;
		}if(bit){
			return higherChild;
		}else{
			return lowerChild;
		}
		
		
	}
	
	public void setNumberOfBitsInFinalByte(int n) {
		numberOfBitsInFinalByte = n;
	}
	
	public int getNumberOfBitsInFinalByte() {
		return numberOfBitsInFinalByte;
	}
	
}
