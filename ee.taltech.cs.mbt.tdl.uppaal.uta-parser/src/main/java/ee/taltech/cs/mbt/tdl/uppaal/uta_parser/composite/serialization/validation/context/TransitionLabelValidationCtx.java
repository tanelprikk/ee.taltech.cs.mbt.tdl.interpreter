package ee.taltech.cs.mbt.tdl.uppaal.uta_parser.composite.serialization.validation.context;

import ee.taltech.cs.mbt.tdl.uppaal.uta_model.structure.labels.AbsUtaLabel;

public class TransitionLabelValidationCtx extends AbsLabelValidationCtx<TransitionValidationCtx> {
	TransitionLabelValidationCtx(AbsUtaLabel<?> contextObject, TransitionValidationCtx parentCtx) {
		super(contextObject, parentCtx);
	}

	@Override
	public String getName() {
		return "transition label ("
					+ getContextObject().getClass().getSimpleName()
				+ ")";
	}
}
