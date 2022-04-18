package com.distributed.project.dictionary.server.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used for request and response between the server and client.
 * 
 * @author Abhijeet - 1278218
 *
 */
public class DictionaryDto implements Serializable {

	private static final long serialVersionUID = -4590868446082091257L;

	private String action;

	private String searchKey;

	private String word;

	private String meaning;

	private String secondaryMeaning;

	private boolean isPresent;

	private String message;

	private long timeTaken = 0;

	private Map<String, List<Long>> responseTimeMap;

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getSecondaryMeaning() {
		return secondaryMeaning;
	}

	public void setSecondaryMeaning(String secondaryMeaning) {
		this.secondaryMeaning = secondaryMeaning;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, List<Long>> getResponseTimeMap() {
		return responseTimeMap;
	}

	public void setResponseTimeMap(Map<String, List<Long>> responseTimeMap) {
		this.responseTimeMap = responseTimeMap;
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, isPresent, meaning, message, responseTimeMap, searchKey, secondaryMeaning,
				timeTaken, word);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictionaryDto other = (DictionaryDto) obj;
		return Objects.equals(action, other.action) && isPresent == other.isPresent
				&& Objects.equals(meaning, other.meaning) && Objects.equals(message, other.message)
				&& Objects.equals(responseTimeMap, other.responseTimeMap) && Objects.equals(searchKey, other.searchKey)
				&& Objects.equals(secondaryMeaning, other.secondaryMeaning) && timeTaken == other.timeTaken
				&& Objects.equals(word, other.word);
	}

	public DictionaryDto(String action, String searchKey, String word, String meaning, String secondaryMeaning,
			boolean isPresent, String message) {
		super();
		this.action = action;
		this.searchKey = searchKey;
		this.word = word;
		this.meaning = meaning;
		this.secondaryMeaning = secondaryMeaning;
		this.isPresent = isPresent;
		this.message = message;
	}

	public DictionaryDto() {
		super();
	}

	@Override
	public String toString() {
		return "DictionaryDto [action=" + action + ", searchKey=" + searchKey + ", word=" + word + ", meaning="
				+ meaning + ", secondaryMeaning=" + secondaryMeaning + ", isPresent=" + isPresent + ", message="
				+ message + ", timeTaken=" + timeTaken + ", responseTimeMap=" + responseTimeMap + "]";
	}
}
