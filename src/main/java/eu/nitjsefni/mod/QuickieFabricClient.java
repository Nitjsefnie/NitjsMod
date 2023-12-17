package eu.nitjsefni.mod;

import eu.nitjsefni.mod.container.HandledScreenBlockStacker;
import eu.nitjsefni.mod.init.RegistryManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

/**
 *
 * @author jotty
 *
 */
@Environment(EnvType.CLIENT)
public class QuickieFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(RegistryManager.STACKER_SCREEN_HANDLER, HandledScreenBlockStacker::new);
	}
}
