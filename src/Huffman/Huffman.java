package Huffman;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class Huffman{
	private static Scanner scan = new Scanner(System.in);
	private static Map<Character, String> prefixCodeTable = new HashMap<>(); // char= char / String  = numbers 10101010
	
	public static void main(String args[]) {
		System.out.println("put original data");
		//Origin text data
		String data = scan.nextLine();
		System.out.println("Original Data: "+data);
		
		//Huffman Encoding
		String encodeData = encode(data);
		System.out.println("Encoded data : "+ encodeData);
		
		//Huffman Decoding
		
		// Report
		int originalDataByteSize= data.getBytes(StandardCharsets.UTF_8).length;	
		System.out.println("Original Data Size: " +originalDataByteSize*8+" Bit");
		int encodedDataByteSize = encodeData.length();
		System.out.println("Encoded Data Size: "+encodedDataByteSize+" Bit");
		}
	
	//Encoding method
	public static String encode(String data) {
		//Get Frequency by Character
		Map<Character, Integer> charFreq = new HashMap<>();
		
		for(char c :data.toCharArray()) {
			if(!charFreq.containsKey(c)) {
				charFreq.put(c, 1);
			}else {
				int no = charFreq.get(c);
				charFreq.put(c,++no);
			}
		}
		System.out.println("Frequency by Character:"+ charFreq);
		
		//Build Huffman Tree
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		Set<Character> keySet = charFreq.keySet();
		for(char c : keySet) {
		Node node = new Node(c, charFreq.get(c));// c= key= char  , charfreq.get(c) = freq
		priorityQueue.offer(node);
		}
		Node rootNode = buildTree(priorityQueue);  // recursion
		
		//Set PrefixCode by Char
		System.out.println("PrefixCodeTable");
		setPrefixCode(rootNode,"");// recursion
		
		//Convert Origin data to Prefix code
		StringBuilder sb = new StringBuilder();
		for(char c : data.toCharArray()) {
			sb.append(prefixCodeTable.get(c));
		}
		
		return sb.toString();
		
	}
	public static Node buildTree(PriorityQueue<Node> priorityQueue) {//default: smallest # will be the first poll
		if(priorityQueue.size()==1) {
			return priorityQueue.poll();
		}else {
			Node leftNode= priorityQueue.poll();
			Node rightNode = priorityQueue.poll();
			
			Node sumNode = new Node();
			sumNode.cData = '`';
			sumNode.frequency =leftNode.frequency +rightNode.frequency;
			sumNode.left = leftNode;
			sumNode.right = rightNode;
			priorityQueue.offer(sumNode);
		}
		
		
		return buildTree(priorityQueue);
		
	}
	
	//Set prefix Code recursive method
	
	public static void setPrefixCode(Node node, String code) {
		if(node == null) {
			return;
		}
		
		if(node.cData !='`' && node.left == null && node.right== null) {
			prefixCodeTable.put(node.cData, code);
			System.out.println("- " + node.cData + "(" + node.frequency + ") = " + code);
		}else {
			setPrefixCode(node.left,code +'0');
			setPrefixCode(node.right, code+'1');
		}
	}
}