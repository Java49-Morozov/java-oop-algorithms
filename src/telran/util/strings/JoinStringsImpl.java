package telran.util.strings;

public class JoinStringsImpl implements JoinStrings {

	@Override
	public String join(String[] strings, String delimiter) {
		String res = strings[0];
		for(int i = 1; i < strings.length; i++) {
			res += delimiter + strings[i];
		}
		return res;
	}
}
