package com.helper.clickablepiechart;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClickablePieChartClient implements ClientModInitializer {
    public static final String MOD_ID = "clickablepiechart";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("[ClickablePieChart] Loaded helper mod for Minecraft 26.3");
        ClickablePieChartHandler.init();
    }
}
