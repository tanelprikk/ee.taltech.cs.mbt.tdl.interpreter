package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.labels;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.uta_containers.template.transition.synchronization.UTATransitionSynchronization;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.labels.generic.AbsUTAGuiLabel;

public class UTASynchronizationLabel extends AbsUTAGuiLabel<UTATransitionSynchronization> {
	public boolean isOutbound() {
		return getContent().isActiveSync();
	}

	public void setOutbound(boolean outbound) {
		getContent().setActiveSync(outbound);
	}
}