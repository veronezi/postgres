Feature: [DB] Backup and recovery

  Background:
    Given the system is live

  Scenario: I create thousands of rows
    Given I drop the 'names' table
    And I execute the 'CREATE TABLE names (name_id serial PRIMARY KEY, name CHAR (10) NOT NULL);' update
    And I execute the 'INSERT INTO names (name) VALUES ('%d');' update template 20 times
    Then the count query 'SELECT count(*) FROM names;' returns 20 rows

  Scenario: I switch wal files and wait for the first backup to be triggered
    Given I execute the 'SELECT pg_switch_wal();' query
    And I wait for the first base backup to be ready

  Scenario: I mess up with the database then I restore it
    Given I execute the 'DELETE FROM names;' update
    Then the count query 'SELECT count(*) FROM names;' returns 0 rows
    Given I stop the services
    And I start the services on recovery mode
    Then the count query 'SELECT count(*) FROM names;' returns 20 rows