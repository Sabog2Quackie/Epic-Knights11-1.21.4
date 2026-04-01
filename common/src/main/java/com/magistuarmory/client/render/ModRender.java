// Rendering system currently simplified for Fabric 1.21.4 port
package com.magistuarmory.client.render;

import com.magistuarmory.api.client.render.model.ModModelsProvider;
import com.magistuarmory.api.item.ModItemsProvider;

public class ModRender {
	public static void registerRenderers() {
		// No-op placeholder for legacy rendering hook
	}

	public static void setup(ModItemsProvider content) {
		// No-op placeholder for rendering setup path
	}

	public static void registerModelsLoadListener(ModItemsProvider content) {
		// No-op placeholder for model registration listener
	}

	public static Object createHeraldryItemStackRenderer(String id, Object location) {
		// No-op placeholder renderer creation
		return null;
	}
}

