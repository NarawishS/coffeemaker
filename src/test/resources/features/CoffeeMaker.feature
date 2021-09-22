Feature: Make a coffee in coffee maker
  coffeeMaker can add up to 3 recipes
  coffeeMaker can edit a recipes but the name can't change
  A customer can make a coffee using coffee maker.

  Scenario: Create a new coffeeMaker
    Given I create a coffee maker
    Then The recipe book is 3

  Scenario: Add a Recipe
    Given I create a coffee maker
    When I create a new Recipe with name "Coffee" chocolate "0" coffee "3" milk "1" sugar "1" price "50" to coffeeMaker
    Then The recipe book contain recipe

  Scenario: Add a Recipe
    Given I create a coffee maker
    When I create a new Recipe with name "Mocha" chocolate "10" coffee "3" milk "1" sugar "1" price "75" to coffeeMaker
    Then The recipe book contain recipe

  Scenario: Add coffee to Inventory
    Given I create a coffee maker
    When I add a coffee "1" to coffeeMaker
    Then The coffee in coffeeMaker is 16

  Scenario: Add milk to Inventory
    Given I create a coffee maker
    When I add a milk "1" to coffeeMaker
    Then The milk in coffeeMaker is 16

  Scenario: Add sugar to Inventory
    Given I create a coffee maker
    When I add a sugar "1" to coffeeMaker
    Then The sugar in coffeeMaker is 16

  Scenario: Add chocolate to Inventory
    Given I create a coffee maker
    When I add a chocolate "1" to coffeeMaker
    Then The chocolate in coffeeMaker is 16

  Scenario: Add negative coffee Inventory
    Given I create a coffee maker
    When I add a coffee "-1" to coffeeMaker
    Then The coffee in coffeeMaker is 15

  Scenario: Add negative milk Inventory
    Given I create a coffee maker
    When I add a milk "-1" to coffeeMaker
    Then The milk in coffeeMaker is 15

  Scenario: Add negative sugar Inventory
    Given I create a coffee maker
    When I add a sugar "-1" to coffeeMaker
    Then The sugar in coffeeMaker is 15

  Scenario: Add negative chocolate Inventory
    Given I create a coffee maker
    When I add a chocolate "-1" to coffeeMaker
    Then The chocolate in coffeeMaker is 15

  Scenario: Add String coffee Inventory
    Given I create a coffee maker
    When I add a coffee "a" to coffeeMaker
    Then The coffee in coffeeMaker is 15

  Scenario: Add String milk Inventory
    Given I create a coffee maker
    When I add a milk "a" to coffeeMaker
    Then The milk in coffeeMaker is 15

  Scenario: Add String sugar Inventory
    Given I create a coffee maker
    When I add a sugar "a" to coffeeMaker
    Then The sugar in coffeeMaker is 15

  Scenario: Add String chocolate Inventory
    Given I create a coffee maker
    When I add a chocolate "a" to coffeeMaker
    Then The chocolate in coffeeMaker is 15

  Scenario: Delete a Recipe
    Given I create a coffee maker
    And I create a new Recipe with name "Coffee" chocolate "0" coffee "3" milk "1" sugar "1" price "50" to coffeeMaker
    When I delete a recipe 0
    Then The recipe 0 is deleted

  Scenario: Edit a Recipe
    Given I create a coffee maker
    And I create a new Recipe with name "Coffee" chocolate "0" coffee "3" milk "1" sugar "1" price "50" to coffeeMaker
    When I edit a recipe 0 ingredient to chocolate "10" coffee "10" milk "10" sugar "10" and price "10"
    Then The recipe 0 is "Coffee" with ingredient chocolate "10" coffee "10" milk "10" sugar "10" and price "10"

  Scenario: Purchase Beverage
    Given I create a coffee maker
    And I create a new Recipe with name "Coffee" chocolate "0" coffee "3" milk "1" sugar "1" price "50" to coffeeMaker
    When I make coffee recipe 0 with money 100
    Then I get change 50
    And Inventory is coffee 12 milk 14 sugar 14 chocolate 15

  Scenario: Purchase Beverage 0 money
    Given I create a coffee maker
    And I create a new Recipe with name "Coffee" chocolate "0" coffee "3" milk "1" sugar "1" price "50" to coffeeMaker
    When I make coffee recipe 0 with money 0
    Then I get change 0
    And Inventory is coffee 15 milk 15 sugar 15 chocolate 15

  Scenario: Purchase Beverage expensive coffee
    Given I create a coffee maker
    And I create a new Recipe with name "Coffee" chocolate "0" coffee "3" milk "1" sugar "1" price "500" to coffeeMaker
    When I make coffee recipe 0 with money 100
    Then I get change 100
    And Inventory is coffee 15 milk 15 sugar 15 chocolate 15

  Scenario: Purchase Beverage negative money
    Given I create a coffee maker
    And I create a new Recipe with name "Coffee" chocolate "0" coffee "3" milk "1" sugar "1" price "50" to coffeeMaker
    When I make coffee recipe 0 with money -100
    Then I get change -100
    And Inventory is coffee 15 milk 15 sugar 15 chocolate 15