package scripts.dax.teleports.teleport_utils;

/**
 * Check limits of a teleport, e.g. wilderness level under 20 or 30.
 */
public interface TeleportLimit {
    boolean canCast();
}
