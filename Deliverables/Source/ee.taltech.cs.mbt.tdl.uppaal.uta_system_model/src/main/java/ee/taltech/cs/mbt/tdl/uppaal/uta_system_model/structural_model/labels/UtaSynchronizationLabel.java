package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.labels;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.template.Synchronization;

public class UtaSynchronizationLabel extends AbsUtaLabel<Synchronization> {
	public static UtaSynchronizationLabel of(Synchronization synchronization) {
		UtaSynchronizationLabel inst = new UtaSynchronizationLabel();
		inst.setContent(synchronization);
		return inst;
	}

	public boolean isOutbound() {
		return getContent().isActiveSync();
	}

	public void setOutbound(boolean outbound) {
		getContent().setActiveSync(outbound);
	}
}
