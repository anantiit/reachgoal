package com.csfundamentals.algo;

/*
 * print a-z in sequence there should 2 threads 
 */
public class Test7 {
	public static void main(String args[]) {
		PrintChar p1 = new PrintChar();
		PrintChar p2 = new PrintChar();
		// PrintNextChar p2 = new PrintNextChar();
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class PrintChar implements Runnable {
	static Integer counter = 0;
	static Object lock = new Object();

	@Override
	public void run() {
		while (counter < 26) {
			synchronized (lock) {
				if (counter % 2 == 0) {
					System.out.println("Thread" + Thread.currentThread().getName() + "char:" + (char) (counter + 'a'));
					counter++;
					// Remember we need to notify before we wait if the wait and notify are in same
					// synchronized block.
					lock.notify();
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This notify is for the last iteration one of the thread which went into wait
					// it will not get notified when the loop completes.
					lock.notify();
				} else {
					System.out.println("Thread" + Thread.currentThread().getName() + "char:" + (char) (counter + 'a'));
					counter++;
					lock.notify();
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// This notify is for the last iteration one of the thread which went into wait
					// it will not get notified when the loop completes.
					lock.notify();
				}
			}
		}
		return;
	}
}
