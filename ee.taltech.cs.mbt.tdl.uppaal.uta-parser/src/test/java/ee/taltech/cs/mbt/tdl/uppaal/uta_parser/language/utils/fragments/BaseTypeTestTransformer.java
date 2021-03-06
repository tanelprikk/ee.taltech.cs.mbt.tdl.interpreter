package ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.utils.fragments;

import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionSequenceNode;
import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionStringNode;
import ee.taltech.cs.mbt.tdl.commons.test.test_utils.test_plan.pipeline.ISimpleTransformer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.BaseType;

public class BaseTypeTestTransformer implements ISimpleTransformer {
	@Override
	public Object transform(Object in) {
		BaseType baseType = (BaseType) in;
		String prefix;
		switch (baseType.getPrefix()) {
		case URGENT:
			prefix = "URGENT";
			break;
		case META:
			prefix = "META";
			break;
		case CONSTANT:
			prefix = "CONSTANT";
			break;
		case BROADCAST:
			prefix = "BROADCAST";
			break;
		case NONE:
		default:
			prefix = "NOPREFIX";
			break;
		}
		return new SExpressionSequenceNode()
				.addChild(new SExpressionStringNode().setString("BASETYPE"))
				.addChild(new SExpressionSequenceNode()
						.addChild(new SExpressionStringNode().setString(prefix))
						.addChild((SExpressionSequenceNode) new TypeIdTestTransformer().transform(baseType.getTypeId()))
				);
	}
}
