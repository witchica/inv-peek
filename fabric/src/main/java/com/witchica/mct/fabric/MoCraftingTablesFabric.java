package com.witchica.mct.fabric;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.block.CraftingBenchBlock;
import com.witchica.mct.common.block.CraftingTableSlabBlock;
import com.witchica.mct.common.block.MctCraftingTableBlock;
import com.witchica.mct.common.block.entity.CraftingBenchBlockEntity;
import com.witchica.mct.common.menu.CraftingBenchMenu;
import com.witchica.mct.common.menu.MctCraftingTableMenu;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MoCraftingTablesFabric implements ModInitializer {

    public static final CreativeModeTab MO_CREATIVE_TABS_CREATIVE_MODE_TAB = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.mo_crafting_tables.generic"))
            .icon(MoCraftingTables::getCreativeModeDisplayItem)
            .displayItems((itemDisplayParameters, output) -> {
                output.acceptAll(MoCraftingTables.getCreativeModeTabItems());
            }).build();
    @Override
    public void onInitialize() {
        MoCraftingTables.onInitialize();

        MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE = new ExtendedScreenHandlerType<>(CraftingBenchMenu::new);
        Registry.register(BuiltInRegistries.MENU, MoCraftingTables.GENERIC_RESOURCE_LOCATION, MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE);

        MoCraftingTables.MO_CRAFTING_TABLES_MENU_TYPE = new MenuType<>(MctCraftingTableMenu::new, FeatureFlags.DEFAULT_FLAGS);
        Registry.register(BuiltInRegistries.MENU, MoCraftingTables.MO_CRAFTING_TABLES_MENU_RESOURCE_LOCATION, MoCraftingTables.MO_CRAFTING_TABLES_MENU_TYPE);

        for(int i = 0; i < MoCraftingTables.WOOD_TYPES.length; i++) {
            MoCraftingTables.CRAFTING_TABLE_BLOCKS[i] = Registry.register(BuiltInRegistries.BLOCK, MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table"), new MctCraftingTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));
            Registry.register(BuiltInRegistries.ITEM, MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table"), new BlockItem(MoCraftingTables.CRAFTING_TABLE_BLOCKS[i], new Item.Properties()));

            MoCraftingTables.CRAFTING_BENCH_BLOCKS[i] = Registry.register(BuiltInRegistries.BLOCK, MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_bench"), new CraftingBenchBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion()));
            Registry.register(BuiltInRegistries.ITEM, MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_bench"), new BlockItem(MoCraftingTables.CRAFTING_BENCH_BLOCKS[i], new Item.Properties()));

            MoCraftingTables.CRAFTING_TABLE_SLAB_BLOCKS[i] = Registry.register(BuiltInRegistries.BLOCK, MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table_slab"), new CraftingTableSlabBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion()));
            Registry.register(BuiltInRegistries.ITEM, MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table_slab"), new BlockItem(MoCraftingTables.CRAFTING_TABLE_SLAB_BLOCKS[i], new Item.Properties()));
        }

        MoCraftingTables.BENCH_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(CraftingBenchBlockEntity::new, MoCraftingTables.CRAFTING_BENCH_BLOCKS).build(null);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MoCraftingTables.BENCH_RESOURCE_LOCATION, MoCraftingTables.BENCH_BLOCK_ENTITY_TYPE);

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MoCraftingTables.GENERIC_RESOURCE_LOCATION, MO_CREATIVE_TABS_CREATIVE_MODE_TAB);
    }
}
