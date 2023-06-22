package lld.autosuggest;

import java.util.List;

import lld.autosuggest.autosuggest.AutoSuggestService;
import lld.autosuggest.frequency.FrequencyService;

public class AutoSuggestController {
	AutoSuggestService autoSuggestService;
	FrequencyService frequencyService;

	public List<String> autosuggest(String prefix) {
		return autoSuggestService.autosuggest(prefix);
	}

	public void frequencyUpdate(String word) {
		frequencyService.frequencyUpdate(word);
	}
}
