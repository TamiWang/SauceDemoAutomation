# SauceDemoAutomation
This is SauceDemoAutomation automation project built with Java, Selenium WebDriver, Cucumber, JUnit 5, and Allure for reporting. 
It covers main user flows on www.saucedemo.com, including login, product purchase, and logout.


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