Feature: This feature will login to the SonnetSoft website.

  Background:
    Given I open a browser
    Given I navigate to "https://sonnetsoft.com"
    Given I wait for 2 seconds

  Scenario:
    When I check the title is "Sonnet - Login"
    And I input the following username and password:
    | username | password |
    | admin    | pass     |
    And I click login
    Then I check the title is "Sonnet - Home"
    And I click Contact Us
    Then I check the title is "Sonnet - Contact Us"
    And I input the following contact details:
      | fullName   | email        | message                               |
      | A Customer | me@there.com | I have a question about your product. |
    And I click the contact by phone checkbox
    And I input the phone number "01234567890"
    And I click Submit
    Then I check the title is "Message Received"