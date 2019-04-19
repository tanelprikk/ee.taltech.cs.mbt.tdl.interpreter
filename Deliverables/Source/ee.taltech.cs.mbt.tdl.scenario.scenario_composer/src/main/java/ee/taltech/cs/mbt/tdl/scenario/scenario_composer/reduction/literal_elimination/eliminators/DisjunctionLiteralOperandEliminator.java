package ee.taltech.cs.mbt.tdl.scenario.scenario_composer.reduction.literal_elimination.eliminators;

import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.generic.AbsBooleanInternalNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.BooleanValueWrapperNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.DisjunctionNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.generic.TdlExpression;

import java.util.Deque;

public class DisjunctionLiteralOperandEliminator extends AbsLiteralEliminator<DisjunctionNode> {
	public static DisjunctionLiteralOperandEliminator getInstance(
			TdlExpression expression,
			DisjunctionNode parentNode,
			BooleanValueWrapperNode childLeaf,
			Deque<BooleanValueWrapperNode> remainingLeaves
	) {
		return new DisjunctionLiteralOperandEliminator(expression, parentNode, childLeaf, remainingLeaves);
	}

	private DisjunctionLiteralOperandEliminator(
			TdlExpression expression,
			DisjunctionNode parentNode,
			BooleanValueWrapperNode childLeaf,
			Deque<BooleanValueWrapperNode> remainingLeaves
	) {
		super(expression, parentNode, childLeaf, remainingLeaves);
	}

	@Override
	protected void eliminate(
			TdlExpression expression,
			DisjunctionNode parentNode,
			BooleanValueWrapperNode childLeaf,
			Deque<BooleanValueWrapperNode> remainingLeaves
	) {
		AbsBooleanInternalNode rightChild = parentNode.getChildContainer().getRightChild();
		AbsBooleanInternalNode leftChild = parentNode.getChildContainer().getLeftChild();
		boolean rightChildIsBoolValue = rightChild == childLeaf;

		if (childLeaf.wrapsTrue()) { // True or x ==> True.
			BooleanValueWrapperNode replacementNode = BooleanValueWrapperNode.trueWrapper();
			expression.replaceDescendant(parentNode, replacementNode);
			remainingLeaves.addFirst(replacementNode);
		} else { // False or x ==> x.
			expression.replaceDescendant(parentNode, rightChildIsBoolValue ? leftChild : rightChild);
		}
	}
}
