package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.system.transition;

import ee.taltech.cs.mbt.tdl.generic.antlr_facade.converter.IParseTreeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.common.type.TypeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageBaseVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.SelectionContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.identifier.IdentifierName;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.type.Type;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.type.type_identifier.AbsTypeIdentifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.system.selection.Selection;

public class SelectionConverter extends UtaLanguageBaseVisitor<Selection<AbsTypeIdentifier>>
	implements IParseTreeConverter<Selection<AbsTypeIdentifier>, SelectionContext>
{
	public static SelectionConverter getInstance() {
		return INSTANCE;
	}

	private static final SelectionConverter INSTANCE = new SelectionConverter();

	private SelectionConverter() { }

	@Override
	public Selection<AbsTypeIdentifier> convert(SelectionContext rootContext) {
		Selection<AbsTypeIdentifier> selection = new Selection<>();
		Type<AbsTypeIdentifier> sourceType = TypeConverter.getInstance().convert(rootContext.type());
		IdentifierName targetIdentifier = new IdentifierName();
		targetIdentifier.setName(rootContext.IDENTIFIER_NAME().getText());
		selection.setSelectionSourceType(sourceType);
		selection.setVariable(targetIdentifier);
		return selection;
	}
}
