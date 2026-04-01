// Decoration remove recipe implementation for 1.21.4
package com.magistuarmory.item.crafting;

import com.magistuarmory.component.ModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.PlacementInfo;

public class DecorationRemoveRecipe implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput input, Level level) {
        if (input.size() < 1) return false;
        
        ItemStack armorStack = input.getItem(0);
        if (armorStack.isEmpty()) return false;
        
        // Check if armor has decorations
        return armorStack.has(ModDataComponents.ARMOR_DECORATION.get());
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        ItemStack armorStack = input.getItem(0).copy();
        
        // Remove decorations
        armorStack.remove(ModDataComponents.ARMOR_DECORATION.get());
        
        return armorStack;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null; // TODO: Find correct RecipeBookCategory constant
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return (RecipeType<? extends Recipe<RecipeInput>>) ModRecipes.DECORATION_REMOVE_TYPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    public static final RecipeSerializer<DecorationRemoveRecipe> SERIALIZER = new RecipeSerializer<DecorationRemoveRecipe>() {
        @Override
        public MapCodec<DecorationRemoveRecipe> codec() {
            return MapCodec.unit(new DecorationRemoveRecipe());
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DecorationRemoveRecipe> streamCodec() {
            return StreamCodec.unit(new DecorationRemoveRecipe());
        }
    };
}
