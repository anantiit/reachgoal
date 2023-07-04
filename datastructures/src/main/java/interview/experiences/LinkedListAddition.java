package interview.experiences;
import lombok.ToString;

public class LinkedListAddition {
	/*
	 * public static LinkedList addLinkedList(LinkedList<Integer> a,
	 * LinkedList<Integer> b) { LinkedList<Integer> result = new
	 * LinkedList<Integer>(); LinkedList<Integer> minSizeList; LinkedList<Integer>
	 * maxSizeList; if(a.size()<b.size()) { minSizeList = a; maxSizeList = b; } else
	 * { minSizeList = b; maxSizeList = a; } int carry = 0;
	 * 
	 * for(int i=minSizeList.size();i>=0;i--) { int temp = minSizeList.get(i)+
	 * 
	 * }
	 * 
	 * return result;
	 * 
	 * }
	 */

	public static void reverseLinkedList(LinkedList1 a) {
		Node cur = a.head;
		Node prev = null;
		Node next = null;
		while (cur != null) {
			next = cur.next;
			cur.next = prev;
			prev = cur.next;
			cur = next;
			prev = cur;
		}
		a.head = prev;
	}

// 1->2->3	
//	cur=1 prev=null temp = 1; 1->null cur= 
	public static void main(String args) {
		LinkedList1 list = new LinkedList1();
		System.out.println(a);
	}
}

class LinkedList1 {
	Node head;
	int size;

	public void add(Node newElem) {
		Node temp = head.next;
		head.next = newElem;
		newElem.next = temp;
		size++;
	}

}

@ToString
class Node {
	Node next;
	int digit;

}
