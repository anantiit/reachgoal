package com.csfundamentals;

//Java program for reversing the Linked list

class LinkedList {

	static Node head;

	static class Node {

		int data;
		Node next;

		Node(int d) {
			data = d;
			next = null;
		}
	}

	// A simple and tail recursive function to reverse
	// a linked list. prev is passed as NULL initially.
	Node reverseUtil(Node curr, Node prev) {

		/* If last node mark it head*/
		if (curr.next == null) {
			head = curr;

			/* Update next to prev node */
			curr.next = prev;
			return null;
		}

		/* Save curr->next node for recursive call */
		Node next1 = curr.next;

		/* and update next ..*/
		curr.next = prev;

		reverseUtil(next1, curr);
		return head;
	}
	
	Node reverseInGroups(Node head, int k){
		if(head==null||head.next==null)return head;
		Node cur=head;
		Node prev= null;
		Node next = null;
		int i=0;
		while(cur.next!=null&&i<k){
			next = cur.next;
			cur.next=prev;
			prev=cur;
			cur=next;
			i++;
		}
		head.next=reverseInGroups(cur,k);
		head=prev;
	 return head;
	}
	
	Node reverse(Node head){
		Node cur=head;
		Node prev= null;
		Node next = null;
		while(cur!=null){
			next = cur.next;
			cur.next=prev;
			prev=cur;
			cur=next;
		}
	 return prev;
	}

	// prints content of double linked list
	void printList(Node node) {
		while (node != null) {
			System.out.print(node.data + " ");
			node = node.next;
		}
	}
void printOneNodeFromStartAndOneNodeFromEnd(Node head){
	if(head==null)
		return;
	Node slowPtr=null;
	Node fastPtr=head.next;
	Node tempHead=head;
	while(fastPtr!=null&&fastPtr.next!=null){
		if(slowPtr==null)
		{
			slowPtr=head;	
		}
		else
		slowPtr=slowPtr.next;
		fastPtr=fastPtr.next.next;
	}
	Node secondHalfReversed = reverse(slowPtr.next);
	//slowPtr.next=secondHalfReversed;
	System.out.println("\nslow pointer"+slowPtr.data+" ");
	//printList(head);
	while(secondHalfReversed!=null&&tempHead!=null){
		if(tempHead!=null){
		System.out.print(tempHead.data + " ");
		tempHead=tempHead.next;
		}
		if(secondHalfReversed!=null){
		System.out.print(secondHalfReversed.data+" ");
		secondHalfReversed = secondHalfReversed.next;
		}
	}
}
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.head = new Node(1);
		list.head.next = new Node(2);
		list.head.next.next = new Node(3);
		list.head.next.next.next = new Node(4);
		list.head.next.next.next.next = new Node(5);
		list.head.next.next.next.next.next = new Node(6);
		list.head.next.next.next.next.next.next = new Node(7);
		list.head.next.next.next.next.next.next.next = new Node(8);
		list.head.next.next.next.next.next.next.next.next = new Node(9);
		System.out.println("Original Linked list ");
		list.printList(head);
		Node revG=list.reverseInGroups(head,2);
		System.out.println("\nReversed linked list in groups ");
		list.printList(revG);
		Node res = list.reverse(revG);
		System.out.println("\nReversed linked list ");
		list.printList(res);
		System.out.println();
		list.printOneNodeFromStartAndOneNodeFromEnd(res);
	}
}


//This code has been contributed by Mayank Jaiswal

