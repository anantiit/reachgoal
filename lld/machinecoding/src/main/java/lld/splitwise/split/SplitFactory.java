package lld.splitwise.split;

public class SplitFactory {

	public static Split getSplitInstance(SplitType type) {
		if (SplitType.EQUAL == type) {
			return new EqualSplit();
		}
		if (SplitType.UNEQUAL == type) {
			return new UnEqualSplit();
		}
		if (SplitType.PERCENTAGE == type) {
			return new PercentageSplit();
		}
		throw new IllegalArgumentException("Splittype given is invalid : " + type);

	}

}
