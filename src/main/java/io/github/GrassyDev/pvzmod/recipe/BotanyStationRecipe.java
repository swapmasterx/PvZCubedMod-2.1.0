package io.github.GrassyDev.pvzmod.recipe;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.*;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Holder;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class BotanyStationRecipe implements Recipe<CraftingInventory> {

	final Ingredient packetTemplate;
	final Ingredient sunInput;
	private final ItemStack output;
	private final List<Ingredient> recipeItems;

	public BotanyStationRecipe(List<Ingredient> ingredients, ItemStack itemStack, Ingredient packetTemplate, Ingredient sunInput){
		this.packetTemplate = packetTemplate;
		this.sunInput = sunInput;
		this.output = itemStack;
		this.recipeItems = ingredients;
	}

	@Override
	public boolean matches(CraftingInventory inventory, World world) {
		RecipeMatcher recipeMatcher = new RecipeMatcher();
		int i = 0;

		for(int j = 0; j < inventory.size(); ++j) {
			ItemStack itemStack = inventory.getStack(j);
			if (!itemStack.isEmpty()) {
				++i;
				recipeMatcher.addInput(itemStack, 64);
			}
		}

		return i == this.recipeItems.size() && recipeMatcher.match(this, (IntList)null) && this.packetTemplate.test(inventory.getStack(7)) && this.sunInput.test(inventory.getStack(0));
	}
//		return recipeItems.get(0).test(inventory.getStack(1));


	@Override
	public ItemStack craft(CraftingInventory inventory, DynamicRegistryManager registryManager) {
		ItemStack itemStack = inventory.getStack(7);
		if (this.packetTemplate.test(itemStack)) {
			//insert more code here
				}
		return output;
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResult(DynamicRegistryManager registryManager) {
		return output;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
		list.addAll(recipeItems);
		return list;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return null;
	}

	@Override
	public RecipeType<?> getType() {
		return null;
	}
}
