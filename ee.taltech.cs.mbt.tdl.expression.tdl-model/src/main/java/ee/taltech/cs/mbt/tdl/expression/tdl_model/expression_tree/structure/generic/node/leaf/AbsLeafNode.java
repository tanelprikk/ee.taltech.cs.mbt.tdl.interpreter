package ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.generic.node.leaf;

import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.generic.node.AbsExpressionNode;

public abstract class AbsLeafNode extends AbsExpressionNode {
	@Override
	public abstract AbsLeafNode deepClone();
}
