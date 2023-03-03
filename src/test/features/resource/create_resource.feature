Feature: Create and Delete a Resource using resource service API

  Scenario: Upload a valid file and delete it
    Given the API is available
    And a file "valid_resource_1.mp3"
    When send a POST request to "/resources"
    Then the response status code should be 200
    And the response body should contain a Resource Id
    And I wait for 2 seconds
    When send a DELETE request to "/resources" with the Resource Id
    Then the response status code should be 200
    And the response body should contain a List of deleted Resource Id
