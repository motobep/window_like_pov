package net.name.windowlikepov.client.mixin;

import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.name.windowlikepov.client.WindowLikePovClient;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import static net.name.windowlikepov.client.Server.pointArrShared;
import static net.name.windowlikepov.client.WindowLikePovClient.enableCamera;

@Mixin(ProjectionMatrixBuffer.class)
public class GameRendererMixin {
    private static int counter = 0;
    private static int stride_modifier = 2;

    @Inject(
            method = "writeBuffer",
            at = @At("HEAD")
    )
    private void modifyProjectionMatrix(
            Matrix4f projectionMatrix,
            CallbackInfoReturnable<GpuBufferSlice> callback) {
        if (!enableCamera) return;

        if (counter > 2_000_000) {
            counter = 0;
        }
        counter++;

        WindowLikePovClient.PROJECTION_MATRIX = projectionMatrix;

        if (counter > 2) {
            float[] values = pointArrShared.get();
            if (values != null && values.length == 3) {
                projectionMatrix.m30(-values[0]*stride_modifier);
                projectionMatrix.m31(-values[1]*stride_modifier);
            }
        }
    }
}
