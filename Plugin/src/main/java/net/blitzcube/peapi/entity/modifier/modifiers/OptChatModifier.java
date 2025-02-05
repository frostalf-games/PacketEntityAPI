package net.blitzcube.peapi.entity.modifier.modifiers;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.blitzcube.peapi.api.entity.modifier.IModifiableEntity;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.Optional;

/**
 * Created by iso2013 on 8/20/2018.
 */
public class OptChatModifier extends OptModifier<BaseComponent[]> {
    private final WrappedDataWatcher.Serializer serializer =
            WrappedDataWatcher.Registry.getChatComponentSerializer(true);
    private final PseudoStringModifier pseudoStringModifier = new PseudoStringModifier(this);

    public OptChatModifier(int index, String label, Optional<BaseComponent[]> def) {
        super(null, index, label, def);
    }

    @Override
    @SuppressWarnings({"unchecked", "deprecation"})
    public Optional<BaseComponent[]> getValue(IModifiableEntity target) {
        Object val = target.read(super.index);
        if (val == null) return null;
        if (!(val instanceof Optional))
            throw new IllegalStateException("Read inappropriate type from modifiable entity!");
        Optional<WrappedChatComponent> bp = (Optional<WrappedChatComponent>) val;
        return bp.map(wrappedChatComponent -> ComponentSerializer.parse(wrappedChatComponent.getJson()));
    }

    @Override
    public void setValue(IModifiableEntity target, Optional<BaseComponent[]> newValue) {
        if (newValue != null) {
            if (newValue.isPresent()) {
                BaseComponent[] v = newValue.get();
                WrappedChatComponent wcc = WrappedChatComponent.fromJson(ComponentSerializer.toString(v));

                target.write(
                        index,
                        Optional.of(wcc.getHandle()),
                        serializer
                );
            } else {
                target.write(super.index, Optional.empty(), serializer);
            }
        } else super.unsetValue(target);
    }

    @Override
    public Class<BaseComponent[]> getFieldType() {
        return BaseComponent[].class;
    }

    public PseudoStringModifier asPseudoStringModifier() {
        return pseudoStringModifier;
    }
}
