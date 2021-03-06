package ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.antlr.converter.template;

import ee.taltech.cs.mbt.tdl.commons.facades.antlr_facade.converter.IParseTreeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageBaseVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.SelectionContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.identifier.Identifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.template.Selection;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.type.BaseType;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.antlr.converter.type.BaseTypeConverter;

public class SelectionConverter extends UtaLanguageBaseVisitor<Selection>
		implements IParseTreeConverter<Selection, SelectionContext> {
	public static SelectionConverter getInstance() {
		return INSTANCE;
	}

	private static final SelectionConverter INSTANCE = new SelectionConverter();

	private SelectionConverter() { }

	@Override
	public Selection convert(SelectionContext ctx) {
		BaseType sourceType = BaseTypeConverter.getInstance().convert(ctx.type());

		Identifier variableIdentifier = Identifier.of(ctx.IDENTIFIER_NAME().getText());
		Selection selection = new Selection();
		selection.setSelectType(sourceType);
		selection.setVariableName(variableIdentifier);

		return selection;
	}
}
