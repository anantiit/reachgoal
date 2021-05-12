package com.reachgoal.assignments.swiggy;

/**
 * Hello world!
 *
 */

/**
 * void foo(int n) { for (int i = n/2; i < n; i++) { for (int j = 1; j < n; j =
 * j*2) { for (int k = 0; k < n/2; k++) { printf("*"); } } } }
 * 
 * find complexity for above problem: n/2 * log2n* n/2
 * 
 * void foo(int n) { int i=1; int s=1; while(s <= n) { i++; s = s + i;
 * printf("*"); } }
 * 
 * find complexity for above problem: s(s+1)/2 = n s~sqrt(n) o(sqrt(n))
 * 
 * 
 * //This function should return 9^n
 * 
 * int pow(int n) { int[] power9 = int power9[n]; return power(power9,n); } 9^7
 * ==> p(7), p(3), p(1), p(2), p(4)
 * 
 * int power(int[] power9, int n){ if(n==1){ return 9; } if(power9[n]>0){ return
 * power9[n]; } else{ if(n%2==0){ if(power9[n/2]<=0){ power9[n/2] = power(n/2);
 * } power9[n] = power9[n/2] * power9[n/2]; return power9[n]; } else{
 * if(power9[n/2]<=0){ power9[n/2] = power(n/2); } if(power9[n/2+1]<=0){
 * power9[n/2+1] = power(n/2+1); } power9[n] = power9[n/2] * power9[n/2]; return
 * power9[n]; } } } }
 * 
 * 
 * level -> l->e-v->e->l leel -> l->e->e->l
 * 
 * 1. Know the size : n steps 2. You will iteratte till mid and push on stack :
 * n/2 3. You will iterate second half and compare stack : n/2
 * 
 * 
 * class Node { char value; Node next; } s= l, sp=e fp=v s= e, sp=v, fp =e
 * 
 * leel s=l sp=e fp=e s= abcdeedcba
 * 
 * 
 * boolean isPalindrome( Node head) { Stack<Character> s= new Stack<Character>
 * (); Node fastPtr = head; Node slowPtr= head; while(fastPtr.next!=null &&
 * fastPtr.next.next!=null){ s.push(slowPtr.char); slowPtr = slowPtr.next;
 * fastPtr = slowPtr.next; } boolean oddSize; if(fastPtr.next.next==null){
 * oddSize=true; } if(oddSize){ slowPtr = slowPtr.next; } while(!s.isEmpty &&
 * slowPtr!=null){ if(s.pop()!=slowPtr.char ){ return false; } slowPtr =
 * slowPtr.next; } return true; }
 * 
 * @author appalanaiduabotula
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
