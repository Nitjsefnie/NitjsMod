package eu.nitjsefni.mod;

import eu.nitjsefni.mod.init.RegistryManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class NitjsModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(RegistryManager.STACKER_SCREEN_HANDLER, HandledScreenBlockStacker::new);
	}
}