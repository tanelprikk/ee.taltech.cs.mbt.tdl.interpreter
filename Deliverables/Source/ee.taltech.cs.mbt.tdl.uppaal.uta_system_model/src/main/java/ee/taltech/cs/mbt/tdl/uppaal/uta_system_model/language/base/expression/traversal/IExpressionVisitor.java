package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.traversal;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.access.ArrayLookupExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.arithmetic.*;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.binary.*;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.hybrid.PostfixDecrementExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.hybrid.PostfixIncrementExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.hybrid.PrefixDecrementExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.hybrid.PrefixIncrementExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.literals.KeywordLiteral;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.literals.NaturalNumberLiteral;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.ConjunctionExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.DisjunctionExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.EqualityExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.NegationExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.inequality.GTEExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.inequality.GreaterThanExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.inequality.LTEExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.inequality.LessThanExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.phrasal.PhrasalConjunctionExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.phrasal.PhrasalDisjunctionExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.phrasal.PhrasalImplicationExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.phrasal.PhrasalNegation;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.logical.quantification.QuantificationExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.misc.*;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.categories.structural.GroupedExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language.base.expression.generic.AssignmentWrapper;

public interface IExpressionVisitor {
	void visitArrayLookup(ArrayLookupExpression arrayLookup);
	void visitAddition(AdditionExpression addition);
	void visitDivision(DivisionExpression division);
	void visitMaximum(MaximumExpression maximum);
	void visitMinimum(MinimumExpression minimum);
	void visitModulo(ModuloExpression modulo);
	void visitMultiplication(MultiplicationExpression multiplication);
	void visitNegative(NegativeExpression negative);
	void visitPositive(PositiveExpression positive);
	void visitSubtraction(SubtractionExpression subtraction);
	void visitBitwiseAnd(BitwiseAndExpression bitwiseAnd);
	void visitBitwiseExclusiveOr(BitwiseExclusiveOrExpression bitwiseExclusiveOr);
	void visitBitwiseOr(BitwiseOrExpression bitwiseOr);
	void visitLeftShift(LeftShiftExpression leftShift);
	void visitRightShift(RightShiftExpression rightShift);
	void visitPostfixDecrement(PostfixDecrementExpression postfixDecrement);
	void visitPostfixIncrement(PostfixIncrementExpression postfixIncrement);
	void visitPrefixDecrement(PrefixDecrementExpression prefixDecrement);
	void visitPrefixIncrement(PrefixIncrementExpression prefixIncrement);
	void visitKeyword(KeywordLiteral keyword);
	void visitNaturalNumber(NaturalNumberLiteral number);
	void visitGroup(GroupedExpression group);
	void visitAssignment(AssignmentExpression assignment);
	void visitCall(CallExpression call);
	void visitIdentifierRef(IdentifierRefExpression identifierRef);
	void visitIfElse(IfElseExpression ifElseTernary);
	void visitNegation(NegationExpression negation);
	void visitEquality(EqualityExpression equality);
	void visitDisjunction(DisjunctionExpression disjunction);
	void visitConjunction(ConjunctionExpression conjunction);
	void visitQuantification(QuantificationExpression quantification);
	void visitFieldAccess(FieldAccessExpression fieldAccess);
	void visitGreaterThan(GreaterThanExpression greaterThan);
	void visitLessThan(LessThanExpression lessThan);
	void visitGTE(GTEExpression greaterThanOrEqual);
	void visitLTE(LTEExpression lessThanOrEqual);
	void visitPhrasalConjunction(PhrasalConjunctionExpression conjunction);
	void visitPhrasalDisjunction(PhrasalDisjunctionExpression disjunction);
	void visitPhrasalImplication(PhrasalImplicationExpression implication);
	void visitPhrasalNegation(PhrasalNegation negation);
	void visitAssignmentWrapper(AssignmentWrapper assignmentWrapper);
}