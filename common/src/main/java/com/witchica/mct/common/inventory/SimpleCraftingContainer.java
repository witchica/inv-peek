package com.witchica.mct.common.inventory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.CraftingContainer;

public class SimpleCraftingContainer extends SimpleContainer implements CraftingContainer {
    public SimpleCraftingContainer() {
        super(3*3);
    }
    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }
}
