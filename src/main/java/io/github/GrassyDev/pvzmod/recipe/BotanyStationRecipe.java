package io.github.GrassyDev.pvzmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class BotanyStationRecipe implements Recipe<SimpleInventory> {

	final Ingredient packetTemplate;
//	final Ingredient sunInput;
	final String group;
//	final CraftingCategory category;
	private final ItemStack output;
	private final DefaultedList<Ingredient> recipeItems;

	public BotanyStationRecipe(DefaultedList<Ingredient> ingredients, ItemStack stack, Ingredient packetTemplate, String group){
		this.packetTemplate = packetTemplate;
//		this.category = category;
		this.group = group;
//		this.sunInput = sunInput;
//		, Ingredient sunInput
		this.output = stack;
		this.recipeItems = ingredients;
	}
	public String getGroup() {
		return this.group;
	}

//	public CraftingCategory getCategory() {
//		return this.category;
//	}
	@Override
	public boolean matches(SimpleInventory inventory, World world) {
		RecipeMatcher recipeMatcher = new RecipeMatcher();
		int i = 0;

		for(int j = 0; j < inventory.size(); ++j) {
			ItemStack itemStack = inventory.getStack(j);
			if (!itemStack.isEmpty()) {
				++i;
				recipeMatcher.addInput(itemStack, 1);
			}
		}

		return i == this.recipeItems.size() && recipeMatcher.match(this, (IntList)null) && this.packetTemplate.test(inventory.getStack(7));
	}
//		return recipeItems.get(0).test(inventory.getStack(1));


	@Override
	public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
		ItemStack itemStack = inventory.getStack(7);
		if (this.packetTemplate.test(itemStack)) {
			return output;
				}
		else{
			return null;
		}
	}

	public boolean fits(int width, int height) {
		return width * height >= this.recipeItems.size();
	}

	@Override
	public ItemStack getResult(DynamicRegistryManager registryManager) {
		return output;
	}


	public boolean matchesPacketTemplateIngredient(ItemStack stack) {
		return this.packetTemplate.test(stack);
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
		list.addAll(recipeItems);
		return list;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return Serializer.BOTANYSTATIONSERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {

		return Type.BOTANYFICATION;
	}

	public static class Type implements RecipeType<BotanyStationRecipe>{
		public static final Type BOTANYFICATION = new Type();
		public static final String ID = "botany_station";
	}

public static class Serializer implements RecipeSerializer<BotanyStationRecipe> {
		public static final Serializer BOTANYSTATIONSERIALIZER = new Serializer();
		public static final String ID = "botany_station";

	private static final Codec<BotanyStationRecipe> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codecs.method_53049(Codec.STRING, "group", "").forGetter((botanyStationRecipe) ->
				{return botanyStationRecipe.group;}),
				Ingredient.field_46095.fieldOf("packettemplate").forGetter((botanyStationRecipe) ->
				{return botanyStationRecipe.packetTemplate;}),
				ItemStack.field_47309.fieldOf("result").forGetter((botanyStationRecipe) ->
				{return botanyStationRecipe.output;}),
				Ingredient.field_46096.listOf().fieldOf("ingredients").flatXmap((list) ->
						{Ingredient[] ingredients = (Ingredient[])list.stream().filter((ingredient) ->
						{return !ingredient.isEmpty();}).toArray(Ingredient[]::new);
							if (ingredients.length == 0)
							{return DataResult.error(() ->
							{return "No ingredients for Botany Box recipe";});}
							else
							{return ingredients.length > 6 ? DataResult.error(() ->
							{return "Too many ingredients for Botany Box recipe";}) :
										DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients));}},
								DataResult::success).forGetter((botanyStationRecipe) -> {
									return botanyStationRecipe.recipeItems;})).apply(
											instance, (String group1, Ingredient packetTemplate1, ItemStack stack1,
													   DefaultedList<Ingredient> ingredients1) ->
						new BotanyStationRecipe(ingredients1, stack1, packetTemplate1, group1));
	});

		@Override
		public Codec<BotanyStationRecipe> getCodec() {
			return CODEC;
		}


		@Override
		public BotanyStationRecipe read(PacketByteBuf buf) {
			//group string
			String string = buf.readString();
			//seed packet template
			Ingredient packetTemp = Ingredient.fromPacket(buf);
			//defaulted list
			int i = buf.readVarInt();
			DefaultedList<Ingredient> inputs = DefaultedList.ofSize(i, Ingredient.EMPTY);
			for(int j = 0; j < inputs.size(); ++j) {
				inputs.set(j, Ingredient.fromPacket(buf));
			}

			//recipe output
			ItemStack output = buf.readItemStack();

			return new BotanyStationRecipe(inputs, output,  packetTemp, string);
		}

		@Override
		public void write(PacketByteBuf buf, BotanyStationRecipe botanyStationRecipe) {

			buf.writeString(botanyStationRecipe.group);

			botanyStationRecipe.packetTemplate.write(buf);

			buf.writeInt(botanyStationRecipe.getIngredients().size());
			for (Ingredient ingredient : botanyStationRecipe.getIngredients()) {
				ingredient.write(buf);
			}

			buf.writeItemStack(botanyStationRecipe.getResult(null));
		}
	}
}
