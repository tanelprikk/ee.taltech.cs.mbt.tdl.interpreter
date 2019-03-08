package ee.taltech.cs.mbt.tdl.expression.model.expression_tree.structure.generic;

import ee.taltech.cs.mbt.tdl.expression.model.expression_tree.structure.generic.node.AbsExpressionNode;

public abstract class AbsExpressionTree<R extends AbsExpressionNode> {
	private R rootNode;

	public R getRootNode() {
		return rootNode;
	}

	public void setRootNode(R rootNode) {
		this.rootNode = rootNode;
	}
}