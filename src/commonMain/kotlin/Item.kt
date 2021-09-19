enum class Item {NONE, STAR, WING, GUN, CANNON, DOUBLEJUMP, PIRATE}

fun getNameForItem(item: Item): String {
    return when(item){
        Item.NONE -> ""
        Item.STAR -> "Blue Star"
        Item.WING -> "Wing"
        Item.GUN -> "Gun"
        Item.CANNON -> "Cannon"
        Item.DOUBLEJUMP -> "Double Jump"
        Item.PIRATE -> "Pirate"
    }
}

fun pickupItem(itemSpawn: ItemSpawn) {
    pickupItem(itemSpawn.item, itemSpawn.getX().toDouble() - Globals.cameraX, itemSpawn.getY().toDouble() - Globals.cameraY)
}

fun pickupItem(newItem: Item, screenX: Double, screenY: Double) {
    Globals.item = newItem
    Globals.itemScreenX = screenX
    Globals.itemScreenY = screenY
    Globals.cannon = null

    Globals.playSound(Sounds.item)
}
