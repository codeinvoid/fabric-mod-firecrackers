package moe.pie.hoige.firecrackers

import moe.pie.hoige.firecrackers.entity.FireCrackersEntity
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Firecrackers : ModInitializer {
	const val ModID = "firecrackers";
	val logger = LoggerFactory.getLogger(ModID)
	val entity  = Registry.register(
		Registries.ENTITY_TYPE,
		Identifier("firecrackers", "fire_crackers"),
		EntityType.Builder
			.create(::FireCrackersEntity, SpawnGroup.MISC)
			.setDimensions(0.25f, 0.25f)
			.maxTrackingRange(4)
			.trackingTickInterval(10)
			.build("fire_crackers")
	)

	override fun onInitialize() {
		ItemRegistry.register()
	}
}

