import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class RunTests {

    public static void main(String[] args) {
        JUnitCore core = new JUnitCore();
        core.addListener(new RunListener() {
            @Override
            public void testStarted(org.junit.runner.Description desc) {
                System.out.println("Starting test: " + desc.getMethodName());
            }

            @Override
            public void testFinished(org.junit.runner.Description desc) {
                System.out.println("✅ Passed: " + desc.getMethodName());
            }

            @Override
            public void testFailure(Failure failure) {
                System.out.println("❌ Failed: " + failure.getDescription().getMethodName());
                System.out.println("   ↳ " + failure.getMessage());
            }
        });

        // run the student’s test class
        Result result = core.run(DynamicArrayTests.class);

        System.out.println("\n====================");
        System.out.println("Summary:");
        System.out.println("✔ Passed: " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("✘ Failed: " + result.getFailureCount());
        System.out.println("⏱ Time:   " + result.getRunTime() + " ms");
        System.out.println("====================");

        if (result.wasSuccessful()) {
            System.out.println("🎉 All tests passed!");
        }
    }
}
