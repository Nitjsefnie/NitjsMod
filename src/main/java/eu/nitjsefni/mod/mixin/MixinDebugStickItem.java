package eu.nitjsefni.mod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DebugStickItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;

@Mixin(DebugStickItem.class)
public class MixinDebugStickItem {

    @Shadow
    private static void sendMessage(PlayerEntity player, Text message) {
    }

    @Shadow
    private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) {
        return null;
    }

    @Shadow
    private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) {
        return null;
    }

    @Shadow
    private static <T extends Comparable<T>> String getValueString(BlockState state, Property<T> property) {
        return null;
    }

    /**
     * @author Nitjsefnie
     * @reason make usable by players not in creative level 2
     */
    @Overwrite
    private boolean use(PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos, boolean update, ItemStack stack) {
        Block block = state.getBlock();
        StateManager<Block, BlockState> stateManager = block.getStateManager();
        Collection<Property<?>> collection = stateManager.getProperties();
        String string = Registries.BLOCK.getId(block).toString();
        if (collection.isEmpty()) {
            sendMessage(player, Text.translatable(((Item) (Object) this).getTranslationKey() + ".empty", new Object[]{string}));
            return false;
        }
        NbtCompound nbtCompound = stack.getOrCreateSubNbt("DebugProperty");
        String string2 = nbtCompound.getString(string);
        Property<?> property = stateManager.getProperty(string2);
        if (update) {
            if (property == null) {
                property = (Property) collection.iterator().next();
            }

            BlockState blockState = cycle(state, property, player.shouldCancelInteraction());
            world.setBlockState(pos, blockState, 18);
            sendMessage(player, Text.translatable(((Item) (Object) this).getTranslationKey() + ".update", new Object[]{property.getName(), getValueString(blockState, property)}));
        }
        else {
            property = (Property) cycle((Iterable) collection, (Object) property, player.shouldCancelInteraction());
            String string3 = property.getName();
            nbtCompound.putString(string, string3);
            sendMessage(player, Text.translatable(((Item) (Object) this).getTranslationKey() + ".select", new Object[]{string3, getValueString(state, property)}));
        }

        return true;
    }
}
