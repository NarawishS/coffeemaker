package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoffeeMakerMockitoTest {

    @Mock
    private RecipeBook stubRecipeBook;

    @Spy
    private RecipeBook recipeBookSpy;

    private CoffeeMaker coffeeMaker;
    private CoffeeMaker coffeeMaker2;
    private Recipe recipe1;
    private Recipe recipe2;
    private Recipe recipe3;
    private Recipe recipe4;
    private Recipe recipe5;

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

    public static String printInventory(int coffee, int milk, int sugar, int chocolate) {
        return String.format("Coffee: %d\nMilk: %d\nSugar: %d\nChocolate: %d\n",
                coffee, milk, sugar, chocolate);
    }

    @Before
    public void setUp() throws RecipeException {
        coffeeMaker = new CoffeeMaker(stubRecipeBook, new Inventory());
        coffeeMaker2 = new CoffeeMaker(recipeBookSpy, new Inventory());
        recipe1 = createRecipe("Coffee", "0", "3", "1", "1", "50");
        recipe2 = createRecipe("Mocha", "10", "3", "1", "1", "75");
        recipe3 = createRecipe("Latte", "0", "3", "3", "1", "100");
        recipe4 = createRecipe("Hot Chocolate", "4", "0", "1", "1", "65");
        recipe5 = createRecipe("GoldE Coffee", "1000", "1000", "1000", "1000", "1000");
    }

    @Test
    public void testAddRecipe() {
        Recipe[] recipes = new Recipe[]{recipe1, recipe2, recipe3};
        when(stubRecipeBook.addRecipe(any(Recipe.class))).thenReturn(true).thenReturn(true).thenReturn(true).thenThrow(InventoryException.class);
        when(stubRecipeBook.getRecipes()).thenReturn(recipes);

        assertTrue(coffeeMaker.addRecipe(recipe1));
        assertTrue(coffeeMaker.addRecipe(recipe2));
        assertTrue(coffeeMaker.addRecipe(recipe3));
        assertFalse("RecipeBook should be full at 3 and cannot add more", coffeeMaker.addRecipe(recipe4));

        verify(stubRecipeBook, times(4)).addRecipe(any(Recipe.class));

        assertArrayEquals("cofferMaker recipes should be the same as mockCoffee",
                recipes, coffeeMaker.getRecipes());

        verify(stubRecipeBook).getRecipes();
    }

    @Test
    public void testAddRecipeSpy() {
        assertTrue("coffee not full why failed", recipeBookSpy.addRecipe(recipe1));
        doReturn(false).when(recipeBookSpy).addRecipe(any(Recipe.class));
        assertFalse("test spy object failure what went wrong?", recipeBookSpy.addRecipe(recipe2));
    }

    @Test
    public void testAddIngredient() throws InventoryException {
        coffeeMaker.addInventory("1", "0", "0", "0");
        assertEquals("Positive Integer should be able to add to Inventory",
                printInventory(16, 15, 15, 15),
                coffeeMaker.checkInventory());
        coffeeMaker.addInventory("0", "1", "0", "0");
        assertEquals("Positive Integer should be able to add to Inventory",
                printInventory(16, 16, 15, 15),
                coffeeMaker.checkInventory());
        coffeeMaker.addInventory("0", "0", "1", "0");
        assertEquals("Positive Integer should be able to add to Inventory",
                printInventory(16, 16, 16, 15),
                coffeeMaker.checkInventory());
        coffeeMaker.addInventory("0", "0", "0", "1");
        assertEquals("Positive Integer should be able to add to Inventory",
                printInventory(16, 16, 16, 15),
                coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffee() {
        when(stubRecipeBook.addRecipe(recipe2)).thenReturn(true);
        when(stubRecipeBook.getRecipes()).thenReturn(new Recipe[]{recipe2});

        assertTrue(coffeeMaker.addRecipe(recipe2));
        verify(stubRecipeBook).addRecipe(recipe2);
        assertEquals(25, coffeeMaker.makeCoffee(0, 100));
        verify(stubRecipeBook, times(4)).getRecipes();
        assertEquals(printInventory(12, 14, 14, 5), coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeNoMoney() {
        when(stubRecipeBook.addRecipe(recipe1)).thenReturn(true);
        when(stubRecipeBook.getRecipes()).thenReturn(new Recipe[]{recipe1});

        assertTrue(coffeeMaker.addRecipe(recipe1));
        verify(stubRecipeBook).addRecipe(recipe1);
        assertEquals(0, coffeeMaker.makeCoffee(0, 0));
        verify(stubRecipeBook, times(2)).getRecipes();
        assertEquals(printInventory(15, 15, 15, 15), coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeNegativeMoney() {
        when(stubRecipeBook.addRecipe(recipe1)).thenReturn(true);
        when(stubRecipeBook.getRecipes()).thenReturn(new Recipe[]{recipe1});

        assertTrue(coffeeMaker.addRecipe(recipe1));
        verify(stubRecipeBook).addRecipe(recipe1);
        assertEquals(-50, coffeeMaker.makeCoffee(0, -50));
        verify(stubRecipeBook, times(2)).getRecipes();
        assertEquals(printInventory(15, 15, 15, 15), coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeZeroMoney() {
        when(stubRecipeBook.addRecipe(recipe1)).thenReturn(true);
        when(stubRecipeBook.getRecipes()).thenReturn(new Recipe[]{recipe1});

        assertTrue(coffeeMaker.addRecipe(recipe1));
        verify(stubRecipeBook).addRecipe(recipe1);
        assertEquals(0, coffeeMaker.makeCoffee(0, 0));
        verify(stubRecipeBook, times(2)).getRecipes();
        assertEquals(printInventory(15, 15, 15, 15), coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeNoRecipe() {
        when(stubRecipeBook.getRecipes()).thenReturn(new Recipe[3]);
        assertEquals(100, coffeeMaker.makeCoffee(0, 100));
        verify(stubRecipeBook).getRecipes();
        assertEquals(printInventory(15, 15, 15, 15), coffeeMaker.checkInventory());
    }

    @Test
    public void testMakeCoffeeInsufficientInventory() {
        when(stubRecipeBook.addRecipe(recipe5)).thenReturn(true);
        when(stubRecipeBook.getRecipes()).thenReturn(new Recipe[]{recipe5});

        assertTrue(coffeeMaker.addRecipe(recipe5));
        verify(stubRecipeBook).addRecipe(recipe5);
        assertEquals(1000, coffeeMaker.makeCoffee(0, 1000));
        verify(stubRecipeBook, times(3)).getRecipes();
        assertEquals(printInventory(15, 15, 15, 15), coffeeMaker.checkInventory());
    }
}
