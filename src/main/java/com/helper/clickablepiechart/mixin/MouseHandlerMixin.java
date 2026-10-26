package com.helper.clickablepiechart.mixin;

import com.helper.clickablepiechart.ClickablePieChartClient;
import com.helper.clickablepiechart.ClickablePieChartHandler;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow private boolean mouseGrabbed;

    @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
    private void clickablepiechart$onButton(long window, MouseButtonInfo buttonInfo, int action, CallbackInfo ci) {
        if (this.minecraft.gui.screen() == null && ClickablePieChartHandler.isPieChartActive(this.minecraft)) {
            ClickablePieChartClient.LOGGER.debug("[ClickablePieChart] onButton: action={}, button={}, mouseGrabbed={}", action, buttonInfo.button(), this.mouseGrabbed);
            if (action == InputConstants.PRESS) {
                if (ClickablePieChartHandler.onMouseClick(this.minecraft, buttonInfo.button())) {
                    ci.cancel();
                }
            } else if (action == InputConstants.RELEASE) {
                if (ClickablePieChartHandler.onMouseRelease(this.minecraft, buttonInfo.button())) {
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void clickablepiechart$onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (this.minecraft.gui.screen() == null && ClickablePieChartHandler.isPieChartActive(this.minecraft)) {
            if (ClickablePieChartHandler.onMouseScroll(this.minecraft, horizontal, vertical)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "handleAccumulatedMovement", at = @At("HEAD"))
    private void clickablepiechart$onHandleMovement(CallbackInfo ci) {
        ClickablePieChartHandler.updateCursorState(this.minecraft);
    }
}
