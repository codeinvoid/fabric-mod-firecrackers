package moe.pie.hoige.firecrackers

import moe.pie.hoige.firecrackers.entity.FireCrackersEntity
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier


object ItemRegistry {
    fun register() {
        Registry.register(Registries.ITEM, Identifier("firecrackers", "gunpowder_chip"), Items.GUNPOWDER_CHIPS)
        Registry.register(Registries.ITEM, Identifier("firecrackers", "fire_crackers"), Items.FIRE_CRACKERS)
    }
}