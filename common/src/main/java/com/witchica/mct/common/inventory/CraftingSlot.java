package com.witchica.mct.common.inventory;

import com.witchica.mct.common.menu.CraftingBenchMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CraftingSlot extends Slot {
    private final CraftingBenchMenu menu;

    public CraftingSlot(CraftingBenchMenu menu, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.menu = menu;
    }

    @Override
    public void set(ItemStack stack) {
        super.set(stack);
        menu.slotsChanged(container);
    }
}
