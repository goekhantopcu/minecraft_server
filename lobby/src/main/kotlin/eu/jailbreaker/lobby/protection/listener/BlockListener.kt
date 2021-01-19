package eu.jailbreaker.lobby.protection.listener

import eu.jailbreaker.lobby.protection.canBuild
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.*

class BlockListener : Listener {

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockBreak(event: BlockBreakEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockBurn(event: BlockBurnEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockCook(event: BlockCookEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockDamage(event: BlockDamageEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockDispenseArmor(event: BlockDispenseArmorEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockDispense(event: BlockDispenseEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockDropItem(event: BlockDropItemEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockExp(event: BlockExpEvent) {
        event.expToDrop = 0
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockExplode(event: BlockExplodeEvent) {
        event.isCancelled = true
        event.blockList().clear()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockFade(event: BlockFadeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockFertilize(event: BlockFertilizeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockForm(event: BlockFormEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockFromTo(event: BlockFromToEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockGrow(event: BlockGrowEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockIgnite(event: BlockIgniteEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockMultiPlace(event: BlockMultiPlaceEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockPhysics(event: BlockPhysicsEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockPistonExtend(event: BlockPistonExtendEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockPistonRetract(event: BlockPistonRetractEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockPlace(event: BlockPlaceEvent) {
        event.isCancelled = !event.player.canBuild()
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockRedstone(event: BlockRedstoneEvent) {
        event.newCurrent = event.oldCurrent
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockShearEntity(event: BlockShearEntityEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onBlockSpread(event: BlockSpreadEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onCauldronLevelChange(event: CauldronLevelChangeEvent) {
        event.isCancelled = true
        event.newLevel = event.oldLevel
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onFluidLevelChange(event: FluidLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onLeavesDecay(event: LeavesDecayEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onMoistureChange(event: MoistureChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onNotePlay(event: NotePlayEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onSignChange(event: SignChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.LOW)
    fun onSpongeAbsorb(event: SpongeAbsorbEvent) {
        event.isCancelled = true
    }
}