package eu.jailbreaker.lobby.protection.listener

import eu.jailbreaker.lobby.protection.canBuild
import eu.jailbreaker.lobby.whitelistedEntities
import org.bukkit.entity.EntityType.FALLING_BLOCK
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.*

class EntityListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onAreaEffectCloudApply(event: AreaEffectCloudApplyEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBatToggleSleep(event: BatToggleSleepEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onCreatureSpawn(event: CreatureSpawnEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onCreeperPower(event: CreeperPowerEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEnderDragonChangePhase(event: EnderDragonChangePhaseEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityAirChange(event: EntityAirChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityBreakDoor(event: EntityBreakDoorEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityBreed(event: EntityBreedEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityChangeBlock(event: EntityChangeBlockEvent) {
        event.isCancelled = true
        if (event.entityType == FALLING_BLOCK) {
            event.entity.remove()
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityCombustByBlock(event: EntityCombustByBlockEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityCombustByEntity(event: EntityCombustByEntityEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityCombust(event: EntityCombustEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityDamageByBlock(event: EntityDamageByBlockEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        event.isCancelled = true
        if (event.damager is Player) {
            event.isCancelled = !(event.damager as Player).canBuild()
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityDamage(event: EntityDamageEvent) {
        event.isCancelled = event.cause != EntityDamageEvent.DamageCause.ENTITY_ATTACK
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityDeath(event: EntityDeathEvent) {
        event.droppedExp = 0
        event.drops.clear()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityDropItem(event: EntityDropItemEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityEnterBlock(event: EntityEnterBlockEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityEnterLoveMode(event: EntityEnterLoveModeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityExplode(event: EntityExplodeEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
        event.blockList().clear()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityInteract(event: EntityInteractEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityPickupItem(event: EntityPickupItemEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityPortal(event: EntityPortalEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityPotionEffect(event: EntityPotionEffectEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityRegainHealth(event: EntityRegainHealthEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityResurrect(event: EntityResurrectEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityShootBow(event: EntityShootBowEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntitySpawn(event: EntitySpawnEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
    }

//    @EventHandler(priority = EventPriority.LOW)
//    fun onEntitySpellCast(event: EntitySpellCastEvent) {
//        event.isCancelled = true
//    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityTame(event: EntityTameEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityTarget(event: EntityTargetEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityTargetLivingEntity(event: EntityTargetLivingEntityEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityTeleport(event: EntityTeleportEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityToggleGlide(event: EntityToggleGlideEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityToggleSwim(event: EntityToggleSwimEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onEntityTransform(event: EntityTransformEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onExpBottle(event: ExpBottleEvent) {
        event.experience = 0
        event.showEffect = false
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onExplosionPrime(event: ExplosionPrimeEvent) {
        event.isCancelled = true
        event.radius = 0.0F
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onFireworkExplode(event: FireworkExplodeEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onItemMerge(event: ItemMergeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onItemSpawn(event: ItemSpawnEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onLingeringPotionSplash(event: LingeringPotionSplashEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPigZap(event: PigZapEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPigZombieAnger(event: PigZombieAngerEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.deathMessage = null
        event.keepInventory = true
        event.newExp = 0
        event.newLevel = 0
        event.newTotalExp = 0
        event.entity.spigot().respawn()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPlayerLeashEntity(event: PlayerLeashEntityEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPotionSplash(event: PotionSplashEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onProjectileHit(event: ProjectileHitEvent) {
        if (!whitelistedEntities.contains(event.entityType)) {
            event.entity.remove()
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onPotionSplash(event: ProjectileLaunchEvent) {
        event.isCancelled = !whitelistedEntities.contains(event.entityType)
        if (event.isCancelled) {
            event.entity.remove()
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onSheepDyeWool(event: SheepDyeWoolEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onSheepRegrowWool(event: SheepRegrowWoolEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onSlimeSplit(event: SlimeSplitEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onSpawnerSpawn(event: SpawnerSpawnEvent) {
        event.isCancelled = true
    }

//    @EventHandler(priority = EventPriority.LOW)
//    fun onStriderTemperatureChange(event: StriderTemperatureChangeEvent) {
//        event.isCancelled = true
//    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVillagerAcquireTrade(event: VillagerAcquireTradeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVillagerCareerChange(event: VillagerCareerChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onVillagerReplenishTrade(event: VillagerReplenishTradeEvent) {
        event.isCancelled = true
    }
}