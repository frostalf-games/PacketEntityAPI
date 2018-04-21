package net.blitzcube.peapi.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.ImmutableList;
import net.blitzcube.peapi.api.entity.modifier.IEntityIdentifier;
import net.blitzcube.peapi.api.packet.IPacketEntityDestroy;
import net.blitzcube.peapi.entity.modifier.EntityIdentifier;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by iso2013 on 4/21/2018.
 */
public class EntityDestroyPacket extends EntityPacket implements IPacketEntityDestroy {
    private static final int TICK_DELAY = 1;
    private static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;
    private final List<IEntityIdentifier> targets;
    private boolean changed = false;

    private EntityDestroyPacket() {
        super(null, new PacketContainer(TYPE));
        this.targets = new ArrayList<>();
    }

    protected EntityDestroyPacket(PacketContainer rawPacket, List<IEntityIdentifier> targets) {
        super(null, rawPacket);
        this.targets = targets;
    }

    public static EntityPacket unwrap(PacketContainer c, Player p) {
        return new EntityDestroyPacket(c, Arrays.stream(c.getIntegerArrays().read(0))
                .mapToObj(value -> new EntityIdentifier(value, p)).collect(Collectors.toList())
        );
    }

    @Override
    public ImmutableList<IEntityIdentifier> getGroup() {
        return ImmutableList.copyOf(targets);
    }

    @Override
    public void removeFromGroup(IEntityIdentifier e) {
        targets.remove(e);
        changed = true;
    }

    @Override
    public void addToGroup(IEntityIdentifier e) {
        targets.add(e);
        changed = true;
    }

    @Override
    public void apply() {
        if (!changed) return;
        super.rawPacket.getIntegerArrays().write(0, targets.stream().mapToInt(IEntityIdentifier::getEntityID)
                .toArray());
    }

    @Override
    public PacketContainer getRawPacket() {
        assert targets.size() > 0;
        return super.getRawPacket();
    }
}