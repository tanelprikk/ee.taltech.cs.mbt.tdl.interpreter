package ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes;

import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.SExpression;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SExpressionSequenceNode extends AbsSExpressionNode implements Iterable<AbsSExpressionNode> {
	private List<AbsSExpressionNode> children = new LinkedList<>();

	public List<AbsSExpressionNode> getChildren() {
		return children;
	}

	public SExpressionSequenceNode addChild(AbsSExpressionNode child) {
		children.add(child);
		child.setParent(this);
		return this;
	}

	public SExpressionSequenceNode addChild(SExpression subtree) {
		children.add(subtree.getRoot());
		subtree.getRoot().setParent(this);
		subtree.setRoot(null);
		return this;
	}

	@Override
	public <T> T accept(ISExpressionVisitor<T> visitor) {
		return visitor.visitSequence(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof SExpressionSequenceNode))
			return false;
		SExpressionSequenceNode other = (SExpressionSequenceNode) obj;
		return Objects.equals(other.children, this.children);
	}

	@Override
	public Iterator<AbsSExpressionNode> iterator() {
		return getChildren().iterator();
	}
}
