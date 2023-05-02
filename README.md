# PACUtilities
A plugin adding various commands and features

## Features
- Play a sound when receiving a private message. Requires Essentials.

## Commands

### /dispatchevent
Dispatches a custom event with a given name and player.

Permission: `pacutilities.dispatchevent`

Usage:
- `/dispatchevent <type> <player> <name>`

### /pacutilities
Reloads the plugin.

Permission: `pacutilities.pacutilities`

Usage:
- `/pacutilities reload`

### /reglesevent
Displays custom event rules from the `events.yml` file.

Permission: `pacutilities.reglesevent`

Usage:
- `/reglesevent <event>`

### /removetags
Removes tags from a player, with filters.

Permission: `pacutilities.removetags`

Usage:
- `/removetags <player> all`
- `/removetags <player> contains <filter>`
- `/removetags <player> ends_with <filter>`
- `/removetags <player> matches <filter>`
- `/removetags <player> starts_with <filter>`

### /spawndragon
Respawns the ender dragon in the End, as if it was manually done.

Permission: `pacutilities.spawndragon`

Usage:
- `/spawndragon [<minplayercount>] [<radiustocheck>]`

### /tickettete
Adds, removes or gets tickets tête of a player.

Permission: `pacutilities.tickettete`

Usage:
- `/tickettete add <player> <amount>`
- `/tickettete remove <player> <amount>`
- `/tickettete get <player>`

### /timer
Starts, stops or stores the time of a timer, for a player.

Permission: `pacutilities.timer`

Usage:
- `/timer <player> start`
- `/timer <player> stop`
- `/timer <player> store <score> [<ifBetter>]`

### /togglemsgsound
Toggles the sound played when receiving a private message.

Permission: `pacutilities.togglemsgsound`

Usage:
- `/togglemsgsound`
