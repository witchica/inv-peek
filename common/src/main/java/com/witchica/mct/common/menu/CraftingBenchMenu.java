package com.witchica.mct.common.menu;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.block.entity.CraftingBenchBlockEntity;
import com.witchica.mct.common.inventory.CraftingSlot;
import com.witchica.mct.common.inventory.SimpleResultContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class CraftingBenchMenu extends RecipeBookMenu<CraftingContainer> {
    public static final int RESULT_SLOT = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_END = 10;
    private static final int INV_SLOT_START = 10;
    private static final int INV_SLOT_END = 37;
    private static final int USE_ROW_SLOT_START = 37;
    private static final int USE_ROW_SLOT_END = 46;
    private final CraftingContainer craftSlots;
    private final SimpleResultContainer resultSlots;
    private final Level level;
    private final Player player;
    private CraftingBenchBlockEntity blockEntity;

    public CraftingBenchMenu(int syncId, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(syncId, inventory, friendlyByteBuf.readBlockPos());
    }


    public CraftingBenchMenu(int containerId, Inventory playerInventory, BlockPos pos) {
        super(MoCraftingTables.PERSISTENT_CRAFTING_TABLE_MENU_TYPE, containerId);

        this.level = playerInventory.player.level();
         blockEntity = (CraftingBenchBlockEntity) playerInventory.player.level().getBlockEntity(pos);

        this.craftSlots = blockEntity.getCraftingContainer();
        this.resultSlots = blockEntity.getResultContainer();
        this.player = playerInventory.player;
        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        int i;
        int j;
        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 3; ++j) {
                this.addSlot(new CraftingSlot(this, this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        slotChangedCraftingGrid(this, level, player, craftSlots, resultSlots);
    }
    protected static void slotChangedCraftingGrid(CraftingBenchMenu menu, Level level, Player player, CraftingContainer container, SimpleResultContainer result) {
        if (!level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, level); if (optional.isPresent()) {
                CraftingRecipe craftingRecipe = (CraftingRecipe)optional.get();
                if (result.setRecipeUsed(level, serverPlayer, craftingRecipe)) {
                    ItemStack itemStack2 = craftingRecipe.assemble(container, level.registryAccess());
                    if (itemStack2.isItemEnabled(level.enabledFeatures())) {
                        itemStack = itemStack2;
                    }
                }
            }

            result.setItem(0, itemStack);
            menu.setRemoteSlot(0, itemStack);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));
        }
    }

    public void slotsChanged(Container container) {
        slotChangedCraftingGrid(this, level, this.player, this.craftSlots, this.resultSlots);
        blockEntity.setChanged();
    }

    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        this.craftSlots.fillStackedContents(itemHelper);
    }

    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    @Override
    public boolean recipeMatches(Recipe<? super CraftingContainer> recipe) {
        return recipe.matches(this.craftSlots, this.player.level());
    }

    public void removed(Player player) {
        super.removed(player);
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index == 0) {
                itemStack2.getItem().onCraftedBy(itemStack2, level, player);
                if (!this.moveItemStackTo(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index >= 10 && index < 46) {
                if (!this.moveItemStackTo(itemStack2, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
            if (index == 0) {
                player.drop(itemStack2, false);
            }
        }

        return itemStack;
    }

    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public int getResultSlotIndex() {
        return 0;
    }

    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    public int getSize() {
        return 10;
    }

    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex != this.getResultSlotIndex();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
