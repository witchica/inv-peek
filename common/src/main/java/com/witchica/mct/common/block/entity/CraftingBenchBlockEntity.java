package com.witchica.mct.common.block.entity;

import com.witchica.mct.common.MoCraftingTables;
import com.witchica.mct.common.inventory.SimpleCraftingContainer;
import com.witchica.mct.common.inventory.SimpleResultContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CraftingBenchBlockEntity extends BlockEntity {

    private final SimpleCraftingContainer craftingContainer;
    private final SimpleResultContainer resultContainer;

    public CraftingBenchBlockEntity(BlockPos pos, BlockState blockState) {
        super(MoCraftingTables.BENCH_BLOCK_ENTITY_TYPE, pos, blockState);
        this.craftingContainer = new SimpleCraftingContainer();
        this.resultContainer = new SimpleResultContainer();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        ListTag listTag = new ListTag();
        for(int i = 0; i < craftingContainer.getContainerSize(); i++) {
            listTag.add(i, craftingContainer.getItem(i).save(new CompoundTag()));
        }

        tag.put("CraftingItems", listTag);
        tag.put("ResultItems", this.resultContainer.createTag());
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        craftingContainer.clearContent();

        ListTag list = tag.getList("CraftingItems", Tag.TAG_COMPOUND);
        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemStack = ItemStack.of(list.getCompound(i));
            if (!itemStack.isEmpty()) {
                craftingContainer.setItem(i, itemStack);
            }
        }
        resultContainer.fromTag(tag.getList("ResultItems", Tag.TAG_COMPOUND));
    }
    public SimpleCraftingContainer getCraftingContainer() {
        return craftingContainer;
    }

    public SimpleResultContainer getResultContainer() {
        return resultContainer;
    }
}
