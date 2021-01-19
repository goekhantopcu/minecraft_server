package eu.jailbreaker.lobby.secret

class SecretTask : Runnable {
    companion object {
        private const val TELEPORT_Y = 0.009
    }

    private var yaw = 0F
    private var ticker = 0.0

    override fun run() {
        yaw += 0.9F
        ticker += 0.25
        if (ticker >= 20) {
            ticker = 0.0
        }
        if (yaw >= 360) {
            yaw = 0F
        }
        Secret.SECRETS.forEach { secret ->
            val loc = secret.armorStand.location.clone().add(
                0.0,
                if (ticker < 10) -TELEPORT_Y else TELEPORT_Y,
                0.0
            )
            loc.yaw = yaw
            secret.armorStand.teleportAsync(loc)
        }
    }
}