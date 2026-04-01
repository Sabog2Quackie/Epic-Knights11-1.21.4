// Heraldry recipe implementation for 1.21.4
package com.magistuarmory.item.crafting;

import com.magistuarmory.EpicKnights;
import com.magistuarmory.item.MedievalShieldItem;
import com.magistuarmory.item.armor.ISurcoat;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;

public class HeraldryRecipe implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput input, Level level) {
        ItemStack targetStack = ItemStack.EMPTY;
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof BannerItem) {
                    if (!bannerStack.isEmpty()) {
                        return false;
                    }
                    bannerStack = stack;
                } else {
                    if (!isApplicableForBanner(stack.getItem())) {
                        return false;
                    }
                    if (!targetStack.isEmpty()) {
                        return false;
                    }

                    BannerPatternLayers patterns = stack.get(DataComponents.BANNER_PATTERNS);
                    if (patterns != null && !patterns.layers().isEmpty()) {
                        return false;
                    }

                    targetStack = stack;
                }
            }
        }

        return !targetStack.isEmpty() && !bannerStack.isEmpty();
    }

    @Override
    public ItemStack assemble(RecipeInput input, HolderLookup.Provider registries) {
        ItemStack targetStack = ItemStack.EMPTY;
        ItemStack bannerStack = ItemStack.EMPTY;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof BannerItem) {
                    bannerStack = stack;
                } else if (isApplicableForBanner(stack.getItem())) {
                    targetStack = stack.copy();
                }
            }
        }

        if (!targetStack.isEmpty() && !bannerStack.isEmpty()) {
            BannerPatternLayers patterns = bannerStack.get(DataComponents.BANNER_PATTERNS);
            DyeColor color = ((BannerItem) bannerStack.getItem()).getColor();

            if (wornWithSurcoat(targetStack.getItem())) {
                targetStack.set(DataComponents.CUSTOM_NAME, Component.translatable("magistuarmory.withsurcoat." + color.getName(), targetStack.getHoverName().getString()));
            } else if (wornWithCaparison(targetStack.getItem())) {
                targetStack.set(DataComponents.CUSTOM_NAME, Component.translatable("magistuarmory.withcaparison." + color.getName(), targetStack.getHoverName().getString()));
            }

            if (patterns != null) {
                targetStack.set(DataComponents.BANNER_PATTERNS, patterns);
            }

            targetStack.set(DataComponents.BASE_COLOR, color);
        }

        return targetStack;
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

    static boolean isPaintableShield(Item item) {
        return item instanceof MedievalShieldItem && ((MedievalShieldItem) item).isPaintable();
    }

    static boolean wornWithCaparison(Item item) {
        // Caparison is a horse armor styling layer, so this should support AnimalArmorItem
        return item instanceof AnimalArmorItem;
    }

    static boolean wornWithSurcoat(Item item) {
        return item instanceof ArmorItem && (EpicKnights.GENERAL_CONFIG.enableSurcoatRecipeForAllArmor || item instanceof ISurcoat);
    }

    static boolean isApplicableForBanner(Item item) {
        return isPaintableShield(item) || wornWithCaparison(item) || wornWithSurcoat(item);
    }
}
