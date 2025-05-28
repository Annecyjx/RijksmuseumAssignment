Feature: Rijksmuseum collection search API validation

Scenario: Verify search results for "painting"
  Given the search API from Rijksmuseum is available
  When I search for "painting"
  Then I should receive a successful response with a list of results

Scenario: Verify search results for "aaaabbbb"
  Given the search API from Rijksmuseum is available
  When I search for "aaaabbbb"
  Then I should receive a not found response with no results
