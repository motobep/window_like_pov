//package net.name.windowlikepov.client.mixin;
//
//import com.mojang.authlib.GameProfile;
//
//import net.minecraft.client.player.AbstractClientPlayer;
//import net.minecraft.util.Mth;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.Level;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import static net.name.windowlikepov.client.Server.sharedData;
//
//@Mixin(AbstractClientPlayer.class)
//public abstract class AbstractClientPlayerMixin extends Player {
//    public AbstractClientPlayerMixin(Level level, GameProfile gameProfile) {
//        super(level, gameProfile);
//    }
//
//    @Inject(method = "getFieldOfViewModifier", at = @At(value = "TAIL"), cancellable = true)
//    private void getFieldOfViewModifierMixin(boolean firstPerson, float effectScale, CallbackInfoReturnable<Float> info) {
//        float modifier = 1f;
//        //System.out.println("\n--- getFieldOfViewModifier ---");
//        float[] values = sharedData.get();
//        if (values != null && values.length == 3) {
//            float z = values[2];
//
//            modifier = Math.clamp(z * 5.0f, 1.0f, 20.0f);
//            info.setReturnValue(modifier);
//        }
//
//    }
//}