package com.csfundamentals;

//Java program for reversing the Linked list

class LinkedList {

	ListNode head;

	static class ListNode {

		int data;
		ListNode next;

		ListNode(int d) {
			data = d;
			next = null;
		}
	}

	// A simple and tail recursive function to reverse
	// a linked list. prev is passed as NULL initially.
	ListNode reverseUtil(ListNode curr, ListNode prev) {

		/* If last node mark it head */
		if (curr.next == null) {
			head = curr;

			/* Update next to prev node */
			curr.next = prev;
			return null;
		}

		/* Save curr->next node for recursive call */
		ListNode next1 = curr.next;

		/* and update next .. */
		curr.next = prev;

		reverseUtil(next1, curr);
		return head;
	}

	static ListNode reverseInGroups(ListNode head, int k) {
		if (head == null || head.next == null)
			return head;
		ListNode cur = head;
		ListNode prev = null;
		ListNode next = null;
//		while (cur.next != null) {
//			prev = null;
//			next = null;
		int i = 0;
		while (cur.next != null && i < k) {
			next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
			i++;
		}
//			head.next = cur;
//			head = prev;
//		}
		head.next = reverseInGroups(cur, k);
		head = prev;
		return head;
	}

	ListNode reverse(ListNode head) {
		ListNode cur = head;
		ListNode prev = null;
		ListNode next = null;
		while (cur != null) {
			next = cur.next;
			cur.next = prev;
			prev = cur;
			cur = next;
		}
		return prev;
	}

	// prints content of double linked list
	public static void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.data + " ");
			node = node.next;
		}
	}

	void printOneNodeFromStartAndOneNodeFromEnd(ListNode head) {
		if (head == null)
			return;
		ListNode slowPtr = null;
		ListNode fastPtr = head.next;
		ListNode tempHead = head;
		while (fastPtr != null && fastPtr.next != null) {
			if (slowPtr == null) {
				slowPtr = head;
			} else
				slowPtr = slowPtr.next;
			fastPtr = fastPtr.next.next;
		}
		ListNode secondHalfReversed = reverse(slowPtr.next);
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

	public static ListNode addTwoLinkedLists(ListNode list1, ListNode list2) {
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
		ListNode head1 = list1;
		ListNode head2 = list2;
		ListNode head3 = null;
		LinkedList list3 = new LinkedList();

		// Calculate sizes of the linked list only
		while (head1 != null && head2 != null) {
			int sum = head1.data + head2.data + carry;
			int data = sum % 10;
			carry = sum / 10;
			ListNode temp = new ListNode(data);
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
		ListNode bigListHead = null;
		if (head1 == null) {
			bigListHead = head2;
		} else if (head2 == null) {
			bigListHead = head1;
		}
		while (bigListHead != null) {
			int sum = bigListHead.data + carry;
			int data = sum / 10;
			carry = sum % 10;
			ListNode temp = new ListNode(data);
			if (head3 != null) {
				head3.next = temp;

			} else {
				head3 = temp;
			}
			bigListHead = bigListHead.next;
		}
		if (carry > 0) {
			ListNode temp = new ListNode(carry);
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
		list1.head = new ListNode(1);
		list1.head.next = new ListNode(2);
		list1.head.next.next = new ListNode(3);
		list1.head.next.next.next = new ListNode(4);
		LinkedList list2 = new LinkedList();
		list2.head = new ListNode(5);
		list2.head.next = new ListNode(6);
		list2.head.next.next = new ListNode(7);
		list2.head.next.next.next = new ListNode(8);
		list2.head.next.next.next.next = new ListNode(9);
		printList(reverseInGroups(list1.head, 2));
		// printList(addTwoLinkedLists(list1.head, list2.head));
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
