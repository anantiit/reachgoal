package lld.checkrandom;

import orestes.bloomfilter.CountingBloomFilter;
import orestes.bloomfilter.FilterBuilder;

public class CountingBloomFilterExample {
	public static void main(String[] args) {
		// Create a counting Bloom filter with an estimated number of elements and
		// desired false positive probability
		int expectedElementCount = 100000;
		CountingBloomFilter<String> bloomFilter = new FilterBuilder(expectedElementCount, .01)
				.buildCountingBloomFilter();

		// Add elements to the Bloom filter
		bloomFilter.add("element1");
		bloomFilter.add("element2");
		bloomFilter.add("element3");

		// Check if an element exists in the Bloom filter
		boolean exists = bloomFilter.contains("element1");
		System.out.println("Element exists: " + exists);

		// Remove an element from the Bloom filter
		// bloomFilter.remove("element2");

		// Check again if the removed element exists
		exists = bloomFilter.contains("element2");
		System.out.println("Element exists: " + exists);

		// Get the count of an element in the Bloom filter
		long count = bloomFilter.getEstimatedCount("element1");
		System.out.println("Element count: " + count);
	}
}
