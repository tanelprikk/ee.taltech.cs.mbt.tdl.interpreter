package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.common.identifier;

import ee.taltech.cs.mbt.tdl.generic.antlr_facade.converter.IParseTreeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.common.identifier.IdentifierVariantConverter.IdentifierData;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.common.identifier.ParameterIdentifierConverter.ParameterIdentifierData;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageBaseVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.ByReferenceVariableContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.ByValueVariableContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.IdentifierNameVariantContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.ParameterIdentifierContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.identifier.Identifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.misc.array_size_modifier.AbsArrayModifier;

import java.util.Collection;
import java.util.LinkedList;

public class ParameterIdentifierConverter
	extends UtaLanguageBaseVisitor<ParameterIdentifierData>
	implements IParseTreeConverter<ParameterIdentifierData, ParameterIdentifierContext>
{
	public static ParameterIdentifierConverter getInstance() {
		return INSTANCE;
	}

	private static final ParameterIdentifierConverter INSTANCE = new ParameterIdentifierConverter();

	private ParameterIdentifierConverter() { }

	public static class ParameterIdentifierData {
		private boolean byReference;
		private Identifier identifier;
		private Collection<AbsArrayModifier> arrayModifiers = new LinkedList<>();

		public boolean isByReference() {
			return byReference;
		}

		public void setByReference(boolean byReference) {
			this.byReference = byReference;
		}

		public Identifier getIdentifier() {
			return identifier;
		}

		public void setIdentifier(Identifier identifier) {
			this.identifier = identifier;
		}

		public Collection<AbsArrayModifier> getArrayModifiers() {
			return arrayModifiers;
		}
	}

	@Override
	public ParameterIdentifierData convert(ParameterIdentifierContext rootContext) {
		return rootContext.accept(this);
	}

	@Override
	public ParameterIdentifierData visitByValueVariable(ByValueVariableContext ctx)
	{
		ParameterIdentifierData result = visitIdentifierNameVariant(ctx.identifierNameVariant());
		result.setByReference(false);
		return result;
	}

	@Override
	public ParameterIdentifierData visitByReferenceVariable(ByReferenceVariableContext ctx)
	{
		ParameterIdentifierData result = visitIdentifierNameVariant(ctx.identifierNameVariant());
		result.setByReference(true);
		return result;
	}

	private ParameterIdentifierData visitIdentifierNameVariant(
		IdentifierNameVariantContext identifierNameVariantContext) {
		IdentifierData identifierData = IdentifierVariantConverter.getInstance().convert(identifierNameVariantContext);
		ParameterIdentifierData parameterIdentifierData = new ParameterIdentifierData();
		parameterIdentifierData.setIdentifier(identifierData.getIdentifier());
		parameterIdentifierData.getArrayModifiers().addAll(identifierData.getArrayModifiers());
		return parameterIdentifierData;
	}
}