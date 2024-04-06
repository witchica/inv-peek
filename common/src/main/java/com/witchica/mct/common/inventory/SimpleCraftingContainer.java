package com.witchica.mct.common.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

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

    @Override
    public List<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(getWidth() * getHeight(), ItemStack.EMPTY);

        for(int i = 0; i < getWidth() * getHeight(); i++) {
            items.set(i, getItem(i));
        }

        return items;
    }
}
