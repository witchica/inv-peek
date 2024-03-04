package com.witchica.mct.fabric;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.block.entity.CraftingBenchBlockEntity;
import com.witchica.mct.common.menu.CraftingBenchMenu;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;

public class MoCraftingTablesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MoCraftingTables.onInitialize();

        MoCraftingTables.CRAFTING_TABLE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, MoCraftingTables.CRAFTING_TABLE_BLOCK_RESOURCE_LOCATION, MoCraftingTables.PERSISTENT_CRAFTING_TABLE_BLOCK_SUPPLIER.get());
        Registry.register(BuiltInRegistries.ITEM, MoCraftingTables.CRAFTING_TABLE_BLOCK_RESOURCE_LOCATION, new BlockItem(MoCraftingTables.CRAFTING_TABLE_BLOCK, new FabricItemSettings()));

        MoCraftingTables.GENERIC_BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(CraftingBenchBlockEntity::new, MoCraftingTables.CRAFTING_TABLE_BLOCK).build(null);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, MoCraftingTables.GENERIC_RESOURCE_LOCATION, MoCraftingTables.GENERIC_BLOCK_ENTITY_TYPE);

        MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE = new ExtendedScreenHandlerType<>(CraftingBenchMenu::new);
        Registry.register(BuiltInRegistries.MENU, MoCraftingTables.GENERIC_RESOURCE_LOCATION, MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.addAfter(Blocks.CRAFTING_TABLE, MoCraftingTables.getCreativeModeTabItems());
        });
    }
}
