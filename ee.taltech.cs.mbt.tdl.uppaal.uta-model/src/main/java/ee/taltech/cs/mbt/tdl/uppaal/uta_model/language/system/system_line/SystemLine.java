package ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.system.system_line;

import ee.taltech.cs.mbt.tdl.commons.utils.objects.IDeepCloneable;
import ee.taltech.cs.mbt.tdl.commons.utils.objects.IMergeable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SystemLine implements Iterable<ProcessReferenceGroup>, IMergeable<SystemLine>, IDeepCloneable<SystemLine> {
	private List<ProcessReferenceGroup> processPrioritySequence = new LinkedList<>();

	public List<ProcessReferenceGroup> getProcessPrioritySequence() {
		return processPrioritySequence;
	}

	public SystemLine addGroup(ProcessReferenceGroup group) {
		getProcessPrioritySequence().add(group);
		return this;
	}

	@Override
	public Iterator<ProcessReferenceGroup> iterator() {
		return processPrioritySequence.iterator();
	}

	@Override
	public void merge(SystemLine other) {
		if (other.processPrioritySequence.isEmpty())
			return;

		if (processPrioritySequence.isEmpty()) {
			processPrioritySequence.addAll(other.processPrioritySequence);
		} else {
			if (processPrioritySequence.size() == 1 && other.processPrioritySequence.size() == 1) {
				processPrioritySequence.get(0).getProcessIdentifiers()
						.addAll(other.processPrioritySequence.get(0).getProcessIdentifiers());
			} else {
				processPrioritySequence.addAll(other.processPrioritySequence);
			}
		}
	}

	@Override
	public SystemLine deepClone() {
		SystemLine clone = new SystemLine();
		processPrioritySequence.stream()
				.forEachOrdered(s -> clone.processPrioritySequence.add(s.deepClone()));
		return clone;
	}
}
