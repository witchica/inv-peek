package com.witchica.mct.forge;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.block.CraftingBenchBlock;
import com.witchica.mct.common.block.CraftingTableSlabBlock;
import com.witchica.mct.common.block.MctCraftingTableBlock;
import com.witchica.mct.common.block.entity.CraftingBenchBlockEntity;
import com.witchica.mct.common.menu.CraftingBenchMenu;
import com.witchica.mct.common.menu.MctCraftingTableMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(MoCraftingTables.MOD_ID)
public class MoCraftingTablesForge {

    public static final CreativeModeTab MO_CREATIVE_TABS_CREATIVE_MODE_TAB = CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mo_crafting_tables.generic"))
            .icon(MoCraftingTables::getCreativeModeDisplayItem)
            .displayItems((itemDisplayParameters, output) -> {
                output.acceptAll(MoCraftingTables.getCreativeModeTabItems());
            }).build();
    public MoCraftingTablesForge() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MoCraftingTables.onInitialize();
    }

    @SubscribeEvent
    public void registerEvent(RegisterEvent registerEvent) {
        MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE = IForgeMenuType.create(CraftingBenchMenu::new);
        MoCraftingTables.MO_CRAFTING_TABLES_MENU_TYPE = new MenuType<>(MctCraftingTableMenu::new, FeatureFlags.DEFAULT_FLAGS);

        registerEvent.register(ForgeRegistries.Keys.MENU_TYPES, (menuTypeRegisterHelper -> {
            menuTypeRegisterHelper.register(MoCraftingTables.GENERIC_RESOURCE_LOCATION, MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE);
            menuTypeRegisterHelper.register(MoCraftingTables.MO_CRAFTING_TABLES_MENU_RESOURCE_LOCATION, MoCraftingTables.MO_CRAFTING_TABLES_MENU_TYPE);
        }));

        registerEvent.register(Registries.BLOCK, blockRegisterHelper -> {
            for(int i = 0; i < MoCraftingTables.WOOD_TYPES.length; i++) {
                MoCraftingTables.CRAFTING_TABLE_BLOCKS[i] = new MctCraftingTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE));
                blockRegisterHelper.register(MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table"), MoCraftingTables.CRAFTING_TABLE_BLOCKS[i]);

                MoCraftingTables.CRAFTING_BENCH_BLOCKS[i] = new CraftingBenchBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion());
                blockRegisterHelper.register(MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_bench"), MoCraftingTables.CRAFTING_BENCH_BLOCKS[i]);

                MoCraftingTables.CRAFTING_TABLE_SLAB_BLOCKS[i] = new CraftingTableSlabBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion());
                blockRegisterHelper.register(MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table_slab"), MoCraftingTables.CRAFTING_TABLE_SLAB_BLOCKS[i]);
            }
        });

        registerEvent.register(Registries.ITEM, itemRegisterHelper -> {
            for(int i = 0; i < MoCraftingTables.WOOD_TYPES.length; i++) {
                itemRegisterHelper.register(MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table"), new BlockItem(MoCraftingTables.CRAFTING_TABLE_BLOCKS[i], new Item.Properties()));
                itemRegisterHelper.register(MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_bench"), new BlockItem(MoCraftingTables.CRAFTING_BENCH_BLOCKS[i], new Item.Properties()));
                itemRegisterHelper.register(MoCraftingTables.makeResourceLocation(MoCraftingTables.WOOD_TYPES[i] + "_crafting_table_slab"), new BlockItem(MoCraftingTables.CRAFTING_TABLE_SLAB_BLOCKS[i], new Item.Properties()));
            }
        });

        registerEvent.register(Registries.BLOCK_ENTITY_TYPE, blockEntityTypeRegisterHelper -> {
            MoCraftingTables.BENCH_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(CraftingBenchBlockEntity::new, MoCraftingTables.CRAFTING_BENCH_BLOCKS).build(null);
            blockEntityTypeRegisterHelper.register(MoCraftingTables.BENCH_RESOURCE_LOCATION, MoCraftingTables.BENCH_BLOCK_ENTITY_TYPE);
        });

        registerEvent.register(Registries.CREATIVE_MODE_TAB, creativeModeTabRegisterHelper -> {
            creativeModeTabRegisterHelper.register(MoCraftingTables.GENERIC_RESOURCE_LOCATION, MO_CREATIVE_TABS_CREATIVE_MODE_TAB);
        });
    }
}