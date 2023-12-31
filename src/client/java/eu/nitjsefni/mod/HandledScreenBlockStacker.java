package eu.nitjsefni.mod;

import eu.nitjsefni.mod.container.ScreenHandlerBlockStacker;
import eu.nitjsefni.mod.init.RegistryManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HandledScreenBlockStacker extends HandledScreen<ScreenHandlerBlockStacker>
        implements ScreenHandlerProvider<ScreenHandlerBlockStacker> {
    private final static Identifier TEXTURE = new Identifier(RegistryManager.MOD_ID, "textures/gui/stacker.png");

    public HandledScreenBlockStacker(ScreenHandlerBlockStacker handler, PlayerInventory inventory, Text text) {
        super(handler, inventory, text);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(drawContext);
        super.render(drawContext, mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int containerWidth = 176;
        int guiX = (this.width - containerWidth) / 2;
        int containerHeight = 222;
        int guiY = (this.height - containerHeight) / 2;
        super.renderBackground(context);
        context.drawTexture(TEXTURE, guiX, guiY, 0, 0, containerWidth, containerHeight);
    }
}
