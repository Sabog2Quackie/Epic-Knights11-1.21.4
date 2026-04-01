// Armor decoration recipe implementation for 1.21.4
package com.magistuarmory.item.crafting;

import com.magistuarmory.item.ArmorDecoration;
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
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.PlacementInfo;

public class ArmorDecorationRecipe implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput input, Level level) {
        if (input.size() < 2) return false;
        
        ItemStack armorStack = input.getItem(0);
        ItemStack decorationStack = input.getItem(1);
        
        if (armorStack.isEmpty() || decorationStack.isEmpty()) return false;
        
        // Check if decoration can be applied to armor
        if (decorationStack.getItem() instanceof ArmorDecoration decoration) {
            return decoration.isApplicableForDecoration(armorStack);
        }
        
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        ItemStack armorStack = input.getItem(0).copy();
        ItemStack decorationStack = input.getItem(1);
        
        if (decorationStack.getItem() instanceof ArmorDecoration decoration) {
            decoration.decorate(armorStack, decorationStack);
        }
        
        return armorStack;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return (RecipeType<? extends Recipe<RecipeInput>>) ModRecipes.ARMOR_DECORATION_TYPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    public static final RecipeSerializer<ArmorDecorationRecipe> SERIALIZER = new RecipeSerializer<ArmorDecorationRecipe>() {
        @Override
        public MapCodec<ArmorDecorationRecipe> codec() {
            return MapCodec.unit(new ArmorDecorationRecipe());
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ArmorDecorationRecipe> streamCodec() {
            return StreamCodec.unit(new ArmorDecorationRecipe());
        }
    };
}
