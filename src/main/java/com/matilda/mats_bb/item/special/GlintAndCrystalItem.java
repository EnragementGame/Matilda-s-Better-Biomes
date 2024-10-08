package com.matilda.mats_bb.item.special;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GlintAndCrystalItem extends Item {
    public GlintAndCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
            if (target.fireImmune() || target.isOnFire()){
                return InteractionResult.FAIL;
            }
        if (!player.level().isClientSide) {
            target.setRemainingFireTicks(120);
            stack.hurtAndBreak(1, player, stack.getEquipmentSlot());
        }
        return InteractionResult.SUCCESS;
    }
}
