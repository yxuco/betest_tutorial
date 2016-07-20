package frauddetection;

import org.junit.Test;

import com.tibco.psg.beunit.TestHelper;

/**
 * Test rules in rule context
 * @author yxu
 *
 */
public class RuleContextTest {
	public static final String folder = "/Test/RuleContextTests/";

	@Test
	public void testApplyDebit() {
		TestHelper.assertRuleFunction(
				folder + "setupApplyDebit", true);
		TestHelper.assertRuleFunction(
				folder + "testApplyDebit", false);
	}

	@Test
	public void testBadCreateAccount() {
		TestHelper.assertRuleFunction(
				folder + "setupBadCreateAccount", true);
		TestHelper.assertRuleFunction(
				folder + "testBadCreateAccount", false);
	}

	@Test
	public void testUnsuspendAccount() {
		TestHelper.assertRuleFunction(
				folder + "setupUnsuspendAccount", true);
		TestHelper.assertRuleFunction(
				folder + "testUnsuspendAccount", false);
	}

}
