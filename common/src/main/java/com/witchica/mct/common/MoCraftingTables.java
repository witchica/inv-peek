package com.witchica.mct.common;

import com.witchica.mct.common.block.CraftingBenchBlock;
import com.witchica.mct.common.block.entity.CraftingBenchBlockEntity;
import com.witchica.mct.common.menu.CraftingBenchMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class MoCraftingTables {
    public static final String MOD_ID = "mo_crafting_tables";
    public static final String[] WOOD_TYPES = new String[] {
        "spruce",
        "oak",
        "crimson",
        "birch",
        "dark_oak",
        "bamboo",
        "acacia",
        "mangrove",
        "warped",
        "cherry",
        "jungle"
    };

    public static MenuType<CraftingBenchMenu> PERSISTENT_CRAFTING_TABLE_MENU_TYPE;

    public static ResourceLocation makeResourceLocation(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static final Supplier<Block> PERSISTENT_CRAFTING_TABLE_BLOCK_SUPPLIER = () -> new CraftingBenchBlock(BlockBehaviour.Properties.of());

    /*
        Resource Locations
     */
    public static final ResourceLocation CRAFTING_TABLE_BLOCK_RESOURCE_LOCATION = makeResourceLocation("crafting_table");
    public static final ResourceLocation CRAFTING_TABLE_SLAB_RESOURCE_LOCATION = makeResourceLocation("crafting_table_slab");
    public static final ResourceLocation GENERIC_RESOURCE_LOCATION = makeResourceLocation("persistent_crafting_table");

    /*
        Block + Item stores
     */
    public static Block CRAFTING_TABLE_BLOCK;
    public static BlockEntityType<CraftingBenchBlockEntity> GENERIC_BLOCK_ENTITY_TYPE;

    public static void onInitialize() {

    }

    public static Collection<ItemStack> getCreativeModeTabItems() {
        List<ItemStack> items = new ArrayList<ItemStack>();

        items.add(new ItemStack(CRAFTING_TABLE_BLOCK, 1));

        return items;
    }
}
