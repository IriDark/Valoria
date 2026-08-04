package com.idark.valoria.core.datagen;

import com.idark.valoria.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraftforge.common.data.*;

public class ModSoundProvider extends SoundDefinitionsProvider {

    public ModSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Valoria.ID, helper);
    }

    @Override
    public void registerSounds() {
        add("ambient.valoria.additions", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ambient/valoria/addition0")).volume(0.3f))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/addition1")).volume(0.3f))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/addition2")).volume(0.3f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition1")).volume(0.3f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition2")).volume(0.3f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition3")).volume(0.1f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition4")).volume(0.67f).weight(5))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition5")).volume(0.3f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition6")).volume(0.3f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition7")).volume(0.3f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/addition8")).volume(0.67f).weight(5))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/dark1")).volume(0.9f).weight(5))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/dark2")).volume(0.9f).weight(5))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/ground1")).volume(0.25f).weight(3))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/ground2")).volume(0.4f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/ground3")).volume(0.4f))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/ground4")).volume(0.4f)));

        add("ambient.crimson.mood", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ambient/valoria/crimson/mood0")))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/crimson/mood1")))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/crimson/mood2")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood1")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood2")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood4")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood5"))));

        add("ambient.valoria.mood", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ambient/valoria/mood0")))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/mood1")))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/mood2")))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/mood3")))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/mood4")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood1")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood2")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood4")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/nether_wastes/mood5"))));

        add("ambient.shade_forest.additions", SoundDefinition.definition()
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/addition1")).volume(0.4f).weight(3))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/addition2")).volume(0.5f).weight(3))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/addition3")).volume(0.32f).weight(3))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/particles1")).volume(0.4f).weight(35))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/particles2")).volume(0.4f).weight(35))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/particles3")).volume(0.4f).weight(35))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/forest/addition0")).volume(0.1f).pitch(0.5f).weight(6))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/shroom1")).volume(0.25f).weight(2))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/shroom2")).volume(0.25f).weight(2))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/shroom3")).volume(0.25f).weight(2))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/twang1")).volume(0.25f).weight(2))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/voom1")).volume(0.7f).weight(4))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/voom1")).volume(0.7f).pitch(0.8f).weight(4))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/voom2")).volume(0.7f).weight(4))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/voom2")).volume(0.7f).pitch(0.8f).weight(4)));

        add("ambient.shade_forest.mood", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ambient/valoria/forest/mood0")))
                .with(sound(new ResourceLocation("valoria:ambient/valoria/forest/mood1")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/mood1")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/mood2")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/mood3")))
                .with(sound(new ResourceLocation("minecraft:ambient/nether/crimson_forest/mood4"))));

        add("boss.necromancer.music", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/necromancer")).stream()));

        add("dungeon.necromancer.music", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/necromancer_dungeon")).stream()));

        add("music.valoria.carrion", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/carrion")).stream())
                .with(sound(new ResourceLocation("valoria:music/arriving")).stream())
                .with(sound(new ResourceLocation("valoria:music/blood_pole")).stream())
                .with(sound(new ResourceLocation("valoria:music/explore")).stream()));

        add("music.valoria.arriving", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/arriving")).stream())
                .with(sound(new ResourceLocation("valoria:music/singing_skies")).stream())
                .with(sound(new ResourceLocation("valoria:music/explore")).stream())
                .with(sound(new ResourceLocation("valoria:music/old_times")).stream()));

        add("music.valoria.blood_pole", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/carrion")).stream())
                .with(sound(new ResourceLocation("valoria:music/rising")).stream())
                .with(sound(new ResourceLocation("valoria:music/arriving")).stream())
                .with(sound(new ResourceLocation("valoria:music/blood_pole")).stream())
                .with(sound(new ResourceLocation("valoria:music/explore")).stream()));

        add("music.valoria.rising", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/rising")).stream())
                .with(sound(new ResourceLocation("valoria:music/arriving")).stream())
                .with(sound(new ResourceLocation("valoria:music/explore")).stream())
                .with(sound(new ResourceLocation("valoria:music/old_times")).stream())
                .with(sound(new ResourceLocation("valoria:music/singing_skies")).stream()));

        add("music.valoria.enduring", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/enduring")).stream())
                .with(sound(new ResourceLocation("valoria:music/old_times")).stream())
                .with(sound(new ResourceLocation("valoria:music/rising")).stream())
                .with(sound(new ResourceLocation("valoria:music/arriving")).stream())
                .with(sound(new ResourceLocation("valoria:music/explore")).stream())
                .with(sound(new ResourceLocation("valoria:music/singing_skies")).stream()));

        add("music.valoria.shaded_lands", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:music/singing_skies")).stream())
                .with(sound(new ResourceLocation("valoria:music/shaded_lands")).stream())
                .with(sound(new ResourceLocation("valoria:music/rising")).stream())
                .with(sound(new ResourceLocation("valoria:music/arriving")).stream())
                .with(sound(new ResourceLocation("valoria:music/explore")).stream())
                .with(sound(new ResourceLocation("valoria:music/old_times")).stream()));

        add("ui.codex_click", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ui/codex_button_click"))));

        add("ui.click", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ui/click"))));

        add("ui.alchemy.brew", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ui/alchemy/brew_0")))
                .with(sound(new ResourceLocation("valoria:ui/alchemy/brew_1")))
                .with(sound(new ResourceLocation("valoria:ui/alchemy/brew_2")))
                .with(sound(new ResourceLocation("valoria:ui/alchemy/brew_3"))));

        add("ui.alchemy.nether_upgrade", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ui/alchemy/nether_upgrade"))));

        add("ui.alchemy.elemental_upgrade", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ui/alchemy/elemental_upgrade"))));

        add("ui.alchemy.nihility_upgrade", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:ui/alchemy/nihility_upgrade"))));

        add("mob.breath", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/breath"))));

        add("mob.attack.miss", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/miss"))));

        add("mob.attack.dodge", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/dodge"))));

        add("item.nihility_alert.active", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:item/alert"))));

        add("block.shade.place", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.place")
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit1")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit2")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit3")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit4"))));

        add("block.shade.fall", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit1")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit2")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit3")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit4"))));

        add("block.shade.hit", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit1")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit2")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit3")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/hit4"))));

        add("block.shade.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.step")
                .with(sound(new ResourceLocation("valoria:block/shade_wood/step1")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/step2")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/step3")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/step4"))));

        add("block.shade.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/shade_wood/break1")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/break2")))
                .with(sound(new ResourceLocation("valoria:block/shade_wood/break3"))));

        add("block.flesh.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/flesh/break1")))
                .with(sound(new ResourceLocation("valoria:block/flesh/break2")))
                .with(sound(new ResourceLocation("valoria:block/flesh/break3"))));

        add("block.cyst.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/cyst/break1")))
                .with(sound(new ResourceLocation("valoria:block/cyst/break2")))
                .with(sound(new ResourceLocation("valoria:block/cyst/break3"))));

        add("block.cyst.summon", SoundDefinition.definition()
                .subtitle("subtitles.valoria.cyst.summon")
                .with(sound(new ResourceLocation("valoria:block/cyst/summon1")))
                .with(sound(new ResourceLocation("valoria:block/cyst/summon2")))
                .with(sound(new ResourceLocation("valoria:block/cyst/summon3"))));

        add("block.cyst.fall", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.fall")
                .with(sound(new ResourceLocation("valoria:block/cyst/break1")))
                .with(sound(new ResourceLocation("valoria:block/cyst/break2")))
                .with(sound(new ResourceLocation("valoria:block/cyst/break3"))));

        add("block.cyst.spreads", SoundDefinition.definition()
                .subtitle("subtitles.valoria.cyst.spread")
                .with(sound(new ResourceLocation("valoria:block/cyst/spreads1")))
                .with(sound(new ResourceLocation("valoria:block/cyst/spreads2")))
                .with(sound(new ResourceLocation("valoria:block/cyst/spreads3")))
                .with(sound(new ResourceLocation("valoria:block/cyst/spreads4")))
                .with(sound(new ResourceLocation("valoria:block/cyst/spreads5"))));

        add("block.flesh.hit", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.hit")
                .with(sound(new ResourceLocation("valoria:block/flesh/hit1")))
                .with(sound(new ResourceLocation("valoria:block/flesh/hit2")))
                .with(sound(new ResourceLocation("valoria:block/flesh/hit3")))
                .with(sound(new ResourceLocation("valoria:block/flesh/hit4")))
                .with(sound(new ResourceLocation("valoria:block/flesh/hit5"))));

        add("block.flesh.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/flesh/step1")))
                .with(sound(new ResourceLocation("valoria:block/flesh/step2")))
                .with(sound(new ResourceLocation("valoria:block/flesh/step3")))
                .with(sound(new ResourceLocation("valoria:block/flesh/step4"))));

        add("block.flesh.fall", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.fall")
                .with(sound(new ResourceLocation("valoria:block/flesh/step1")))
                .with(sound(new ResourceLocation("valoria:block/flesh/step2")))
                .with(sound(new ResourceLocation("valoria:block/flesh/step3")))
                .with(sound(new ResourceLocation("valoria:block/flesh/step4"))));

        add("block.flesh.place", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.place")
                .with(sound(new ResourceLocation("valoria:block/flesh/place1")))
                .with(sound(new ResourceLocation("valoria:block/flesh/place2")))
                .with(sound(new ResourceLocation("valoria:block/flesh/place3"))));

        add("block.elemental_manipulator.loop", SoundDefinition.definition()
                .subtitle("subtitles.valoria.elemental_manipulator.loop")
                .with(sound(new ResourceLocation("valoria:block/elemental_manipulator/loop"))));

        add("block.tombstone.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/tombstone/break1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/break2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/break3"))));

        add("block.tombstone.hit", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.hit")
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit3")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit4"))));

        add("block.tombstone.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/tombstone/step1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/step2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/step3"))));

        add("block.tombstone.fall", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.fall")
                .with(sound(new ResourceLocation("valoria:block/tombstone/step1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/step2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/step3"))));

        add("block.tombstone.place", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.place")
                .with(sound(new ResourceLocation("valoria:block/tombstone/place1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/place2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/place3"))));

        add("block.suspicious_tombstone.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/suspicious_tombstone/break1")))
                .with(sound(new ResourceLocation("valoria:block/suspicious_tombstone/break2")))
                .with(sound(new ResourceLocation("valoria:block/suspicious_tombstone/break3"))));

        add("block.suspicious_tombstone.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/suspicious_tombstone/step1")))
                .with(sound(new ResourceLocation("valoria:block/suspicious_tombstone/step2")))
                .with(sound(new ResourceLocation("valoria:block/suspicious_tombstone/step3"))));

        add("block.tombstone_bricks.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/break1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/break2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/break3"))));

        add("block.tombstone_bricks.place", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.place")
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/place1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/place2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/place3"))));

        add("block.tombstone_bricks.hit", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.hit")
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit3")))
                .with(sound(new ResourceLocation("valoria:block/tombstone/hit4"))));

        add("block.tombstone_bricks.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/step1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/step2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/step3"))));

        add("block.tombstone_bricks.fall", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.fall")
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/step1")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/step2")))
                .with(sound(new ResourceLocation("valoria:block/tombstone_bricks/step3"))));

        add("block.sarcophagus.open", SoundDefinition.definition()
                .subtitle("subtitles.valoria.sarcophagus.open")
                .with(sound(new ResourceLocation("valoria:block/sarcophagus_open"))));

        add("block.valoria_portal.spawn", SoundDefinition.definition()
                .subtitle("subtitles.valoria.valoria_portal.spawn")
                .with(sound(new ResourceLocation("valoria:block/valoria_portal/portal"))));

        add("block.keg.ambient", SoundDefinition.definition()
                .subtitle("subtitles.valoria.keg.ambient")
                .with(sound(new ResourceLocation("valoria:block/keg_brewery/ambient_0")))
                .with(sound(new ResourceLocation("valoria:block/keg_brewery/ambient_1")))
                .with(sound(new ResourceLocation("valoria:block/keg_brewery/ambient_2")))
                .with(sound(new ResourceLocation("valoria:block/keg_brewery/ambient_3"))));

        add("block.keg.brew", SoundDefinition.definition()
                .subtitle("subtitles.valoria.keg.brew")
                .with(sound(new ResourceLocation("valoria:block/keg_brewery/final_brew"))));

        add("item.soul_collect.full", SoundDefinition.definition()
                .subtitle("subtitles.valoria.soul_collect")
                .with(sound(new ResourceLocation("valoria:item/soul_collect_full"))));

        add("item.soul_collect", SoundDefinition.definition()
                .subtitle("subtitles.valoria.soul_collect")
                .with(sound(new ResourceLocation("valoria:item/soul_collect_0")))
                .with(sound(new ResourceLocation("valoria:item/soul_collect_1"))));

        add("mob.magic.shoot", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/magic_projectile/shoot1")))
                .with(sound(new ResourceLocation("valoria:entity/magic_projectile/shoot2"))));

        add("mob.magic.hit", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/magic_projectile/hit1")))
                .with(sound(new ResourceLocation("valoria:entity/magic_projectile/hit2"))));

        add("mob.dryador.stomp", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/dryador/stomp"))));

        add("mob.wicked_crystal.altar", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/wicked_crystal_altar"))));

        add("mob.crystal_frost.prepare", SoundDefinition.definition()
                .subtitle("subtitles.valoria.minion.prepare")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/frost_crystal_prepare"))));

        add("mob.crystal_acid.prepare", SoundDefinition.definition()
                .subtitle("subtitles.valoria.minion.prepare")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/acid_crystal_prepare"))));

        add("mob.crystal_fire.prepare", SoundDefinition.definition()
                .subtitle("subtitles.valoria.minion.prepare")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/fire_crystal_prepare"))));

        add("mob.crystal_frost.attack", SoundDefinition.definition()
                .subtitle("subtitles.valoria.minion.attack")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/frost_crystal"))));

        add("mob.crystal_acid.attack", SoundDefinition.definition()
                .subtitle("subtitles.valoria.minion.attack")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/acid_crystal"))));

        add("mob.crystal_fire.attack", SoundDefinition.definition()
                .subtitle("subtitles.valoria.minion.attack")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/fire_crystal"))));

        add("mob.wicked_crystal.transform", SoundDefinition.definition()
                .subtitle("subtitles.valoria.boss.transform")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/transform"))));

        add("mob.crystal.storm", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/crystal_storm"))));

        add("mob.crystal.fall", SoundDefinition.definition()
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/crystal_fall1")))
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/crystal_fall2")))
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/crystal_fall3"))));

        add("mob.wicked_crystal.summon", SoundDefinition.definition()
                .subtitle("subtitles.valoria.boss.summon")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/summon"))));

        add("mob.wicked_crystal.hurt", SoundDefinition.definition()
                .subtitle("subtitles.valoria.boss.hurt")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/hurt1")))
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/hurt2")))
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/hurt3")))
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/hurt4"))));

        add("mob.wicked_crystal.death", SoundDefinition.definition()
                .subtitle("subtitles.valoria.boss.death")
                .with(sound(new ResourceLocation("valoria:entity/wicked_crystal/death"))));

        add("mob.haunted_merchant.hurt", SoundDefinition.definition()
                .subtitle("subtitles.valoria.haunted_merchant.hurt")
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/hurt1")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/hurt2")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/hurt3"))));

        add("mob.necromancer_summon", SoundDefinition.definition()
                .subtitle("subtitles.valoria.necromancer_summon")
                .with(sound(new ResourceLocation("valoria:entity/necromancer/summon"))));

        add("mob.necromancer_summon.air", SoundDefinition.definition()
                .subtitle("subtitles.valoria.necromancer_summon.air")
                .with(sound(new ResourceLocation("valoria:entity/necromancer/summon_air"))));

        add("mob.necromancer_summon.ground", SoundDefinition.definition()
                .subtitle("subtitles.valoria.necromancer_summon.ground")
                .with(sound(new ResourceLocation("valoria:entity/necromancer/summon_ground"))));

        add("mob.devil.idle", SoundDefinition.definition()
                .subtitle("subtitles.valoria.devil.idle")
                .with(sound(new ResourceLocation("valoria:entity/devil/idle1")))
                .with(sound(new ResourceLocation("valoria:entity/devil/idle2")))
                .with(sound(new ResourceLocation("valoria:entity/devil/idle3")))
                .with(sound(new ResourceLocation("valoria:entity/devil/idle4"))));

        add("mob.devil.hurt", SoundDefinition.definition()
                .subtitle("subtitles.valoria.devil.hurt")
                .with(sound(new ResourceLocation("valoria:entity/devil/hurt1")))
                .with(sound(new ResourceLocation("valoria:entity/devil/hurt2")))
                .with(sound(new ResourceLocation("valoria:entity/devil/hurt3")))
                .with(sound(new ResourceLocation("valoria:entity/devil/hurt4"))));

        add("mob.devil.death", SoundDefinition.definition()
                .subtitle("subtitles.valoria.devil.death")
                .with(sound(new ResourceLocation("valoria:entity/devil/death1")))
                .with(sound(new ResourceLocation("valoria:entity/devil/death2")))
                .with(sound(new ResourceLocation("valoria:entity/devil/death3")))
                .with(sound(new ResourceLocation("valoria:entity/devil/death4"))));

        add("mob.devil.attack", SoundDefinition.definition()
                .subtitle("subtitles.valoria.devil.attack")
                .with(sound(new ResourceLocation("valoria:entity/devil/attack1")))
                .with(sound(new ResourceLocation("valoria:entity/devil/attack2")))
                .with(sound(new ResourceLocation("valoria:entity/devil/attack3")))
                .with(sound(new ResourceLocation("valoria:entity/devil/attack4"))));

        add("mob.haunted_merchant.death", SoundDefinition.definition()
                .subtitle("subtitles.valoria.haunted_merchant.death")
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/death1")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/death2")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/death3"))));

        add("mob.haunted_merchant.idle", SoundDefinition.definition()
                .subtitle("subtitles.valoria.haunted_merchant.idle")
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/idle1")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/idle2")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/idle3"))));

        add("mob.haunted_merchant.yes", SoundDefinition.definition()
                .subtitle("subtitles.valoria.haunted_merchant.yes")
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/yes1")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/yes2")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/yes3"))));

        add("mob.haunted_merchant.no", SoundDefinition.definition()
                .subtitle("subtitles.valoria.haunted_merchant.no")
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/no1")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/no2")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/no3"))));

        add("mob.haunted_merchant.melee_attack", SoundDefinition.definition()
                .subtitle("subtitles.valoria.haunted_merchant.melee_attack")
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/melee_attack/attack1")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/melee_attack/attack2")))
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/melee_attack/attack3"))));

        add("mob.haunted_merchant.ranged_attack", SoundDefinition.definition()
                .subtitle("subtitles.valoria.haunted_merchant.ranged_attack")
                .with(sound(new ResourceLocation("valoria:entity/haunted_merchant/melee_attack/attack1"))));

        add("mob.elemental_golem.attack.1", SoundDefinition.definition()
                .subtitle("subtitles.valoria.elemental_golem.attack")
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/attack1_1")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/attack1_2"))));

        add("mob.elemental_golem.attack.2", SoundDefinition.definition()
                .subtitle("subtitles.valoria.elemental_golem.attack")
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step1")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step2")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step3")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step4"))));

        add("mob.elemental_golem.attack.3", SoundDefinition.definition()
                .subtitle("subtitles.valoria.elemental_golem.attack")
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/attack3_1")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/attack3_2"))));

        add("mob.elemental_golem.attack.4", SoundDefinition.definition()
                .subtitle("subtitles.valoria.elemental_golem.attack")
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/attack4"))));

        add("mob.elemental_golem.hurt", SoundDefinition.definition()
                .subtitle("subtitles.valoria.elemental_golem.hurt")
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/hurt1")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/hurt2")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/hurt3")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/hurt4"))));

        add("mob.elemental_golem.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step1")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step2")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step3")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/step4"))));

        add("mob.elemental_golem.death", SoundDefinition.definition()
                .subtitle("subtitles.valoria.elemental_golem.death")
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/death1")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/death2")))
                .with(sound(new ResourceLocation("valoria:entity/elemental_golem/death3"))));

        add("mob.goblin.idle", SoundDefinition.definition()
                .subtitle("subtitles.valoria.goblin.idle")
                .with(sound(new ResourceLocation("valoria:entity/goblin/idle1")))
                .with(sound(new ResourceLocation("valoria:entity/goblin/idle2")))
                .with(sound(new ResourceLocation("valoria:entity/goblin/idle3")))
                .with(sound(new ResourceLocation("valoria:entity/goblin/idle4"))));

        add("mob.goblin.hurt", SoundDefinition.definition()
                .subtitle("subtitles.valoria.goblin.hurt")
                .with(sound(new ResourceLocation("valoria:entity/goblin/hurt1")))
                .with(sound(new ResourceLocation("valoria:entity/goblin/hurt2")))
                .with(sound(new ResourceLocation("valoria:entity/goblin/hurt3"))));

        add("mob.goblin.death", SoundDefinition.definition()
                .subtitle("subtitles.valoria.goblin.death")
                .with(sound(new ResourceLocation("valoria:entity/goblin/death1")))
                .with(sound(new ResourceLocation("valoria:entity/goblin/death2"))));

        add("mob.troll.disappear", SoundDefinition.definition()
                .subtitle("subtitles.valoria.troll.disappear")
                .with(sound(new ResourceLocation("valoria:entity/troll/disappear0")))
                .with(sound(new ResourceLocation("valoria:entity/troll/disappear1"))));

        add("mob.troll.hurt", SoundDefinition.definition()
                .subtitle("subtitles.valoria.troll.hurt")
                .with(sound(new ResourceLocation("valoria:entity/troll/hurt0")))
                .with(sound(new ResourceLocation("valoria:entity/troll/hurt1")))
                .with(sound(new ResourceLocation("valoria:entity/troll/hurt2"))));

        add("mob.troll.idle", SoundDefinition.definition()
                .subtitle("subtitles.valoria.troll.idle")
                .with(sound(new ResourceLocation("valoria:entity/troll/idle0")))
                .with(sound(new ResourceLocation("valoria:entity/troll/idle1")))
                .with(sound(new ResourceLocation("valoria:entity/troll/idle2"))));

        add("mob.troll.death", SoundDefinition.definition()
                .subtitle("subtitles.valoria.troll.death")
                .with(sound(new ResourceLocation("valoria:entity/troll/death0")))
                .with(sound(new ResourceLocation("valoria:entity/troll/death1")))
                .with(sound(new ResourceLocation("valoria:entity/troll/death2"))));

        add("item.spear.hit_ground", SoundDefinition.definition()
                .subtitle("subtitles.valoria.spear.hit_ground")
                .with(sound(new ResourceLocation("valoria:entity/spear/ground_impact1")))
                .with(sound(new ResourceLocation("valoria:entity/spear/ground_impact2")))
                .with(sound(new ResourceLocation("valoria:entity/spear/ground_impact3")))
                .with(sound(new ResourceLocation("valoria:entity/spear/ground_impact4"))));

        add("item.spear.return", SoundDefinition.definition()
                .subtitle("subtitles.valoria.spear.return")
                .with(sound(new ResourceLocation("valoria:entity/spear/return1")))
                .with(sound(new ResourceLocation("valoria:entity/spear/return2"))));

        add("item.spear.throw", SoundDefinition.definition()
                .subtitle("subtitles.valoria.spear.throw")
                .with(sound(new ResourceLocation("valoria:entity/spear/throw1")))
                .with(sound(new ResourceLocation("valoria:entity/spear/throw2"))));

        add("item.disappear.ambient", SoundDefinition.definition()
                .subtitle("subtitles.valoria.spectral_blade.disappear")
                .with(sound(new ResourceLocation("valoria:entity/spectral/disappear"))));

        add("item.curse.ambient", SoundDefinition.definition()
                .subtitle("subtitles.valoria.curse.equip")
                .with(sound(new ResourceLocation("valoria:item/bloodsight/equip_curse"))));

        add("item.repair.ambient", SoundDefinition.definition()
                .subtitle("subtitles.valoria.curse.lost")
                .with(sound(new ResourceLocation("valoria:item/bloodsight/repair"))));

        add("item.vampiric_rune.activate", SoundDefinition.definition()
                .subtitle("subtitles.valoria.vampiric_rune.activate")
                .with(sound(new ResourceLocation("valoria:item/vampiric_rune"))));

        add("item.shield.parry", SoundDefinition.definition()
                .subtitle("subtitles.valoria.shield.parry")
                .with(sound(new ResourceLocation("valoria:item/abilities/parry"))));

        add("item.water_ability.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/water_ability"))));

        add("item.phantasm_ability_legacy.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/phantom_totem_legacy"))));

        add("item.phantasm_ability.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/phantom_totem"))));

        add("item.bloodhound_ability_legacy.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/bloodhound_ability_legacy"))));

        add("item.bloodhound_ability.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/bloodhound_ability"))));

        add("item.swiftslice_legacy.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/swiftslice_legacy"))));

        add("item.swiftslice.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/swiftslice"))));

        add("item.halloween_legacy_slice.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/halloween_slice_legacy"))));

        add("item.halloween_slice.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/halloween_slice"))));

        add("item.recharge.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.item.recharge")
                .with(sound(new ResourceLocation("valoria:item/abilities/recharge"))));

        add("item.blazecharge_legacy.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/blazecharge_legacy"))));

        add("item.blazecharge.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/blazecharge"))));

        add("item.eruption.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.ability.use")
                .with(sound(new ResourceLocation("valoria:item/abilities/eruption"))));

        add("block.void_stone.place", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.place")
                .with(sound(new ResourceLocation("valoria:block/void_stone/break1")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break2")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break3")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break4")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break5")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break6"))));

        add("block.void_stone.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/void_stone/break1")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break2")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break3")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break4")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break5")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/break6"))));

        add("block.void_stone.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/void_stone/step1")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/step2")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/step3")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/step4")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/step5")))
                .with(sound(new ResourceLocation("valoria:block/void_stone/step6"))));

        add("block.void_grass.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/void_grass/break1")))
                .with(sound(new ResourceLocation("valoria:block/void_grass/break2")))
                .with(sound(new ResourceLocation("valoria:block/void_grass/break3"))));

        add("block.void_grass.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/void_grass/step1")))
                .with(sound(new ResourceLocation("valoria:block/void_grass/step2")))
                .with(sound(new ResourceLocation("valoria:block/void_grass/step3")))
                .with(sound(new ResourceLocation("valoria:block/void_grass/step4")))
                .with(sound(new ResourceLocation("valoria:block/void_grass/step5")))
                .with(sound(new ResourceLocation("valoria:block/void_grass/step6"))));

        add("block.pot.break", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.break")
                .with(sound(new ResourceLocation("valoria:block/pot/shatter1")))
                .with(sound(new ResourceLocation("valoria:block/pot/shatter2")))
                .with(sound(new ResourceLocation("valoria:block/pot/shatter3")))
                .with(sound(new ResourceLocation("valoria:block/pot/shatter4")))
                .with(sound(new ResourceLocation("valoria:block/pot/shatter5")))
                .with(sound(new ResourceLocation("valoria:block/pot/shatter6"))));

        add("block.pot.step", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.footsteps")
                .with(sound(new ResourceLocation("valoria:block/pot/step1")))
                .with(sound(new ResourceLocation("valoria:block/pot/step2")))
                .with(sound(new ResourceLocation("valoria:block/pot/step3")))
                .with(sound(new ResourceLocation("valoria:block/pot/step4")))
                .with(sound(new ResourceLocation("valoria:block/pot/step5"))));

        add("block.pot.place", SoundDefinition.definition()
                .subtitle("subtitles.block.generic.place")
                .with(sound(new ResourceLocation("valoria:block/pot/place1")))
                .with(sound(new ResourceLocation("valoria:block/pot/place2")))
                .with(sound(new ResourceLocation("valoria:block/pot/place3")))
                .with(sound(new ResourceLocation("valoria:block/pot/place4"))));

        add("item.bag_open.use", SoundDefinition.definition()
                .subtitle("subtitles.valoria.lootbag.open")
                .with(sound(new ResourceLocation("valoria:item/bag/open1")))
                .with(sound(new ResourceLocation("valoria:item/bag/open2")))
                .with(sound(new ResourceLocation("valoria:item/bag/open3"))));

    }
}
