Feature: This feature will register and delete an account.

  Background:
    Given I open a browser
    Given I navigate to "https://techbeamers.com/selenium-practice-test-page/"

  Scenario:
    Given I type the name "Username"
    When I type the email "email@email.com"
    And I type the password "password1234"
    And I select the following interests:
      | sports | music | reading | travel |
      | Yes    | No    | No      | Yes    |
    And I submit the form
    Then I verify the submission works

