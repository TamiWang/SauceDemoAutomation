Feature: Login Functionality

  Background:
    Given The user opens the SauceDemo login page

  @smoke
  Scenario: User attempts to log in with different credentials from CSV test data
    When the user logs in using credentials from credentials.csv
    Then the login outcomes should match the expected results
