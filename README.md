# Clickable Pie Chart

A helper mod for Minecraft Fabric (26.3) that makes the **F3 debug profiler pie chart** (`Shift + F3`) fully interactive using your mouse.

In vanilla Minecraft, you can only navigate the profiler using number keys `1`–`9` and `0` (back). If a category has 10 or more entries (such as **`[12] level`** or entities/block entities further down), there is physically no key to select it.

**Clickable Pie Chart** solves this by letting you click any item directly with your mouse.

---

## Features

- **Mouse Clicking**: Hold **Shift** while the pie chart is open to unlock your mouse cursor. Click any entry (`[1]`, `[2]`, ..., `[12] level`, etc.) or click the pie slices directly to drill down.
- **Back Navigation**: Right-click anywhere on the pie chart panel or click `[0]` to navigate back to the parent category.
- **Visual Hover Highlights**: Hovering over any entry in the list or slice in the pie chart highlights the row with a clean indicator so you always know what you're selecting.
- **Status / Hint Bar**: Displays clear in-game reminders at the bottom of the pie chart:
  - *Normal mode:* `[Hold Shift] to unlock mouse & click`
  - *Cursor mode:* `[Shift] Click: Select | Right-click: Back`
- **Shift + Number Shortcut**: Holding Shift and pressing number keys `0`–`9` acts as a shortcut for `10`–`19` (e.g., **`Shift + 2`** selects entry **12**).
- **Mouse Wheel Support**: Scroll your mouse wheel (or swipe sideways on a trackpad) over the pie chart to cycle through items, then left-click to enter the picked one. Moving the mouse goes back to picking by hover.
- **Toggle Mode**: Press the **`~`** (Grave / Tilde) key while the pie chart is open to toggle cursor mode on or off without needing to hold Shift.
- **Seamless Camera Movement**: Releasing Shift instantly re-locks the mouse cursor to your normal camera view, so gameplay and pie-raying are never disrupted.

---

## How to Use

1. Press **`Shift + F3`** in-game to display the debug profiler pie chart.
2. Hold **`Shift`** to free the mouse cursor.
3. Hover over any entry (e.g., `[12] level`) and **Left-Click** to enter it.
4. **Right-Click** or click `[0]` to go back to the parent category.
5. Release **`Shift`** to return to normal first-person camera control.

---

## Building

Requires **JDK 25** and Gradle:

```bash
./gradlew build
```

The compiled mod jar will be in `build/libs/clickable-piechart-1.0.0.jar`.
