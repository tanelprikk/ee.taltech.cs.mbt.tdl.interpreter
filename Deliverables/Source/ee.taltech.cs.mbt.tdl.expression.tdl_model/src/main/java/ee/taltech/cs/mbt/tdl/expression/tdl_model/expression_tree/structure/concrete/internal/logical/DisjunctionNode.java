package ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical;

import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.generic.AbsLogicalOperatorNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.ILogicalOperatorVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.ITdlExpressionVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.generic.node.internal.BinaryChildContainer;

public class DisjunctionNode extends AbsLogicalOperatorNode<
			AbsLogicalOperatorNode,
		BinaryChildContainer<AbsLogicalOperatorNode>
		> {
	public DisjunctionNode() {
		super(new BinaryChildContainer<>());
	}

	@Override
	public <T> T accept(ITdlExpressionVisitor<T> visitor) {
		return visitor.visitDisjunction(this);
	}

	@Override
	public <T> T accept(ILogicalOperatorVisitor<T> visitor) {
		return visitor.visitDisjunction(this);
	}

	@Override
	public DisjunctionNode deepClone() {
		DisjunctionNode clone = new DisjunctionNode();
		clone.setNegated(isNegated());
		clone.getChildContainer().setLeftOperand(getChildContainer().getLeftOperand().deepClone());
		clone.getChildContainer().setRightOperand(getChildContainer().getRightOperand().deepClone());
		return clone;
	}
}
