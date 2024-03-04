package com.witchica.mct.common.inventory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class SimpleResultContainer extends SimpleContainer implements RecipeCraftingHolder {
    private RecipeHolder<?> recipe;

    public SimpleResultContainer() {
        super(1);
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipe) {
        this.recipe = recipe;
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return recipe;
    }
}
