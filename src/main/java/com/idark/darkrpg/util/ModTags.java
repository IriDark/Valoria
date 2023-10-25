package com.idark.darkrpg.util;
import com.idark.darkrpg.DarkRPG;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModTags {
    private final static String MODID = DarkRPG.MOD_ID;
        public static final TagKey<Item> ALCOHOL = TagKey.create(Registries.ITEM, new ResourceLocation(MODID, "alcohol"));
        public static final TagKey<Item> RUM = TagKey.create(Registries.ITEM, new ResourceLocation(MODID, "rum"));
        public static final TagKey<Item> GEODES = TagKey.create(Registries.ITEM, new ResourceLocation(MODID, "geodes"));

        //public static final Tags.IOptionalNamedTag<Item> TRASH = createForgeTag("rarity/trash");
        //public static final Tags.IOptionalNamedTag<Item> DEFAULT = createForgeTag("rarity/default");
        //public static final Tags.IOptionalNamedTag<Item> COMMON = createForgeTag("rarity/common");
        //public static final Tags.IOptionalNamedTag<Item> UNCOMMON = createForgeTag("rarity/uncommon");
        //public static final Tags.IOptionalNamedTag<Item> RARE = createForgeTag("rarity/rare");
        //public static final Tags.IOptionalNamedTag<Item> EPIC = createForgeTag("rarity/epic");
        //public static final Tags.IOptionalNamedTag<Item> MYTHIC = createForgeTag("rarity/mythic");
        //public static final Tags.IOptionalNamedTag<Item> MASTERY = createForgeTag("rarity/mastery");
        //public static final Tags.IOptionalNamedTag<Item> LEGENDARY = createForgeTag("rarity/legendary");
        //public static final Tags.IOptionalNamedTag<Item> RELIC = createForgeTag("rarity/relic");
        //public static final Tags.IOptionalNamedTag<Item> ETERNAL = createForgeTag("rarity/eternal");

    private static TagKey<Item> bind(String pName) {
         return TagKey.create(Registries.ITEM, new ResourceLocation(pName));
    }

    public static TagKey<Item> create(final ResourceLocation name) {
        return TagKey.create(Registries.ITEM, name);
    }
}