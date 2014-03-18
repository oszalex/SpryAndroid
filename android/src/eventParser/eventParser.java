import java.util.*;

// # tags
// @ ort
// + personen
// !  zeitpunkt

class Event {
	
}

public class EventParser {

	public void testMethod() {

		testVariable
	}

	public void parse(String word) {
		if (null == word || word.length() == 0)
			return;

		char identifier = word.charAt(0);
		word = word.subString(1);

		if ('#' == identifier) parseTag(word);
		else if ('@' == identifier) parseLocation(word);
		else if ('+' == identifier) parsePerson(word);
		else if ('!' == identifier) parseTime(word);

	}

	public void parseTag(String word) {

	}

	public void parsePerson(String word) {

	}

	public void parseTime(String word) {

	}

	public void parseLocation(String word) {

	}
}