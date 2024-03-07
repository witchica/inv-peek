package com.witchica.mct.common.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class MctCraftingTableMenu extends CraftingMenu {
    private final ContainerLevelAccess access;

    public MctCraftingTableMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
        this.access = access;
    }

    public MctCraftingTableMenu(int containerId, Inventory playerInventory) {
        super(containerId, playerInventory);
        this.access = null;
    }

    @Override
    public boolean stillValid(Player player) {
        return access.evaluate((arg3, arg4) -> {
            return player.distanceToSqr((double)arg4.getX() + 0.5, (double)arg4.getY() + 0.5, (double)arg4.getZ() + 0.5) <= 64.0;
        }, true);
    }
}
