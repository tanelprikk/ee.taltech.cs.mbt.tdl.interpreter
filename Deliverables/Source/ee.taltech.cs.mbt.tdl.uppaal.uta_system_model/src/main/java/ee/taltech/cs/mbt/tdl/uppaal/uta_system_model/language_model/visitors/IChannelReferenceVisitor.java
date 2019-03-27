package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.visitors;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.channel_priority.channel_reference.ChannelArrayLookup;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.channel_priority.channel_reference.ChannelIdentifierReference;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.channel_priority.channel_reference.DefaultChannelReference;

public interface IChannelReferenceVisitor<T> {
	T visitChannelArrayLookup(ChannelArrayLookup ref);
	T visitChannelIdentifierReference(ChannelIdentifierReference ref);
	T visitDefaultChannelReference(DefaultChannelReference ref);
}
