package com.witchica.mct.neoforge;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.client.screen.CraftingBenchScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MoCraftingTables.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCraftingTablesClientNeoForge {
    @SubscribeEvent
    public static void registerClient(FMLClientSetupEvent fmlClientSetupEvent) {
        MenuScreens.register(MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE, CraftingBenchScreen::new);
        MenuScreens.register(MoCraftingTables.MO_CRAFTING_TABLES_MENU_TYPE, CraftingScreen::new);
    }
}
