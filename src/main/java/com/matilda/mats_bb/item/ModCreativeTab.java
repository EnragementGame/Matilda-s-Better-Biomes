package com.matilda.mats_bb.item;

import com.matilda.mats_bb.Mats_BB;
import com.matilda.mats_bb.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mats_BB.MODID);

    public static final Supplier<CreativeModeTab> MATS_BB_ITEM_TAB = CREATIVE_MODE_TAB.register("mats_bb_item_tab", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.GLINT.get()))
            .title(Component.translatable("creativetab.mats_bb.mats_bb_items"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.GLINT);
                output.accept(ModItems.GLINT_AND_CRYSTAL);
            }).build());

    public static final Supplier<CreativeModeTab> MATS_BB_BLOCK_TAB = CREATIVE_MODE_TAB.register("mats_bb_block_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Mats_BB.MODID, "mats_bb_item_tab"))
            .icon(() -> new ItemStack(ModBlocks.BEACHROCK.get()))
            .title(Component.translatable("creativetab.mats_bb.mats_bb_blocks"))
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModBlocks.BEACHROCK);
                output.accept(ModBlocks.TRILOBITE_FOSSIL);
            }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
