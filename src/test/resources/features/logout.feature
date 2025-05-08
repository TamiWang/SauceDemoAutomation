Feature: Logout Functionality

  @smoke @e2e @logout
  Scenario Outline: Validate that a user can log out successfully after logging in
    Given the user logs in as <username>
    When the user clicks on the menu button
    And the user clicks the logout link
    Then the user should be redirected to the login page

    Examples:
      | username      |
      | standard_user |