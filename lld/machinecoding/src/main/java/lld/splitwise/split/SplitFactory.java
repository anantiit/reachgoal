package lld.splitwise.split;

public class SplitFactory {

	private static final Split EQUAL_SPLIT = new EqualSplit();
	private static final Split UNEQUAL_SPLIT = new UnEqualSplit();
	private static final Split PERCENTAGE_SPLIT = new PercentageSplit();

	public static Split getSplitInstance(SplitType type) {
		if (SplitType.EQUAL == type) {
			return EQUAL_SPLIT;
		}
		if (SplitType.UNEQUAL == type) {
			return UNEQUAL_SPLIT;
		}
		if (SplitType.PERCENTAGE == type) {
			return PERCENTAGE_SPLIT;
		}
		throw new IllegalArgumentException("Split type given is invalid: " + type);
	}
}
