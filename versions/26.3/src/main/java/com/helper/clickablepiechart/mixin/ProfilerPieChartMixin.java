package com.helper.clickablepiechart.mixin;

import com.helper.clickablepiechart.ClickablePieChartHandler;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.debugchart.ProfilerPieChart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProfilerPieChart.class)
public abstract class ProfilerPieChartMixin {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void clickablepiechart$onExtractRenderState(GuiGraphicsExtractor extractor, int screenWidth, int screenHeight, CallbackInfo ci) {
        ClickablePieChartHandler.renderOverlay((ProfilerPieChart) (Object) this, extractor, screenWidth, screenHeight);
    }
}
