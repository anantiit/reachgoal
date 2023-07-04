package digitalwallet.wallet;

import java.util.UUID;

public class WalletIdGenerator {

	public static UUID generateId() {
		return UUID.randomUUID(); // TODO as uuid is too long we can have some shorter id generated different algo
									// can be used for that
	}

}
