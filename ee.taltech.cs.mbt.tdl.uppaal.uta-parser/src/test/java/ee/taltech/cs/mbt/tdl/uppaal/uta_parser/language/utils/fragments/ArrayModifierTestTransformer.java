package ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.utils.fragments;

import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionSequenceNode;
import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionStringNode;
import ee.taltech.cs.mbt.tdl.commons.test.test_utils.test_plan.pipeline.ISimpleTransformer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.AbsExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.misc.array_modifier.AbsArrayModifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.misc.array_modifier.SizeExpressionArrayModifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.misc.array_modifier.SizeTypeArrayModifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.BaseType;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.IArrayModifierVisitor;

public class ArrayModifierTestTransformer implements ISimpleTransformer {
	private class TransformerVisitor implements IArrayModifierVisitor<SExpressionSequenceNode> {
		@Override
		public SExpressionSequenceNode visitSizeExpressionModifier(SizeExpressionArrayModifier modifier) {
			AbsExpression expression = modifier.getSizeSpecifier();
			return new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("ARRSIZEEXPR"))
					.addChild(((SExpressionSequenceNode) new ExpressionTestTransformer().transform(expression)));
		}

		@Override
		public SExpressionSequenceNode visitSizeTypeModifier(SizeTypeArrayModifier modifier) {
			BaseType baseType = modifier.getSizeSpecifier();
			return new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("ARRSIZETYPE"))
					.addChild(((SExpressionSequenceNode) new BaseTypeTestTransformer().transform(baseType)));
		}
	}

	@Override
	public Object transform(Object in) {
		AbsArrayModifier arrayModifier = (AbsArrayModifier) in;
		return new SExpressionSequenceNode()
				.addChild(new SExpressionStringNode().setString("ARRAYMODIFIER"))
				.addChild((SExpressionSequenceNode) arrayModifier.accept(new TransformerVisitor()));
	}
}
