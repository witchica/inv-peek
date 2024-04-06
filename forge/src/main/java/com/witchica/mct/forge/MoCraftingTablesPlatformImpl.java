package com.witchica.mct.forge;

import com.witchica.mct.common.menu.CraftingBenchMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkHooks;

public class MoCraftingTablesPlatformImpl {
    public static void openMenu(Player player, BlockPos pos) {
        if(player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider((syncId, inventory, pPlayer) -> {
                return new CraftingBenchMenu(syncId, inventory, pos);
            }, Component.translatable("container.mo_crafting_tables.crafting_bench")), friendlyByteBuf -> {
                friendlyByteBuf.writeBlockPos(pos);
            });
        }
    }
}
