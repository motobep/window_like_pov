package net.name.windowlikepov.client.mixin;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.name.windowlikepov.client.Server.pointArrShared;
import static net.name.windowlikepov.client.WindowLikePovClient.enableCamera;
import static net.name.windowlikepov.client.WindowLikePovClient.FOV_DEGREES;

@Mixin(Camera.class)
public class CameraMixin {
    private static final float SCREEN_HEIGHT = 2;
    private static final float n = 1;

    @Inject(method = "calculateFov", at = @At("TAIL"), cancellable = true)
    private void changeCalculateFov(final float partialTicks, CallbackInfoReturnable<Float> cir) {
        if (!enableCamera) return;

        float[] values = pointArrShared.get();
        if (values != null && values.length == 3) {
            float z1 = values[2];
            float z_distance = n - z1;
            float z_distance_clamped = Math.clamp(z_distance, 0, 100);

            float fovDegrees = (float) Math.toDegrees(2 * Math.atan((SCREEN_HEIGHT / 2) / z_distance_clamped));
            fovDegrees = Math.clamp(fovDegrees, 10.0f, 179.0f);

            FOV_DEGREES = fovDegrees;
            cir.setReturnValue(fovDegrees);
        }
    }
}
