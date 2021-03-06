package ee.taltech.cs.mbt.tdl.expression.tdl_parser.antlr;

import ee.taltech.cs.mbt.tdl.commons.facades.antlr_facade.AbsAntlrParserFacade;
import ee.taltech.cs.mbt.tdl.expression.tdl_grammar.antlr_parser.TdlExpressionLanguageLexer;
import ee.taltech.cs.mbt.tdl.expression.tdl_grammar.antlr_parser.TdlExpressionLanguageParser;
import ee.taltech.cs.mbt.tdl.expression.tdl_grammar.antlr_parser.TdlExpressionLanguageParser.ExpressionContext;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.generic.TdlExpression;
import ee.taltech.cs.mbt.tdl.expression.tdl_parser.antlr.converter.ExpressionTreeConverter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenStream;

public class TdlParserFacade extends AbsAntlrParserFacade<
		TdlExpression,
		TdlExpressionLanguageParser,
		TdlExpressionLanguageLexer,
		ExpressionContext
> {
	@Override
	protected TdlExpressionLanguageParser getParserInstance(TokenStream tokenStream) {
		return new TdlExpressionLanguageParser(tokenStream);
	}

	@Override
	protected TdlExpressionLanguageLexer getLexerInstance(CharStream forInputStream) {
		return new TdlExpressionLanguageLexer(forInputStream);
	}

	@Override
	protected ExpressionContext getRootContext(TdlExpressionLanguageParser parser) {
		return parser.expression();
	}

	@Override
	protected ExpressionTreeConverter getConverter() {
		return ExpressionTreeConverter.getInstance();
	}

	public TdlParserFacade() {}
}
