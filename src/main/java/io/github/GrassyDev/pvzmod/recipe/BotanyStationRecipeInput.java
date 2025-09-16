package io.github.GrassyDev.pvzmod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInput;
import net.minecraft.recipe.RecipeMatcher;

import java.util.Iterator;
import java.util.List;

public class BotanyStationRecipeInput implements RecipeInput {

	private final List<ItemStack> stacks;
	private final RecipeMatcher matcher = new RecipeMatcher();
	private final int inputCount;
	private final ItemStack slot1;
	private final ItemStack slot2;
	private final ItemStack slot3;
	private final ItemStack slot4;
	private final ItemStack slot5;
	private final ItemStack slot6;
	private final ItemStack slotseed;

	public BotanyStationRecipeInput(List<ItemStack> stacks, int inputCount,
			ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot4, ItemStack slot5, ItemStack slot6, ItemStack slotseed) {

		this.stacks = stacks;
		Iterator<ItemStack> var5 = stacks.iterator();
		while(var5.hasNext()) {
			ItemStack itemStack = (ItemStack)var5.next();
			if (!itemStack.isEmpty()) {
				++inputCount;
				this.matcher.addInput(itemStack, 1);
			}
		}
		this.inputCount = inputCount;
		this.slot1 = slot1;
		this.slot2 = slot2;
		this.slot3 = slot3;
		this.slot4 = slot4;
		this.slot5 = slot5;
		this.slot6 = slot6;
		this.slotseed = slotseed;
	}
//	public BotanyStationRecipeInput(List<ItemStack> stacks, int inputCount){
//		inputCount = 0;
//		this.stacks = stacks;
//		Iterator<ItemStack> var5 = stacks.iterator();
//		while(var5.hasNext()) {
//			ItemStack itemStack = (ItemStack)var5.next();
//			if (!itemStack.isEmpty()) {
//				++inputCount;
//				this.matcher.addInput(itemStack, 1);
//			}
//		}
//		this.inputCount = inputCount;
//	}
//	public boolean isEmpty() {
//		return this.inputCount == 0;
//	}

	public RecipeMatcher getMatcher() {
		return this.matcher;
	}

	public List<ItemStack> getStacks() {
		return this.stacks;
	}
	@Override
	public ItemStack get(int i) {
		return (ItemStack)this.stacks.get(i);
	}

	@Override
	public int getSize() {
		return this.stacks.size();
	}
}
