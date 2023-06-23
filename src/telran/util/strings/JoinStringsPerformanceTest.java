package telran.util.strings;

public class JoinStringsPerformanceTest<T> extends PerformanceTest<T> {

	String[] strings;
	JoinStrings joinStrings;
	
	JoinStringsPerformanceTest(String testName, int nRuns, String[] strings, JoinStrings joinStrings) {
		super(testName, nRuns);
		
		this.strings = strings;
		this.joinStrings = joinStrings;
	}

	@Override
	protected void runTest() {
		joinStrings.join(strings, ";");
	}
}
