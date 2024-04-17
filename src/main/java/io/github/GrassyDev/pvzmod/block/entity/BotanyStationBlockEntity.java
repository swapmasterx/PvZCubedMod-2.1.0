package io.github.GrassyDev.pvzmod.block.entity;

import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.recipe.BotanyStationRecipe;
import io.github.GrassyDev.pvzmod.screen.BotanyStationScreenHandler;
import io.wispforest.owo.util.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeHolder;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class BotanyStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {

    protected DefaultedList<ItemStack> inventory;


    private static final int INPUT_SUN_SLOT = 0;
    private static final int INPUT_SLOT_1 = 1;
    private static final int INPUT_SLOT_2= 2;
    private static final int INPUT_SLOT_3 = 3;
    private static final int INPUT_SLOT_4 = 4;
    private static final int INPUT_SLOT_5 = 5;
    private static final int INPUT_SLOT_6 = 6;
    private static final int SEED_PACKET_SLOT = 7;
    private static final int OUTPUT_SLOT = 8;



    protected final PropertyDelegate propertyDelegate;
    int currentSunResource = 0;
    int maxsunResource = 200;
    int sunCost = 0;
    int craftDelay = 0;
    int maxcraftDelay = 20;

	int missingSun = 0;

    public BotanyStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BOTANY_STATION_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
//                switch (index){
//                    case 0:
//                        return BotanyStationBlockEntity.this.minsunResource;
//                    case 1:
//                        return BotanyStationBlockEntity.this.maxsunResource;
//                    case 2:
//                        return BotanyStationBlockEntity.this.currentSunResource;
//                    case 3:
//                        return BotanyStationBlockEntity.this.sunCost;
//                    default:
//                        return 0;
                return switch (index) {
                    case 0 -> BotanyStationBlockEntity.this.maxsunResource;
                    case 1 -> BotanyStationBlockEntity.this.currentSunResource;
                    case 2 -> BotanyStationBlockEntity.this.sunCost;
                    case 3 -> BotanyStationBlockEntity.this.craftDelay;
                    case 4 -> BotanyStationBlockEntity.this.maxcraftDelay;
					case 5 -> BotanyStationBlockEntity.this.missingSun;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
//                switch (index) {
//                    case 0:
//                        BotanyStationBlockEntity.this.minsunResource = value;
//                        break;
//                    case 1:
//                        BotanyStationBlockEntity.this.maxsunResource = value;
//                        break;
//                    case 2:
//                        BotanyStationBlockEntity.this.currentSunResource = value;
//                        break;
//                    case 3:
//                        BotanyStationBlockEntity.this.sunCost = value;
//                        break;
                switch (index) {
                    case 0 -> BotanyStationBlockEntity.this.maxsunResource = value;
                    case 1 -> BotanyStationBlockEntity.this.currentSunResource = value;
                    case 2 -> BotanyStationBlockEntity.this.sunCost = value;
                    case 3 -> BotanyStationBlockEntity.this.craftDelay = value;
                    case 4 -> BotanyStationBlockEntity.this.maxcraftDelay = value;
					case 5 -> BotanyStationBlockEntity.this.missingSun = value;
                }

            }

            @Override
            public int size() {
                return 6;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {

        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
		nbt.putInt("craft_delay", this.craftDelay);
        nbt.putInt("sun_stored", this.currentSunResource);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
		this.craftDelay = nbt.getInt("craft_delay");
        this.currentSunResource = nbt.getInt("sun_stored");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);

    }

    @Override
    public Text getDisplayName() {
        return (Text.translatable("block.pvzmod.entity.botany_station"));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BotanyStationScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
    public void tick(World world, BlockPos pos, BlockState state){
        if(world.isClient()){
            return;
        }

        if(isOutputSlotEmptyOrReceivable()){
//            if(this.hasRecipe()){
				if(canAffordSunCost()){
					this.increaseCraftDelay();
					markDirty(world, pos, state);
					if(hasCraftingFinished()){

						this.craftItem();
						this.deductSunCost();
						this.resetCraftDelay();}
					else {
                        this.resetCraftDelay();
                    }
                }
//            }
            else {
                this.resetCraftDelay();
            }
        }
        else {
            this.resetCraftDelay();
            markDirty(world, pos, state);
        }
    }

    private  boolean canAffordSunCost(){

        if (this.sunCost <= this.currentSunResource){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<BotanyStationRecipe>> recipe = getCurrentRecipe();


        return recipe.isPresent()&& canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null)) && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem());
    }

    private Optional<RecipeHolder<BotanyStationRecipe>> getCurrentRecipe() {
		RecipeMatcher recipeMatcher = new RecipeMatcher();
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return getWorld().getRecipeManager().getFirstMatch(BotanyStationRecipe.Type.INSTANCE, inv, getWorld());
    }
	private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {
		Item item2 = item.asItem();
		fuelTimes.put(item2, fuelTime);
	}
	public static Map<Item, Integer> createSunAmountMap() {
		Map<Item, Integer> map = Maps.newLinkedHashMap();
		addFuel(map, ModItems.SUN, 2);
		addFuel(map, ModItems.LARGESUN, 4);
		addFuel(map, ModItems.SMALLSUN, 1);
		return map;
	}
    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private void increaseCraftDelay() {
        craftDelay++;
    }

    private boolean hasCraftingFinished() {
        return craftDelay >= maxcraftDelay;
    }

    private void craftItem() {
        Optional<RecipeHolder<BotanyStationRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT_1, 1);
        this.removeStack(INPUT_SLOT_2, 1);
        this.removeStack(INPUT_SLOT_3, 1);
        this.removeStack(INPUT_SLOT_4, 1);
        this.removeStack(INPUT_SLOT_5, 1);
        this.removeStack(INPUT_SLOT_6, 1);
        this.removeStack(SEED_PACKET_SLOT, 1);

        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(),
                getStack(OUTPUT_SLOT).getCount() + recipe.get().value().getResult(null).getCount()));

    }

    private void deductSunCost() {

		this.currentSunResource -= this.sunCost;
		if (this.currentSunResource < 0){
			this.currentSunResource = 0;
		}
    }

    private void resetCraftDelay() {
        this.craftDelay = 0;
    }
}
