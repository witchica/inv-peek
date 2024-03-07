package com.witchica.mct.fabric.client;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.client.screen.CraftingBenchScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;

public class MoCraftingTablesClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE, CraftingBenchScreen::new);
        MenuScreens.register(MoCraftingTables.MO_CRAFTING_TABLES_MENU_TYPE, CraftingScreen::new);
    }
}
