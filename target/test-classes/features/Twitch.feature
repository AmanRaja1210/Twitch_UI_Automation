Feature: Search and view a streamer

  Scenario: Search and view a streamer
    Given I open Twitch website
    When I click on browse
    And I input "StarCraft II"
    And I scroll down 2 times
    And I select one streamer
    And I select Data from DataTable
      | username   | password   |
      | aman       | aman123    |
      | raja       | raja456    |
      | testuser   | test789    |

    Then I take a screenshot after page is loaded
