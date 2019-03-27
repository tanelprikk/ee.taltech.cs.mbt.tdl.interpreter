package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.declaration;

import ee.taltech.cs.mbt.tdl.generic.antlr_facade.converter.IParseTreeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.common.declaration.DeclarationConverter;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageBaseVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.DeclarationContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.UtaDeclarationsContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.AbsDeclarationStatement;

import java.util.LinkedList;
import java.util.List;

public class DeclarationsConverter extends UtaLanguageBaseVisitor<List<AbsDeclarationStatement>>
	implements IParseTreeConverter<List<AbsDeclarationStatement>, UtaDeclarationsContext>
{
	public static DeclarationsConverter getInstance() {
		return INSTANCE;
	}

	private static final DeclarationsConverter INSTANCE = new DeclarationsConverter();

	private DeclarationsConverter() { }

	@Override
	public List<AbsDeclarationStatement> convert(UtaDeclarationsContext rootContext) {
		List<AbsDeclarationStatement> declarationStatements = new LinkedList<>();
		if (rootContext.declarationSequence() != null) {
			for (DeclarationContext declCtx : rootContext.declarationSequence().declaration()) {
				declarationStatements.add(DeclarationConverter.getInstance().convert(declCtx));
			}
		}
		return declarationStatements;
	}
}