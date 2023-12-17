package eu.nitjsefni.mod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class RandomFishingEnchantment extends Enchantment {
    public static final EquipmentSlot[] VALID_SLOTS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};

    public RandomFishingEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.FISHING_ROD, VALID_SLOTS);
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.LUCK_OF_THE_SEA;
    }
}
