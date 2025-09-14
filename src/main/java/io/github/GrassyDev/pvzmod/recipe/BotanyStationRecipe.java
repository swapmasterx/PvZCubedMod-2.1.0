package io.github.GrassyDev.pvzmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.GrassyDev.pvzmod.block.entity.BotanyStationBlockEntity;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.HolderLookup;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class BotanyStationRecipe(Ingredient inputItem, ItemStack output) implements Recipe<BotanyStationRecipeInput> {

//	final Ingredient packetTemplate;
	//	final Ingredient sunInput;
	//	final CraftingCategory category;
	private final ItemStack output;
	private final List<Ingredient> recipeItems;
	final int sunCost;
	public BotanyStationRecipe(int sunCost, List<Ingredient> ingredients, ItemStack output){
		this.sunCost = sunCost;
		this.recipeItems = ingredients;
		this.output = output;
	}
	@Override
	public RecipeSerializer<? extends Recipe<BotanyStationRecipe>> getSerializer() {
		return ModRecipes.RECIPE_SERIALIZER;
	}

	@Override
	public RecipeType<? extends Recipe<BotanyStationRecipe>> getType() {
		return ModRecipes.RECIPE_TYPE;
	}
	@Override
	public boolean matches(SimpleInventory inventory, World world) {
		RecipeMatcher recipeMatcher = new RecipeMatcher();
		int notEmptyStacks = 0;

		for(int j = 0; j < inventory.size(); ++j) {
			ItemStack itemStack = inventory.getStack(j);
			if (!itemStack.isEmpty()) {
				++notEmptyStacks;
				recipeMatcher.addInput(itemStack, 1);
			}
		}

		if(world.isClient()) {
			return false;
		}
		else {
			return notEmptyStacks == this.recipeItems.size() && recipeMatcher.match(this, null);
		}
	}

	@Override
	public ItemStack craft(BotanyStationRecipeInput input, HolderLookup.Provider registryManager) {
		return this.output;
	}
	public int getSunCost() {
		return this.sunCost;
	}

	@Override
	public boolean matches(BotanyStationRecipeInput input, World world) {
		return false;
	}

	public boolean fits(int width, int height) {
		return width * height >= this.recipeItems.size();
	}

	@Override
	public ItemStack getResult(HolderLookup.Provider registryManager) {
		return output;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(this.recipeItems.size());
		ingredients.addAll(recipeItems);
		return ingredients;
	}

	@Override
	public RecipeSerializer<BotanyStationRecipe> getSerializer() {
		return Serializer.INSTANCE;
	}

	@Override
	public RecipeType<BotanyStationRecipe> getType() {
		return Type.INSTANCE;
	}

//	public static class Type implements RecipeType<BotanyStationRecipe>{
//		public static final Type INSTANCE = new Type();
//		public static final String ID = "botany_station";
//	}
//
//	public static class Serializer implements RecipeSerializer<BotanyStationRecipe> {
//		public static final Serializer INSTANCE = new Serializer();
//		public static final String ID = "botany_station";
public static class Serializer implements RecipeSerializer<BotanyStationRecipe> {
		private static final Codec<BotanyStationRecipe> CODEC =
			RecordCodecBuilder.create(instance -> instance.group(
				Codec.INT.fieldOf("suncost").forGetter(botanyStationRecipe -> botanyStationRecipe.sunCost),
				Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap(list -> {
				Ingredient[] ingredients = list.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
				if (ingredients.length == 0) {
					return DataResult.error(() -> "No ingredients for Botany Box recipe");
				} else {
					return ingredients.length > 7 ?
						DataResult.error(() -> "Too many ingredients for Botany Box recipe") :
						DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients));
				}
				}, DataResult::success).forGetter(botanyStationRecipe -> (DefaultedList<Ingredient>) botanyStationRecipe.recipeItems),
				ItemStack.CODEC.fieldOf("result").forGetter(botanyStationRecipe -> botanyStationRecipe.output))
			.apply(instance, BotanyStationRecipe::new));

		@Override
		public Codec<BotanyStationRecipe> getCodec() {
			return CODEC;
		}

		@Override
		public PacketCodec<RegistryByteBuf, BotanyStationRecipe> getPacketCodec() {
			return null;
		}

		@Override
		public BotanyStationRecipe read(PacketByteBuf buf) {

			int suncost = buf.readVarInt();
			int ingredientsCount = buf.readVarInt();
			DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredientsCount, Ingredient.EMPTY);
			inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
			ItemStack output = buf.readItemStack();

			return new BotanyStationRecipe(suncost, inputs, output);
		}

		@Override
		public void write(PacketByteBuf buf, BotanyStationRecipe botanyStationRecipe) {
			buf.writeVarInt(botanyStationRecipe.sunCost);
			buf.writeVarInt(botanyStationRecipe.getIngredients().size());
			for (Ingredient ingredient : botanyStationRecipe.getIngredients()) {
				ingredient.write(buf);
			}
			buf.writeItemStack(botanyStationRecipe.getResult(null));
		}
	}
}
