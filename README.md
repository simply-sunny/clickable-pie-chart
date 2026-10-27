<h1 align="center">Clickable Pie Chart</h1>

<p align="center">Mouse navigation for the F3 profiler pie chart.<br>Fabric & Quilt &nbsp;|&nbsp; 26.3</p>

---

In vanilla Minecraft the profiler pie chart behind `Shift + F3` is driven by the number keys alone: `1`–`9` drill into a category's first entries and `0` goes back, so anything deeper, such as `[12] level`, is out of reach. Hold `Shift` and the chart follows the mouse instead: hover a row or a slice and click to drill in, right-click to go back. Release `Shift` and the cursor returns to the camera exactly as it would without the mod.

## Install

Client-side only. Servers don't need it.

1. Install [Fabric Loader](https://fabricmc.net/use/) (or Quilt Loader) and [Fabric API](https://modrinth.com/mod/fabric-api) for 26.3.
2. Put the jar from [Releases](https://github.com/simply-sunny/clickable-pie-chart/releases) in `mods`.

## Use

| Action | How |
| --- | --- |
| Open the pie chart | `Shift + F3` (as in vanilla) |
| Drill into a row or slice | Hold `Shift`, then left-click it |
| Back to the parent category | Right-click, or click `[0]` |
| Cycle entries with the wheel | Scroll over the chart, then click the picked one |
| Pick 10–19 with the keyboard | Hold `Shift` with a number key, e.g. `Shift + 2` selects `12` |
| Toggle cursor mode | `~` while the chart is open |

- Hovering highlights the row or slice you are about to enter.
- The hint bar at the bottom of the chart shows which mode is active: `[Hold Shift] to unlock mouse & click` normally, `[Shift] Click: Select | Right-click: Back` when the cursor is free.
- Press `~` while the chart is open to toggle cursor mode without holding `Shift`; press it again to hand the mouse back.
- With the wheel, moving the mouse goes back to picking by hover.
- Releasing `Shift` instantly re-locks the cursor; gameplay and the camera never notice.

## Troubleshooting

| Problem | Fix |
| --- | --- |
| The mouse does nothing | Hold `Shift`, or press `~` for cursor mode. |
| The wheel does nothing | Scroll over the pie chart itself, not the log panels beside it. |
| The pie chart never appears | It is vanilla `Shift + F3`. This mod only adds mouse handling to it. |

## Develop

Requires JDK 25.

```bash
./gradlew clean buildAll
```

This builds every supported Minecraft version; jars land in `build/libs/`. To build one target:

```bash
./gradlew :26.3:build
```

To add a Minecraft version: add an entry to `minecraftTargets` in `build.gradle`, add it to `targets` in `settings.gradle`, and put its code in `versions/<mc>/`. The mod touches Minecraft's own pie chart classes through mixins in `versions/26.3/src/main/java`, so each version lives there; anything version-independent would go in `src/common/java`.

## License

MIT, see [LICENSE](LICENSE). Copyright © 2026 Saunak Karnati (simply-sunny).
