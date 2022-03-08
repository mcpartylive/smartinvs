![SmartInvs Logo](http://minuskube.fr/img/smart-invs/smart_invs.png)

[![License](https://img.shields.io/github/license/minuskube/smartinvs.svg?style=flat-square)](https://github.com/MinusKube/SmartInvs/blob/master/LICENSE.md)
[![Javadocs](https://img.shields.io/maven-central/v/fr.minuskube.inv/smart-invs.svg?label=javadoc&style=flat-square)](https://javadoc.io/doc/fr.minuskube.inv/smart-invs)

# SmartInvs
Advanced Inventory API for your Minecraft Bukkit plugins.

*Tested Minecraft versions: 1.18*  
**You can use this as a Plugin, or use it as a library** (see [the docs](https://minuskube.gitbook.io/smartinvs/))

## Features
* Inventories of any type (workbench, chest, furnace, ...)
* Customizable size when possible (chest, ...)
* Custom titles
* Allows to prevent the player from closing its inventory
* Custom listeners for the event related to the inventory
* Iterator for inventory slots
* Page system
* Util methods to fill an inventory's row/column/borders/...
* Actions when player clicks on an item
* Update methods to edit the content of the inventory every tick

## Docs
[Click here to read the docs on Gitbook](https://minuskube.gitbook.io/smartinvs/)

## Usage

1. Firstly, add SmartInvs to your Gradle buildsystem as such:
```
dependencies {
    compileOnly("live.mcparty:smart-invs:1.3.3")
}
```

2. Checkout this example to get started
```java
public class ExampleInventory implements InventoryProvider {

    @Override
    public void init(Player player, InventoryContents contents) {
        // Ran on initialization
        contents.fill(ClickableItem.empty(new ItemStack(Material.DIRT)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {
        // This will be run every tick, can be used to create rainbow effects
        // animations, updating info, etc.
    }
}
```