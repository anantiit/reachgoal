package lld.ratelimiter;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RateLimiterDemo {

	public static void main(String[] args) {
		Random random = new Random();
		RateLimiter rateLimiter = SlidingWindowRateLimiterSingleton.getInstance();
		ExecutorService executors = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 1000; i++) {
			executors.submit(() -> {
				String userId = "user" + random.nextInt(1, 6);
				if (rateLimiter.grantAccess(userId)) {
					System.out.println(Thread.currentThread().getName() + ": user access granted for user : " + userId);
					return;
				}
				System.out.println(Thread.currentThread().getName()
						+ ": Too many requests. Please try after some time for user : " + userId);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		executors.shutdown();

	}
}
