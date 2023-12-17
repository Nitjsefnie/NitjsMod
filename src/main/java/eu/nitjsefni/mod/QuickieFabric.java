package eu.nitjsefni.mod;

import eu.nitjsefni.mod.init.RegistryManager;
import net.fabricmc.api.ModInitializer;

/**
 *
 * @author jotty
 *
 */
public class QuickieFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		RegistryManager.registerItems();
		RegistryManager.registerBlocks();
		RegistryManager.registerBlockEntities();
		RegistryManager.registerTags();
		RegistryManager.registerItemGroup();
	}
}
