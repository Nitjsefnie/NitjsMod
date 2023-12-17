package eu.nitjsefni.mod;

import eu.nitjsefni.mod.init.RegistryManager;
import net.fabricmc.api.ModInitializer;

public class NitjsMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.

    @Override
    public void onInitialize() {
        RegistryManager.registerItems();
        RegistryManager.registerBlocks();
        RegistryManager.registerBlockEntities();
        RegistryManager.registerTags();
        RegistryManager.registerItemGroup();
        RegistryManager.registerEnchantments();
    }
}