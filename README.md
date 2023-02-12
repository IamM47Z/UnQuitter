# Minecraft UnQuitter v1.0.0

Minecraft UnQuitter is a Fabric Client Mod for 1.19.2 that prevents some strange behavior on macOS from interrupting the game. With this mod, its possible to use `Cmd + Q` and other shortcuts (such has `Cmd + M` to minimize the game) without triggering the system shortcuts during the game.

## Known Current Issues:

- `Cmd + Q` is the only system shortcut being propagated to the game and is hardcoded (the keys being propagated to the game wont change even if the user changes the system shortcut);
- `Cmd + Space` triggers the system shortcut.
