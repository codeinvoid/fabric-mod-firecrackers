package moe.pie.hoige.firecrackers

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.FlyingItemEntityRenderer


object FirecrackersClient : ClientModInitializer {
	override fun onInitializeClient() {
		EntityRendererRegistry.register(Firecrackers.entity, ::FlyingItemEntityRenderer)
	}
}