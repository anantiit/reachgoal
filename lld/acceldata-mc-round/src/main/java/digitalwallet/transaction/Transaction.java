package digitalwallet.transaction;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Transaction {
	String payerId;
	String payeeId;
	Double amount;
	int timeStamp;

}
