package ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.visitors;

import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.*;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.literal.DeadlockLiteral;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.literal.FalseLiteral;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.literal.NaturalNumberLiteral;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.language.expression.impl.literal.TrueLiteral;

public interface IExpressionVisitor<T> {
	T visitArrayLookupExpr(ArrayLookupExpression expr);
	T visitAdditionExpr(AdditionExpression expr);
	T visitDivisionExpr(DivisionExpression expr);
	T visitMaximumExpr(MaximumExpression expr);
	T visitMinimumExpr(MinimumExpression expr);
	T visitModuloExpr(ModuloExpression expr);
	T visitMultiplicationExpr(MultiplicationExpression expr);
	T visitAdditiveInverseExpr(AdditiveInverseExpression expr);
	T visitAdditiveIdentityExpr(AdditiveIdentityExpression expr);
	T visitSubtractionExpr(SubtractionExpression expr);
	T visitBitwiseAndExpr(BitwiseAndExpression expr);
	T visitBitwiseXorExpr(BitwiseXorExpression expr);
	T visitBitwiseOrExpr(BitwiseOrExpression expr);
	T visitLeftShiftExpr(LeftShiftExpression expr);
	T visitRightShiftExpr(RightShiftExpression expr);
	T visitPostfixDecrementExpr(PostfixDecrementExpression expr);
	T visitPostfixIncrementExpr(PostfixIncrementExpression expr);
	T visitPrefixDecrementExpr(PrefixDecrementExpression expr);
	T visitPrefixIncrementExpr(PrefixIncrementExpression expr);
	T visitGroupExpr(GroupedExpression expr);
	T visitAssignmentExpr(AssignmentExpression expr);
	T visitCallExpr(CallExpression expr);
	T visitIdentifierExpr(IdentifierExpression expr);
	T visitTernaryExpr(TernaryExpression expr);
	T visitNegationExpr(NegationExpression expr);
	T visitEqualityExpr(EqualityExpression expr);
	T visitDisjunctionExpr(DisjunctionExpression expr);
	T visitConjunctionExpr(ConjunctionExpression expr);
	T visitFieldAccessExpr(FieldAccessExpression expr);
	T visitGreaterThanExpr(GreaterThanExpression expr);
	T visitLessThanExpr(LessThanExpression expr);
	T visitGreaterThanOrEqualExpr(GreaterThanOrEqualExpression expr);
	T visitLessThanOrEqualExpr(LessThanOrEqualExpression expr);
	T visitInequalityExpr(InequalityExpression expr);
	T visitQuantificationExpr(QuantificationExpression expr);
	T visitImplicationExpr(ImplicationExpression expr);
	T visitDeadlockLiteral(DeadlockLiteral literal);
	T visitFalseLiteral(FalseLiteral literal);
	T visitTrueLiteral(TrueLiteral literal);
	T visitNaturalNumberLiteral(NaturalNumberLiteral literal);
}
