package net.name.windowlikepov.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;


import net.minecraft.client.KeyMapping;
import net.name.windowlikepov.WindowLikePov;
import org.joml.Matrix4f;


import java.text.NumberFormat;

import static net.name.windowlikepov.client.Server.pointArrShared;

public class WindowLikePovClient implements ClientModInitializer {
	private static boolean showHud = false;
	public static boolean enableCamera = false;

	public static Matrix4f PROJECTION_MATRIX;
	public static float FOV_DEGREES = 80.0f;

	private static final NumberFormat formatter = NumberFormat.getInstance();

	KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			WindowLikePov.id("key_mapping")
	);
	KeyMapping showHudKey = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"key.example-mod.show_hud", // The translation key for the key mapping.
					InputConstants.Type.KEYBOARD, // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
					InputConstants.KEY_J, // The keycode of the key.
					this.CATEGORY // The category of the mapping.
			));

	KeyMapping enableCameraKey = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
					"key.example-mod.enable_camera", // The translation key for the key mapping.
					InputConstants.Type.KEYBOARD, // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
					InputConstants.KEY_K, // The keycode of the key.
					this.CATEGORY // The category of the mapping.
			));

	@Override
	public void onInitializeClient() {
		System.out.println("--- onInitializeClient ---");

		// Set hud
		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				Identifier.fromNamespaceAndPath("windowlikepov", "matrix_values"),
				WindowLikePovClient::render
		);

		// Bind keys
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (this.showHudKey.consumeClick()) {
				if (client.player == null) return;

				showHud = !showHud;
			}
		});
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (this.enableCameraKey.consumeClick()) {
				if (client.player == null) return;

				enableCamera = !enableCamera;
			}
		});

		formatter.setMaximumFractionDigits(4);

		Server.runServer();

		System.out.println("--- onInitializeClient end ---");
	}



	private static void render(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
		if (!showHud) return;

		float[] values = pointArrShared.get();

		String text = values == null
				? "Waiting for values..."
				: String.format(
						"x: %.4f   y: %.4f   z: %.4f",
				values[0], values[1], values[2]
		);
		int gap = 15;
		graphics.text(
				Minecraft.getInstance().font,
				text,
				4, 4,
				0xFFFFFFFF,
				false
		);
		graphics.text(
				Minecraft.getInstance().font,
				String.format("fov: %.4f", FOV_DEGREES),
				4, 4 + gap,
				0xFFFFFFFF,
				false
		);
		graphics.text(
				Minecraft.getInstance().font,
				"matrix: " + PROJECTION_MATRIX.toString(formatter),
				4, 4 + 2 * gap,
				0xFFFFFFFF,
				false
		);
	}
}