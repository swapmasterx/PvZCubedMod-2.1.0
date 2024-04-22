package io.github.GrassyDev.pvzmod.compat;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.block.ModBlocks;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class BotanyStationCatagory implements DisplayCategory<BasicDisplay> {

	public static final Identifier TEXTURE =
		new Identifier(PvZCubed.MOD_ID, "textures/gui/botany_station_gui.png");

	public static final CategoryIdentifier<BotanyStationDisplay> BOTANY_STATION =
		CategoryIdentifier.of(PvZCubed.MOD_ID, "botany_station");

	@Override
	public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
		return BOTANY_STATION;
	}

	@Override
	public Text getTitle() {
		return Text.literal("Botany Box");
	}

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(ModBlocks.BOTANY_STATION.asItem().getDefaultStack());
	}

	@Override
	public List<Widget> setupDisplay(BasicDisplay display, me.shedaniel.math.Rectangle bounds) {
		return DisplayCategory.super.setupDisplay(display, bounds);
	}

	@Override
	public int getDisplayHeight() {
		return 83;
	}
}
