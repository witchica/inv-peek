package com.witchica.mct.forge;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.client.screen.CraftingBenchScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MoCraftingTables.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCraftingTablesClientForge {
    @SubscribeEvent
    public static void registerClient(FMLClientSetupEvent fmlClientSetupEvent) {
        fmlClientSetupEvent.enqueueWork(() -> {
            MenuScreens.register(MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE, CraftingBenchScreen::new);
            MenuScreens.register(MoCraftingTables.MO_CRAFTING_TABLES_MENU_TYPE, CraftingScreen::new);
        });
    }
}
