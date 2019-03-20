package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.statement.declaration.channel_priority.channel_reference.visitation;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.statement.declaration.channel_priority.channel_reference.ChannelArrayLookup;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.statement.declaration.channel_priority.channel_reference.ChannelIdentifierRef;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.statement.declaration.channel_priority.channel_reference.DefaultChannelPriorityRef;

public interface IChannelRefVisitor {
	void visitChannelArrayLookup(ChannelArrayLookup channelArrayLookup);
	void visitChannelIdentifierRef(ChannelIdentifierRef channelIdentifierRef);
	void visitDefaultChannelPriorityRef(DefaultChannelPriorityRef defaultChannelPriorityRef);
}
