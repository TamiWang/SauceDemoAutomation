Feature: Product Purchase Flow
@smoke
  Scenario Outline: User completes a product purchase successfully
    Given the user logs in as <username>
    When the user adds the first product to the cart
    And the user proceeds to checkout and fills in their <firstName>, <lastName> and <postalCode>
    And the user reviews the order and completes the purchase
    Then the order should be confirmed with a success message <completeMsg>

    Examples:
      | username      | firstName | lastName | postalCode | completeMsg               |
      | standard_user | Tami      | Wang     | 3000       | Thank you for your order!!! |