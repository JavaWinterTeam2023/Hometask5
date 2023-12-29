package TestFiles;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;


@Suite
@SuiteDisplayName("Your Logging Class Test Suite")
@SelectClasses({
		ChargingStationLogTest.class,
		SystemLogTesting.class,
		ResourceBatteryTesting.class
})

public class TestSuit {

}
