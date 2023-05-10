import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
	public static class BlockIterator implements Iterator<List<String>>, Cloneable {
		private final Pattern startOfBlock;
		private Iterator<String> inputIterator;

		public BlockIterator(Iterator<String> inputIterator, Pattern startOfBlock) {
			this.inputIterator = inputIterator;
			this.startOfBlock = startOfBlock;
		}

		/**
		 * If this iterator has more elements in it, return true without advancing the
		 * internal iterator pointer. Otherwise return false.
		 */
		@Override
		public boolean hasNext() {
			TestIterator testIterator = (TestIterator) inputIterator;
			TestIterator tempInputIterator = new TestIterator(testIterator);
			while (tempInputIterator.hasNext()) {
				String next = tempInputIterator.next();
				if (startOfBlock.matcher(next).matches()) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Return the next block. If no more block exists, throw
		 * {@link java.util.NoSuchElementException}.
		 */
		@Override
		public List<String> next() {
			List<String> stringBlock = new ArrayList<String>();
			TestIterator testIterator = (TestIterator) inputIterator;
			while (inputIterator.hasNext()) {
				String next = inputIterator.next();
				if (startOfBlock.matcher(next).matches()) {
					stringBlock.add(next);
					break;
				}
			}
			while (inputIterator.hasNext()) {
				TestIterator prevInputIterator = new TestIterator(testIterator);
				String next = inputIterator.next();
				if (!startOfBlock.matcher(next).matches()) {
					stringBlock.add(next);
				} else {
					this.inputIterator = prevInputIterator;
					break;
				}
			}
			if (stringBlock.size() <= 1) {
				throw new NoSuchElementException();
			}
			return stringBlock;

		}
	}

	// Template code - DO NOT EDIT

	private static final String BLOCK_DELIMITER = "#";
	private static final String ELEMENT_DELIMITER = "_";
	private static final String NO_SUCH_ELEMENT_EXCEPTION = "NoSuchElementException";
	private static final String TRUE = Boolean.TRUE.toString();
	private static final String FALSE = Boolean.FALSE.toString();

	public static class TestIterator implements Iterator<String> {
		private final List<String> loopArray;
		private Iterator<String> iterator;
		private final List<String> fixedArray;

		public TestIterator(List<String> fixedArray, List<String> loopArray) {
			iterator = fixedArray.isEmpty() ? loopArray.iterator() : fixedArray.iterator();
			this.loopArray = loopArray;
			this.fixedArray = fixedArray;
		}

		public TestIterator(TestIterator testIterator) {
			this.loopArray = testIterator.loopArray;
			this.fixedArray = testIterator.fixedArray;
			iterator = fixedArray.isEmpty() ? loopArray.iterator() : fixedArray.iterator();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public String next() {
			String ret = iterator.next();
			if (!iterator.hasNext())
				iterator = loopArray.iterator();
			return ret;
		}
	}

	private static class TestCase {
		public final List<String> input;
		public final String output;

		public TestCase(String inputString, String startOfBlock, String commands, List<String> outputString) {
			input = List.of(inputString, startOfBlock, commands);
			output = String.join(BLOCK_DELIMITER, outputString);
		}

		public void printInput() {
			System.out.println("\nInput: " + List.of(input.get(0).split(ELEMENT_DELIMITER)) + "\nstartOfBlock: "
					+ input.get(1) + "\ncommands: " + input.get(2));
		}

		public void printOutput(String outputString) {
			System.out.println(List.of(outputString.split(BLOCK_DELIMITER)).stream()
					.map(r -> List.of(r.split(ELEMENT_DELIMITER))).collect(Collectors.toList()));
		}
	}

	private static boolean runCase(TestCase testCase) {
		String inputString = testCase.input.get(0).replaceAll(" ", "");
		Pattern startOfBlock = Pattern.compile(testCase.input.get(1));
		String commands = testCase.input.get(2);

		List<String> fixedArray = new ArrayList<>();
		List<String> loopingArray = new ArrayList<>();

		int len = inputString.length();
		int index = 0;
		String curr = "";
		while (index < len) {
			if (inputString.charAt(index) == '_') {
				fixedArray.add(curr);
				curr = "";
			} else if (inputString.charAt(index) == '(') {
				index++; // Skipping open bracket
				while (inputString.charAt(index) != ',') {
					curr += inputString.charAt(index);
					index++;
				}
				index++; // Skipping comma
				if (inputString.charAt(index) == '*') {
					loopingArray.addAll(List.of(curr.split("_")));
					curr = "";
					break;
				}
				int freq = inputString.charAt(index) - '0';
				index++;
				while (inputString.charAt(index) != ')') {
					freq = freq * 10 + (inputString.charAt(index) - '0');
					index++;
				}
				fixedArray.addAll(
						Collections.nCopies(freq, List.of(curr.split("_"))).stream().flatMap(List::stream).toList());
				curr = "";
			} else {
				curr += inputString.charAt(index);
			}
			index++;
		}
		if (!curr.isEmpty()) {
			fixedArray.add(curr);
		}

		BlockIterator blockIterator = new BlockIterator(new TestIterator(fixedArray, loopingArray), startOfBlock);
		String expectedOutput = testCase.output;
		String actualOutput = "";

		for (int i = 0; i < commands.length(); i++) {
			String output;
			try {
				if (commands.charAt(i) == '0') {
					output = Boolean.valueOf(blockIterator.hasNext()).toString();
				} else {
					List<String> nextBlock = blockIterator.next();
					output = String.join(ELEMENT_DELIMITER, nextBlock);
				}
			} catch (NoSuchElementException nsee) {
				output = NO_SUCH_ELEMENT_EXCEPTION;
			}
			if (i > 0) {
				actualOutput += BLOCK_DELIMITER;
			}
			actualOutput += output;
		}

		if (actualOutput.equals(expectedOutput)) {
			return true;
		}

		System.out.print("FAILED: ");
		testCase.printInput();
		System.out.print("Expected: ");
		testCase.printOutput(expectedOutput);
		System.out.print("Found: ");
		testCase.printOutput(actualOutput);
		System.out.println("");
		return false;
	}

	public static void main(String[] args) {
		List<TestCase> testCases = getTestCases();
		List<TestCase> failed = new ArrayList<>();

		testCases.forEach(testCase -> {
			if (runCase(testCase)) {
				failed.add(testCase);
			}
		});
		System.out.println(String.format("%d/%d test cases passed", failed.size(), testCases.size()));
	}

	private static List<TestCase> getTestCases() {
		return List.of(
				new TestCase("start_data", "start", "0101",
						List.of(TRUE, "start_data", FALSE, NO_SUCH_ELEMENT_EXCEPTION)),
				new TestCase("123_start_data1_data2_start_data3_start", "start", "1111",
						List.of("start_data1_data2", "start_data3", "start", NO_SUCH_ELEMENT_EXCEPTION)),
				new TestCase("1", "start", "1", List.of(NO_SUCH_ELEMENT_EXCEPTION)),
				new TestCase("", "start", "1", List.of(NO_SUCH_ELEMENT_EXCEPTION)),
				new TestCase("123_start_data1_data2_start_data3_start_altStart", "altStart", "1", List.of("altStart")),
				new TestCase("altStart_x_y_q_a_altStart", "altStart", "111",
						List.of("altStart_x_y_q_a", "altStart", NO_SUCH_ELEMENT_EXCEPTION)),
				new TestCase("(start, 3)", "start", "1101010",
						List.of("start", "start", TRUE, "start", FALSE, NO_SUCH_ELEMENT_EXCEPTION, FALSE)),
				new TestCase("start_random_(start_xyz, 3)", "start", "11010101",
						List.of("start_random", "start_xyz", TRUE, "start_xyz", TRUE, "start_xyz", FALSE,
								NO_SUCH_ELEMENT_EXCEPTION)),
				new TestCase("start_random_(start, *)_random2", "start", "11010101",
						List.of("start_random", "start", TRUE, "start", TRUE, "start", TRUE, "start")),
				new TestCase("strt_rd1_(r2_staart_r, *)_rd3", "st.*rt", "1101011",
						List.of("strt_rd1_r2", "staart_r_r2", TRUE, "staart_r_r2", TRUE, "staart_r_r2", "staart_r_r2")),
				new TestCase("start_random_(satire_strt_x_start_y_stall, *)_random2", "st.*r.*",
						"1101010111110010101011001010110101",
						List.of("start_random_satire#strt_x#true#start_y_stall_satire#true#strt_x#true#start_y_stall_satire#strt_x#start_y_stall_satire#strt_x#start_y_stall_satire#true#true#strt_x#true#start_y_stall_satire#true#strt_x#true#start_y_stall_satire#strt_x#true#true#start_y_stall_satire#true#strt_x#true#start_y_stall_satire#strt_x#true#start_y_stall_satire#true#strt_x"
								.split("#"))));
	}
}