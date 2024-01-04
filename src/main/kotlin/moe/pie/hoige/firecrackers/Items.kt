package moe.pie.hoige.firecrackers

import moe.pie.hoige.firecrackers.items.FireCrackersItem
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item


object Items {
    val GUNPOWDER_CHIPS : Item = Item(FabricItemSettings())
    val FIRE_CRACKERS: Item = FireCrackersItem(Item.Settings().maxCount(16))
}