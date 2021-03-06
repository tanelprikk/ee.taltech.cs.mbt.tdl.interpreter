package ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.antlr.converter.template;

import ee.taltech.cs.mbt.tdl.commons.facades.antlr_facade.converter.IParseTreeConverter;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageBaseVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.ActiveSynchronizationContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.ExpressionContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.ReactiveSynchronizationContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.antlr_parser.UtaLanguageParser.SynchronizationContext;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.template.Synchronization;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.language.antlr.converter.expression.ExpressionConverter;

public class SynchronizationConverter extends UtaLanguageBaseVisitor<Synchronization>
		implements IParseTreeConverter<Synchronization, SynchronizationContext> {
	public static SynchronizationConverter getInstance() {
		return INSTANCE;
	}

	private static final SynchronizationConverter INSTANCE = new SynchronizationConverter();

	private SynchronizationConverter() { }

	@Override
	public Synchronization convert(SynchronizationContext rootContext) {
		return rootContext.accept(this);
	}

	@Override
	public Synchronization visitActiveSynchronization(ActiveSynchronizationContext ctx) {
		Synchronization synchronization = newSynchronization(ctx.expression());
		synchronization.setActiveSync(true);
		return synchronization;
	}

	@Override
	public Synchronization visitReactiveSynchronization(ReactiveSynchronizationContext ctx) {
		Synchronization synchronization = newSynchronization(ctx.expression());
		synchronization.setActiveSync(false);
		return synchronization;
	}

	private Synchronization newSynchronization(ExpressionContext expressionCtx) {
		Synchronization synchronization = new Synchronization();
		synchronization.setExpression(ExpressionConverter.getInstance().convert(expressionCtx));
		return synchronization;
	}
}
