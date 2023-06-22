package lld.ratelimiter;

public interface RateLimiter {
	public boolean grantAccess(String userId);

}
