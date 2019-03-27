package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.statement;

import ee.taltech.cs.mbt.tdl.generic.antlr_facade.converter.IParseTreeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageBaseVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.StatementContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.StatementSequenceContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.statement.AbsStatement;

import java.util.LinkedList;
import java.util.List;

public class StatementSequenceConverter extends UtaLanguageBaseVisitor<List<AbsStatement>>
	implements IParseTreeConverter<List<AbsStatement>, StatementSequenceContext> {
	public static StatementSequenceConverter getInstance() {
		return INSTANCE;
	}

	private static final StatementSequenceConverter INSTANCE = new StatementSequenceConverter();

	private StatementSequenceConverter() { }

	@Override
	public List<AbsStatement> convert(StatementSequenceContext ctx) {
		List<AbsStatement> statements = new LinkedList<>();

		for (StatementContext stmtCtx : ctx.statement()) {
			statements.add(StatementConverter.getInstance().convert(stmtCtx));
		}

		return statements;
	}
}
