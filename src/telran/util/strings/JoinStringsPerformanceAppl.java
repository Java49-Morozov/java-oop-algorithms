package telran.util.strings;

public class JoinStringsPerformanceAppl {

	public static void main(String[] args) {
		final int N_STRINGS = 1000;
		String[] strings = new String[N_STRINGS];		
		for (int i = 0; i < N_STRINGS; i++) {
			strings[i] = "Hello";
		}
		
		int nRuns = 10000;
		JoinStrings joinString1 = new JoinStringsImpl();
		JoinStrings joinString2 = new JoinStringsBuilderImpl();
		
		JoinStringsPerformanceTest<JoinStringsImpl> joinStringTest = 
				new JoinStringsPerformanceTest<JoinStringsImpl>
				("Join String Test", nRuns, strings, joinString1);
		JoinStringsPerformanceTest<JoinStringsBuilderImpl> joinStringBuilderTest = 
				new JoinStringsPerformanceTest<JoinStringsBuilderImpl>
				("Join StringBuilder Test", nRuns, strings, joinString2);
		
		joinStringTest.run();
		joinStringBuilderTest.run();
	}

}
