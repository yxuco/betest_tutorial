package frauddetection;

import org.junit.Test;

import com.tibco.psg.beunit.TestHelper;

public class FunctionTest {

	@Test
	public void testInitializeScorecard() {
		TestHelper.assertRuleFunction("/Test/FunctionTests/testInitializeScorecard", false);
	}

}
