package io.github.racoondog.bsmpcompanion.client.compat.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class TieredReforgeCategory implements DisplayCategory<TieredReforgeDisplay> {
    private static final Identifier BUTTON_TEXTURE = new Identifier("tiered", "textures/gui/reforging_screen.png");

    @Override
    public CategoryIdentifier<? extends TieredReforgeDisplay> getCategoryIdentifier() {
        return BSMPCompanionReiPlugin.REFORGE_CATEGORY;
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("rei.category.tiered.reforge");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.ANVIL);
    }

    @Override
    public int getDisplayWidth(TieredReforgeDisplay display) {
        return 96;
    }

    @Override
    public int getDisplayHeight() {
        return 49;
    }

    @Override
    public List<Widget> setupDisplay(TieredReforgeDisplay display, Rectangle bounds) {
        return List.of(
            Widgets.createRecipeBase(bounds),
            Widgets.createTexturedWidget(BUTTON_TEXTURE, bounds.getMinX() + 34 + 5, bounds.getMinY() + 22 + 5, 176, 0, 18, 18),
            Widgets.createSlot(new Point(bounds.getMinX() + 5, bounds.getMinY() + 13 + 5)).entries(display.getInputEntries().get(0)),
            Widgets.createSlot(new Point(bounds.getMinX() + 35 + 5, bounds.getMinY() + 5)).entries(display.getInputEntries().get(1)),
            Widgets.createSlot(new Point(bounds.getMinX() + 70 + 5, bounds.getMinY() + 13 + 5)).entries(display.getInputEntries().get(2))
        );
    }
}
