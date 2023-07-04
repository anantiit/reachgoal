package interview.experiences;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class RingCentralRound1 {
	/*
	 * Given a list of strings input and an integer k, return the top k most
	 * frequent strings. You may return the answer in any order.
	 * 
	 * Example 1:
	 * 
	 * Input: input = [ first, first, second, first, first, second, third ], k = 2
	 * 
	 * Output: [ first, second ]
	 * 
	 * Example 2:
	 * 
	 * Input: input = [ first ], k = 1
	 * 
	 * Output: [ first ]
	 * 
	 * 
	 * to count map<String, Integer> frequency map,
	 * 
	 * First - 4 Second-2 Third - 3
	 * 
	 * Priorityqueue of size k
	 */

	public static List<Word> findTopKFrequentWords(String[] words, int k) {
		if (words == null || words.length < 1) {
			throw new IllegalArgumentException("given input list is empty or null+" + Arrays.toString(words));
		}
		if (k <= 0) {
			throw new IllegalArgumentException("given in put param k " + k + " is not positive");
		}
		LinkedList<Word> result = new LinkedList<Word>();
		int n = words.length;
		Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
		for (int i = 0; i < n; i++) {
			frequencyMap.compute(words[i], (k1, v) -> (v == null) ? 1 : v + 1);
		}
		PriorityQueue<Word> minHeap = new PriorityQueue<Word>((a, b) -> a.count - b.count);
		for (Map.Entry<String, Integer> wordEntry : frequencyMap.entrySet()) {
			Word word = new Word(wordEntry.getKey(), wordEntry.getValue());
			if (minHeap.size() < k) {
				minHeap.add(word);
			} else if (minHeap.peek().count == word.count) {
				if (word.word.compareTo(minHeap.peek().word) < 0) {
					minHeap.poll();
					minHeap.add(word);
				}
			} else if (minHeap.peek().count < word.count) {
				minHeap.poll();
				minHeap.add(word);
			}
		}
		// System.out.println(minHeap);
		int maxFreqSize = minHeap.size();
		for (int i = 0; i < maxFreqSize; i++) {
			result.addFirst(minHeap.poll());
		}
		return result;

	}

	public static void main(String[] args) {
		String[] input = { "first", "first", "second", "first", "first", "second", "second", "second", "third" };
		int k = 3;
		System.out.println(findTopKFrequentWords(input, k));
	}
}

class Word {
	String word;
	int count;

	public Word(String word, int count) {
		super();
		this.word = word;
		this.count = count;
	}

	@Override
	public String toString() {
		return "Word [word=" + word + ", count=" + count + "]";
	}

}