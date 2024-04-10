package io.github.GrassyDev.pvzmod.screen;

import io.github.GrassyDev.pvzmod.block.entity.BotanyStationBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class BotanyStationScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;
	public final BotanyStationBlockEntity blockEntity;

	public BotanyStationScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
			new ArrayPropertyDelegate(9));
	}


	public BotanyStationScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
		super(ModScreenHandlers.BOTANY_STATION_SCREEN_HANDLER, syncId);
		checkSize((Inventory) blockEntity, 9);
		this.inventory = ((Inventory) blockEntity);
		playerInventory.onOpen(playerInventory.player);
		this.propertyDelegate = arrayPropertyDelegate;
		this.blockEntity = ((BotanyStationBlockEntity) blockEntity);

		//sun input
		this.addSlot(new Slot(inventory, 0, 26, 8));
		//crafting slots
		this.addSlot(new Slot(inventory, 1, 62, 11));
		this.addSlot(new Slot(inventory, 2, 62, 29));
		this.addSlot(new Slot(inventory, 3, 62, 47));
		this.addSlot(new Slot(inventory, 4, 80, 11));
		this.addSlot(new Slot(inventory, 5, 80, 29));
		this.addSlot(new Slot(inventory, 6, 80, 47));
		//empty seed packet input
		this.addSlot(new Slot(inventory, 7, 107, 11));
		//output
		this.addSlot(new Slot(inventory, 8, 134, 29));

		addPlayerInventory(playerInventory);
		addPlayerHotbar(playerInventory);

		addProperties(arrayPropertyDelegate);
	}

	public boolean isCrafting(){
		return propertyDelegate.get(4) > 0;
	}
	public int getScaledProgress() {
		int progress = this.propertyDelegate.get(4);
		int maxProgress = this.propertyDelegate.get(5);  // Max Progress
		int progressArrowSize = 28; // This is the width in pixels of your arrow

		return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (invSlot < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return newStack;
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	private void addPlayerInventory(PlayerInventory playerInventory) {
		for (int i = 0; i < 3; ++i) {
			for (int l = 0; l < 9; ++l) {
				this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
			}
		}
	}

	private void addPlayerHotbar(PlayerInventory playerInventory) {
		for (int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}
}
