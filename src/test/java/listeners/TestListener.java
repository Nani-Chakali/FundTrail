package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

	@Override
	public void onStart(ITestContext context) {
		System.out.println("==> Test Suite started: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("==> Test Suite finished: " + context.getName());
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("--> Test started: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("--> Test passed: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("--> Test FAILED: " + result.getMethod().getMethodName());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("--> Test skipped: " + result.getMethod().getMethodName());
	}
}
