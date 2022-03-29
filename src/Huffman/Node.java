package Huffman;

public class Node implements Comparable<Node>{
char cData;
int frequency;
Node left, right;
	
Node(){
	
}
Node(char cData, int frequency){
	this.cData = cData;
	this.frequency = frequency;
}
	

//for Sorting priorityQueue
	@Override
	public int compareTo(Node node) {
		// TODO Auto-generated method stub
		return frequency- node.frequency;
	}
	
}
