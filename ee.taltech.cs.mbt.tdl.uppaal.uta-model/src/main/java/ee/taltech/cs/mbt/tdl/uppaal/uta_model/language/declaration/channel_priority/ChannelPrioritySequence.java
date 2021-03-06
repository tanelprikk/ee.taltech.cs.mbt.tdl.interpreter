package ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.declaration.channel_priority;

import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.declaration.AbsDeclarationStatement;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.IDeclarationVisitor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Global declarations can contain at most one channel priority declaration.<br/>
 * A channel priority declaration consists of a series of channel lists.<br/>
 * The less-than separator defines a higher level for channels sequenced on its right side.<br/>
 * The keyword 'default' represents channels that are not mentioned.<br/>
 * Note that channels listed in a channel priority declaration must be declared earlier.<br/>
 * <br/>
 * Syntax:<br/>
 * <pre>
 * ChanPriority ::= 'chan' 'priority' (ChanExpr | 'default') ((',' | '<') (ChanExpr | 'default'))* ';'
 * ChanExpr ::= ID
 *           | ChanExpr '[' Expression ']'
 * </pre>
 */
public class ChannelPrioritySequence extends AbsDeclarationStatement implements Iterable<ChannelReferenceGroup> {
	private List<ChannelReferenceGroup> prioritySequence = new LinkedList<>();

	public List<ChannelReferenceGroup> getPrioritySequence() {
		return prioritySequence;
	}

	public ChannelPrioritySequence addGroup(ChannelReferenceGroup group) {
		getPrioritySequence().add(group);
		return this;
	}

	@Override
	public Iterator<ChannelReferenceGroup> iterator() {
		return prioritySequence.iterator();
	}

	@Override
	public <T> T accept(IDeclarationVisitor<T> visitor) {
		return visitor.visitChannelPriorityDeclaration(this);
	}

	@Override
	public ChannelPrioritySequence deepClone() {
		ChannelPrioritySequence clone = new ChannelPrioritySequence();
		prioritySequence.stream().forEachOrdered(
				p -> clone.prioritySequence.add(p.deepClone())
		);
		return clone;
	}
}
