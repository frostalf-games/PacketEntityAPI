package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.blitzcube.peapi.api.entity.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IEntityClickPacket;
import net.blitzcube.peapi.entity.EntityIdentifier;
import org.bukkit.entity.Player;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityClickPacket extends EntityPacket implements IEntityClickPacket {
    private static final PacketType TYPE = PacketType.Play.Client.USE_ENTITY;
    private ClickType type;

    public EntityClickPacket(IEntityIdentifier identifier) {
        super(identifier, new PacketContainer(TYPE), true);
    }

    private EntityClickPacket(IEntityIdentifier identifier, PacketContainer rawPacket, ClickType type) {
        super(identifier, rawPacket, false);
        this.type = type;
    }

    static EntityPacket unwrap(int entityID, PacketContainer c, Player p) {
        return new EntityClickPacket(
                EntityIdentifier.produce(entityID, p, false),
                c,
                ClickType.getByProtocolLib(c.getEntityUseActions().read(0))
        );
    }

    @Override
    public ClickType getClickType() {
        return type;
    }

    @Override
    public void setClickType(ClickType type) {
        this.type = type;
        super.rawPacket.getEntityUseActions().write(0, type.getProtocolLibEquivalent());
    }

    @Override
    public PacketContainer getRawPacket() {
        assert type != null;
        return super.getRawPacket();
    }

    @Override
    public EntityPacket clone() {
        EntityClickPacket p = new EntityClickPacket(getIdentifier());
        p.setClickType(type);
        return p;
    }
}
