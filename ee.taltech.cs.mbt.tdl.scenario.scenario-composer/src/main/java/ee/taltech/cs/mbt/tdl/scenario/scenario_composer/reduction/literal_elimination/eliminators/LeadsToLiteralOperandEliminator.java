package ee.taltech.cs.mbt.tdl.scenario.scenario_composer.reduction.literal_elimination.eliminators;

import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.generic.AbsBooleanInternalNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.BooleanValueWrapperNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.LeadsToNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.generic.TdlExpression;

import java.util.Deque;

public class LeadsToLiteralOperandEliminator extends AbsLiteralOperandEliminator<LeadsToNode> {
	public static LeadsToLiteralOperandEliminator getInstance(
			TdlExpression expression,
			LeadsToNode parentNode,
			BooleanValueWrapperNode childLeaf,
			Deque<BooleanValueWrapperNode> remainingLeaves
	) {
		return new LeadsToLiteralOperandEliminator(expression, parentNode, childLeaf, remainingLeaves);
	}

	private LeadsToLiteralOperandEliminator(
			TdlExpression expression,
			LeadsToNode parentNode,
			BooleanValueWrapperNode childLeaf,
			Deque<BooleanValueWrapperNode> remainingLeaves
	) {
		super(expression, parentNode, childLeaf, remainingLeaves);
	}

	@Override
	protected void eliminate(
			TdlExpression expression,
			LeadsToNode leadsTo,
			BooleanValueWrapperNode childLeaf,
			Deque<BooleanValueWrapperNode> remainingLeaves
	) {
		// FIXME: Double-check.
		AbsBooleanInternalNode rightChild = leadsTo.getChildContainer().getRightChild();
		AbsBooleanInternalNode leftChild = leadsTo.getChildContainer().getLeftChild();
		boolean rightChildIsBoolValue = rightChild == childLeaf;

		if (childLeaf.wrapsTrue()) {
			if (rightChildIsBoolValue) {
				// p ~> True ==> p.
				expression.replaceDescendant(leadsTo, leftChild);
			} else {
				// True ~> p ==> p.
				expression.replaceDescendant(leadsTo, rightChild);
			}
		} else {
			// False ~> p ==> False.
			// p ~> False ==> False.
			BooleanValueWrapperNode replacementNode = BooleanValueWrapperNode.falseWrapper();
			expression.replaceDescendant(leadsTo, replacementNode);
			remainingLeaves.addFirst(replacementNode);
		}
	}
}