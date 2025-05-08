# SauceDemoAutomation
This is SauceDemoAutomation automation project built with Java, Selenium, Cucumber, JUnit 5, and Allure for reporting. 
It covers main user flows on www.saucedemo.com, including login, product purchase, and logout.

**Design Details**
SauceDemoAutomation project follows the **Page Object Model (POM)** pattern to encapsulate UI interactions, enabling separation
between test logic and page structure. 

The framework includes:

- **Selenium** for browser automation

- **Cucumber** for BDD-style, human-readable test cases

- **JUnit 5** as the test runner

- **Allure** for rich test reporting with step-wise screenshots and failure diagnostics

- **CSV-based** test data loading to support data-driven testing

- **Gradle** for build automation and flexible test execution with environment properties and tag filtering

Test setup and teardown are managed using **Cucumber Hooks**, with centralized driver management through a custom DriverFactory.
This factory implements the **Singleton design pattern**, ensuring only one browser instance is created and reused per test session.

Browser configuration (including headless and guest modes) is externally configurable, supporting cross-environment execution and CI/CD pipelines.

**Setup Instructions**
1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-repo/SauceDemoAutomation.git
   cd SauceDemoAutomation
   
2. **Run Tests**
   ```bash
   ./gradlew clean test
   or
   ./gradlew clean test -Dcucumber.filter.tags="@smoke" -Dchrome.guest.mode=true

3. **Generate Allure Report**
   ```bash
   ./gradlew allureReport
   ./gradlew allureServe