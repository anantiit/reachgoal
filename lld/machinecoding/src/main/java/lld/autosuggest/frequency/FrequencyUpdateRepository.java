package lld.autosuggest.frequency;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FrequencyUpdateRepository {
	  private static final String KEYSPACE = "your_keyspace";
	    private static final String TABLE = "your_table";
	    private static final String COLUMN = "counter_column";

	public void batchUpdate(List<String> prefixes, String word) {
		  // Create a BatchStatement
	 String incrementStatement = String.format(
                 "UPDATE %s.%s SET %s = %s + 1 WHERE key = ?",
                 KEYSPACE, TABLE, COLUMN, COLUMN);
        BatchStatement batch = BatchStatement.builder(BatchStatement.Type.LOGGED)
                .build();

        // List of items to update

        // Iterate over the items and add insert statements to the batch
        for (String prefix : prefixes) {
            batch.add(incrementStatement.bind(prefix,word));
        }

        // Execute the batch statement
        session.execute(batch);
    }
	}

}
