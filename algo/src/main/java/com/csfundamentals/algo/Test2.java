package com.csfundamentals.algo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class Test2 {

	public static void main(String[] args) {
		String[][] board1 = { { "5", "3", ".", ".", "7", ".", ".", ".", "." },
				{ "6", ".", ".", "1", "9", "5", ".", ".", "." }, { ".", "9", "8", ".", ".", ".", ".", "6", "." },
				{ "8", ".", "2", ".", "6", ".", ".", ".", "3" }, { "4", ".", ".", "2", ".", "3", ".", ".", "1" },
				{ "7", ".", ".", ".", "2", ".", ".", ".", "6" }, { ".", "6", ".", ".", ".", ".", "2", "8", "." },
				{ ".", ".", ".", "4", "1", "9", ".", ".", "5" }, { ".", ".", ".", ".", "8", ".", ".", "7", "9" } };

		String[][] board2 = { { "8", "3", ".", ".", "7", ".", ".", ".", "." },
				{ "6", ".", ".", "1", "9", "5", ".", ".", "." }, { ".", "9", "8", ".", ".", ".", ".", "6", "." },
				{ "8", ".", ".", ".", "6", ".", ".", ".", "3" }, { "4", ".", ".", "8", ".", "3", ".", ".", "1" },
				{ "7", ".", ".", ".", "2", ".", ".", ".", "6" }, { ".", "6", ".", ".", ".", ".", "2", "8", "." },
				{ ".", ".", ".", "4", "1", "9", ".", ".", "5" }, { ".", ".", ".", ".", "8", ".", ".", "7", "9" } };
		if (checkSudoku(board1)) {
			System.out.println("Valid sudoku");
		} else {
			System.out.println("Not valid sudoku");
		}
		if (checkSudoku(board2)) {
			System.out.println("Valid sudoku");
		} else {
			System.out.println("Not valid sudoku");
		}
	}

	public static boolean checkSudoku(String[][] sudoku) {
		HashMap<String, HashSet<Node>> keys = new HashMap<String, HashSet<Node>>();
		for (int k = 1; k < 10; k++) {
			String key = k + "";
			keys.put(key, null);
		}
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				Node node = new Node(i, j);
				if (keys.get(sudoku[i][j]) != null && !keys.get(sudoku[i][j]).isEmpty()) {
					for (Node oldNode : keys.get(sudoku[i][j])) {
						if (node.sudokuEquals(oldNode)) {
							System.out.println("old" + keys.get(sudoku[i][j]) + "new " + node);
							return false;
						}
					}
				}
				if (!".".equals(sudoku[i][j])) {
					HashSet<Node> keyset;
					if (keys.get(sudoku[i][j]) == null) {
						keyset = new HashSet<Node>();
					} else {
						keyset = keys.get(sudoku[i][j]);
					}
					keyset.add(node);
				}
			}
		}
		return true;
	}
}

class Node {
	int i;
	int j;

	public Node(int i, int j) {
		super();
		this.i = i;
		this.j = j;
	}

	@Override
	public String toString() {
		return "Node [i=" + i + ", j=" + j + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		return true;
	}

	public boolean sudokuEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (i == other.i)
			return true;
		if (j == other.j)
			return true;
		if ((i % 3 < 2 && (i + 1 == other.i + 1)) && (j % 3 < 2 && (j + 1 == other.j + 1))) {
			return true;
		}
		if ((i % 3 < 1 && (i + 2 == other.i + 2)) && (j % 3 < 1 && (j + 2 == other.j + 2))) {
			return true;
		}
		return false;
	}

	Input:matrix=[[1,2,3],[4,5,6],[7,8,9]]Output:[1,2,3,6,9,8,7,4,5]

	Input:matrix=[[1,2,3,4],[5,6,7,8],[9,10,11,12]]Output:[1,2,3,4,8,12,11,10,9,5,6,7]

	0,0 0,1 0,2 1,2 2,2 2,1 2,0 1,1

	private void spiralPrintMatrix(int[][] matrix){
			boolean iHitMax = false;
			boolean jHitMax = false;
			boolean iHitMin = false;
			boolean jHitMin = false;
			int i=0;
			int j=0;
			while(lastElementReached(i,j)){
			if(i==0)
			  iHitMin=true;
			   if(i==m-1)
			  iHitMax=true;
			   if(j==0)
			  jHitMin=true;
			   if(j==n-1)
			  jHitMax=true;
				print(matrix[i][j])
				if(jHitMax&&!iHitMax){
			  i++
			  }
			  if(jHitMin&&!iHitMin){
			  i--
			  }
			  if(iHitMax&&!jHitMin){
			  j++
			  }
			  if(iHitMin&&!jHitMax){
			  j--
			  }
			}
			}

	private boolean lastElementReached(i,j){
			if(i==floor(m/2) && j==floor(n/2)){
			return true;
			}


			-------------------------
			-------------------------

			    3
			   / \
			  9  20
			    /  \
			   15   7

			[
			  [3],
			  [9,20],
			  [15,7]
			]

			 [3],
			  [9,20],
			  [15,7]

	public void levelOrderTraversal(Node root){
				Queue<Node> queue = new Queue<Node>();
				Node specialNode = new Node("$"); 
				ArrayList<ArrayList<Node>> nodeArray = new  ArrayList<ArrayList<Node>>();
				queue.enqueue(root);
			  queue.enqueue(specialNode);
				int rowNumber =0;
				while(!queue.isEmpty){
					Node node = queue.dequeue();
					if("$".equals(node.value)){
						rowNumber++
			      queue.enqueue(specialNode);
					}
			    else{
			    nodeArray[rowNumber].add(node);
					if(node.left!=null)queue.enqueue(node.left);
					if(node.right!=null)queue.enqueue(node.right);
			    }
			  }
			}

}

	private static printLeftView( Node root){
		Node specialNode = new Node(‘$’);
		 Queue<Node> q = new Queue<Node>();
		q.enque(root);
		q.enque(specialnode);
		system.out.println(root.getValue());
		while(!queue.isEmpty()){
		Node node = queue.deque();
		 if(specialNode.equals(node)&&!queue.isEmpty()){
		   node = queue.deque();
		   system.out.println(node.getValue());
		   q.enque(specialnode);
		 }
		if(node.left!=null){queue.enque(node.left);}
		if(node.right!=null){queue.enque(node.left);
		} 
		}
		}
