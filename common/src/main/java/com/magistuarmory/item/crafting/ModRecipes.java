package com.magistuarmory.item.crafting;

import com.magistuarmory.EpicKnights;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;

public class ModRecipes
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(EpicKnights.ID, Registries.RECIPE_SERIALIZER);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(EpicKnights.ID, Registries.RECIPE_TYPE);
	
	public static final RegistrySupplier<RecipeType<?>> HERALDRY_TYPE = RECIPE_TYPES.register("heraldry_recipes", () -> new RecipeType() {});
	public static final RegistrySupplier<RecipeType<?>> ARMOR_DECORATION_TYPE = RECIPE_TYPES.register("armor_decoration_recipes", () -> new RecipeType() {});
	public static final RegistrySupplier<RecipeType<?>> DECORATION_REMOVE_TYPE = RECIPE_TYPES.register("decoration_remove_recipes", () -> new RecipeType() {});
	
	public static final RegistrySupplier<RecipeSerializer<?>> HERALDRY_SERIALIZER = RECIPE_SERIALIZERS.register("heraldry_recipes", () -> HeraldryRecipe.SERIALIZER);
	public static final RegistrySupplier<RecipeSerializer<?>> ARMOR_DECORATION_SERIALIZER = RECIPE_SERIALIZERS.register("armor_decoration_recipes", () -> ArmorDecorationRecipe.SERIALIZER);
	public static final RegistrySupplier<RecipeSerializer<?>> DECORATION_REMOVE_SERIALIZER = RECIPE_SERIALIZERS.register("decoration_remove_recipes", () -> DecorationRemoveRecipe.SERIALIZER);

	public static void init()
	{
		RECIPE_SERIALIZERS.register();
		RECIPE_TYPES.register();
	}
}
