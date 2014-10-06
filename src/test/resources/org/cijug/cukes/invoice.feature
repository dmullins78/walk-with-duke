Feature: See Balances

  Scenario: See my outstanding balance
    Given the following invoices
      | name   | amount | paid  |
      | Google | 100    | true  |
      | Apple  | 200    | false |
    When I see my outstanding balance
    Then I should see "200"