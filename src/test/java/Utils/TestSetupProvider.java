package Utils;

/**
 * Created by borisgurtovyy on 10/20/17.
 */
public class TestSetupProvider {
    private static TestSetupProvider ourInstance = new TestSetupProvider();
    private final TestSetup testSetup;

    private TestSetupProvider() {
        testSetup = TestSetup.getFromSystemProperty();
    }

    public static TestSetupProvider getInstance() {
        return ourInstance;
    }

    public TestSetup getTestSetup() {
        return testSetup;
    }
}
