package eu.nitjsefnie.mod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NitjsMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "nitjsefnie";

	public static final Enchantment RANDOM_FISHING_ENCHANTMENT = new RandomFishingEnchantment();
	public static final Enchantment MULTI_ROD_ENCHANTMENT = new MultiRodEnchantment();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "random_fishing"), RANDOM_FISHING_ENCHANTMENT);
		Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "multi_rod"), MULTI_ROD_ENCHANTMENT);
	}
}