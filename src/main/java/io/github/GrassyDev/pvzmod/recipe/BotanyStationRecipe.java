package io.github.GrassyDev.pvzmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
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

import java.util.Iterator;
import java.util.List;

import static software.bernie.geckolib.constant.dataticket.SerializableDataTicket.STREAM_CODEC;

public record BotanyStationRecipe (int sunCost, List<Ingredient> recipeItems, ItemStack output) implements Recipe<BotanyStationRecipeInput> {

//	final Ingredient packetTemplate;
	//	final Ingredient sunInput;
	//	final CraftingCategory category;
//	private final ItemStack output;
//	private final List<Ingredient> recipeItems;
//	final int sunCost;
	public BotanyStationRecipe(int sunCost, List<Ingredient> recipeItems, ItemStack output){
		this.sunCost = sunCost;
		this.recipeItems = recipeItems;
		this.output = output;
	}
	@Override
	public RecipeSerializer<? extends Recipe<BotanyStationRecipeInput>> getSerializer() {
		return ModRecipes.BOTANY_BOX_SERIALIZER;
	}


	public static class Type implements RecipeType<BotanyStationRecipe>{
		public static final Type INSTANCE = new Type();
		public static final String ID = "botany_station";
	}
	@Override
	public RecipeType<? extends Recipe<BotanyStationRecipeInput>> getType() {
		return ModRecipes.BOTANY_BOX_TYPE;
	}
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

//	@Override
//	public RecipeSerializer<BotanyStationRecipe> getSerializer() {
//		return Serializer.INSTANCE;
//	}
//
//	@Override
//	public RecipeType<BotanyStationRecipe> getType() {
//		return Type.INSTANCE;
//	}

//	public static class Type implements RecipeType<BotanyStationRecipe>{
//		public static final Type INSTANCE = new Type();
//		public static final String ID = "botany_station";
//	}
//
//	public static class Serializer implements RecipeSerializer<BotanyStationRecipe> {
//		public static final Serializer INSTANCE = new Serializer();
//		public static final String ID = "botany_station";

public static class Serializer implements RecipeSerializer<BotanyStationRecipe> {
		private static final MapCodec<BotanyStationRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> {

				Codec.INT.fieldOf("suncost").forGetter(BotanyStationRecipe::sunCost),
				Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap(list -> {
				Ingredient[] ingredients = list.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
				if (ingredients.length == 0) {
					return DataResult.error(() -> "No ingredients for Botany Box recipe");
				} else {
					return ingredients.length > 7 ?
						DataResult.error(() -> "Too many ingredients for Botany Box recipe") :
						DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients));
				}
				}, DataResult::success).forGetter(botanyStationRecipe -> BotanyStationRecipe::recipeItems),
				ItemStack.CODEC.fieldOf("result").forGetter(BotanyStationRecipe::output)).apply(instance, BotanyStationRecipe::new);
		});
	@Override
	public PacketCodec<RegistryByteBuf, BotanyStationRecipe> getPacketCodec() {return STREAM_CODEC;}

		@Override
		public MapCodec<BotanyStationRecipe> getCodec() {
			return CODEC;
		}


	private static BotanyStationRecipe read(RegistryByteBuf buf) {
		int suncost = buf.readVarInt();
		int ingredientsCount = buf.readVarInt();
		DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredientsCount, Ingredient.EMPTY);
		inputs.replaceAll((ingredient) -> {
			return (Ingredient)Ingredient.PACKET_CODEC.decode(buf);
		});
		ItemStack itemStack = (ItemStack)ItemStack.PACKET_CODEC.decode(buf);
		return new BotanyStationRecipe(suncost, inputs, itemStack);
	}
//	@Override
//		public BotanyStationRecipe read(RegistryByteBuf buf) {
//
//			int suncost = buf.readVarInt();
//			int ingredientsCount = buf.readVarInt();
//			DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredientsCount, Ingredient.EMPTY);
//			inputs.replaceAll((ingredient) -> {
//				return (Ingredient)Ingredient.PACKET_CODEC.decode(buf);
//			});
//			ItemStack output = buf.readItemStack();
//
//			return new BotanyStationRecipe(suncost, inputs, output);
//		}
		private static void write(RegistryByteBuf buf, BotanyStationRecipe recipe) {
            buf.writeVarInt(recipe.sunCost);
			buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.recipeItems) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
        }

//		@Override
//		public void write(PacketByteBuf buf, BotanyStationRecipe botanyStationRecipe) {
//			buf.writeVarInt(botanyStationRecipe.sunCost);
//			buf.writeVarInt(botanyStationRecipe.getIngredients().size());
//			for (Ingredient ingredient : botanyStationRecipe.getIngredients()) {
//				ingredient.write(buf);
//			}
//			buf.writeItemStack(botanyStationRecipe.getResult(null));
//		}
	}
}
