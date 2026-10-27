package com.helper.clickablepiechart.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.debugchart.ProfilerPieChart;
import net.minecraft.util.profiling.ProfileResults;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ProfilerPieChart.class)
public interface ProfilerPieChartAccessor {
    @Accessor("profilerPieChartResults")
    ProfileResults clickablepiechart$getResults();

    @Accessor("profilerTreePath")
    String clickablepiechart$getTreePath();

    @Accessor("profilerTreePath")
    void clickablepiechart$setTreePath(String path);

    @Accessor("bottomOffset")
    int clickablepiechart$getBottomOffset();

    @Accessor("font")
    Font clickablepiechart$getFont();
}
