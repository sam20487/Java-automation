# Report Listener

A Customize Listener extending the Testng, to track the reports.

Jersey Rest Cosumer is used to interact with the APIs. Jackson is used for object mapping. Custom annotations and Reflections are used to read the report values during run time from the test classes.

## Requirements

- JDK 1.8
- Maven Framework
- Jersey Rest Client
- Jackson

## Installation

1. Clone the repository: `git clone ssh://git@gitlab.corp.olacabs.com:2222/benedict.johnson/report-listener.git`
2. Import the application: `Eclipse` or `Intellij`
3. Create a report property file under the config folder: `report.properties`
4. Use Custom Annotations at the class and method level in the Test class `@Report`

