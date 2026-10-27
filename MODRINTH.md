# Modrinth release sheet

This file is the source of truth for the Modrinth project settings, version metadata, and listing body. The listing body starts after the horizontal rule. Not yet submitted; create the project at [modrinth.com/dashboard/projects/new](https://modrinth.com/dashboard/projects/new) when publishing.

## Project settings

| Field | Value |
| --- | --- |
| Title | **Clickable Pie Chart** |
| Slug | `clickable-pie-chart` |
| Project type | Mod |
| Project ID | *(not submitted yet)* |
| Summary | Mouse navigation for the F3 profiler pie chart. Fabric and Quilt 26.3, client-side. |
| License | MIT |
| Environment | Client-side only (`client_only`) |
| Source | https://github.com/simply-sunny/clickable-pie-chart |
| Issues | https://github.com/simply-sunny/clickable-pie-chart/issues |
| Wiki / docs | https://github.com/simply-sunny/clickable-pie-chart#use |
| Discord | *(none)* |
| Donation links | *(none)* |
| Icon | *(none yet)* |
| Gallery | *(screenshot of the pie chart with the cursor freed, 1920 px wide, from 26.3)* |

## Version metadata

Upload one Modrinth version per JAR, version number as in `gradle.properties`, channel `release`, loaders `fabric` and `quilt`, environment `client_only`. Quilt loads Fabric mods and is expected to work.

| JAR | Version ID |
| --- | --- |
| `clickable-piechart-26.3-1.0.0.jar` | *(upload pending)* |

Version name format: `Clickable Pie Chart 1.0.0 (26.3)`.

### Dependencies

| Project | ID | Type |
| --- | --- | --- |
| Fabric API | `P7dR8mSH` | Required |

---

Mouse navigation for the F3 profiler pie chart.

In vanilla Minecraft the profiler pie chart behind `Shift + F3` is driven by the number keys alone: `1`–`9` drill into a category's first entries and `0` goes back, so anything deeper, such as `[12] level`, is out of reach. Hold `Shift` and the chart follows the mouse instead: hover a row or a slice and click to drill in, right-click to go back. Release `Shift` and the cursor returns to the camera exactly as it would without the mod.

## Features

- **Mouse navigation:** hold `Shift` to free the cursor and click any entry or slice.
- **Back:** right-click, or `[0]`.
- **Wheel:** scroll over the chart to cycle entries, click to enter.
- **Keyboard:** `Shift` with a number key selects 10–19; `~` toggles cursor mode.
- **Hover highlights** on rows and slices, and a hint bar showing the active mode.

## Installation

1. Install Fabric Loader (or Quilt Loader) and Fabric API for 26.3.
2. Put the jar in `mods`.

## Compatibility

- **Minecraft:** 26.3 on Fabric and Quilt. Java 25.
- **Client-side only.** Servers don't need it.
- **Other debug-screen mods** that rebuild the profiler pie chart may stand in the way.

## License

MIT, see the [LICENSE](https://github.com/simply-sunny/clickable-pie-chart/blob/main/LICENSE).
