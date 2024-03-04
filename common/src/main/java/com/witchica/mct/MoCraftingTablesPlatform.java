package com.witchica.mct;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

public class MoCraftingTablesPlatform {
    @ExpectPlatform
    public static void openMenu(Player player, BlockPos pos) {
        throw new AssertionError("Not implemented");
    }
}
