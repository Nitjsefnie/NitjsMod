package eu.nitjsefni.mod.container;

import eu.nitjsefni.mod.init.RegistryManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 *
 * @author jotty
 *
 */
@Environment(EnvType.CLIENT)
public class HandledScreenBlockStacker extends HandledScreen<ScreenHandlerBlockStacker>
		implements ScreenHandlerProvider<ScreenHandlerBlockStacker> {
	private final static Identifier TEXTURE = new Identifier(RegistryManager.QUICKIEFABRIC, "textures/gui/stacker.png");
	private final Integer containerHeight = 222;
	private final Integer containerWidth = 176;

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
//    context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int guiX = (this.width - this.containerWidth) / 2;
		int guiY = (this.height - this.containerHeight) / 2;
    super.renderBackground(context);
		context.drawTexture(TEXTURE, guiX, guiY, 0, 0, containerWidth, containerHeight);
	}
}
