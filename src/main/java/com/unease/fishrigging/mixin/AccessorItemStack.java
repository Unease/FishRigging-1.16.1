package com.unease.fishrigging.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemStack.class)
public interface AccessorItemStack {

	@Accessor("tag")
	public void setCompoundTag(CompoundTag tag); // For future stuff in files
}
