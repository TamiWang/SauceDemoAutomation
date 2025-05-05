Feature: Login Functionality

  Background:
    Given The user opens the SauceDemo login page

  Scenario: Login with credentials from CSV test data
    When the user logs in using credentials from credentials.csv
    Then the login outcomes should match the expected results


#  @security
#  Scenario: Verify error message disappears after closing
#    When the user enters invalid username "invalid_user"
#    And the user enters invalid password "invalid_pass"
#    And the user clicks the login button
#    And an error message is displayed
#    When the user closes the error message
#    Then the error message should not be visible
