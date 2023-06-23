package telran.util.strings;

public abstract class PerformanceTest<T> {
	String testName;
	int nRuns;
	
	PerformanceTest(String testName,int nRuns){
		this.testName = testName;
		this.nRuns = nRuns;
	}
	
	protected abstract void runTest();
	
	public void run() {
		long milliSec1 = System.currentTimeMillis();
		for (int i=0;i<nRuns;i++) {
			runTest();
		}
		long milliSec2 = System.currentTimeMillis();
		System.out.println(testName + " - " + nRuns + " times: " + (milliSec2-milliSec1) + " msec");
	}
}
