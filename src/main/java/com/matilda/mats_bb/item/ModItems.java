package com.matilda.mats_bb.item;

import com.matilda.mats_bb.Mats_BB;
import com.matilda.mats_bb.item.special.GlintAndCrystalItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Mats_BB.MODID);

    public static final DeferredItem<Item> GLINT = ITEMS.register("glint", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GLINT_AND_CRYSTAL = ITEMS.register("glint_and_crystal", () -> new GlintAndCrystalItem(new Item.Properties()
            .durability(32)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
