package lld.ratelimiter;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowRateLimiterSingleton implements RateLimiter, Cloneable, Serializable {
	private static final int MILLI = 1000;
	private static final int RATE_LIMT_PER_TIME_WINDOW = 5;
	private static final int RATE_LIMT_TIME_WINDOW_IN_SECONDS = 2;
	private volatile static SlidingWindowRateLimiterSingleton rateLimiter;
	private volatile static Map<String, ConcurrentLinkedQueue<Long>> rateLimitUserQueueMap = new ConcurrentHashMap<>();

	private SlidingWindowRateLimiterSingleton() {
		if (rateLimiter != null) {
			throw new IllegalStateException("Object already created");
		}
	}

	@Override
	public boolean grantAccess(String userId) {
		ConcurrentLinkedQueue<Long> queue = rateLimitUserQueueMap.get(userId);
		if (queue == null) {
			System.out.println("Map size: " + rateLimitUserQueueMap.size() + " Creating new queue for user :" + userId);
			queue = new ConcurrentLinkedQueue<Long>();
			rateLimitUserQueueMap.put(userId, queue);
		}
		// System.out.println(rateLimitUserQueueMap);
		Long now = System.currentTimeMillis();
		comapareAndRemoveElementsBelongToPrevWindows(queue, now);
		if (queue.size() < RATE_LIMT_PER_TIME_WINDOW) {
			queue.add(now);
			System.out.println("userId:" + userId + "queue size: " + queue.size() + " queue:" + queue);
			return true;
		}
		return false;
	}

	private void comapareAndRemoveElementsBelongToPrevWindows(ConcurrentLinkedQueue<Long> queue, Long now) {
		while (!queue.isEmpty() && now > (queue.peek() + RATE_LIMT_TIME_WINDOW_IN_SECONDS * MILLI)) {
			queue.poll();
		}

	}

	public static SlidingWindowRateLimiterSingleton getInstance() {
		if (rateLimiter == null) {
			synchronized (SlidingWindowRateLimiterSingleton.class) {
				if (rateLimiter == null) {
					rateLimiter = new SlidingWindowRateLimiterSingleton();
				}
			}
		}
		return rateLimiter;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new CloneNotSupportedException();
	}

	protected Object readResolve() {
		return rateLimiter;
	}

}
