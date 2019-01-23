Feature: [Backend] Creating and updating todos

  Background:
    Given the system is live
    And the database is ready

  Scenario: I create thousands of rows
    Given I execute the template 'INSERT INTO names (name) VALUES (%d);' 10000 times
