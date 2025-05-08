Feature: Logout Functionality

  @logout
  Scenario: Validate that a user can log out successfully after logging in
    Given the user logs in with username "standard_user" and password "secret_sauce"
    When the user clicks on the menu button
    And the user clicks the logout link
    Then the user should be redirected to the login page