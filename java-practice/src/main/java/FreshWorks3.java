import java.util.LinkedList;
import java.util.Queue;

public class FreshWorks3 {

	/*
	 * post order traversal
	 * 
	 * 
	 * 
	 */

	public void connectNextInTree(Node root) {
		if (root == null) {
			return;
		}
		Queue<Node> q = new LinkedList<Node>();
		Node cur;
		q.offer(root);
		while (!q.isEmpty()) {
			int i = 0;
			int prevLevelSize = q.size();
			while (i < prevLevelSize) {
				cur = q.poll();
				cur.next = q.peek();
				if (cur.left != null) {
					q.offer(cur.left);
				}
				if (cur.right != null) {
					q.offer(cur.right);
				}
				i++;
			}
		}

	}
}

/*
 * Key value Store
 * 
 * FR : 1. client shoud be able to put key , value pair 2. Client also can get
 * on key to get the value 3. concurrency (v2) 4. Rest APIs exposed get, put
 * 
 * NFR: Highly scalable (Distributed ) Highly Available (Redundancy) Consistancy
 * 
 * 
 * Design Considerations
 * 
 * Partition based on key Consistant hashing
 * 
 * 
 * 
 */

//a, b, c, d, e,f
// q a
// q b c
// q c f
// q f d e
class Node {
	Node right;
	Node left;
	Node next;
	int val;
}
