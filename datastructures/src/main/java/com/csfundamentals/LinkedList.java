package com.csfundamentals;

//Java program for reversing the Linked list

class LinkedList {

	Node head;

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

		/* If last node mark it head */
		if (curr.next == null) {
			head = curr;

			/* Update next to prev node */
			curr.next = prev;
			return null;
		}

		/* Save curr->next node for recursive call */
		Node next1 = curr.next;

		/* and update next .. */
		curr.next = prev;

		reverseUtil(next1, curr);
		return head;
	}

	Node reverseInGroups(Node head, int k) {
		if (head == null || head.next == null)
			return head;
		Node cur = head;
		Node prev = null;
		Node next = null;
		int i = 0;
		while (cur.next != null && i < k) {
			next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
			i++;
		}
		head.next = reverseInGroups(cur, k);
		head = prev;
		return head;
	}

	Node reverse(Node head) {
		Node cur = head;
		Node prev = null;
		Node next = null;
		while (cur != null) {
			next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}
		return prev;
	}

	// prints content of double linked list
	public static void printList(Node node) {
		while (node != null) {
			System.out.print(node.data + " ");
			node = node.next;
		}
	}

	void printOneNodeFromStartAndOneNodeFromEnd(Node head) {
		if (head == null)
			return;
		Node slowPtr = null;
		Node fastPtr = head.next;
		Node tempHead = head;
		while (fastPtr != null && fastPtr.next != null) {
			if (slowPtr == null) {
				slowPtr = head;
			} else
				slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		Node secondHalfReversed = reverse(slowPtr.next);
		// slowPtr.next=secondHalfReversed;
		System.out.println("\nslow pointer" + slowPtr.data + " ");
		// printList(head);
		while (secondHalfReversed != null && tempHead != null) {
			if (tempHead != null) {
				System.out.print(tempHead.data + " ");
				tempHead = tempHead.next;
			}
			if (secondHalfReversed != null) {
				System.out.print(secondHalfReversed.data + " ");
				secondHalfReversed = secondHalfReversed.next;
			}
		}
	}

	public static Node addTwoLinkedLists(Node list1, Node list2) {
		// 1->2->3->4 represents 4321
		// 4->5->6 represents 654
		// Result: 5->7->9 975
		if (list1 == null) {
			return list2;
		}
		if (list2 == null) {
			return list2;
		}
		int carry = 0;
		Node head1 = list1;
		Node head2 = list2;
		Node head3 = null;
		LinkedList list3 = new LinkedList();

		// Calculate sizes of the linked list only
		while (head1 != null && head2 != null) {
			int sum = head1.data + head2.data + carry;
			int data = sum % 10;
			carry = sum / 10;
			Node temp = new Node(data);
			if (head3 != null) {
				head3.next = temp;
				head3 = head3.next;
			} else {
				head3 = temp;
				list3.head = head3;
			}
			head1 = head1.next;
			head2 = head2.next;
		}
		Node bigListHead = null;
		if (head1 == null) {
			bigListHead = head2;
		} else if (head2 == null) {
			bigListHead = head1;
		}
		while (bigListHead != null) {
			int sum = bigListHead.data + carry;
			int data = sum / 10;
			carry = sum % 10;
			Node temp = new Node(data);
			if (head3 != null) {
				head3.next = temp;

			} else {
				head3 = temp;
			}
			bigListHead = bigListHead.next;
		}
		if (carry > 0) {
			Node temp = new Node(carry);
			if (head3 != null) {
				head3.next = temp;

			} else {
				head3 = temp;
			}
		}

		return list3.head;
	}

	public static void main(String[] args) {
		LinkedList list1 = new LinkedList();
		list1.head = new Node(1);
		list1.head.next = new Node(2);
		list1.head.next.next = new Node(3);
		list1.head.next.next.next = new Node(4);
		LinkedList list2 = new LinkedList();
		list2.head = new Node(5);
		list2.head.next = new Node(6);
		list2.head.next.next = new Node(7);
		list2.head.next.next.next = new Node(8);
		list2.head.next.next.next.next = new Node(9);
		printList(addTwoLinkedLists(list1.head, list2.head));
		// 4321
		// 98765
		// 4321
		// 13086
		// System.out.println("Original Linked list ");
		// list.printList(head);
		// Node revG = list.reverseInGroups(head, 2);
		// System.out.println("\nReversed linked list in groups ");
		// list.printList(revG);
		// Node res = list.reverse(revG);
		// System.out.println("\nReversed linked list ");
		// list.printList(res);
		// System.out.println();
		// list.printOneNodeFromStartAndOneNodeFromEnd(res);
	}
}
