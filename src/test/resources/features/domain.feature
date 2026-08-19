Feature: Subscription component
  Scenario: list subscriptions
    When I GET "/subscriptions"
    Then the response status is 200
