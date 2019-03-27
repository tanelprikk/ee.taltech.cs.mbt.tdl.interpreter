package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.common.declaration;

import ee.taltech.cs.mbt.tdl.generic.antlr_facade.converter.IParseTreeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.antlr.converter.common.expression.ExpressionConverter;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageBaseVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.FlatInitializerContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.InitializerExpressionContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.StructuredInitializerContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.variable.initializer.AbsVariableInitializer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.variable.initializer.FlatVariableInitializer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.variable.initializer.StructuredVariableInitializer;

public class InitializerExpressionConverter extends UtaLanguageBaseVisitor<AbsVariableInitializer>
	implements IParseTreeConverter<AbsVariableInitializer, InitializerExpressionContext>
{
	public static InitializerExpressionConverter getInstance() {
		return INSTANCE;
	}

	private static final InitializerExpressionConverter INSTANCE = new InitializerExpressionConverter();

	private InitializerExpressionConverter() { }

	@Override
	public AbsVariableInitializer convert(InitializerExpressionContext rootContext) {
		return rootContext.accept(this);
	}

	@Override
	public AbsVariableInitializer visitFlatInitializer(FlatInitializerContext ctx) {
		FlatVariableInitializer flatInitializer = new FlatVariableInitializer();
		flatInitializer.setExpression(
			ExpressionConverter.getInstance().convert(ctx.expression())
		);
		return flatInitializer;
	}

	@Override
	public AbsVariableInitializer visitStructuredInitializer(StructuredInitializerContext ctx) {
		StructuredVariableInitializer structuredInitializer = new StructuredVariableInitializer();
		for (InitializerExpressionContext initExprCtx : ctx.initializerExpression()) {
			structuredInitializer.getInitializers().add(
				initExprCtx.accept(this)
			);
		}
		return structuredInitializer;
	}
}