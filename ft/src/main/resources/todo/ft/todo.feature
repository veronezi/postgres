Feature: [Backend] Creating and updating todos

  Background:
    Given the system is live

  Scenario: I create thousands of rows
    Given I drop the 'names' table
    And I execute the 'CREATE TABLE names (name_id serial PRIMARY KEY, name CHAR (10000) NOT NULL);' update
    And I execute the 'INSERT INTO names (name) VALUES ('%d');' update template 50000 times
    Then the count query 'SELECT count(*) FROM names' returns 50000 rows
