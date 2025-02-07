package net.blitzcube.peapi.api.packet;

import org.bukkit.potion.PotionEffect;

/**
 * Created by iso2013 on 6/8/2018.
 */
public interface IEntityPotionAddPacket extends IEntityPacket {
    /**
     * Gets the potion effect that will be sent
     *
     * @return the potion effect
     */
    PotionEffect getEffect();

    /**
     * Set the effect to be added to the entity
     *
     * @param value the new effect
     */
    void setEffect(PotionEffect value);
}
