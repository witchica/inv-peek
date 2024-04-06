package com.witchica.mct.common.inventory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.Nullable;

public class SimpleResultContainer extends SimpleContainer implements RecipeHolder {

    private Recipe<?> recipe;

    public SimpleResultContainer() {
        super(1);
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        this.recipe = recipe;
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return recipe;
    }
}
