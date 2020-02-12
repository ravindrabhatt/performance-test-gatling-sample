# Sample performance test using gatling

Performance test are written using gatling. Sample simulation available in SampleSimulation 
Sample inputs are read from the resources/search*.csv files.

To test it out, simply execute the following command:
```bash
    mvn gatling:test
```

## Parameters - sample only
| Parameter              | Description   |
| ---------------------- |:-------------|
| -Dusers                | Number of users - **rampUp scenario**                                 |
| -DrampupDuration       | Duration in seconds - **rampUp scenario**                             |
| -Drps                  | Max RPS to be reached - **rampUp scenario**                           |
| -DholdFor              | Hold max RPS for how long - **rampUp scenario**                       |
| -DrpsDuration          | Time to be taken for reaching the RPS value - **rampUp scenario**     |
| -DconstantUserPerSec   | Number of users to be increased per second for - **constant scenario**|
| -DconstantLoadDuration | Duration in seconds for - **constant scenario**                       |
| -DbaseUrl              | Base url for application                                              |

## Changing log level
Change LOG level in logback-test.xml
```xml
	<root level="REPLACE_ME">
		<appender-ref ref="CONSOLE" />
	</root>
```