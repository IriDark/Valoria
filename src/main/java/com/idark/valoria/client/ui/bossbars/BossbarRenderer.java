package com.idark.valoria.client.ui.bossbars;

//todo
//public class BossbarRenderer{
//    public static final Map<UUID, Bossbar> ACTIVE_BOSSBARS = new HashMap<>();
//    private static final ResourceLocation VANILLA_LOC = new ResourceLocation("textures/gui/bars.png");
//    public static void onBossInfoRender(CustomizeGuiOverlayEvent.BossEventProgress ev){
//        Minecraft mc = Minecraft.getInstance();
//        if (ev.isCanceled() || mc.level == null) return;
//        GuiGraphics pGuiGraphics = ev.getGuiGraphics();
//        Map<UUID, LerpingBossEvent> events = ((BossHealthOverlayAccessor)mc.gui.getBossOverlay()).getEvents();
//        if(events.isEmpty()) return;
//        int screenWidth = pGuiGraphics.guiWidth();
//        if(ClientConfig.BOSSBAR_TITLE.get()){
//            titledBossbar(ev.getIncrement(), events, screenWidth, pGuiGraphics, mc);
//        }else{
//            bossbar(ev.getIncrement(), events, screenWidth, pGuiGraphics);
//        }
//
//        ev.setCanceled(true);
//    }
//
//    private static void bossbar(int increment, Map<UUID, LerpingBossEvent> events, int screenWidth, GuiGraphics pGuiGraphics){
//        int yOffset = 0;
//        int vyOffset = 16;
//        int offset = 0;
//        for(LerpingBossEvent event : events.values()){
//            Optional<Bossbar> bossbar = ;
//            if(bossbar.isPresent()){
//                Bossbar boss = bossbar.get();
//                if(boss.getName().equals(event.getName().toString())){
//                    boolean flag = event.getName().getString().equals(boss.getName());
//                    int xOffset = screenWidth / 2 - 91;
//                    if(flag){
//                        offset += 8;
//                        drawBar(boss.getTexture(), pGuiGraphics, xOffset, offset + yOffset, event);
//                        offset += increment;
//                    }
//
//                    if(!flag){
//                        offset -= 6;
//                        drawVanillaBar(pGuiGraphics, xOffset, offset + vyOffset, event);
//                        offset += increment;
//                    }
//
//                    if(offset >= pGuiGraphics.guiHeight() / 3) break;
//                }
//            }
//        }
//    }
//
//    private static void titledBossbar(int increment, Map<UUID, LerpingBossEvent> events, int screenWidth, GuiGraphics pGuiGraphics, Minecraft mc){
//        int yOffset = -6;
//        int vyOffset = 12;
//        int offset = 0;
//        for(LerpingBossEvent event : events.values()){
//            Optional<Bossbar> bossbar = ;
//            if(bossbar.isPresent()){
//                Bossbar boss = bossbar.get();
//                Component component = event.getName();
//                boolean flag = boss.getName().equals(event.getName().getString());
//                int xOffset = screenWidth / 2 - 91;
//                if(flag){
//                    offset += 14;
//                    drawBar(boss.getTexture(), pGuiGraphics, xOffset, offset + yOffset, event);
//                    int nameX = screenWidth / 2 - mc.font.width(component) / 2;
//                    int nameY = offset + yOffset + 24;
//                    pGuiGraphics.drawString(mc.font, component, nameX, nameY, 16777215);
//                    offset += increment;
//                }
//
//                if(!flag){
//                    offset += 6;
//                    drawVanillaBar(pGuiGraphics, xOffset, offset + vyOffset, event);
//                    int nameX = screenWidth / 2 - mc.font.width(component) / 2;
//                    int nameY = offset + vyOffset - 9;
//                    pGuiGraphics.drawString(mc.font, component, nameX, nameY, 16777215);
//                    offset += increment;
//                }
//
//                if(offset >= pGuiGraphics.guiHeight() / 3) break;
//            }
//        }
//    }
//
//    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent) {
//        drawVanillaBar(pGuiGraphics, pX, pY, pBossEvent, 182, 0);
//        int i = (int)(pBossEvent.getProgress() * 183.0F);
//        if (i > 0) {
//            drawVanillaBar(pGuiGraphics, pX, pY, pBossEvent, i, 5);
//        }
//    }
//
//    private static void drawVanillaBar(GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent, int pWidth, int p_281636_) {
//        pGuiGraphics.blit(VANILLA_LOC, pX, pY, 0, pBossEvent.getColor().ordinal() * 5 * 2 + p_281636_, pWidth, 5);
//        if (pBossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
//            RenderSystem.enableBlend();
//            pGuiGraphics.blit(VANILLA_LOC, pX, pY, 0, 80 + (pBossEvent.getOverlay().ordinal() - 1) * 5 * 2 + p_281636_, pWidth, 5);
//            RenderSystem.disableBlend();
//        }
//    }
//
//    private static void drawBar(ResourceLocation texture, GuiGraphics pGuiGraphics, int pX, int pY, BossEvent pBossEvent) {
//        pGuiGraphics.blit(texture, pX, pY, 0, 0, 183, 24, 256, 64);
//        int i = (int)(pBossEvent.getProgress() * 177.0F);
//        if (i > 0) {
//            if (pBossEvent.getOverlay() == BossEvent.BossBarOverlay.PROGRESS) {
//                RenderSystem.enableBlend();
//                pGuiGraphics.blit(texture, pX + 3, pY + 14, 3, 30, i, 4, 256, 64);
//                RenderSystem.disableBlend();
//            }
//        }
//    }
//}
