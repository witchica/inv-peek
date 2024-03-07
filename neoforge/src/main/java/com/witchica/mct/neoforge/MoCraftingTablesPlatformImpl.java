package com.witchica.mct.neoforge;

import com.witchica.mct.common.menu.CraftingBenchMenu;
import com.witchica.mct.common.menu.MctCraftingTableMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.jetbrains.annotations.Nullable;

public class MoCraftingTablesPlatformImpl {
    public static void openMenu(Player player, BlockPos pos) {
        if(player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider((syncId, inventory, pPlayer) -> {
                return new CraftingBenchMenu(syncId, inventory, pos);
            }, Component.translatable("container.mo_crafting_tables.crafting_bench")), friendlyByteBuf -> {
                friendlyByteBuf.writeBlockPos(pos);
            });
        }
    }
}
