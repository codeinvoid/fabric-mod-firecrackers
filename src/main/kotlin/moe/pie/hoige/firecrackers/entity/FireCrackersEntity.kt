package moe.pie.hoige.firecrackers.entity

import moe.pie.hoige.firecrackers.Firecrackers
import moe.pie.hoige.firecrackers.Items.FIRE_CRACKERS
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.ThrownItemEntity
import net.minecraft.item.Item
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.ItemStackParticleEffect
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.World


class FireCrackersEntity : ThrownItemEntity {
    companion object {
        val FUSE: TrackedData<Int> = DataTracker.registerData(FireCrackersEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        private val TIMES = DataTracker.registerData(FireCrackersEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
    }

    constructor(entityType: EntityType<out FireCrackersEntity>, world: World) : super(entityType, world)
    constructor(world: World, owner: LivingEntity) : super(
        Firecrackers.entity,
        owner,
        world,
    )

    constructor(world: World,x: Double, y: Double, z: Double) : super(
        Firecrackers.entity,
        x,
        y,
        z,
        world
    ) {
        fuse = 10
        times = 3
        prevX = x
        prevY = y
        prevZ = z
    }

    override fun getDefaultItem(): Item {
        return FIRE_CRACKERS
    }

    @get:Environment(EnvType.CLIENT)
    private val particleParameters: ParticleEffect
        get() {
            val itemStack = this.item
            return (if (itemStack.isEmpty) ParticleTypes.ITEM_SNOWBALL else ItemStackParticleEffect(
                ParticleTypes.ITEM,
                itemStack
            )) as ParticleEffect
        }

    @Environment(EnvType.CLIENT)
    override fun handleStatus(status: Byte) {
        if (status.toInt() == 3) {
            val particleEffect: ParticleEffect = this.particleParameters
            for (i in 0..7) {
                world.addParticle(particleEffect, this.x, this.y, this.z, 0.0, 0.0, 0.0)
            }
        }
    }

    override fun onEntityHit(entityHitResult: EntityHitResult) {
        super.onEntityHit(entityHitResult)
        val entity: Entity = entityHitResult.entity
        val i = if (entity is PlayerEntity) 0 else 3

        entity.damage(damageSources.thrown(this, this.owner), i.toFloat())
    }

    override fun onCollision(hitResult: HitResult) {
        super.onCollision(hitResult)
        if (!world.isClient) {
            world.sendEntityStatus(this, 3.toByte())
            this.explode()
        }
    }

    override fun tick() {
        super.tick()
        val i = fuse - 1
        fuse = i
        if (i <= 0) {
            if (times > 0) {
                fuse += 5
            }

            if (!this.world.isClient) {
                this.explode()
                times -= 1
            }
        }

        if (times <= 0) {
            this.discard()
        }
    }

    override fun initDataTracker() {
        super.initDataTracker()
        this.dataTracker.startTracking(FUSE, 10)
        this.dataTracker.startTracking(TIMES, 3)
    }

    private fun explode() {
        world.createExplosion(this, this.x, this.y, this.z, 4.0f, World.ExplosionSourceType.NONE)
    }

    private var fuse: Int
        get() = dataTracker.get(FUSE)
        set(fuse) {
            dataTracker.set(FUSE, fuse)
        }

    var times: Int
        get() = dataTracker.get(TIMES)
        set(times) {
            dataTracker.set(TIMES, times)
        }


    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        nbt.putShort("Fuse", fuse.toShort())
        nbt.putShort("Times", times.toShort())
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        this.fuse = nbt.getShort("Fuse").toInt()
        this.times = nbt.getShort("Times").toInt()
    }
}