package com.helper.clickablepiechart;

import com.helper.clickablepiechart.mixin.ProfilerPieChartAccessor;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.debugchart.ProfilerPieChart;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ResultField;

import java.util.List;

public class ClickablePieChartHandler {
    private static boolean mouseReleasedByMod = false;
    private static boolean toggleCursorMode = false;
    private static int hoveredIndex = -1;

    // Layout bounds (in debug GUI coordinate space)
    private static int panelLeft = 0;
    private static int panelTop = 0;
    private static int panelRight = 0;
    private static int panelBottom = 0;
    private static int headerTop = 0;
    private static int headerBottom = 0;
    private static int pieTop = 0;
    private static int pieBottom = 0;
    private static int listTop = 0;
    private static int listBottom = 0;
    private static int pieCenterX = 0;
    private static int pieCenterY = 0;
    private static int visibleItemCount = 0;

    public static void init() {
        // Ready for client lifecycle
    }

    public static boolean isPieChartActive(Minecraft minecraft) {
        return minecraft != null && minecraft.getDebugOverlay() != null && minecraft.getDebugOverlay().showProfilerChart();
    }

    public static boolean isShiftHeld(Minecraft minecraft) {
        return InputConstants.isKeyDown(InputConstants.KEY_LSHIFT) ||
               InputConstants.isKeyDown(InputConstants.KEY_RSHIFT);
    }

    public static boolean isCursorModeActive(Minecraft minecraft) {
        return isPieChartActive(minecraft) && (isShiftHeld(minecraft) || toggleCursorMode);
    }

    public static void updateCursorState(Minecraft minecraft) {
        if (minecraft == null || minecraft.mouseHandler == null) return;

        if (minecraft.gui.screen() != null) {
            // Regular GUI screen has focus
            mouseReleasedByMod = false;
            return;
        }

        if (isCursorModeActive(minecraft)) {
            if (minecraft.mouseHandler.isMouseGrabbed()) {
                minecraft.mouseHandler.releaseMouse();
                mouseReleasedByMod = true;
            }
        } else {
            if (mouseReleasedByMod) {
                if (!minecraft.mouseHandler.isMouseGrabbed()) {
                    minecraft.mouseHandler.grabMouse();
                }
                mouseReleasedByMod = false;
            }
        }
    }

    public static int getDebugScale(Minecraft minecraft) {
        Window window = minecraft.getWindow();
        int guiScale = window.getGuiScale();
        int debugGuiScale = minecraft.options.debugGuiScale().get();
        int debugScale;
        if (debugGuiScale == -1) {
            debugScale = guiScale;
        } else if (debugGuiScale == 0) {
            debugScale = window.calculateScale(0, minecraft.isEnforceUnicode()) / 2;
        } else {
            debugScale = window.calculateScale(debugGuiScale, minecraft.isEnforceUnicode());
        }
        if (debugScale >= guiScale || debugScale <= 0) {
            debugScale = guiScale;
        }
        return debugScale;
    }

    public static double getMouseDebugX(Minecraft minecraft) {
        int scale = getDebugScale(minecraft);
        return minecraft.mouseHandler.xpos() / scale;
    }

    public static double getMouseDebugY(Minecraft minecraft) {
        int scale = getDebugScale(minecraft);
        return minecraft.mouseHandler.ypos() / scale;
    }

    public static boolean isMouseOverPanel(Minecraft minecraft) {
        if (!isPieChartActive(minecraft)) return false;
        double mouseX = getMouseDebugX(minecraft);
        double mouseY = getMouseDebugY(minecraft);
        return mouseX >= panelLeft && mouseX <= panelRight && mouseY >= panelTop && mouseY <= panelBottom;
    }

    public static void renderOverlay(ProfilerPieChart chart, GuiGraphicsExtractor extractor, int screenWidth, int screenHeight) {
        ProfilerPieChartAccessor acc = (ProfilerPieChartAccessor) chart;
        ProfileResults results = acc.clickablepiechart$getResults();
        if (results == null) return;

        String path = acc.clickablepiechart$getTreePath();
        if (path == null) path = "";

        List<ResultField> rawTimes = results.getTimes(path);
        if (rawTimes.isEmpty()) return;

        List<ResultField> times = new java.util.ArrayList<>(rawTimes);
        times.removeFirst(); // removes root/header element, matching vanilla behavior

        Minecraft mc = Minecraft.getInstance();
        Font font = acc.clickablepiechart$getFont();
        int bottomOffset = acc.clickablepiechart$getBottomOffset();

        int xCenter = screenWidth - 130 - 10;
        int leftX = xCenter - 130;
        int rightX = xCenter + 130;
        int totalListHeight = times.size() * 9;
        int bottomY = screenHeight - bottomOffset - 5;
        int topOfListY = bottomY - totalListHeight;
        int pieYCenter = topOfListY - 62 - 5;
        int pTop = pieYCenter - 62 + 10;
        int pBottom = pieYCenter + 62;
        int bgTop = pieYCenter - 62 - 10;

        panelLeft = leftX - 5;
        panelRight = rightX + 5;
        panelTop = bgTop - 5;
        panelBottom = bottomY + 16;
        headerTop = bgTop - 5;
        headerBottom = pTop - 2;
        pieTop = pTop;
        pieBottom = pBottom;
        listTop = topOfListY;
        listBottom = bottomY;
        pieCenterX = xCenter;
        pieCenterY = pieYCenter;
        visibleItemCount = times.size();

        double mouseX = getMouseDebugX(mc);
        double mouseY = getMouseDebugY(mc);

        int currentHover = -1;
        boolean isOver = mouseX >= panelLeft && mouseX <= panelRight && mouseY >= panelTop && mouseY <= panelBottom;

        if (isOver) {
            if (mouseY >= headerTop && mouseY < headerBottom) {
                currentHover = 0; // Header [0]
            } else if (mouseY >= pieTop && mouseY <= pieBottom) {
                double dx = mouseX - pieCenterX;
                double dy = (mouseY - pieCenterY) / 0.5;
                if (dx * dx + dy * dy <= 105.0 * 105.0) {
                    double angle = Math.atan2(dx, dy);
                    if (angle < 0.0) angle += 2.0 * Math.PI;
                    double targetPct = angle / (2.0 * Math.PI) * 100.0;
                    double accumPct = 0.0;
                    for (int i = 0; i < times.size(); i++) {
                        double slice = times.get(i).percentage;
                        if (targetPct >= accumPct && targetPct < accumPct + slice) {
                            currentHover = i + 1;
                            break;
                        }
                        accumPct += slice;
                    }
                }
            } else if (mouseY >= listTop && mouseY <= listBottom) {
                int listIdx = (int) ((mouseY - listTop) / 9);
                if (listIdx >= 0 && listIdx < times.size()) {
                    currentHover = listIdx + 1;
                }
            }
        }
        hoveredIndex = currentHover;

        boolean cursorActive = isCursorModeActive(mc);

        // Highlight hovered entry or header
        if (cursorActive && hoveredIndex >= 0) {
            if (hoveredIndex > 0 && hoveredIndex <= times.size()) {
                int lineY = listTop + (hoveredIndex - 1) * 9;
                // Highlight box
                extractor.fill(leftX - 3, lineY, rightX + 3, lineY + 9, 0x45FFFFFF);
                // Cyan accent markers
                extractor.fill(leftX - 4, lineY, leftX - 2, lineY + 9, 0xFF55FFFF);
                extractor.fill(rightX + 2, lineY, rightX + 4, lineY + 9, 0xFF55FFFF);
            } else if (hoveredIndex == 0) {
                extractor.fill(leftX - 3, headerTop, rightX + 3, headerBottom, 0x45FFFFFF);
                extractor.fill(leftX - 4, headerTop, leftX - 2, headerBottom, 0xFFFFAA00);
            }
        }

        // Information / Hint bar
        int hintY = bottomY + 6;
        extractor.fill(leftX - 5, hintY - 1, rightX + 5, hintY + 11, 0xC0101010);
        if (cursorActive) {
            extractor.text(font, "§b[Shift] §fClick: Select  §7|  §fRight-click: Back", leftX, hintY + 1, 0xFFFFFFFF);
        } else {
            extractor.text(font, "§e[Hold Shift] §7to unlock mouse & click", leftX, hintY + 1, 0xFFAAAAAA);
        }
    }

    public static boolean onMouseClick(Minecraft minecraft, int button) {
        if (!isPieChartActive(minecraft)) return false;

        ProfilerPieChart chart = minecraft.getDebugOverlay().getProfilerPieChart();
        if (chart == null) return false;

        if (hoveredIndex >= 0) {
            if (button == 0) { // Left click
                chart.profilerPieChartKeyPress(hoveredIndex);
                playClickSound(minecraft);
                return true;
            } else if (button == 1) { // Right click
                chart.profilerPieChartKeyPress(0);
                playClickSound(minecraft);
                return true;
            }
        } else if (isMouseOverPanel(minecraft)) {
            if (button == 1) { // Right click anywhere on panel navigates back
                chart.profilerPieChartKeyPress(0);
                playClickSound(minecraft);
                return true;
            }
            return true; // Consume click on panel background
        }

        return false;
    }

    public static boolean onMouseRelease(Minecraft minecraft, int button) {
        if (!isPieChartActive(minecraft)) return false;
        return isMouseOverPanel(minecraft);
    }

    public static boolean onMouseScroll(Minecraft minecraft, double vertical) {
        if (!isPieChartActive(minecraft) || !isMouseOverPanel(minecraft)) return false;

        // Move hover selection when scrolling
        if (visibleItemCount > 0) {
            if (vertical > 0) {
                if (hoveredIndex <= 1) {
                    hoveredIndex = 0;
                } else {
                    hoveredIndex--;
                }
            } else if (vertical < 0) {
                if (hoveredIndex < visibleItemCount) {
                    hoveredIndex++;
                }
            }
        }
        return true;
    }

    public static boolean handleKeyPress(Minecraft minecraft, int keyCode) {
        if (!isPieChartActive(minecraft)) return false;
        if (minecraft.gui.screen() != null) return false;

        // Optional toggle key: Grave/Tilde (`) toggles cursor lock
        if (keyCode == InputConstants.KEY_GRAVE) {
            toggleCursorMode = !toggleCursorMode;
            updateCursorState(minecraft);
            playClickSound(minecraft);
            return true;
        }

        boolean shift = isShiftHeld(minecraft);

        int digit = -1;
        if (keyCode >= InputConstants.KEY_0 && keyCode <= InputConstants.KEY_9) {
            digit = keyCode - InputConstants.KEY_0;
        } else if (keyCode >= InputConstants.KEY_NUMPAD0 && keyCode <= InputConstants.KEY_NUMPAD9) {
            digit = keyCode - InputConstants.KEY_NUMPAD0;
        }

        if (digit >= 0 && shift) {
            // Shift + 0 -> 10, Shift + 1 -> 11, Shift + 2 -> 12, etc.
            int targetIndex = (digit == 0) ? 10 : (10 + digit);
            minecraft.getDebugOverlay().getProfilerPieChart().profilerPieChartKeyPress(targetIndex);
            playClickSound(minecraft);
            return true;
        }

        return false;
    }

    private static void playClickSound(Minecraft minecraft) {
        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
