Feature: Product Purchase Flow

  Scenario Outline: User completes a product purchase successfully
    Given the user logs in as <username>
    When the user adds a product to the cart
    And proceeds to checkout and completes the purchase
    Then the order should be confirmed successfully

    Examples:
      | username       |
      | login_standard |