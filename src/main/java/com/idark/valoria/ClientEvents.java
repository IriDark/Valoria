package com.idark.valoria;


import com.idark.valoria.core.*;
import com.idark.valoria.core.capability.*;
import com.idark.valoria.core.config.*;
import com.idark.valoria.core.interfaces.*;
import com.idark.valoria.core.network.*;
import com.idark.valoria.core.network.packets.*;
import com.idark.valoria.registries.*;
import com.idark.valoria.registries.entity.living.decoration.*;
import com.idark.valoria.registries.item.types.*;
import com.idark.valoria.registries.item.types.BossSummonableItem.*;
import com.idark.valoria.registries.item.types.curio.hands.*;
import com.idark.valoria.util.*;
import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.player.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import pro.komaru.tridot.client.gfx.text.*;
import pro.komaru.tridot.client.render.*;
import pro.komaru.tridot.util.*;
import top.theillusivec4.curios.api.*;

import java.text.*;
import java.util.*;

public class ClientEvents{
    public static final DecimalFormat FORMAT = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final ResourceLocation FLAME_ICON = Valoria.loc("textures/gui/flame_icon.png");
    private static SpawnResult cachedSpawnResult = null;
    private static BlockPos cachedTargetPos = null;
    private static long lastCheckTime = 0;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.getMainHandItem().isEmpty()) return;

        if (mc.player.getMainHandItem().getItem() instanceof BossSummonableItem summonableItem) {
            HitResult hit = mc.hitResult;
            if (hit != null && hit.getType() == HitResult.Type.BLOCK){
                BlockHitResult blockHit = (BlockHitResult)hit;
                BlockPos targetPos = blockHit.getBlockPos().above();

                AABB box = summonableItem.getAABB(targetPos);
                renderBeautifulBox(event.getPoseStack(), mc.gameRenderer.getMainCamera().getPosition(), box);
                renderBlocking(summonableItem, mc.player.level(), event.getPoseStack(), mc.gameRenderer.getMainCamera().getPosition(), targetPos, box);
            }
        }
    }

    public static void renderBeautifulBox(PoseStack poseStack, Vec3 cameraPos, AABB box) {
        AABB renderBox = box.move(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        poseStack.pushPose();
        poseStack.translate(renderBox.minX, renderBox.minY, renderBox.minZ);

        float width = (float)(renderBox.maxX - renderBox.minX);
        float height = (float)(renderBox.maxY - renderBox.minY);
        float length = (float)(renderBox.maxZ - renderBox.minZ);

        RenderBuilder.create()
        .setFormat(DefaultVertexFormat.POSITION_COLOR)
        .setVertexConsumer(builder)
        .setColor(0.4f, 0.0f, 0.6f, 0.1f)
        .renderCube(poseStack, width, height, length);

        poseStack.popPose();
        tesselator.end();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);

        float lineR = 0.8f; float lineG = 0.0f; float lineB = 1.0f; float lineA = 1.0f;
        LevelRenderer.renderLineBox(poseStack, builder, renderBox, lineR, lineG, lineB, lineA);

        tesselator.end();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();

        poseStack.popPose();
    }

    public static void renderBlocking(BossSummonableItem summonableItem, Level level, PoseStack poseStack, Vec3 cameraPos, BlockPos targetPos, AABB box) {
        poseStack.pushPose();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        long currentTime = System.currentTimeMillis();
        if (cachedSpawnResult == null || !targetPos.equals(cachedTargetPos) || currentTime - lastCheckTime > 500) {
            cachedSpawnResult = summonableItem.canSpawnHere(level, box);
            cachedTargetPos = targetPos;
            lastCheckTime = currentTime;
        }

        SpawnResult result = cachedSpawnResult;
        if (!result.success()) {
            if (!result.preventingBlocks().isEmpty()) {
                RenderSystem.disableDepthTest();
                builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                for (PreventingBlock pb : result.preventingBlocks()) {
                    BlockPos pos = pb.pos();
                    AABB blockBounds = pb.shape().bounds().move(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);

                    poseStack.pushPose();
                    poseStack.translate(blockBounds.minX, blockBounds.minY, blockBounds.minZ);

                    float width = (float)(blockBounds.maxX - blockBounds.minX);
                    float height = (float)(blockBounds.maxY - blockBounds.minY);
                    float length = (float)(blockBounds.maxZ - blockBounds.minZ);

                    RenderBuilder.create()
                    .setFormat(DefaultVertexFormat.POSITION_COLOR)
                    .setVertexConsumer(builder)
                    .setColor(1.0f, 0.0f, 0.0f, 0.1f)
                    .renderCube(poseStack, width, height, length);

                    poseStack.popPose();
                }

                tesselator.end();

                builder.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR);
                float errR = 1.0f; float errG = 0.0f; float errB = 0.0f; float errA = 0.5f;
                for (PreventingBlock pb : result.preventingBlocks()) {
                    BlockPos pos = pb.pos();
                    VoxelShape shape = pb.shape();

                    double renderX = pos.getX() - cameraPos.x;
                    double renderY = pos.getY() - cameraPos.y;
                    double renderZ = pos.getZ() - cameraPos.z;
                    LevelRenderer.renderVoxelShape(poseStack, builder, shape, renderX, renderY, renderZ, errR, errG, errB, errA, false);
                }

                tesselator.end();
            }
        }

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();

        poseStack.popPose();
    }

    private static float[] getColor(ItemStack stack){
        if(stack.getItem() instanceof DyeableGlovesItem){
            int color = ((DyeableLeatherItem)stack.getItem()).getColor(stack);
            float r = (float)(color >> 16 & 255) / 255.0F;
            float g = (float)(color >> 8 & 255) / 255.0F;
            float b = (float)(color & 255) / 255.0F;

            return new float[]{r, g, b};
        }else{
            return new float[]{1, 1, 1};
        }
    }

    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        MultiBufferSource pBuffer = event.getMultiBufferSource();
        int pLight = event.getPackedLight();
        var pPlayer = event.getPlayer();
        var playerArm = event.getArm();
        var pPose = event.getPoseStack();

        CuriosApi.getCuriosHelper().getCuriosHandler(pPlayer).ifPresent(handler -> {
            var stacksHandler = handler.getCurios().get("hands");
            if(stacksHandler != null){
                for(int i = 0; i < stacksHandler.getSlots(); i++){
                    if(stacksHandler.getRenders().get(i)){
                        ItemStack stack = stacksHandler.getCosmeticStacks().getStackInSlot(i);
                        if(stack.isEmpty()){
                            stack = stacksHandler.getStacks().getStackInSlot(i);
                        }

                        if(stack.getItem() instanceof GlovesItem item){
                            float[] color = getColor(stack);
                            boolean slim = !pPlayer.getModelName().equals("default");
                            var pTexture = item.getTexture(stack, pPlayer);
                            if(pTexture == null) continue;

                            var pModel = slim ? ValoriaClient.handsSlim : ValoriaClient.hands;
                            var entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(pPlayer);
                            if(entityRenderer instanceof PlayerRenderer playerRenderer){
                                var playerModel = playerRenderer.getModel();
                                playerModel.attackTime = 0.0F;
                                playerModel.crouching = false;
                                playerModel.swimAmount = 0.0F;
                                playerModel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

                                if(playerArm == HumanoidArm.RIGHT){
                                    pModel.right_glove.copyFrom(playerModel.rightArm);
                                    pModel.right_glove.xRot = 0.0F;
                                    pModel.right_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
                                }else{
                                    pModel.left_glove.copyFrom(playerModel.leftArm);
                                    pModel.left_glove.xRot = 0.0F;
                                    pModel.left_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
                                }
                            }else{
                                // Safe fallback
                                float yPos = slim ? 2.5F : 2.0F;
                                if(playerArm == HumanoidArm.RIGHT){
                                    pModel.right_glove.setRotation(0.0F, -0.1F, 0.0F);
                                    pModel.right_glove.setPos(-5.0F, yPos, 0.0F);
                                    pModel.right_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
                                }else{
                                    pModel.left_glove.setRotation(0.0F, 0.1F, 0.0F);
                                    pModel.left_glove.setPos(5.0F, yPos, 0.0F);
                                    pModel.left_glove.render(pPose, pBuffer.getBuffer(RenderType.entityTranslucent(pTexture)), pLight, OverlayTexture.NO_OVERLAY, color[0], color[1], color[2], 1);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ValoriaClient.JEWELRY_BONUSES_KEY.consumeClick()) {
            PacketHandler.sendToServer(new OnKeyInputPacket(0));
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.FOOD_LEVEL.type()) return;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || player.isCreative() || player.isSpectator()) return;
        player.getCapability(IMagmaLevel.INSTANCE).ifPresent(magmaLevel -> {
            float max = magmaLevel.getMaxAmount(player);
            float amount = magmaLevel.getAmount();
            if(max <= 0 || amount >= max) return;

            GuiGraphics gui = event.getGuiGraphics();
            int width = event.getWindow().getGuiScaledWidth();
            int height = event.getWindow().getGuiScaledHeight();

            int left = width / 2 + 10;
            int startY = height - 39 - 10;

            int bubblesToDraw = (int) Math.ceil(amount);
            RandomSource random = player.getRandom();
            boolean shouldShake = player.isInLava() || player.isOnFire();
            int shakeTick = mc.gui.getGuiTicks();

            for (int i = 0; i < bubblesToDraw; i++) {
                int row = i / 10;
                int col = i % 10;

                int x = left + (col * 8);
                int y = startY - (row * 10);
                if (shouldShake) {
                    if ((shakeTick + i * 2) % 7 == 0) {
                        y += random.nextInt(3) - 1;
                    }
                }

                gui.blit(FLAME_ICON, x, y, 0, 0, 9, 9, 9, 9);
            }
        });
    }

    @SubscribeEvent
    public static void onEntityRender(RenderLivingEvent.Post<LivingEntity, ?> event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ILivingEntityData data)) return;
        float lastDamage = data.valoria$getLastDamage();
        
        if(!entity.getType().is(TagsRegistry.DAMAGE_INDICATOR_IGNORED)){
            if(ClientConfig.DAMAGE_INDICATOR.get() || entity instanceof MannequinEntity){
                if(!(lastDamage > 0 && entity.hurtTime > 0)) return;

                Col textColor = Col.red;
                Component component = Component.literal(FORMAT.format(lastDamage));
                for(DamageData damageData : DamageData.dataTypes){
                    if(data.valoria$getLastDamageSource() != null && damageData.predicate().test(data.valoria$getLastDamageSource())){
                        if(damageData.getText() != null) component = damageData.getText();
                        textColor = damageData.getColor();
                    }
                }

                ValoriaUtils.renderText(entity, textColor, component, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), entity.hurtTime);
            }
        }

        if(data.valoria$getMissTime() > 0) {
            ValoriaUtils.renderText(entity, Col.lightGray, Component.translatable("popup.valoria.miss"), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), data.valoria$getMissTime());
        }

        if(data.valoria$getDodgeTime() > 0) {
            ValoriaUtils.renderText(entity, Col.lightGray, Component.translatable("popup.valoria.dodge"), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), data.valoria$getDodgeTime());
        }
    }

    @SubscribeEvent
    public static void onClientJoin(ClientPlayerNetworkEvent.LoggingIn event){
        var modInfo = ModList.get().getModFileById(Valoria.ID).getMods().get(0);
        var result = VersionChecker.getResult(modInfo);
        if(ClientConfig.SHOW_UPDATES.get()){
            if(!modInfo.getVersion().getQualifier().equals("0.0NONE") && result.status().shouldDraw()){
                var newVersion = result.target().toString();
                Component message = Component.literal("\uD83E\uDEB7 Valoria: ").withStyle(style -> DotStyle.of().color(Pal.verySoftPink)).append(Component.translatable("tooltip.valoria.update_available", newVersion).withStyle(ChatFormatting.WHITE));
                var actions = Component.translatable("tooltip.valoria.download").withStyle(style -> style.withUnderlined(true).withFont(Valoria.FONT).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.curseforge.com/minecraft/mc-mods/valoria")))
                .append(Component.literal(" | ")
                .append(Component.literal(" | "))
                .append(Component.translatable("tooltip.valoria.patreon").withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/c/valoriamod"))))
                );

                var separator = Component.literal("<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->-<->").withStyle(style -> DotStyle.of().color(Pal.verySoftPink.copy().darker()));

                event.getPlayer().displayClientMessage(separator, false);

                event.getPlayer().displayClientMessage(message, false);
                event.getPlayer().displayClientMessage(Component.empty(), false);
                event.getPlayer().displayClientMessage(actions, false);

                event.getPlayer().displayClientMessage(separator, false);
            }
        }
    }
}
