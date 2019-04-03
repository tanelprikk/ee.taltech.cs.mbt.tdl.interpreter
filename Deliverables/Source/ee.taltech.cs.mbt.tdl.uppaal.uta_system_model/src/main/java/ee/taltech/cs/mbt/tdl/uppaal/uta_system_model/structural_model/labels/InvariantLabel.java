package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.labels;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.expression.generic.AbsExpression;

public class InvariantLabel extends AbsUtaLabel<AbsExpression> {
	public static InvariantLabel of(AbsExpression expression) {
		InvariantLabel inst = new InvariantLabel();
		inst.setContent(expression);
		return inst;
	}
}
