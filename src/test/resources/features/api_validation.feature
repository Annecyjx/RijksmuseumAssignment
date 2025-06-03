Feature: Rijksmuseum collection API validations

Scenario: Verify the search API to get results for searching "painting"
  Given the search API from Rijksmuseum is available
  When I search for "painting"
  Then I should receive a successful response with a list of results

Scenario: Verify the search API to get no results for searching "aaaabbbb"
  Given the search API from Rijksmuseum is available
  When I search for "aaaabbbb"
  Then I should receive a successful response with no results

Scenario: Verify the search API to get results for searching "Van Gogh"
  Given the search API from Rijksmuseum is available
  When I search for "Van Gogh"
  Then I should receive a successful response with a list of results
