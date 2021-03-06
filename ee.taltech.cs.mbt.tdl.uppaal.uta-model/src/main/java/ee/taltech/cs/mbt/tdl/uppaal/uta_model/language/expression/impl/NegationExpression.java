package ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl;

import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.IHasPhraseCounterpart;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.internal.AbsUnaryExprNode;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.IExpressionVisitor;

/**
 * Represents a logical negation operation.<br/>
 * Syntax:<br/>
 * <pre>
 * Expression ::= ...
 *             |  '!' Expression
 *             | ...
 * </pre>
 * <br/>
 * <table>
 *   <tr>
 *     <th>Method</th>
 *     <th>Return type description</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #getChild()} ()}</td>
 *     <td>The negated expression.</td>
 *   </tr>
 * </table>
 */
public class NegationExpression extends AbsUnaryExprNode implements IHasPhraseCounterpart {
	private boolean phrase;

	@Override
	public boolean isPhrase() {
		return phrase;
	}

	@Override
	public NegationExpression setPhrase(boolean phrase) {
		this.phrase = phrase;
		return this;
	}

	@Override
	public <T> T accept(IExpressionVisitor<T> visitor) {
		return visitor.visitNegationExpr(this);
	}

	@Override
	protected NegationExpression topLevelClone() {
		NegationExpression clone = new NegationExpression();
		clone.setPhrase(isPhrase());
		return clone;
	}

	@Override
	public NegationExpression deepClone() {
		return (NegationExpression) super.deepClone();
	}
}
