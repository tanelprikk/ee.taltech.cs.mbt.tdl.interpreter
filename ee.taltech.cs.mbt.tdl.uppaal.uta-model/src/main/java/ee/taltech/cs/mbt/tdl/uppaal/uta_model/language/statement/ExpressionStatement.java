package ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.statement;

import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.AbsExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.IStatementVisitor;

/**
 * Represents an expression being used as a statement
 * (generally either a plain assignment or an assignment operation).<br/>
 * Syntax:<br/>
 * <pre>
 * Statement       ::= ...
 *                  |  Expression ';'
 *                  | ...
 * </pre>
 */
public class ExpressionStatement extends AbsStatement {
	private AbsExpression expression;

	public ExpressionStatement() { }

	public AbsExpression getExpression() {
		return expression;
	}

	public ExpressionStatement setExpression(AbsExpression expression) {
		this.expression = expression;
		return this;
	}

	@Override
	public <T> T accept(IStatementVisitor<T> visitor) {
		return visitor.visitExpressionStatement(this);
	}

	@Override
	public ExpressionStatement deepClone() {
		ExpressionStatement clone = new ExpressionStatement();
		clone.expression = expression.deepClone();
		return clone;
	}
}
