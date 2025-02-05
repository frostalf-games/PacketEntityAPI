package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;

/**
 * Created by iso2013 on 8/16/2018.
 */
public class NbtCompoundModifier extends GenericModifier<NbtCompound> {
    private final WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.getNBTCompoundSerializer();

    public NbtCompoundModifier(int index, String label, NbtCompound def) {
        super(null, index, label, def);
    }

    @Override
    public NbtCompound getValue(IModifiableEntity target) {
        return (NbtCompound) target.read(super.index);
    }

    @Override
    public void setValue(IModifiableEntity target, NbtCompound newValue) {
        target.write(super.index, newValue, serializer);
    }

    @Override
    public Class<NbtCompound> getFieldType() {
        return NbtCompound.class;
    }
}
