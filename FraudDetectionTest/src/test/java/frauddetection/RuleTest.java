package frauddetection;

import org.junit.Test;

import com.tibco.psg.beunit.TestHelper;

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

}
