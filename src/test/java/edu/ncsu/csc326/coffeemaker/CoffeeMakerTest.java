/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 *
 * Permission has been explicitly granted to the University of Minnesota
 * Software Engineering Center to use and distribute this source for
 * educational purposes, including delivering online education through
 * Coursera or other entities.
 *
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including
 * fitness for purpose.
 *
 *
 * Modifications
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to
 * 							 coding standards.  Added test documentation.
 */
package edu.ncsu.csc326.coffeemaker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

/**
 * Unit tests for CoffeeMaker class.
 *
 * @author Sarah Heckman
 */
public class CoffeeMakerTest {

    /**
     * The object under test.
     */
    private CoffeeMaker coffeeMaker;

    // Sample recipes to use in testing.
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;
    private Recipe recipe5;

    /**
     * Initializes some recipes to test with and the {@link CoffeeMaker}
     * object we wish to test.
     *
     * @throws RecipeException if there was an error parsing the ingredient
     *                         amount when setting up the recipe.
     */
    @Before
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker();

        //Set up for r1
        recipe1 = new Recipe();
        recipe1.setName("Coffee");
        recipe1.setAmtChocolate("0");
        recipe1.setAmtCoffee("3");
        recipe1.setAmtMilk("1");
        recipe1.setAmtSugar("1");
        recipe1.setPrice("50");

        //Set up for r2
        recipe2 = new Recipe();
        recipe2.setName("Mocha");
        recipe2.setAmtChocolate("20");
        recipe2.setAmtCoffee("3");
        recipe2.setAmtMilk("1");
        recipe2.setAmtSugar("1");
        recipe2.setPrice("75");

        //Set up for r3
        recipe3 = new Recipe();
        recipe3.setName("Latte");
        recipe3.setAmtChocolate("0");
        recipe3.setAmtCoffee("3");
        recipe3.setAmtMilk("3");
        recipe3.setAmtSugar("1");
        recipe3.setPrice("100");

        //Set up for r4
        recipe4 = new Recipe();
        recipe4.setName("Hot Chocolate");
        recipe4.setAmtChocolate("4");
        recipe4.setAmtCoffee("0");
        recipe4.setAmtMilk("1");
        recipe4.setAmtSugar("1");
        recipe4.setPrice("65");

        recipe5 = new Recipe();
        recipe5.setName("Weed Coffee");
        recipe5.setAmtChocolate("16");
        recipe5.setAmtCoffee("16");
        recipe5.setAmtMilk("16");
        recipe5.setAmtSugar("16");
        recipe5.setPrice("500");
    }


    @Test
    public void testInitialInventory() {
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @Test
    public void testInitialRecipeBook() {
        for (int i = 0; i < 3; i++) {
            assertNull(coffeeMaker.getRecipes()[i]);
        }
    }

    @Test
    public void testRecipeBookSize() {
        assertEquals("Maximum size should be 3", 3, coffeeMaker.getRecipes().length);
    }

    @Test
    public void testAddRecipe() {
        assertNull(coffeeMaker.getRecipes()[0]);
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        coffeeMaker.addRecipe(recipe2);
        assertEquals(recipe2, coffeeMaker.getRecipes()[1]);
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker.getRecipes()[2]);
    }

    @Test
    public void testAddRecipeOver() {
        assertNull(coffeeMaker.getRecipes()[0]);
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        coffeeMaker.addRecipe(recipe2);
        assertEquals(recipe2, coffeeMaker.getRecipes()[1]);
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker.getRecipes()[2]);
        assertFalse("Recipe should be full and can't be add", coffeeMaker.addRecipe(recipe4));
    }

    @Test
    public void testDeleteRecipeIndex() {
        assertNull(coffeeMaker.getRecipes()[0]);
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        coffeeMaker.addRecipe(recipe2);
        assertEquals(recipe2, coffeeMaker.getRecipes()[1]);
        coffeeMaker.addRecipe(recipe3);
        assertEquals(recipe3, coffeeMaker.getRecipes()[2]);
        assertEquals(recipe3.toString(), coffeeMaker.deleteRecipe(2));
        assertEquals(new Recipe(), coffeeMaker.getRecipes()[2]);
    }

    @Test
    public void testDeleteRecipeNegativeIndex() throws ArrayIndexOutOfBoundsException {
        coffeeMaker.addRecipe(recipe1);
        assertNull("Main should be able to handle negative input",
                coffeeMaker.deleteRecipe(-1));
    }

    @Test
    public void testDeleteRecipeOutOfBoundIndex() throws ArrayIndexOutOfBoundsException {
        coffeeMaker.addRecipe(recipe1);
        assertNull("Main should be able to handle IndexOutOfBound",
                coffeeMaker.deleteRecipe(4));
    }

    @Test
    public void testEditRecipeIndex() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals(recipe1, coffeeMaker.getRecipes()[0]);
        assertEquals(recipe1.toString(), coffeeMaker.editRecipe(0, recipe2));
        assertEquals(recipe2, coffeeMaker.getRecipes()[0]);
        assertEquals("A recipe name mey not be changed",
                recipe1.getName(), coffeeMaker.getRecipes()[0].getName());
    }

    @Test
    public void testEditRecipeNegativeIndex() throws ArrayIndexOutOfBoundsException {
        coffeeMaker.addRecipe(recipe1);
        assertNull("Main should be able to handle this", coffeeMaker.editRecipe(-1, recipe2));
    }

    @Test
    public void testEditRecipeOutOfBoundIndex() throws ArrayIndexOutOfBoundsException {
        coffeeMaker.addRecipe(recipe1);
        assertNull("Main should be able to handle this", coffeeMaker.editRecipe(4, recipe2));
    }

    @Test
    public void testAddInventoryStringValue() {
        assertThrows(InventoryException.class, () ->
                coffeeMaker.addInventory("a", "0", "0", "0"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertThrows(InventoryException.class, () ->
                coffeeMaker.addInventory("0", "a", "0", "0"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertThrows(InventoryException.class, () ->
                coffeeMaker.addInventory("0", "0", "a", "0"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertThrows(InventoryException.class, () ->
                coffeeMaker.addInventory("0", "0", "0", "a"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with well-formed quantities
     * Then we do not get an exception trying to read the inventory quantities.
     *
     * @throws InventoryException if there was an error parsing the quanity
     *                            to a positive integer.
     */
    @Test
    public void testAddInventoryPositiveInteger() throws InventoryException {
        coffeeMaker.addInventory("1", "0", "0", "0");
        assertEquals("Positive Integer should be able to add to Inventory",
                "Coffee: 16\nMilk: 15\nSugar: 15\nChocolate: 15\n",
                coffeeMaker.checkInventory());
        coffeeMaker.addInventory("0", "1", "0", "0");
        assertEquals("Positive Integer should be able to add to Inventory",
                "Coffee: 16\nMilk: 16\nSugar: 15\nChocolate: 15\n",
                coffeeMaker.checkInventory());
        coffeeMaker.addInventory("0", "0", "1", "0");
        assertEquals("Positive Integer should be able to add to Inventory",
                "Coffee: 16\nMilk: 16\nSugar: 16\nChocolate: 15\n",
                coffeeMaker.checkInventory());
        coffeeMaker.addInventory("0", "0", "0", "1");
        assertEquals("Positive Integer should be able to add to Inventory",
                "Coffee: 16\nMilk: 16\nSugar: 16\nChocolate: 16\n",
                coffeeMaker.checkInventory());
    }

    /**
     * Given a coffee maker with the default inventory
     * When we add inventory with malformed quantities (i.e., a negative
     * quantity and a non-numeric string)
     * Then we get an inventory exception
     *
     * @throws InventoryException if there was an error parsing the quanity
     *                            to a positive integer.
     */
    @Test
    public void testAddInventoryNegativeInteger() throws InventoryException {
        assertThrows("Error should be thrown when add negative value", InventoryException.class, () ->
                coffeeMaker.addInventory("-1", "0", "0", "0"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertThrows("Error should be thrown when add negative value", InventoryException.class, () ->
                coffeeMaker.addInventory("0", "-1", "0", "0"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertThrows("Error should be thrown when add negative value", InventoryException.class, () ->
                coffeeMaker.addInventory("0", "0", "0", "-1"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertThrows("Error should be thrown when add negative value", InventoryException.class, () ->
                coffeeMaker.addInventory("0", "0", "-1", "0"));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @Test
    public void testAddInventoryZero() throws InventoryException {
        coffeeMaker.addInventory("0", "0", "0", "0");
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }


    /**
     * Given a coffee maker with one valid recipe
     * When we make coffee, selecting the valid recipe and paying more than
     * the coffee costs
     * Then we get the correct change back.
     */
    @Test
    public void testMakeCoffeeWithEnoughMoney() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertEquals(25, coffeeMaker.makeCoffee(0, 75));
        assertEquals("Ingredient should change when purchase is made",
                "Coffee: 12\nMilk: 14\nSugar: 14\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeWithNotEnoughMoney() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertEquals(25, coffeeMaker.makeCoffee(0, 25));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeNegativeMoney() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertEquals(-25, coffeeMaker.makeCoffee(0, -25));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeZeroMoney() {
        coffeeMaker.addRecipe(recipe1);
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
        assertEquals(0, coffeeMaker.makeCoffee(0, 0));
        assertEquals("Coffee: 15\nMilk: 15\nSugar: 15\nChocolate: 15\n", coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeIndexNoRecipe() {
        assertArrayEquals(new Recipe[coffeeMaker.getRecipes().length], coffeeMaker.getRecipes());
        assertEquals(50, coffeeMaker.makeCoffee(0, 50));
    }

    @Test
    public void testMakeCoffeeIndexOutOfBound() throws ArrayIndexOutOfBoundsException {
        assertArrayEquals(new Recipe[coffeeMaker.getRecipes().length], coffeeMaker.getRecipes());
        assertEquals(50, coffeeMaker.makeCoffee(-1, 50));
    }

    @Test
    public void testMakeCoffeeInsufficientInventory() {
        coffeeMaker.addRecipe(recipe5);
        assertEquals(500, coffeeMaker.makeCoffee(0, 500));
    }

}
