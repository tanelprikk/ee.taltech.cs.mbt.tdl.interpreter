package ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.st_generator.extractors.declaration;

import ee.taltech.cs.mbt.tdl.commons.facades.st_facade.context_mapping.ContextBuilder;
import ee.taltech.cs.mbt.tdl.commons.facades.st_facade.context_mapping.IContextExtractor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_grammar.st_generator.extractors.expression.ExpressionCtxExtractor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.declaration.variable.initializer.AbsVariableInitializer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.declaration.variable.initializer.FlatVariableInitializer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.declaration.variable.initializer.StructuredVariableInitializer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.IInitializerVisitor;

public class InitializerCtxExtractor implements IContextExtractor<AbsVariableInitializer>, IInitializerVisitor<ContextBuilder> {
	public static InitializerCtxExtractor getInstance() {
		return INSTANCE;
	}

	public static final InitializerCtxExtractor INSTANCE = new InitializerCtxExtractor();

	private InitializerCtxExtractor() { }

	@Override
	public ContextBuilder extract(AbsVariableInitializer inst) {
		return inst.accept(this);
	}

	@Override
	public ContextBuilder visitFlatInitializer(FlatVariableInitializer init) {
		ContextBuilder exprCtx = ExpressionCtxExtractor.getInstance().extract(init.getExpression());
		return ContextBuilder.newBuilder("flatInitializer")
				.put("expression", exprCtx);
	}

	@Override
	public ContextBuilder visitStructuredInitializer(StructuredVariableInitializer init) {
		return ContextBuilder.newBuilder("structuredInitializer")
				.put("nestedInitializers", this.extract(init.getInitializers()));
	}
}
