package frauddetection;

import org.junit.Test;

import com.tibco.psg.beunit.TestHelper;

/**
 * Test rules in preprocessor context
 * @author yxu
 *
 */
public class RuleTest {
	public static final String folder = "/Test/RuleTests/";

	@Test
	public void testApplyDebit() {
		TestHelper.assertRuleFunction(
				folder + "testApplyDebit", true);
	}

	@Test
	public void testBadApplyDebit() {
		TestHelper.assertRuleFunction(
				folder + "testBadApplyDebit", true);
	}

	@Test
	public void testBadCreateAccount() {
		TestHelper.assertRuleFunction(
				folder + "testBadCreateAccount", true);
	}

	@Test
	public void testCheckNegativeBalance() {
		TestHelper.assertRuleFunction(
				folder + "testCheckNegativeBalance", true);
	}

	@Test
	public void testCreateAccount() {
		TestHelper.assertRuleFunction(
				folder + "testCreateAccount", true);
	}

	@Test
	public void testUnsuspendAccount() {
		TestHelper.assertRuleFunction(
				folder + "testUnsuspendAccount", true);
	}

	@Test
	public void testFraudDetection() {
		TestHelper.assertRuleFunction(
				folder + "testFraudDetection", true);
	}

	@Test
	public void testApplyDebitOnSuspendedAcct() {
		TestHelper.assertRuleFunction(
				folder + "testApplyDebitOnSuspendedAcct", true);
	}

}
