package ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl;

import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.generic.internal.AbsUnaryExprNode;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors.IExpressionVisitor;

/**
 * Represents an additive inverse expression.<br/>
 * Syntax:<br/>
 * <pre>
 * Expression ::= ...
 *             |  '-' Expression
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
 *     <td>The expression that is being additively inverted.</td>
 *   </tr>
 * </table>
 */
public class AdditiveInverseExpression extends AbsUnaryExprNode {
	@Override
	public <T> T accept(IExpressionVisitor<T> visitor) {
		return visitor.visitAdditiveInverseExpr(this);
	}

	@Override
	protected AbsUnaryExprNode topLevelClone() {
		return new AdditiveInverseExpression();
	}

	@Override
	public AdditiveInverseExpression deepClone() {
		return (AdditiveInverseExpression) super.deepClone();
	}
}
