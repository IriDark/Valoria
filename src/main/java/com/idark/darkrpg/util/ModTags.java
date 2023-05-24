package com.idark.darkrpg.util;

import com.idark.darkrpg.DarkRPG;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class ModTags {

    public static class Items {

        public static final Tags.IOptionalNamedTag<Item> TRASH = createForgeTag("rarity/trash");
        public static final Tags.IOptionalNamedTag<Item> DEFAULT = createForgeTag("rarity/default");
        public static final Tags.IOptionalNamedTag<Item> COMMON = createForgeTag("rarity/common");
        public static final Tags.IOptionalNamedTag<Item> UNCOMMON = createForgeTag("rarity/uncommon");
        public static final Tags.IOptionalNamedTag<Item> RARE = createForgeTag("rarity/rare");
        public static final Tags.IOptionalNamedTag<Item> EPIC = createForgeTag("rarity/epic");
        public static final Tags.IOptionalNamedTag<Item> MYTHIC = createForgeTag("rarity/mythic");
        public static final Tags.IOptionalNamedTag<Item> MASTERY = createForgeTag("rarity/mastery");
        public static final Tags.IOptionalNamedTag<Item> LEGENDARY = createForgeTag("rarity/legendary");
        public static final Tags.IOptionalNamedTag<Item> RELIC = createForgeTag("rarity/relic");
        public static final Tags.IOptionalNamedTag<Item> ETERNAL = createForgeTag("rarity/eternal");

        private static Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(DarkRPG.MOD_ID, name));
        }

        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}