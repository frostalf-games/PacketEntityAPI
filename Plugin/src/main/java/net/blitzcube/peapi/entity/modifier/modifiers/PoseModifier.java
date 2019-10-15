package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import org.bukkit.entity.Pose;

/**
 * Created by iso2013 on 10/11/19.
 */
public class PoseModifier extends GenericModifier<Pose> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Integer.class);

    public PoseModifier(int index, String label, Pose def) {
        super(null, index, label, def);
    }

    @Override
    public Pose getValue(IModifiableEntity target) {
        return Pose.values()[(int) target.read(super.index)];
    }

    @Override
    public void setValue(IModifiableEntity target, Pose newValue) {
        if(newValue != null) {
            target.write(super.index, newValue.ordinal(), serializer);
        } else super.unsetValue(target);
    }

    @Override
    public Class<?> getFieldType() {
        return Pose.class;
    }
}