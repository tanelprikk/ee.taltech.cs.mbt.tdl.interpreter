package ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl;

import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.IHasPhraseCounterpart;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.internal.AbsBinaryExprNode;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.IExpressionVisitor;

/**
 * Represents a disjunction operation.<br/>
 * Syntax:<br/>
 * <pre>
 * Expression ::= ...
 *             |  Expression '||' Expression
 *             | ...
 * </pre>
 * <br/>
 * <table>
 *   <tr>
 *     <th>Method</th>
 *     <th>Return type description</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #getLeftChild()}</td>
 *     <td>The left operand.</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #getRightChild()}</td>
 *     <td>The right operand.</td>
 *   </tr>
 * </table>
 */
public class DisjunctionExpression extends AbsBinaryExprNode implements IHasPhraseCounterpart {
	private boolean phrase;

	@Override
	public boolean isPhrase() {
		return phrase;
	}

	@Override
	public DisjunctionExpression setPhrase(boolean phrase) {
		this.phrase = phrase;
		return this;
	}

	@Override
	public <T> T accept(IExpressionVisitor<T> visitor) {
		return visitor.visitDisjunctionExpr(this);
	}

	@Override
	protected DisjunctionExpression topLevelClone() {
		DisjunctionExpression clone = new DisjunctionExpression();
		clone.setPhrase(isPhrase());
		return clone;
	}

	@Override
	public DisjunctionExpression deepClone() {
		return (DisjunctionExpression) super.deepClone();
	}
}
