package lld.autosuggest.autosuggest;

import java.util.List;

import lld.autosuggest.Constants;

public class AutoSuggestService {
	AutoSuggestRepository autoSuggestRepository;

	public List<String> autosuggest(String prefix) {
		List<String> result;
		result = searchInRedis(Constants.AUTO_SUGEST_LIMIT);
		if (result == null || result.isEmpty()) {
			result = populateRedisAutoSuggestionsFromDB(prefix, Constants.TTL_SECS);
		}
		return result;
	}

	private List<String> populateRedisAutoSuggestionsFromDB(String prefix, int ttlSecs) {
		// TODO Auto-generated method stub
		autoSuggestRepository.getAutoSuggestionsFromDB(prefix, Constants.AUTO_SUGEST_LIMIT);
		return null;
	}

	private List<String> searchInRedis(int autoSugestLimit) {
		// TODO Auto-generated method stub

		return null;
	}
}
