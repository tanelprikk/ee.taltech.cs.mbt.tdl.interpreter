package ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.evaluation.impl;

import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.trapset_expression.RelativeComplementNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.leaf.trapset.TrapsetNode;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.evaluation.AbsTrapsetExpressionEvaluator;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.utils.UTAExpressionUtils;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.base.BaseTrapset;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.evaluated.impl.RelativeComplementTrapsetEvaluation;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.evaluated.AbsTrapsetEvaluation;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trap.BaseTrap;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.UtaSystem;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.AbsExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.AssignmentExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.ConjunctionExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.IdentifierExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.identifier.Identifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.structure.templates.Template;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.structure.transitions.Transition;

import java.util.Map;

public class RelativeComplementEvaluator extends AbsTrapsetExpressionEvaluator<RelativeComplementNode> {
	private static final String RELATIVE_COMPLEMENT_NAME_MODIFIER = "_RelativeComplementWith_";

	public static RelativeComplementEvaluator getInstance(
			UtaSystem system,
			RelativeComplementNode trapsetDerivingNode,
			Map<TrapsetNode, BaseTrapset> baseTrapsetMap
	) {
		return new RelativeComplementEvaluator(system, trapsetDerivingNode, baseTrapsetMap);
	}

	private RelativeComplementEvaluator(
			UtaSystem system,
			RelativeComplementNode trapsetDerivingNode,
			Map<TrapsetNode, BaseTrapset> baseTrapsetMap
	) {
		super(system, trapsetDerivingNode, baseTrapsetMap);
	}

	@Override
	protected AbsTrapsetEvaluation evaluate(
			UtaSystem system,
			RelativeComplementNode relativeComplement,
			Map<TrapsetNode, BaseTrapset> baseTrapsetMap
	) {
		TrapsetNode leftTrapsetNode = relativeComplement.getChildContainer().getLeftChild();
		TrapsetNode rightTrapsetNode = relativeComplement.getChildContainer().getRightChild();

		BaseTrapset includedTrapset = baseTrapsetMap.get(leftTrapsetNode);
		BaseTrapset excludedTrapset = baseTrapsetMap.get(rightTrapsetNode);

		Identifier trapsetName = Identifier.of(
				includedTrapset.getName() + RELATIVE_COMPLEMENT_NAME_MODIFIER + excludedTrapset.getName()
		);
		RelativeComplementTrapsetEvaluation resultTrapset = new RelativeComplementTrapsetEvaluation();
		resultTrapset.setName(trapsetName);

		for (Transition includedTransition : includedTrapset) {
			Template parentTemplate = includedTrapset.getParentTemplate(includedTransition);
			AbsExpression conditionExpr = includedTrapset.getMarkerCondition(includedTransition).deepClone();

			if (excludedTrapset.contains(includedTransition)) {
				// As long as the condition doesn't hold, the transition is excluded from excludedTrapset.
				if (!excludedTrapset.isConditional(includedTransition))
					continue;

				// Negate the inclusion expression:
				conditionExpr = new ConjunctionExpression()
						.setLeftChild(conditionExpr)
						.setRightChild(UTAExpressionUtils.negateCondition(
								excludedTrapset.getMarkerCondition(includedTransition).deepClone()
						));
			}

			AssignmentExpression markerExpr = (AssignmentExpression) new AssignmentExpression()
					.setLeftChild(IdentifierExpression.of(trapsetName))
					.setRightChild(conditionExpr);
			resultTrapset.addTrap(BaseTrap.of(parentTemplate, includedTransition, markerExpr));
		}

		return resultTrapset;
	}
}
