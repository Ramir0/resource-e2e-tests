Feature: Create Invalid Resource using Resource Service API

  Scenario: Upload an invalid file
    Given the API is available
    And a file "invalid_resource_1.txt"
    When send a POST request to "/resources"
    Then the response body should contain the message "Validation failed or request body is invalid MP3"
    And the response status code should be 400
