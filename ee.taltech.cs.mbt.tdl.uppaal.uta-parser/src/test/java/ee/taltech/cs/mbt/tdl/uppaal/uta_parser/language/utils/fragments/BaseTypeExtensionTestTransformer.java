package ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.utils.fragments;

import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionSequenceNode;
import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionStringNode;
import ee.taltech.cs.mbt.tdl.commons.test.test_utils.test_plan.pipeline.ISimpleTransformer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.misc.BaseTypeExtensionMap;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.misc.BaseTypeExtensionMap.BaseTypeExtension;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.misc.array_modifier.AbsArrayModifier;

public class BaseTypeExtensionTestTransformer implements ISimpleTransformer {
	@Override
	public Object transform(Object in) {
		BaseTypeExtensionMap extensionMap = (BaseTypeExtensionMap) in;
		SExpressionSequenceNode baseTypeNode = new SExpressionSequenceNode()
				.addChild((SExpressionSequenceNode) new BaseTypeTestTransformer().transform(extensionMap.getBaseType()));
		SExpressionSequenceNode extensions = new SExpressionSequenceNode();
		for (BaseTypeExtension baseTypeExtension : extensionMap.collectionView()) {
			SExpressionSequenceNode arrayModifiers = new SExpressionSequenceNode();
			for (AbsArrayModifier arrayModifier : baseTypeExtension.getArrayModifiers()) {
				arrayModifiers.addChild((SExpressionSequenceNode) new ArrayModifierTestTransformer().transform(arrayModifier));
			}
			extensions.addChild(
					new SExpressionSequenceNode()
							.addChild(new SExpressionStringNode().setString(baseTypeExtension.getIdentifier().toString()))
							.addChild(arrayModifiers)
			);
		}
		return new SExpressionSequenceNode().addChild(new SExpressionStringNode().setString("BASETYPEEXT"))
				.addChild(new SExpressionSequenceNode().addChild(baseTypeNode)
				.addChild(extensions)
		);
	}
}
