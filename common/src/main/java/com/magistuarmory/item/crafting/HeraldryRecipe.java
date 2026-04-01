// Heraldry recipe implementation for 1.21.4
package com.magistuarmory.item.crafting;

import com.magistuarmory.item.MedievalShieldItem;
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
import net.minecraft.world.item.DyeItem;

public class HeraldryRecipe implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput input, Level level) {
        if (input.size() < 2) return false;
        
        ItemStack shieldStack = input.getItem(0);
        ItemStack dyeStack = input.getItem(1);
        
        if (shieldStack.isEmpty() || dyeStack.isEmpty()) return false;
        
        // Check if first item is a shield
        if (!(shieldStack.getItem() instanceof MedievalShieldItem)) return false;
        
        // Check if second item is a dye
        return dyeStack.getItem() instanceof DyeItem;
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        ItemStack shieldStack = input.getItem(0).copy();
        // TODO: Apply heraldry patterns to the shield
        // For now, just return the shield
        return shieldStack;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return (RecipeType<? extends Recipe<RecipeInput>>) ModRecipes.HERALDRY_TYPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    public static final RecipeSerializer<HeraldryRecipe> SERIALIZER = new RecipeSerializer<HeraldryRecipe>() {
        @Override
        public MapCodec<HeraldryRecipe> codec() {
            return MapCodec.unit(new HeraldryRecipe());
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, HeraldryRecipe> streamCodec() {
            return StreamCodec.unit(new HeraldryRecipe());
        }
    };
}
