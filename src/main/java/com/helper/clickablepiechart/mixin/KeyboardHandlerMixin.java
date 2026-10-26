package com.helper.clickablepiechart.mixin;

import com.helper.clickablepiechart.ClickablePieChartHandler;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void clickablepiechart$onKeyPress(long window, int action, KeyEvent keyEvent, CallbackInfo ci) {
        if (ClickablePieChartHandler.isPieChartActive(this.minecraft)) {
            if (action == 1) { // GLFW_PRESS
                if (ClickablePieChartHandler.handleKeyPress(this.minecraft, keyEvent.key())) {
                    ci.cancel();
                    return;
                }
            }
            ClickablePieChartHandler.updateCursorState(this.minecraft);
        }
    }
}
