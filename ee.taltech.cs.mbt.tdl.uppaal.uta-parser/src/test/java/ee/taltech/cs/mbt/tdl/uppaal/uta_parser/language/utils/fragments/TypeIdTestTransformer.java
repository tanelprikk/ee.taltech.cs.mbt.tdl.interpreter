package ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.utils.fragments;

import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionSequenceNode;
import ee.taltech.cs.mbt.tdl.commons.test.sexpr.s_expression_model.nodes.SExpressionStringNode;
import ee.taltech.cs.mbt.tdl.commons.test.test_utils.test_plan.pipeline.ISimpleTransformer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.AbsTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.BaseTypeIdentifiers.BooleanTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.BaseTypeIdentifiers.ChannelTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.BaseTypeIdentifiers.ClockTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.BaseTypeIdentifiers.IntegerTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.BoundedIntegerTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.CustomTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.ScalarTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.StructTypeId;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.identifier.field.AbsFieldDeclaration;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.ITypeIdentifierVisitor;

public class TypeIdTestTransformer implements ISimpleTransformer {
	private class TransformerVisitor implements ITypeIdentifierVisitor<SExpressionSequenceNode> {
		private SExpressionSequenceNode wrap(SExpressionSequenceNode seqNode) {
			return new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("TYPEID"))
					.addChild(seqNode);
		}

		@Override
		public SExpressionSequenceNode visitStructTypeIdentifier(StructTypeId id) {
			SExpressionSequenceNode fieldDeclSeq = new SExpressionSequenceNode();
			for (AbsFieldDeclaration fieldDeclaration : id.getFieldDeclarations()) {
				fieldDeclSeq.addChild((SExpressionSequenceNode) new FieldDeclarationTestTransformer().transform(fieldDeclaration));
			}
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("STRUCT"))
					.addChild(fieldDeclSeq)
			);
		}

		@Override
		public SExpressionSequenceNode visitScalarTypeIdentifier(ScalarTypeId id) {
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("SCALAR"))
					.addChild(new SExpressionSequenceNode()
							.addChild((SExpressionSequenceNode) new ExpressionTestTransformer().transform(id.getSizeExpression()))
					)
			);
		}

		@Override
		public SExpressionSequenceNode visitBoundedIntegerTypeIdentifier(BoundedIntegerTypeId id) {
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("BOUNDEDINT"))
					.addChild(new SExpressionSequenceNode()
							.addChild((SExpressionSequenceNode) new ExpressionTestTransformer().transform(id.getMinimumBound()))
							.addChild((SExpressionSequenceNode) new ExpressionTestTransformer().transform(id.getMaximumBound()))
					)
			);
		}

		@Override
		public SExpressionSequenceNode visitBooleanTypeIdentifier(BooleanTypeId id) {
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("BOOLEAN"))
			);
		}

		@Override
		public SExpressionSequenceNode visitIntegerTypeIdentifier(IntegerTypeId id) {
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("INT"))
			);
		}

		@Override
		public SExpressionSequenceNode visitChannelTypeIdentifier(ChannelTypeId id) {
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("CHANNEL"))
			);
		}

		@Override
		public SExpressionSequenceNode visitClockTypeIdentifier(ClockTypeId id) {
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("CLOCK"))
			);
		}

		@Override
		public SExpressionSequenceNode visitCustomTypeIdentifier(CustomTypeId id) {
			return wrap(new SExpressionSequenceNode()
					.addChild(new SExpressionStringNode().setString("CUSTOM"))
					.addChild(new SExpressionStringNode().setString(id.getIdentifier().toString()))
			);
		}
	}

	@Override
	public Object transform(Object in) {
		AbsTypeId typeId = (AbsTypeId) in;
		return typeId.accept(new TransformerVisitor());
	}
}
