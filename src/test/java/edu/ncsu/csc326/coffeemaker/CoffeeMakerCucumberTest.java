package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;


public class CoffeeMakerCucumberTest {
    private CoffeeMaker coffeeMaker;
    private Recipe recipe1;
    private int money;

    public static String printInventory(int coffee, int milk, int sugar, int chocolate) {
        return String.format("Coffee: %d\nMilk: %d\nSugar: %d\nChocolate: %d\n",
                coffee, milk, sugar, chocolate);
    }

    public Recipe createRecipe(String name, String chocolate, String coffee, String milk, String sugar, String price)
            throws RecipeException {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setAmtChocolate(chocolate);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setPrice(price);
        return recipe;
    }

    @Given("I create a coffee maker")
    public void setup() {
        coffeeMaker = new CoffeeMaker();
    }

    @Then("The recipe book is {int}")
    public void testRecipeBookSize(int size) {
        assertEquals(String.format("Size of coffeeMaker RecipeBook should be %d", size),
                size, coffeeMaker.getRecipes().length);
    }


    @When("I create a new Recipe with name {string} chocolate {string} coffee {string} milk {string} sugar {string} price {string} to coffeeMaker")
    public void createAndAdd(String name, String chocolate, String coffee, String milk, String sugar, String price)
            throws RecipeException {
        recipe1 = createRecipe(name, chocolate, coffee, milk, sugar, price);
        coffeeMaker.addRecipe(recipe1);
    }

    @Then("The recipe book contain recipe")
    public void testAddRecipe() {
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
    }

    @When("I add a coffee {string} to coffeeMaker")
    public void addCoffee(String amtCoffee) throws InventoryException {
        try {
            coffeeMaker.addInventory(amtCoffee, "0", "0", "0");
        } catch (InventoryException ie) {
            assertThrows(InventoryException.class, () ->
                    coffeeMaker.addInventory(amtCoffee, "0", "0", "0"));
        }
    }

    @Then("The coffee in coffeeMaker is {int}")
    public void testAddCoffee(int amtCoffee) {
        assertEquals(printInventory(amtCoffee, 15, 15, 15), coffeeMaker.checkInventory());
    }

    @When("I add a milk {string} to coffeeMaker")
    public void addMilk(String amtMilk) throws InventoryException {
        try {
            coffeeMaker.addInventory("0", amtMilk, "0", "0");
        } catch (InventoryException ie) {
            assertThrows(InventoryException.class, () ->
                    coffeeMaker.addInventory("0", amtMilk, "0", "0"));
        }
    }

    @Then("The milk in coffeeMaker is {int}")
    public void testAddMilk(int amtMilk) {
        assertEquals(printInventory(15, amtMilk, 15, 15), coffeeMaker.checkInventory());
    }

    @When("I add a sugar {string} to coffeeMaker")
    public void addSugar(String amtSugar) throws InventoryException {
        try {
            coffeeMaker.addInventory("0", "0", amtSugar, "0");
        } catch (InventoryException ie) {
            assertThrows(InventoryException.class, () ->
                    coffeeMaker.addInventory("0", "0", amtSugar, "0"));
        }
    }

    @Then("The sugar in coffeeMaker is {int}")
    public void testAddSugar(int amtSugar) {
        assertEquals(printInventory(15, 15, amtSugar, 15), coffeeMaker.checkInventory());
    }

    @When("I add a chocolate {string} to coffeeMaker")
    public void addChocolate(String amtChocolate) throws InventoryException {
        try {
            coffeeMaker.addInventory("0", "0", "0", amtChocolate);
        } catch (InventoryException ie) {
            assertThrows(InventoryException.class, () ->
                    coffeeMaker.addInventory("0", "0", "0", amtChocolate));
        }
    }

    @Then("The chocolate in coffeeMaker is {int}")
    public void testAddChocolate(int amtChocolate) {
        assertEquals(printInventory(15, 15, 15, amtChocolate), coffeeMaker.checkInventory());
    }

    @When("I delete a recipe {int}")
    public void deleteARecipe(int index) {
        assertEquals(recipe1.getName(), coffeeMaker.deleteRecipe(index));
    }

    @Then("The recipe {int} is deleted")
    public void testDeleteRecipe(int index) {
        assertEquals(new Recipe(), coffeeMaker.getRecipes()[index]);
    }

    @When("I edit a recipe {int} ingredient to chocolate {string} coffee {string} milk {string} sugar {string} and price {string}")
    public void editARecipe(int index, String amtChocolate, String amtCoffee, String amtMilk, String amtSugar, String price)
            throws RecipeException {
        Recipe r = createRecipe("", amtChocolate, amtCoffee, amtMilk, amtSugar, price);
        coffeeMaker.editRecipe(index, r);
    }

    @Then("The recipe {int} is {string} with ingredient chocolate {string} coffee {string} milk {string} sugar {string} and price {string}")
    public void testEditRecipe(int index, String name, String amtChocolate, String amtCoffee, String amtMilk, String amtSugar, String price)
            throws RecipeException {
        Recipe r = createRecipe(name, amtChocolate, amtCoffee, amtMilk, amtSugar, price);
        assertEquals(r, coffeeMaker.getRecipes()[index]);
    }

    @When("I make coffee recipe {int} with money {int}")
    public void makeACoffee(int recipeIndex, int amtPaid) {
        money = coffeeMaker.makeCoffee(recipeIndex, amtPaid);
    }

    @Then("I get change {int}")
    public void changePaid(int amtMoney) {
        assertEquals(amtMoney, money);
    }

    @Then("Inventory is coffee {int} milk {int} sugar {int} chocolate {int}")
    public void testMakeCoffee(int amtChocolate, int amtCoffee, int amtMilk, int amtSugar) {
        assertEquals(printInventory(amtChocolate, amtCoffee, amtMilk, amtSugar), coffeeMaker.checkInventory());
    }
}
