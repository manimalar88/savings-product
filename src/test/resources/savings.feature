Feature: savings product creation
  Scenario: client makes call to POST /products/iban/
      When I send a POST request to /products/iban/ with the following:
      """
      {"productType":"SAVINGS"}
      """
    Then the response status should be 200