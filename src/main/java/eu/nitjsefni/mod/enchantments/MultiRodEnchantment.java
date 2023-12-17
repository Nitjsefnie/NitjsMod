package eu.nitjsefni.mod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class MultiRodEnchantment extends Enchantment {
    public static final EquipmentSlot[] VALID_SLOTS = new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
    public MultiRodEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.FISHING_ROD, VALID_SLOTS);
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }
}
