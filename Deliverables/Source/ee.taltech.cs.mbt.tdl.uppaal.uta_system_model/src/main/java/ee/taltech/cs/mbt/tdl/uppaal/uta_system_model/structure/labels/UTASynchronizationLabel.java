package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.labels;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.system.Synchronization;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.labels.generic.AbsUTAGuiLabel;

public class UTASynchronizationLabel extends AbsUTAGuiLabel<Synchronization> {
	public boolean isOutbound() {
		return getContent().isActiveSync();
	}

	public void setOutbound(boolean outbound) {
		getContent().setActiveSync(outbound);
	}
}
