package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.arithmetic;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.generic.IHasAssignmentCounterpart;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.generic.internal.AbsBinaryExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.traversal.IExpressionVisitor;

public class DivisionExpression extends AbsBinaryExpression implements IHasAssignmentCounterpart {
	@Override
	public Associativity getAssociativity() {
		return Associativity.LEFT;
	}

	@Override
	public void accept(IExpressionVisitor visitor) {
		visitor.visitDivision(this);
	}
}
