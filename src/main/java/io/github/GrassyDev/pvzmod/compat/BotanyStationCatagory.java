package io.github.GrassyDev.pvzmod.compat;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.block.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
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
		return Text.literal("botany_box");
	}

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(ModBlocks.BOTANY_STATION.asItem().getDefaultStack());
	}

	@Override
	public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
		final Point startPoint = new Point(bounds.getCenterX()-87, bounds.getCenterY()-35);
		List<Widget> widgets = new LinkedList<>();
		widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 82)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 26,startPoint.y + 8 ))
			.entries(display.getInputEntries().get(0)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 62,startPoint.y + 11 ))
			.entries(display.getInputEntries().get(1)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 62,startPoint.y + 29 ))
			.entries(display.getInputEntries().get(2)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 62,startPoint.y + 47 ))
			.entries(display.getInputEntries().get(3)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 80,startPoint.y + 11 ))
			.entries(display.getInputEntries().get(4)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 80,startPoint.y + 29 ))
			.entries(display.getInputEntries().get(5)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 80,startPoint.y + 47 ))
			.entries(display.getInputEntries().get(6)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 107,startPoint.y + 11 ))
			.entries(display.getInputEntries().get(7)));

		widgets.add(Widgets.createSlot(new Point(startPoint.x + 134,startPoint.y + 29 ))
			.markOutput().entries(display.getOutputEntries().get(8)));

		return widgets;
	}

	@Override
	public int getDisplayHeight() {
		return 90;
	}
}
