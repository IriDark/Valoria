# 1.0.2.6

# **Added**

- Added Fortress Locator
- Added Draugr Spawnable with tag
- Added Goblin Spawnable with tag
- Added From Sarcophagus Spawnable with tag
- Added From Sarcophagus Halloween Spawnable with tag

# **Fixed**
- Fixed, Loot bags didn't disappear after use, causing duping of items
- Fixed Percent Armor display, added configs (Tridot)

# **Changes**

- Translation changes, now every new line will appear at the  end of the Lang file, those were added automatically and sorted alphabetically earlier 
- Difficulty Scaling: The Nihility system is now tied to World Difficulty
- Decreased Amber, Sapphire, Ruby ore gems drop count
- Reduced mod size ~37MB -> ~33MB
- Converted spawnables _(List of items Entity can spawn with)_ to Item Tags
    - ModPack creators can now decide with which items Entity can spawn with

# 1.0.2.5

# **Added**

- Added Netherite Necklace Wealth upgrading recipe
- Added Codex Progression config (Server // Per world)
    - Includes toggle and update interval
- Added Food Rot config (Server // Per world)
    - Includes toggle and rotting interval
- Added Nihility System config (Server // Per world)
    - Includes toggle, accumulation/decay rates, damage multiplier, and critical blindness effect
- Added Entity Weakness (Golems — Pickaxes, Ents — Axes)
- Added Extra attack for Ent
- Added Extra Additions and Mood sounds to the Valoria biomes
- Added Lunar Items
- Added Red Envelopes
- Added Firework Tube

# **Fixed**

- Fixed Scourge Walking animation Cycle
- Fixed Troll Walking Cycle
- Fixed Devil Walking Cycle
- Fixed Scavenger Walking Cycle
- Fixed Empty enchanted book dropping from Decorated Crypt Pot

# **Changes**

- Config changes
  - Now players (Server hosts) can decide if Nihility, foodRot is enabled in specific world
- Removed Boss checklist from codex (was an experiment)
- Changed Devil Walk animation
- Changed Ent Walk animation
- Tweaks to Scourge animation
- Some improvements to the Scavenger AI
- Some Pathfinding improvements to the Ent
- Wicked Scorpions will only Spit at players for optimization purposes
- Decreased Troll Damage 8 → 6
- Decreased Ent Damage 10 → 4
- Decreased Ent Health 75 → 50
- Decreased Elemental Golems damage 10 → 5
- Reduced Scourge Shadow Texture
- Reduced Troll Shadow Texture
- Enlarged Scavenger Shadow Texture
- Enlarged Wicked Scorpion Shadow Texture
- Enlarged Dryador Shadow Texture
- Changes to Elemental Golems texture

# 1.0.2.4

# **Added**

- Added Nature Golem (3 variants)
- Added River Golem
- Added Upgrade Sounds to Alchemy Stations

# **Fixed**

- Fixed Alchemy Station Upgrading (tier 2 → tier 3)
- Fixed Visual bug in Alchemy Station
- Fixed bug where Necromancer music disc ends withing 4.5 seconds
- Fixed Ent stuttering (Still should be reworked)
- Fixed Empty tag creation on item hover, preventing some items from stacking without hover
- Fixed Alchemy Station returning to the Inventory after upgrade (Duping)

# **Changes**

- Compressed all the sounds — Reduced mod size (**52MB -> ~34MB**)
- Decreased Scorpion Spit Damage

# 1.0.2.3

# **Requires Tridot 1.0.126**

## **CRITICAL PERFORMANCE UPDATE:**

- Fixed a major issue causing low FPS on integrated graphics (Intel/AMD APUs).
- Performance increased from ~30 FPS to ~200 FPS in reported cases.

# **Added**

- Added Alchemy Stations (Upgrade-able up to 4 tiers)
- Added Config value to disable Nihility
- Added Shade Blossom seeds
- Added Shade Blossom growing stages (0→4)
- Added Bookshelf books tag to Codex & Necromancer Grimoire

# **Translation**

- Translated to French
- Translated to Chinese
- Translated to Traditional Chinese

# **Fixed**

- Fixed Bombs reset their radius after rejoining the world
- Fixed Spears converting to Stone spear on broke
- Fixed Iron Chain repair with Gold
- Fixed Gloves Rendering
- Fixed Stone Crusher item updating
- Fixed Elemental Manipulator Visual bugs
- Fixed Devils tried to shoot at player even if not seen directly
- Fixed Piston Pushing causes Heavy Workbench to break its parts
- Fixed Server crashing on throwing Spectral blade
- Fixed Blaze Reap tooltip

# **Technical Changes**

- Reworked Heavy Workbench
- Converted all Spear to Builder (Technical change)
- Converted Stone Crusher Tool to data (Can be useful for pack creators)
- Removal of codex tooltip link

# **Changes**

- Gunpowder and Pyratite charges are stackable now
- Fallen Crown can be right click equipped
- Shade Blossom will drop petals on bonemeal
- Decreased Upgrading time in Elemental Manipulator
- Flesh cyst now has a limit of spawnable Sentinels
- Blazereap buff
- Improved description of the Ethereal Sword
- Food rot revamp

# Balance Changes

Early game:

```
Fallen Collector: 25% (No changes)
Marsh: 20% (No changes)
Black Gold: 20% (No changes)

```

Mid-game (Buffs):

```
⬆️ Cobalt: 25% ➔ 45% (+20%)
➖ Samurai: 35% (No changes)
⬆️ Ethereal: 30% ➔ 50% (+20%)

```

Late-game (Buffs):

```
⬆️ Nature: 40% ➔ 55% (+15%)
⬆️ Depth: 45% ➔ 60% (+15%)
⬆️ Infernal: 50% ➔ 65% (+15%)

```

End-game:
```
🔻 Spider: 55% ➔ 50% (-5%).
⬆️ Pyratite: 50% ➔ 62% (+12%)
⬆️ Crimtane: 60% ➔ 68% (+8%)
⬆️ Void (Awakened): 65% ➔ 70% (+5%)
⬆️ Phantasm: 70% ➔ 80% (+10%)

```
# 1.0.1

# Added

- Added Eye necklace
- Added Halloween Elixir
- Added Rogue Necklace
- Added neaw trades to Haunted Merchant
- Added Starter bundle

# Fixed

- Fixed minions causing triggering a lot of test attacks to nearby players
- Fixed Uncommon tooltips not using default Valoria style (Obscure tooltips)
- Fixed Client crushing on rendering Damage Numbers
- Fixed Heavy Workbench tooltip sizing
- Fixed Heavy Workbench tooltip material rendering
- Fixed Fire Immunity items protecting from lava (not intended behavior)
- Fixed Katana Piercing Damage
- Fixed Corpsecleaver ranged damage

# Changes

- Reworked Accessories
- Reworked Corpsecleaver ability
- Chains are wearables now
- Changed Samurai Belt crafting recipe
- Valoria portal recipe will result in 12 portal blocks for a while (it’s will be changed in the future)
- Gamerule valoria:foodRot was removed, changed into CommonConfig value

# Rebalanced

- Rabalanced Accessories (Rings, necklaces)
- Rebalanced Weapons
- Nerfed Iron Gloves
- Nerfed Golden Gloves
- Nerfed Diamond Gloves
- Nerfed Netherite Gloves
- Nerfed Magmatic Gauntlet
- Nerfed Skeletal Vambrace
- Nerfed Magmatic Vambrace

# 1.0

# Added

- Wicked Amethyst block uncrafting recipe
- Soul Shard block uncrafting recipe
- Uncharged Shard block uncrafting recipe
- Added Description to Gaib Roots
- Added Ephemarite, stone cutting recipe from Ephemarite Low
- Added extra Limestone, stone cutting recipes
- Added Unstable Core
- Added Page uncrafting recipe to Paper
- Added animations to Harmony Heart, Devil Heart, Medicated Harmony Heart, Medicated Devil Heart
- Added particles to Elemental Charm
- Added Tinting to Aloe
- Added growing small bushes of Aloe near Tall Aloe
- Added Obscure Tooltips Compat
- Added Spider Chitin
- Added Icy Scythe Skin
- Added Lotus Scythe Skin
- Added Boss Trophies
- Added Elemental Damage System
- Added Target Dummy Compat
- Added Devil Meat
- Added Stone Crusher JEI Description
- Added Stone Crusher Codex Page
- Added Magmatic Halls Dungeon
- Added Sapphire Geode
- Added Black Gold block
- Added Brick recipe from smelting Rune in Kiln
- Added King Crab
- Added Crab Shell
- Added Crab Claw
- Added Crawhook
- Added Crab Buckler
- Added Crab Legs
- Added Cooked Crab Legs
- Added Nature Crossbow
- Added Aquarius Crossbow
- Added Infernal Crossbow
- Added Void Crossbow
- Added Phantasm Crossbow
- Added Corrupted Well Structure (Built and suggested by Auriny)
- Added Flesh Altar Structure (Built and suggested by Auriny)
- Added Nihility Meter
- Added Nihility Monitor
- Added Void Crystal Talisman
- Added Draugr Shield
- Added Soul Infuser
- Added Vanilla changes config cause of changing vanilla shield behaviour
- Added Rot
    - Non-valoria food will rot on dimension changing to Valoria.
- Added Cleansing Vial
- Added Cleansing Flask
- Added Cleansing Elixir
- Added Healing Vial
- Added Healing Flask
- Added Healing Elixir
- Added Hard Workbench
- Added Fallen Collector Armor (Was a skin previously)
- Added Cobalt Multi Tool
- Added Ethereal Multi Tool
- Added Crimtane Multi Tool
- Added Jade Multi Tool
- Added Nature Multi Tool
- Added Depth Multi Tool
- Added Infernal Multi Tool
- Added Void Multi Tool
- Added Double Void Vine
- Added Texture Variants for some Blocks and Plants
- Added Extra generation features
- Added tags

  #valoria:spears
  #valoria:katanas
  #valoria:throwables
  #valoria:scythes
  #valoria:rapiers
  #valoria:multi_tools

- Added new 2 item tabs: Valoria Tools, Valoria - Consumables
- Added new Gamerule `valoria:foodRot`, default: true — non-valoria dimension food will rot on teleporting to Valoria

# Fixed

- Fixed Arrow desync
- Fixed duping of resources using minions
- Fixed BlazeReap
- Fixed Enchantments Compatibility
- Fixed an issue where weapon abilities could harm players in PvE areas
- Fixed Magma Sword charging
- Crimtane Armor can be trimmed now
- Fixed Keg taking out 2 items from player
- Fixed ItemBorders crashing
- Fixed Hit Effect Applying
- Fixed Soul Collector
- Fixed Drinks Saturation
- Fixed Cup and Bottle Waterlog
- Fixed Potted Dreadwood sapling model
- Fixed Codex Inversed Drag
- Fixed Fire inflicting accessories can set on fire mobs using projectiles
- Fixed Idle Animations of Entities
- Fixed Void Grass dropping Void Stone on breaking with Silk touch
- Fixed Manipulator Random Loss of Cores on reload
- Fixed Bugged Rune of Curses description
- Fixed Pearlium Tags
- Fixed Reinforced Meat Pillar Tags
- Fixed Cysts trying to spawn entities is Peaceful
- Fixed Void Sand (Partially)
- Fixed Crash in Codex when pressing Down Arrow or Left Arrow keys

# Changes

- Reworked Armor Effects
- Reworked Shadewood Branch
- Inversed mouse scrolling in Codex
- Black Gold recipe is now shapeless
- Medicated Devil Heart recipe is now shapeless
- Medicated Harmony Heart recipe is now shapeless
- Magmatic Gauntlet recipe is now shapeless
- Magmatic Vambrace recipe is now shapeless
- Removed Armor breaking of effect applying
- Resprited Aloe, Small Aloe
- Resprited Void Brick
- Resprited Cracked Void Brick
- Resprited Chiseled Void Brick
- Resprited Chiseled Void Bricks
- Changed Rarity of Spider items
- Spider Armor is now crafted from Spider Chitin
- Changed Valoria Portal Frame Model
- Sorted Block Item Tab
- Added Fire Texture back on Infernal Ingot (was asked by a community to revert this change)
- Changes Shield rotations
- Reworked Shields
- Changed Structure chances, more frequent small structures
- Reduced Cyst particles
- Changed Void Sand Update Delay
- Tinkerer’s table is hidden now (will be deleted or reworked)
- Changed Samurai Armor model
- Changed Phantasm Armor model
- Reworked Item Descriptions
- Reworked Bossbars

# Rebalanced

- Rebalanced Food
- Rebalanced Flesh Sentinel loot
- Rebalanced Corrupted loot
- Rebalanced Fortress loot
- Rebalanced Pot LootTable
- Rebalanced Long Pot LootTable (buff)
- Rebalanced CryptPot LootTable (buff)
- Rebalanced Ephemarite LootTable (buff)
- Rebalanced Ephemarite Low LootTable (buff)
- Rebalanced Dirt Geode LootTable (buff)
- Rebalanced Stone Geode LootTable (buff)
- Rebalanced Crypt Archaeology LootTable (buff)
- Nerfed Rune Amount in LootTables
- Nerfed Renewal
- Nerfed Exhaustion
- Nerfed Wicked Crystal Minions damage — 8 → 6
- Nerfed Wicked Crystal Minions projectile velocity — 1.85 → 1.25
- Crystallized deep ruins more frequent now
- Decreased Aloe generation

# 0.9.9

# Added

- Added Warden Skin for Scythe

# Fixed

- Prevent multiple bosses from being summoned at the same altar (nearby)
- Fixed Crash on mouse scroll in Book gui

# Changes

- Rebalanced Experience reward from smelting bricks
- Rebalanced Nature gift dropping from Dryador treasure bag
- Rebalanced Ent LootTable
- Rebalanced Devil LootTable

# 0.9.8

# Fixed

- Fixed server crashing

# 0.9.7

# Added

- Added Translation keys to Dreadwood
- Added Description to Bandages
- Added Pixie summon book to Dryador Treasure Bag
- Added Ability to Phantasm Bow
- Added Update Checker
- Added Page opening through item in Codex

# Fixed

- Fixed Dreadwood Hanging sign
- Fixed bug when Bleeding damaged the player ignoring immunity
- Fixed Arrows hit sync
- Fixed Arrows causing massive lags underwater / on bubble columns
- Fixed Bug when opening Keg Screen, block / item was used.

# Changes

- Reworked Item Tiers
- Jade ore, Picrite Jade ore and Pyratite ore can be gathered with cobalt+ tier
- Reworked Soul Collector a bit
- Halloween Items now receiving extra souls on kill
- Changed Corrupted Particles
- Crimtane Armor now applies Exhaustion to target with 25% chance
- Rebalanced Void Scythe
- Improved most of tooltips
- Improved homing movement in Soul Arrow
- Cobalt spawns in size up to 4 blocks
- Codex changes and improvements

# 0.9.6

# Fixed

- Fixed Server Crashing

# 0.9.5

# Added

- Added Corrupted
- Added Flesh particles
- Added Dreadwood set
- Added Pull Towards Attack for Wicked Crystal

# Fixed

- Fixed Air dashing using Scythes
- Fixed Gaib Roots Generation
- Fixed Aloe Generation
- Fixed Dryador Animations

# Changes

- Wicked Crystal changes:
    - Lifetime of Crystal minions was changed from 325 → 375
    - Radial Attack distance was decreased to 6
    - Radial Attack Crystal count was changed from 10 → 12
    - Crystal Storm Attack spread was changed from 4 → 6
    - Crystal Storm Attack speed was changed from 0.4 → 0.3
    - Crystal Rain Attack count was changed from 32 → 45
    - Crystal Rain Attack spawn height was changed from 4 → 2

# 0.9.2

# Added

- Added new page to Codex describing receiving of new pages
- Added description to Spectral Blade
- Added description to Corpsecleaver
- Added description to Shurikens
- Added description to Coral Reef
- Added description to Beast Scythe
- Added description to Pyratite Spear
- Added description to Pyratite Armor
- Added new painting called Mountain Landscape

# Fixed

- Fixes incorrect mood sound id in biomes
- Fixed Soul Collector disappearing on full inventory
- Fixed Toast centering
- Fixed Wicked Crystal Phase transition
- Fixed Valoria Teleporter position

# Changes

- Page toast are being updated now
- Reworked Page toast
- Changed Skin trims and Skin fragments description
- Follow range of Shadewood spiders was decreased from 20 to 18

# 0.9.1

# Added

- Added Pyratite ore Smelting recipe
- Added Pyratite ore Blasting recipe
- Added Jade ore Smelting recipe
- Added Jade ore Blasting recipe
- Added Picrite Jade ore Smelting recipe
- Added Picrite Jade ore Blasting recipe
- Added Dormant Crystals Smelting recipe
- Added Dormant Crystals Blasting recipe
- Added Extra hints to Codex
- Added Bloodsight Monocle repairing recipe
- Added Arrow dispensing behaviour
- Added Unlocked page tooltip
- Added Crypt map dropping from Sorcerers at night
- Added Experience dropping from Bosses
- Added Idle sounds to Troll
- Added Death sounds to Troll
- Added Jade Armor trim material
- Added Pyratite Armor trim material
- Added Crimtane Armor trim material
- Added Ethereal Armor trim material
- Added Black Gold Armor to Trimmable Armors

# Fixed

- Fixes in Russian language suggested by Auriny
- Fixed Crit percent display in non-English locales
- Fixed Player had a way to equip lesser and default rune of the same type
- Fixed Player was able to put a whole stack into Pedestals
- Fixed Tridot Loot Conditions preventing events from starting
- Fixed Devil spawning on Nether Wart
- Fixed Output duping in Keg

# Changes

- Rebalanced Samurai Belt
- Rebalanced Talismans
- Reworked runes
- Rare items such as Hearts or Elemental crystal has it’s own loot pool now
- Rebalanced Ent and Devil LootTable, chances are much higher now (again)
- Inversed mouse scrolling in the Codex
- Reworked Wicked Crystal LootTable, divided some items to sequence of pools
- Reworked Dryador LootTable, divided some items to sequence of pools
- Reworked Necromancer LootTable, divided some items to sequence of pools
- Reworked Corrupted Troll LootTable, much higher chance to get a Void key
- Reworked Scourge LootTable, much higher chance to get a Oceanic Shell
- Reworked Swamp Wanderer LootTable, much higher chance to get a Oceanic Shell
- Rare items including Hearts, Elemental crystal and that sorta stuff applies to looting chance modifier
- Reworked LootTables
- Removed shadow from Wicked Crystal (Suggested by Auriny)
- Reworked Troll sounds

# 0.9

# Added

- Added Crimtane Armor Recipe

# Fixed

- Elemental arrows were unable to use
- Nature Arrow recipe has been fixed
- Russian translate changes suggester by Auriny
- Crash while loading the game in big modpacks

# Changes

- Arrows crafting recipe has been changed and balanced
- Glaive recipe has been balanced
- Rebalanced Cobalt
- Ents are neutral mobs now

# 0.8

# Added

- Added new Trades for Villagers
- Added Crimtane
- Added Screenshake to Throwable bombs
- Added Uncrafting recipe of Ancient Ingot
- Added Samurai Kunai Recipe
- Added Glaive Recipe
- Added Shurikens
- Added Progression tracking
- Added Jade Recipes
- Added `*SHOW_TOASTS` to client config*
- Added Recipes to Arrows
- Added Extra items to Wicked Crystal LootTable
- Added Elemental Arrows
- Added Elemental Spears
- Added Crypt Archaeology LootTable

# Fixed

- Fixed: Gaib Roots now check both of blocks to spawn
- Fixed: Bombs now discards when hits Entities
- Fixed Screenshake after explosions
- Fixed Glaive tooltip, glaive cant be thrown!
- Fixed Spectral Blade stucking in new chunks
- Fixed Bronze Items now repairs with Bronze Ingot
- Fixed Goblin Item in Hand offset
- Fixed Goblin Idle Animation
- Fixed Rendering issues in Jewelry bag
- Fixed Lags caused by falling sand in Valoria
- Fixed Bug where all crystal shards were consumed on thrown
- Fixed Jade Ore
- Fixed Various crashes

# Changes

- Reduced spawning chance of Gaib Roots
- Changed Rarity of Accessories
- Resprited Elemental Cores
- Resprited Elemental Ingots
- Renamed Archaeology Table to Tinkerers Workbench
- Changed recipe of Silken Kunai
- Rebalanced Kunai’s
- Kunai’s are stackable now
- Changed Trail for Soul Arrow
- Gunpowder charges crafted changed: 2 → 4
- Reworked Lexicon → Codex
- Resprited Ancient Shard
- Resprited Ancient Ingot
- Resprited Jade
- Changed LootTables
- Bow of Darkness now shoot Wicked Arrows

# v0.7

# Translations

- Polish Translation
- Ukrainian Translation
- Improved Russian Translation

# Fixed

- Fixed Cryptic Altar Rendering
- Fixed `LootItem`, now depends on Player Luck
- Fixed bug when Cryptic Altar consumes a whole stack
- Fixed Treasure bag spawning position
- Fixed Necromancer’s Movement
- Fixed Necromancer Crypt Pools
- Fixed Infernal Items Sorting in Creative Tab
- Fixed Cryptic altar is unbreakable now
- Fixed Phantom cooldown
- Fixed Bossbar rendering
- Fixed Cobalt LootTable
- Fixed Black Gold Ingot Recipe
- Fixed Jeweler Translate
- Fixed Zombie Villager Jeweler
- Fixed Crashing when using an item with a Stun effect
- Fixed Crafting stations drops
- Fixed Stun
- Fixed Necromancer Music Disc
- Fixed Ephemarite LootTables
- Fixed Void taint tinting model
- Fixed Deepslate cobalt ore drops Sapphire ore
- Fixed Crash on using Jewelry Bag
- Fixed Armor model rendering with custom model skins
- Fixed Blood Veins
- Fixed Rune of Accuracy
- Fixed Devil Spawn
- Fixed Shadewood Spiders Spawn

# Gamerules

- Added `valoria:disableBossDungeonGriefing` - disables block breaking / placing, in boss dungeons
- Added `valoria:trapActivating` - disables trap activating

# Added

- JEI Compat Changes
- Jer Compat
- Added Ethereal Items Recipes
- Added Crystal Blocks
- Added Wicked Crystal (Boss)
- Added Crystals
- Added Ent
- Added Pot generation chance configuration
- Added Elemental Crystal
- Added Elemental Manipulator Recipe
- Added Armor Template (For elemental progression)
- Added Leather Belt Recipe
- Added Amethyst Gem Recipe
- Added Kiln
- Added Ancient Stone set
- Added Ancient Geode
- Added Lesser Runes
- Added Crypt Map Trading (Tier 2)
- Added Necromancer Crypt Map Trading (Tier 3)
- Added Shadewood tree Root’s Generation
- Added Eldritch tree Root’s Generation
- Added Void Shore BIome
- Added Void Peaks Biome
- Added Void Hills Biome
- Added Void Desert Biome
- Added River Biome
- Added Void Sand
    - Added Void Sandstone
    - Added Void Cut Sandstone
    - Added Void Chiseled Sandstone
    - Added Smooth Void Sandstone
- Added Pyratite Crystal Generation
- Added Sounds to Sorcerer
- Added Runic Dust
- Added Runes Recipes
- Added JEI informations
- Added Scourge
- Added Exhaustion Effect
- Added Renewal Effect
- Added Maggots
- Added Zombie transformation to Swamp Wanderer in Swamps
- Added Ethereal Armor
- Added Marsh Armor
- Added Marsh Cloth
- Added KubeJS Compat
- Added Boss Configs
- Added Nuggets (Bronze, Cobalt, Black Gold)
- Added Devil’s Heart
- Added Harmony Heart
- Added Bandage
- Added Medicated Devil Heart
- Added Medicated Harmony Heart
- Added Elemental Charm
- Added Skeletal Vambrace
- Added Magmatic Vambrace
- Added Magmatic Gauntlet
- Added Cobbled Shale Bricks
- Added Cobbled Shale Spikes Trap
- Added Cobbled Shale Firecharge Trap
- Added Wicked Fortress
- Added extra durability to Accessories
- Added Tooltips to Pickaxes (Proposed by players)
- Added Iron Ring Amber Recipe
- Added Pyratite Arrow
- Added Pyratite Armor
- Added Custom model to Infernal, Void, Phantasm Armors
- Added Offsets to Bottles and Cups
- Added Offsets to Pots
- Added Dryador (Boss)
- Added Pixie
- Added Pyratite Crystal gen
- Added Gaib Roots Gen
- Added Sounds to Devils
- Added Crown of Harmony
- Added Descriptions to some of items

# Removal

- Removed Drink related achievements
- Removed Spider Eggs

# Changes

- Changed Boss Music to Music category from Hostile
- Changed Dungeon Music to Music category from Ambient
- Necromancer Treasure bag now has 2 rolls ( <3 Auriny and your Luck )
- Necromancer now hurts player depending on health rather than static 15
- Changed Cobalt Ore, generated count 2 → 8
- Nerfed Necromancer Crypt LootTable:
    - Runes generated from (min: 2, max: 6) → (min: 0, max: 3);
    - Raw Cobalt generated from (min: 1, max: 6) → (min: 1, max: 4);
    - Removed Trash
- Removed Trash from Crypt LootTable
- Removed Fall damage from Devils
- Removed Lexicon Trading from Cartographer
- Moved Percent Armor Bar
- Necromancer no longer check for the Day condition
- Undeads now ignore damage from Necromancer
- Nerfed Silken Wakizashi - 3 Dash Dist. → 1.5 Dash Dist.
- Nerfed Necromancer
- Reduced Goblin spawn chance
- Optimized Sorcerer Spell
- Reduced Pot spawn chance
- Buffed Runes
- Switched to Overwold Biome Generation
- Rebalanced  Villager Trades
- Rebalanced  Armors
- Changed sounds
- Count of amber ore changed from 5 to 3
- Count of sapphire ore changed from 4 to 3
- Size of ruby ore changed from 2 to 4
- Goblins now drop Bronze nuggets, ingot will be a rare drop
- Traps can be activated be an Item
- Elemental Manipulator saves item enchantments
- Changed Sorcerer Spell Color to Brown
- Buffed Sorcerer
- Changed crafting time of Jewelry Recipes
- Reworked Percent Armor
- Reworked Tipsy effect
- Reworked Pyratite Spear
- Reworked Manipulator
- Nerfed Bleeding a bit
- Bleeding enchantment now compatible with Sharpness
- Rebalanced Items
- Bonemeal now can be used on Aloe and Gaib Roots

# 0.6.4

# Fixed

- Fixed missing particles in Abyssal Glowfern
- Fixed mischeck of large model in Wraith katana and Wakizashi, which caused missing model
- Fixed missing sounds
- Fixed crashing issues
- Fixed server crash when using Beast Scythe
- Fixed render types in bottles and cups
- Fixed Elemental Manipulator particles
- Fixed miss-texture of Polished Deep Marble
- Fixed Corpsecleaver healing
- Fixed bug where some of the Necromancer's spells were interruptible
- FIxed Rendering of Blazereap Overlay
- Fixed `Unknown recipe category: [!!!com.mojang.logging.LogUtils$1ToString@aa0770f=>java.lang.NullPointerException`
- Fixed and Optimized Bomb’s
- Fixed model mismatch in Mossy pots
- Fixed a bug where katanas without a large model couldn't receive skins
- Fixed https://github.com/IriDark/Valoria/issues/16
- Fixed https://github.com/IriDark/Valoria/issues/19
- Fixed https://github.com/IriDark/Valoria/issues/20
- Fixed https://github.com/IriDark/Valoria/issues/21
- Fixed https://github.com/IriDark/Valoria/issues/22
- Fixed https://github.com/IriDark/Valoria/issues/23
- Fixed https://github.com/IriDark/Valoria/issues/25
- Fixed https://github.com/IriDark/Valoria/issues/27
- Fixed https://github.com/IriDark/Valoria/issues/28
- Fixed https://github.com/IriDark/Valoria/issues/29
- Fixed Phantom ability
- Fixed Projectiles
- Fixed Crypt generation
- Fixed `CrushableBlockEntit` non-Thread safe crash
- Fixed FireTrap Particles
- Fixed Crashing when equipping non-armor items with some of Valoria Armor
    - `Caused by: java.lang.ClassCastException: class net.minecraft.world.item.StandingAndWallBlockItem cannot be cast to class net.minecraft.world.item.ArmorItem (net.minecraft.world.item.StandingAndWallBlockItem and net.minecraft.world.item.ArmorItem are in module`
- Fixed Armor Trims

# Added

- Added Day & Night Cycle to Valoria
- Added Infernal Spear
- Added Jeweler Profession
- Added Loop Sound for Elemental Manipulator
- Added Meat content that includes:
    - New Mob - Flesh Sentinel
    - New Item
    - New Block types
    - New Biome
    - New Cave biome
- Added Flowers to `replaceables` tags
- Added `Shadewood Branch` Generation
- Added leaf vine decoration to `Fancy Shadewood Tree`
- Added Glow layer for Dormant Crystals
- Added Bag opening sound
- Added Corrupted Troll
- Added Bossbar to Necromancer
- Added Biome and Panorama Music
- Added Spider Armor
- Added Dash Enchantment
- Added Overdrive Enchantment
- Added Necromancer Treasure Bag
- Added Functionality to Archaeology Table
- Added Relic Gold
- Added Empty Totem | Winglet | Gazer
- Added Skin fragments
- Added Ancient Shard
- Added Samurai Belt
- Added Succubus spawn
- Added Bronze Item tier
- Added Necromancer’s Crypt
- Added Necromancer Summonable - Necromancer’s Grimoire
- Added Cryptic Altar
- Added Ethereal Items (Pre-nether items dropped from Necromancer boss)
- Added Sorcerer

# Changed

- Increased spawn rate of Shadewood Spiders
- Replaced Skeletons with Trolls in Shade Forest
- Tagged all the biomes
- Updated some structures and added processors to them
- Balance changes
- Changed foliage color in Shade Forest
- Reworked Loot Dropping items
- Reworked Succubus and renamed to Devil
- Plants are glowing brighter now
- Traps cant be activated on Peaceful difficulty (QoL)

# Resprited

- Resprited Corpsecleaver
- Resprited Meat Block
- Resprited Eye Meat

## Technical changes

- Updated JEI to **15.20.0.105**
- Lang cleanup | Improved some langs
- Optimized some Loottables
- Reworked Registration of Blocks

# 0.6.2 - 0.6.3

## New Features and Additions

- Added Trolls and Succubus mobs
- Introduced Suspicious Ice and Suspicious Iceberg generation
- Added Experience drop from Mobs
- Implemented Epic Fight compatibility (to be released as datapack)
- Added new items: Crypt Lantern, Sake, Halloween Items
- Introduced Pyratite block and Crystal
- Added functionality to Soul Collector
- Implemented Custom Panorama
- Added GUI and sounds to Kegs
- Expanded Valoria Dimension with more biomes
- Introduced Elemental Smithing Templates
- Added shader rendering to Valoria Portal
- Implemented new skins

## Fixes and Optimizations

- Fixed various model loading issues for Prospector's Pick
- Resolved tag loading problems for impact projectiles and smoke particles
- Fixed Cattail and Pot generation
- Addressed server crashing issues
- Fixed Goblin animations and Pot item models
- Resolved smoke and Necromancer particles
- Fixed bug with coffee stacking
- Corrected Murasama's "dash_distance" attribute usage
- Fixed Undead moving AI
- Resolved Loyalty enchantment issues on Spears
- Fixed Glover rendering and Valoria Portal Frames
- Addressed Piercing and Spears rotations
- Fixed projectile damage through FTB Chunks privates
- Corrected Rune of Cold functionality
- Fixed server crash when putting a Shard into a Void Pillar
- Adjusted small offset of placed items on Jewelry table

## Balance Changes

- Nerfed Runes
- Buffed Silken Kunai
- Decreased cooldown time for Scythes
- Reduced Goblin bone drop rate
- Lowered Gunpowder droprate from Ephemarite crushing
- Decreased Geode droprate from Ephemarite mining
- Buffed Katanas
- Adjusted Tipsy effect balance

## Reworks and Changes

- Reworked Goblins, Scythes, Katanas, Murasama
- Overhauled Overlay Rendering and items
- Reworked Bleeding FX, Kegs, Projectiles
- Changed Necromancer Fangs to Devourer
- Reworked Bows and Elemental Progression
- Changed Item Tabs Sorting
- Made drinks placeable and stackable up to 64
- Animated Goblins
- Resprited all drinks, Cobalt Ingot, and Bow of Darkness

## Removals

- Deleted Geodites (drops moved to ephemarite)
- Removed Void Crystal
- Deleted unused "needs_iron_tool" tag

## Technical Updates

- Updated to Forge 47.2.20
- Valoria now requires Fluffy Fur
- Optimized Katanas and Murasama
- Added Silk touch drops to Crystals
- Implemented Goblin Model Configuration
- Added "in-hand" Models to Katanas (Contributed by Kerdo)
- Added Crypt Pots (Contributed by Kerdo)
- Added Glow Layer to some Blocks and Items
- Katanas can't be used in offhand now

# 0.6.1

## New Features and Additions

- Now Valoria requires Lodestone
- Added Custom Voxel shapes to some blocks
- Added Firefly-like particles, spawned in Shadeforest
- Added Particles for Bandages
- Added Shade Blossom
- Added Shade Blossom Bandage
- Added Pots Generation
- Added some sort of Necromancy

## Reworks and Changes

- Various playability changes
- Reworked Stone Crusher (A bit)
- Reworked Accessories
- Reworked BlazeReap
- Reworked Bandages
- Decreased amount of bones dropped from Goblins
- Nerfed Tipsy Effect:
    - Armor: 35% → 10%
    - Damage: 45% → 15%
- ID change: "pick" → "prospectors_pick"
- Changed spawnables in Valoria dimension

## Visual Updates

- Resprited Gazers, totems, winglets
- Resprited Bandages
- Resprited Effects

## Bug Fixes

- Fixed Katanas and Scythes
- Fixed Scythe Attributes
- Fixed infinite use of Katana
- Fixed Gloves rendering
- Fixed Void armor craft recipe

## Balance Changes

- Changed incompatibility of "Bleeding" enchantment from "Sweeping Edge" to "Sharpness"

## Optimizations

- Optimized Gloves code

## Miscellaneous

- Added some Recipes
- Updated mods.toml

# 0.6

## New Features and Additions

- Added functionality for ALL runes
- Added Jade compatibility
- Added compatibility for Enchantment Descriptions
- Added 2 attributes to Prospector's Pick
- Added a new painting: "The Starry Night" by Vincent Van Gogh
- Added entities to vanilla tags
- Added cooldown for Phantom
- Added ColorMapping for plants
- Added Flammable property to wooden blocks
- Added ores to Forge tags
- Added Shadewood branch
- Added Void Taint
- Added The Valoria Dimension with numerous new features:
    - New flora: Violet Sprout, Glow Violet Sprout, Glowfern, Voidvine
    - New fauna: Void Serpents
    - New blocks: Eye Stone, Eye Meat, Voidtaint Lantern, Abyssal Lantern, Deep Marble
    - New items: Eye Chunk, Tainted Roots, Tainted Berries
    - New structures: Wicked Amethyst Gen, Dormant Crystals Gen, Eldritch tree
    - New biomes: Shadewood forest, Void Barren, Ecotone
- Added Fancy Shadewood Tree
- Added Pearlium generation
- Added Crystal disk generation
- Added modded stone variants to Stone Tool Materials tag
- Added animation to some blocks
- Added mod armor to all tags (armors are now trimmable)
- Added armor trim materials
- Added bow zooming functionality
- Added Vanishable property to most items
- Added Ukrainian language support
- Reintroduced and reworked Swamp Wanderer
- Added Shadewood Boat and Chest Boat
- Added Elemental Manipulator crafts
- Added new commands: "addAllPages" and "removeAllPages"
- Added Necromancer and Undead
- Added description to scythes
- Added particles to Manipulator
- Added Valoria ingots to Forge tag #ingots
- Added Shadewood Fence and Fence Gate
- Added Quark compatibility
- Added building blocks for Ephemarite (slabs, stairs, walls)
- Added Void traps
- Added custom arrows and bows
- Added Umbral Activator block
- Added Keg crafting recipe
- Added sword actions to Katanas
- Added ambient particles to some items

## Reworks and Changes

- Reworked Keg
- Reworked Umbral Blocks (formerly Keyblocks)
- Updated Curio slot registry to newer JSON-based system
- Reworked Curio items
- Reworked Lexicon
- Rebalanced mod
- Reworked Shadewood tree
- Reworked Void Grass
- Improved Goblin AI and model (now Humanoid, can hold items)
- Reworked Kunais
- Improved Item2DRenderer for better mod compatibility
- Reworked Sarcophagus
- Reworked Mannequin
- Reworked Accessories
- Reworked Spears

## Visual Updates

- Resprited: Void stone, Uncharged Shard, Wicked Amethyst (block and item), Soul shard, Cattail, Bloodhound, Murasama, Yamato, Shadewood Planks and related items, Katanas, Scythes

## Renamed Items

- Uncharged Stone → Uncharged Shard
- Soulstone → Soul shard
- Amethyst (mod version) → Wicked Amethyst
- Keyblocks → Umbral Blocks

## Gameplay Changes

- Prospector's Pick now breaks ICE blocks much faster
- Server-sided most particles
- Golden accessories now make Piglins neutral
- Accessories will be damaged when player is attacked
- Goblins can now spawn with and take weapons in hand
- Necklaces will change size depending on armor

## Bug Fixes

- Fixed various misspellings in sound tooltips and JEI
- Corrected "Coffe" to "Coffee"
- Fixed 1 pixel Y offset in JEI
- Fixed Plants LootTable
- Fixed Baby Goblins
- Fixed bug where Goblins tried to attack creative players when hurt
- Fixed loud sound bug with multiple items in cooldown
- Fixed necklaces size relative to player's second layer
- Fixed entity attributes
- Fixed crash related to HitResult.getType() MISS (Blazereap)
- Fixed server errors in KatanaItem and CorpsecleaverItem
- Fixed "/valoria addPage/removePage" command server error
- Fixed damage from Scythes/Katanas bypassing armor and FTB Chunks
- Corrected Bloodhound icon 1 pixel offset in GUI
- Fixed Mannequin visual bugs
- Fixed second GUI slot in JEI recipes
- Fixed Katana dash damaging through walls
- Fixed Katanas for double-clicking mice
- Corrected flower pot offsets

## Balance Changes

- Changed Stonecrusher recipe
- Adjusted Eternity opacity after Phantom ability
- Changed attributes maximum value for vanilla armor
- Modified Aloe Bandage animations
- Adjusted hitboxes for all modded entities (vanilla parity)
- Increased chance of receiving a charge when hitting an enemy with Infernal Sword (5% → 10%)
- Increased chance of stunning the target when hitting with Club (5% → 7%)
- Changed Corpsecleaver and HoundItem (Bloodhound sword) to SwordItem class
- Updated ability sounds
- Converted time in JEI recipes to seconds
- Adjusted Block Tab sorting
- Modified Crypt LootTable
- Decreased Limestone spawn chance
- Reduced particle count for Mannequin interactions

## Technical Updates

- Updated Forge version to 1.20.1-47.2.16
- Optimized: Quicksand Fog Rendering, CurioItemProperty, Infernal Sword, Scythe Items, Lexicon Page Item, Mannequin, Weapons abilities

## Miscellaneous

- Added modded stone variants to Stone Tool Materials tag
- Added various items to Forge tags

# 0.5.2.1

- Added Functional to Bloodsight monocle
- Fixed Ephemarite boulders gen
- Fixed Radius enchantment
- Added tetra compat
- Balance changes
- Fixed Wine translate
- Changed Ephemarite boulders gen
- Changed Crypts gen

# 0.5.2

- Added Just Enough Effect Descriptions (JEED) Compat
- Fixed Effect coloring
- Changed Tipsy effect bonuses
- Alcohol now adds nausea to player
- Added Mead Bottle to tags - Alcohol, Bottle Drinks
- Removed Kvass from tag - Alcohol
- Resprited & Changed model of Elegant Pedestal
- Added Elegant Pedestal Recipe
- Redone Drinks Registers
- Added drinks a potion like tooltips
- Balance changes
- Changed Creative Tab Textures
- Changed Jeweler Table recipe (a little)
- Deleted Skully Pedestal (cuz its cringe tbh, most likely will be replaced with something nicer)
- Updated RU lang
- Updated ENG lang
- Fixed Jeweler table recipes JEI compat
- Added Animated progress arrow in jei
- Changed Voxel shape of Pedestal
- Fixed shadewood hanging sign
- Added most of blocks to vanilla tags
- Changed Hanging Shadewood sign texture
- Added Hanging Shadewood sign recipe
- Changed Decorated Bronze lamp model
- Changed Tomb model
- Changed Tomb texture
- Fixed Waterlogged state for Tomb
- Sorted JEI by Time of crafting
- Added New Blocks
- Fixed misspell: Vine -> Wine
- Fixed Overlay render layers
- Mod Blocks now work with Beacon
- Added new dungeon to oceans (A crystallized deep ruin that have drowned spawner inside but it cannot spawn with trident in hand)
- Changed Manipulator model & texture & Voxelshape
- Added Manipulator gui
- Redone Stone Crusher so its now json powered
- Did a smelting recipes for raw cobalt
- Pickaxe used in Crusher now will be damaged by 5 units
- Fixed a bug that item can disappear in pedestals, keg & crusher when inventory is full
- Added ephemarite gen (stone that can be crashed in crusher to get a rune with a chance)
- Decreased goblin spawn chance
- Keg now returns empty bottle if honey bottle is used, to take back honey from a keg you'll need the glass bottle
- Fixed dupe when keg wtih a drink's inside was broken it will drop
  these drink's so you'll dont need to collect these items with specific
  item
- Resprited armor models

# 0.5.1

- Fixed Server Crashing
- Added JEI compat
- Added Jewelry Recipes
- Fixed Keg Brewing Recipes
- Fixed Stonecrusher Item Rendering
- Added Recipes for most of Blocks
- Added Stonecutting recipes
- Resprited some of items
- Marked Lexicon for REDO (very wip thingie btw, all pages will be open until redo)

And more!