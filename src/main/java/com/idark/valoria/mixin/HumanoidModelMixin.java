package com.idark.valoria.mixin;

import com.idark.valoria.registries.world.item.types.ICustomAnimationItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

    @Inject(at = @At("RETURN"), method = "setupAnim")
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        HumanoidModel self = (HumanoidModel) ((Object) this);
        if (pEntity instanceof Player player) {
            if (pEntity.isUsingItem() && pEntity.getUseItemRemainingTicks() > 0) {
                if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof ICustomAnimationItem item) {
                    ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                    if (item.getAnimation(stack) != null) {
                        if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                            item.getAnimation(stack).setupAnimRight(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                        } else {
                            item.getAnimation(stack).setupAnimLeft(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                        }

                        item.getAnimation(stack).setupAnim(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                    }
                }
            }
        }
    }
}