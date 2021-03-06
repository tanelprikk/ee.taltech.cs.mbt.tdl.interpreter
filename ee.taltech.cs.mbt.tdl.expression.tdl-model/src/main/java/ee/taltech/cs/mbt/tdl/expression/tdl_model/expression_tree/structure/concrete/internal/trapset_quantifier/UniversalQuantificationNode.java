package ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.trapset_quantifier;

import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.generic.AbsTrapsetQuantifierNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.IBooleanNodeVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.ITdlExpressionVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.ITrapsetQuantifierVisitor;

public class UniversalQuantificationNode extends AbsTrapsetQuantifierNode {
	@Override
	public <T> T accept(ITdlExpressionVisitor<T> visitor) {
		return visitor.visitUniversalQuantification(this);
	}

	@Override
	public <T> T accept(IBooleanNodeVisitor<T> visitor) {
		return visitor.visitUniversalQuantification(this);
	}

	@Override
	public <T> T accept(ITrapsetQuantifierVisitor<T> visitor) {
		return visitor.visitUniversal(this);
	}

	@Override
	public UniversalQuantificationNode deepClone() {
		UniversalQuantificationNode clone = new UniversalQuantificationNode();
		clone.setNegated(isNegated());
		clone.getChildContainer().setChild(getChildContainer().getChild().deepClone());
		return clone;
	}

	@Override
	public String getHumanReadableName() {
		return "Trapset Universal Quantification";
	}
}
