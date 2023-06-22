package lld.autosuggest.frequency;

import java.util.ArrayList;
import java.util.List;

public class FrequencyService {
	FrequencyUpdateRepository frequencyUpdateRepository;

	public void frequencyUpdate(String word) {
		List<String> prefixes = new ArrayList<String>();
		for (int i = 2; i < word.length(); i++) {
			prefixes.add(word.substring(0, i));
		}
		frequencyUpdateRepository.batchUpdate(prefixes, word);
	}

}
