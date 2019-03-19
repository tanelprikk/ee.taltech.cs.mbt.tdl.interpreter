package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.statement.declaration.channel_priority;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.statement.declaration.channel_priority.channel_refs.AbsChannelRefExpression;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class ChannelRefSequence {
	private Set<AbsChannelRefExpression> channelIdentifiers = new LinkedHashSet<>();

	public Set<AbsChannelRefExpression> getChannelIdentifiers() {
		return channelIdentifiers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getChannelIdentifiers());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ChannelRefSequence))
			return false;
		ChannelRefSequence other = (ChannelRefSequence) obj;
		return Objects.equals(other.channelIdentifiers, this.channelIdentifiers);
	}
}