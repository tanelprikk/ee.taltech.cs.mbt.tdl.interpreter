package ee.taltech.cs.mbt.tdl.scenario.scenario_generator;

import ee.taltech.cs.mbt.tdl.commons.antlr_facade.AbsAntlrParserFacade.ParseException;
import ee.taltech.cs.mbt.tdl.commons.utils.data_structures.DirectedMultigraph;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.generic.AbsBooleanInternalNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.generic.AbsDerivedTrapsetNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.generic.AbsTrapsetQuantifierNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.BooleanValueWrapperNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.BoundedLeadsToNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.BoundedRepetitionNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.ConjunctionNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.DisjunctionNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.EquivalenceNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.GroupNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.ImplicationNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.logical.LeadsToNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.modifier.Bound;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.modifier.EBoundType;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.trapset_derivation.AbsoluteComplementNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.trapset_derivation.LinkedPairNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.trapset_derivation.RelativeComplementNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.trapset_quantifier.ExistentialQuantificationNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.internal.trapset_quantifier.UniversalQuantificationNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.concrete.leaf.trapset.TrapsetNode;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.generic.TdlExpression;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.IBooleanNodeVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.ITrapsetQuantifierVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.IDerivedTrapsetVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.impl.BaseBooleanNodeVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_model.expression_tree.structure.visitors.impl.BaseTdlExpressionVisitor;
import ee.taltech.cs.mbt.tdl.expression.tdl_parser.TdlExpressionParser;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.specification.ScenarioSpecification;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.trapset.trap.BaseTrap;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.trapset.trap.LinkedPairTrap;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.trapset.AbsoluteComplementTrapset;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.trapset.BaseTrapset;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.trapset.LinkedPairTrapset;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.trapset.RelativeComplementTrapset;
import ee.taltech.cs.mbt.tdl.scenario.scenario_model.trapset.generic.AbsTrapset;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.composite.InvalidSystemStructureException;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.composite.parsing.UtaParser;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.composite.parsing.language.EmbeddedCodeSyntaxException;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.structure.UtaNodeMarshaller.MarshallingException;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.UtaSystem;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.AbsDeclarationStatement;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.variable.VariableDeclaration;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.variable.VariableDeclarationGroup;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.expression.generic.AbsExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.expression.impl.AssignmentExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.expression.impl.ConjunctionExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.expression.impl.IdentifierExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.expression.impl.NegationExpression;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.expression.impl.literal.LiteralConsts;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.identifier.Identifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.type.BaseType;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.type.Type;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.type.identifier.BaseTypeIdentifiers;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.labels.impl.AssignmentsLabel;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.locations.Location;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.templates.Template;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.transitions.Transition;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.transitions.TransitionLabels;

import java.math.BigInteger;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GeneratorStub {
	public static void generate(ScenarioSpecification specification) {
		TdlExpression expression = specification.getExpression();
		UtaSystem system = specification.getSystemModel();

		int systemTransitionCount = system.getTemplates().stream()
				.map(Template::getLocationGraph)
				.mapToInt(DirectedMultigraph::edgeCount)
				.sum();

		Set<TrapsetNode> trapsetNodes = extractTrapsetNodes(expression);
		Map<TrapsetNode, BaseTrapset> baseTrapsets = constructBaseTrapsets(system, trapsetNodes);

		List<AbsDerivedTrapsetNode> trapsetOperators = extractTrapsetOperators(expression);
		Map<AbsDerivedTrapsetNode, AbsTrapset> mapDerivedTrapsets = constructDerivedTrapsets(system, trapsetOperators, baseTrapsets);

		List<AbsTrapsetQuantifierNode> trapsetQuantifiers = extractTrapsetQuantifiers(expression);
		for (AbsTrapsetQuantifierNode trapsetQuantifier : trapsetQuantifiers) {
			AbsDerivedTrapsetNode trapsetDerivingNode = trapsetQuantifier.getChildContainer().getChild();
			AbsTrapset derivedTrapset = mapDerivedTrapsets.get(trapsetDerivingNode);

			Boolean replacementValue = trapsetQuantifier.accept(new ITrapsetQuantifierVisitor<Boolean>() {
				@Override
				public Boolean visitExistential(ExistentialQuantificationNode quantifier) {
					return visitAny(quantifier);
				}

				@Override
				public Boolean visitUniversal(UniversalQuantificationNode quantifier) {
					return visitAny(quantifier);
				}

				private Boolean visitAny(AbsTrapsetQuantifierNode quantifier) {
					if (derivedTrapset.isEmpty()) // Empty trapset.
						return quantifier.isNegated();
					if (derivedTrapset.getTrapCount() == systemTransitionCount) // Trapset that covers the entire system.
						return !quantifier.isNegated();
					return null;
				}
			});

			if (replacementValue != null) {
				BooleanValueWrapperNode wrapper = BooleanValueWrapperNode.of(replacementValue);
				expression.replaceDescendant(trapsetQuantifier, wrapper);
			}
		}


		Deque<BooleanValueWrapperNode> booleanLeaves = normalizeExpressionSubtree(expression);
		reduceBooleanValues(booleanLeaves, expression);
		// TODO p v p = p, p && p = p, ...

		System.out.println(expression.getRootNode());
		/*
		 * TODO:
		 * Create the result system based on normalized and reduced expression.
		 * int treeIndex;
		 * int trapsetIndex;
		 */

		// ScenarioStubSystemFactory.DECLARED_NAME_TrapsetActivatorChannels.equals(null);
	}

	private static void reduceBooleanValues(Deque<BooleanValueWrapperNode> booleanLeafNodeDeque, TdlExpression normalizedExpression) {
		while (!booleanLeafNodeDeque.isEmpty()) {
			BooleanValueWrapperNode booleanChildNode = booleanLeafNodeDeque.pollFirst();

			/*
			 * Imagine the following scenario:
			 *      /
			 *     op
			 *    /  \
			 * leafA leafB
			 * queue[leafA, leafB] -> leafA.
			 * queue[leafB]
			 *
			 * process(leafA) causes <op> to be replaced with <op'> but the dequeue will still contain leafB.
			 * The tree will look like this:
			 *      /
			 *    op'
			 * but leafB will still be in the dequeue, waiting for processing, even though it is no
			 * longer attached to the tree.
			 *
			 * We use TdlExpression.isDescendant(...) to check whether the node is still inside the tree.
			 * This will entail a walk from leaf to root and while not 100% efficient,
			 * it is still better than processing an entire discarded subtree.
			 */
			if (!normalizedExpression.isDescendant(booleanChildNode))
				continue;

			// Nothing left to reduce:
			if (booleanChildNode == normalizedExpression.getRootNode())
				return;

			AbsBooleanInternalNode parentNode = (AbsBooleanInternalNode) booleanChildNode.getParentNode();
			parentNode.accept(new IBooleanNodeVisitor<Void>() {
				private void renormalizeSubtree(AbsBooleanInternalNode subtreeRoot) {
					Deque<BooleanValueWrapperNode> newLeaves = normalizeExpressionSubtree(normalizedExpression, subtreeRoot);
					// Reprocess the subtree starting form its Boolean leaves:
					while (!newLeaves.isEmpty()) {
						booleanLeafNodeDeque.addFirst(newLeaves.pollLast());
					}
				}

				@Override
				public Void visitBoundedLeadsTo(BoundedLeadsToNode parentNode) { // TODO.
					// p ~> true == true
					// p ~> false == not(p)
					// true ~> p == p
					// false ~> p == true

					/*
					 * true <n ~> a = true
					 * true >n ~> a = true
					 * true <=n ~> a =
					 * true >=n ~> a
					 * true =n ~> a
					 *
					 * false <n ~> a
					 * false >n ~> a
					 * false <=n ~> a
					 * false >=n ~> a
					 * false =n ~> a
					 *
					 * a <n ~> true
					 * a >n ~> true
					 * a <=n ~> true
					 * a >=n ~> true
					 * a =n ~> true
					 *
					 * a <n ~> false
					 * a >n ~> false
					 * a <=n ~> false
					 * a >=n ~> false
					 * a =n ~> false
					 *
					 * true <n ~> a
					 * true >n ~> a
					 * true <=n ~> a
					 * true >=n ~> a
					 * true =n ~> a
					 *
					 * false <n ~> a
					 * false >n ~> a
					 * false <=n ~> a
					 * false >=n ~> a
					 * false =n ~> a
					 */
					return visitLeadsTo(parentNode);
				}

				@Override
				public Void visitBoundedRepetition(BoundedRepetitionNode parentNode) { // TODO.
					Bound bound = parentNode.getBound();

					BooleanValueWrapperNode replacement = null;
					switch (bound.getBoundType()) {
					case GREATER_THAN:
					case GREATER_THAN_OR_EQUAL_TO:
						replacement = BooleanValueWrapperNode.of(booleanChildNode.wrapsTrue());
						break;
					case LESS_THAN:
					case LESS_THAN_OR_EQUAL_TO:
						replacement = BooleanValueWrapperNode.of(booleanChildNode.wrapsFalse());
						break;
					case EQUALS:
						replacement = BooleanValueWrapperNode.falseWrapper();
						break;
					}

					if (replacement != null) {
						normalizedExpression.replaceDescendant(parentNode, replacement);
						booleanLeafNodeDeque.addFirst(replacement);
					}

					return null;
				}

				@Override
				public Void visitLeadsTo(LeadsToNode parentNode) {
					AbsBooleanInternalNode rightChild = parentNode.getChildContainer().getRightChild();
					AbsBooleanInternalNode leftChild = parentNode.getChildContainer().getLeftChild();
					boolean rightChildIsBoolValue = rightChild == booleanChildNode;

					if (booleanChildNode.wrapsTrue()) {
						if (rightChildIsBoolValue) { // p ~> True ==> True.
							BooleanValueWrapperNode replacementNode = BooleanValueWrapperNode.trueWrapper();
							normalizedExpression.replaceDescendant(parentNode, replacementNode);
							booleanLeafNodeDeque.addFirst(replacementNode);
						} else { // True ~> p ==> p.
							normalizedExpression.replaceDescendant(parentNode, rightChild);
						}
					} else {
						if (rightChildIsBoolValue) { // p ~> False ==> not(p).
							normalizedExpression.replaceDescendant(parentNode, leftChild);
							leftChild.setNegated(true);
							// Get rid of the new negation:
							renormalizeSubtree(leftChild);
						} else { // False ~> p ==> True.
							BooleanValueWrapperNode replacementNode = BooleanValueWrapperNode.trueWrapper();
							normalizedExpression.replaceDescendant(parentNode, replacementNode);
							booleanLeafNodeDeque.addFirst(replacementNode);
						}
					}

					return null;
				}

				@Override
				public Void visitConjunction(ConjunctionNode parentNode) {
					AbsBooleanInternalNode rightChild = parentNode.getChildContainer().getRightChild();
					AbsBooleanInternalNode leftChild = parentNode.getChildContainer().getLeftChild();
					boolean rightChildIsBoolValue = rightChild == booleanChildNode;

					if (booleanChildNode.wrapsTrue()) { // True and x ==> x.
						normalizedExpression.replaceDescendant(parentNode, rightChildIsBoolValue ? leftChild : rightChild);
					} else { // False and x ==> False.
						BooleanValueWrapperNode replacementNode = BooleanValueWrapperNode.falseWrapper();
						normalizedExpression.replaceDescendant(parentNode, replacementNode);
						booleanLeafNodeDeque.addFirst(replacementNode);
					}

					return null;
				}

				@Override
				public Void visitDisjunction(DisjunctionNode parentNode) {
					AbsBooleanInternalNode rightChild = parentNode.getChildContainer().getRightChild();
					AbsBooleanInternalNode leftChild = parentNode.getChildContainer().getLeftChild();
					boolean rightChildIsBoolValue = rightChild == booleanChildNode;

					if (booleanChildNode.wrapsTrue()) { // True or x ==> True.
						BooleanValueWrapperNode replacementNode = BooleanValueWrapperNode.trueWrapper();
						normalizedExpression.replaceDescendant(parentNode, replacementNode);
						booleanLeafNodeDeque.addFirst(replacementNode);
					} else { // False or x ==> x.
						normalizedExpression.replaceDescendant(parentNode, rightChildIsBoolValue ? leftChild : rightChild);
					}

					return null;
				}

				@Override
				public Void visitEquivalence(EquivalenceNode node) {
					// Not possible in a normalized expression.
					// p <-> q ==> p -> q and q -> p ==> (...).
					return null;
				}

				@Override
				public Void visitGroup(GroupNode node) {
					// Not possible in a normalized expression.
					// (p) ==> p.
					return null;
				}

				@Override
				public Void visitImplication(ImplicationNode node) {
					// Not possible in a normalized expression.
					// p -> q ==> not(p) or q.
					return null;
				}

				@Override
				public Void visitUniversalQuantification(UniversalQuantificationNode node) {
					// Not possible in a normalized expression.
					// quantifier(Boolean) -> new Boolean.
					return null;
				}

				@Override
				public Void visitExistentialQuantification(ExistentialQuantificationNode node) {
					// Not possible in a normalized expression.
					// quantifier(Boolean) -> new Boolean.
					return null;
				}

				@Override
				public Void visitValueWrapper(BooleanValueWrapperNode node) {
					// Not possible in context: BooleanValueWrapperNode cannot have BooleanValueWrapperNode as its parent.
					return null;
				}
			});
		}
	}

	private static Deque<BooleanValueWrapperNode> normalizeExpressionSubtree(TdlExpression expression, AbsBooleanInternalNode subtreeRoot) {
		Deque<BooleanValueWrapperNode> valueLeaves = new LinkedList<>();
		subtreeRoot.accept(new BaseBooleanNodeVisitor<Void>() {
			@Override
			public Void visitValueWrapper(BooleanValueWrapperNode valueWrapper) {
				valueLeaves.add(valueWrapper);
				return null;
			}

			@Override
			public Void visitGroup(GroupNode group) {
				AbsBooleanInternalNode<?, ?> child = group.getChildContainer().getChild();
				expression.replaceDescendant(group, child);
				child.setNegated(child.isNegated() ^ group.isNegated());
				return child.accept(this);
			}

			@Override
			public Void visitConjunction(ConjunctionNode conjunction) {
				if (!conjunction.isNegated())
					return visitChildren(conjunction);

				DisjunctionNode disjunction = new DisjunctionNode();
				expression.replaceDescendant(conjunction, disjunction);

				AbsBooleanInternalNode leftChild = conjunction.getChildContainer().getLeftChild();
				AbsBooleanInternalNode rightChild = conjunction.getChildContainer().getRightChild();

				leftChild.setNegated(!leftChild.isNegated());
				rightChild.setNegated(!rightChild.isNegated());

				disjunction.getChildContainer()
						.setLeftChild(leftChild)
						.setRightChild(rightChild);

				return visitChildren(disjunction);
			}

			@Override
			public Void visitDisjunction(DisjunctionNode disjunction) {
				if (!disjunction.isNegated())
					return visitChildren(disjunction);

				ConjunctionNode conjunction = new ConjunctionNode();
				expression.replaceDescendant(disjunction, conjunction);

				AbsBooleanInternalNode leftChild = disjunction.getChildContainer().getLeftChild();
				AbsBooleanInternalNode rightChild = disjunction.getChildContainer().getRightChild();
				leftChild.setNegated(!leftChild.isNegated());
				rightChild.setNegated(!rightChild.isNegated());

				conjunction.getChildContainer()
						.setLeftChild(leftChild)
						.setRightChild(rightChild);

				return visitChildren(conjunction);
			}

			@Override
			public Void visitImplication(ImplicationNode implication) {
				DisjunctionNode disjunction = new DisjunctionNode();
				expression.replaceDescendant(implication, disjunction);

				AbsBooleanInternalNode leftChild = implication.getChildContainer().getLeftChild();
				AbsBooleanInternalNode rightChild = implication.getChildContainer().getRightChild();
				leftChild.setNegated(!leftChild.isNegated());

				disjunction.setNegated(implication.isNegated());
				disjunction.getChildContainer().setLeftChild(leftChild);
				disjunction.getChildContainer().setRightChild(rightChild);

				return visitDisjunction(disjunction);
			}

			@Override
			public Void visitEquivalence(EquivalenceNode equivalence) {
				ImplicationNode implyLTR = new ImplicationNode();
				implyLTR.getChildContainer()
						.setLeftChild(equivalence.getChildContainer().getLeftChild())
						.setRightChild(equivalence.getChildContainer().getRightChild());

				ImplicationNode implyRTL = new ImplicationNode();
				implyRTL.getChildContainer()
						.setLeftChild(equivalence.getChildContainer().getRightChild().deepClone())
						.setRightChild(equivalence.getChildContainer().getLeftChild().deepClone());

				ConjunctionNode conjunction = new ConjunctionNode();
				conjunction.setNegated(equivalence.isNegated());
				conjunction.getChildContainer()
						.setLeftChild(implyLTR)
						.setRightChild(implyRTL);

				expression.replaceDescendant(equivalence, conjunction);
				return visitConjunction(conjunction);
			}

			@Override
			public Void visitBoundedRepetition(BoundedRepetitionNode boundedRepetition) {
				if (!boundedRepetition.isNegated())
					return visitChildren(boundedRepetition);
				/*
				 * not(#a(>n))      ==> #a(<=n)
				 * not(#a(<n))      ==> #a(>=n)
				 * not(#a(>=n))     ==> #a(<n)
				 * not(#a(<=n))     ==> #a(>n)
				 * not(#a(=n))      ==> #a(<n) or #a(>n)
				 */
				Bound bound = boundedRepetition.getBound();
				switch (bound.getBoundType()) {
					case GREATER_THAN:
						bound.setBoundType(EBoundType.LESS_THAN_OR_EQUAL_TO);
						boundedRepetition.setNegated(false);
						return visitChildren(boundedRepetition);
					case LESS_THAN:
						bound.setBoundType(EBoundType.GREATER_THAN_OR_EQUAL_TO);
						boundedRepetition.setNegated(false);
						return visitChildren(boundedRepetition);
					case GREATER_THAN_OR_EQUAL_TO:
						bound.setBoundType(EBoundType.LESS_THAN);
						boundedRepetition.setNegated(false);
						return visitChildren(boundedRepetition);
					case LESS_THAN_OR_EQUAL_TO:
						bound.setBoundType(EBoundType.GREATER_THAN);
						boundedRepetition.setNegated(false);
						return visitChildren(boundedRepetition);
					case EQUALS:
						DisjunctionNode disjunction = new DisjunctionNode();
						expression.replaceDescendant(boundedRepetition, disjunction);

						bound.setBoundType(EBoundType.LESS_THAN);
						boundedRepetition.setNegated(false);
						BoundedRepetitionNode boundedRepetitionClone = boundedRepetition.deepClone();
						boundedRepetitionClone.getBound().setBoundType(EBoundType.GREATER_THAN);

						disjunction.getChildContainer().setLeftChild(boundedRepetition);
						disjunction.getChildContainer().setRightChild(boundedRepetitionClone);

						return visitChildren(disjunction);
				}

				return visitChildren(boundedRepetition);
			}

			@Override
			public Void visitLeadsTo(LeadsToNode leadsTo) {
				if (!leadsTo.isNegated())
					return visitChildren(leadsTo);
				// not(a ~> b) ==> not(a) or (a ~> not(b)).
				DisjunctionNode disjunction = new DisjunctionNode();
				expression.replaceDescendant(leadsTo, disjunction);

				AbsBooleanInternalNode leftChild = leadsTo.getChildContainer().getLeftChild();
				AbsBooleanInternalNode rightChild = leadsTo.getChildContainer().getRightChild();

				AbsBooleanInternalNode leftChildClone = leftChild.deepClone();
				leftChildClone.setNegated(!leftChild.isNegated());
				rightChild.setNegated(!rightChild.isNegated());
				leadsTo.setNegated(false);

				disjunction.getChildContainer().setLeftChild(leftChildClone);
				disjunction.getChildContainer().setRightChild(leadsTo);

				return visitDisjunction(disjunction);
			}

			@Override
			public Void visitBoundedLeadsTo(BoundedLeadsToNode boundedLeadsTo) {
				if (!boundedLeadsTo.isNegated())
					return visitChildren(boundedLeadsTo);
				/*
				 * not(a ~>(>n) b)  ==> a ~>(<=n) b or not(a ~> b)
				 * not(a ~>(<n) b)  ==> a ~>(>=n) b or not(a ~> b)
				 * not(a ~>(>=n) b) ==> a ~>(<n) b or not(a ~> b)
				 * not(a ~>(<=n) b) ==> a ~>(>n) b or not(a ~> b)
				 * not(a ~>(=n) b)  ==> (a ~>(<n) b or a ~>(>n) b) or not(a ~> b)
				 */
				AbsBooleanInternalNode leftChild = boundedLeadsTo.getChildContainer().getLeftChild();
				AbsBooleanInternalNode rightChild = boundedLeadsTo.getChildContainer().getRightChild();

				DisjunctionNode disjunction = new DisjunctionNode();
				LeadsToNode negatedLeadsTo = new LeadsToNode();
				negatedLeadsTo.setNegated(true);
				negatedLeadsTo.getChildContainer().setLeftChild(leftChild.deepClone());
				negatedLeadsTo.getChildContainer().setRightChild(rightChild.deepClone());

				disjunction.getChildContainer().setLeftChild(boundedLeadsTo);
				disjunction.getChildContainer().setRightChild(negatedLeadsTo);

				Bound bound = boundedLeadsTo.getBound();
				switch (bound.getBoundType()) {
					case GREATER_THAN:
						expression.replaceDescendant(boundedLeadsTo, disjunction);
						bound.setBoundType(EBoundType.LESS_THAN_OR_EQUAL_TO);
						break;
					case LESS_THAN:
						expression.replaceDescendant(boundedLeadsTo, disjunction);
						bound.setBoundType(EBoundType.GREATER_THAN_OR_EQUAL_TO);
						break;
					case GREATER_THAN_OR_EQUAL_TO:
						expression.replaceDescendant(boundedLeadsTo, disjunction);
						bound.setBoundType(EBoundType.LESS_THAN);
						break;
					case LESS_THAN_OR_EQUAL_TO:
						expression.replaceDescendant(boundedLeadsTo, disjunction);
						bound.setBoundType(EBoundType.GREATER_THAN);
						break;
					case EQUALS:
						expression.replaceDescendant(boundedLeadsTo, disjunction);
						bound.setBoundType(EBoundType.GREATER_THAN);
						BoundedLeadsToNode boundedLeadsToClone = boundedLeadsTo.deepClone();
						boundedLeadsToClone.getBound().setBoundType(EBoundType.LESS_THAN);
						DisjunctionNode boundDisjunction = new DisjunctionNode();
						boundDisjunction.getChildContainer().setLeftChild(boundedLeadsToClone);
						boundDisjunction.getChildContainer().setRightChild(boundedLeadsTo);
						disjunction.getChildContainer().setLeftChild(boundDisjunction);
						disjunction.getChildContainer().setRightChild(negatedLeadsTo);
						break;
				}

				return visitDisjunction(disjunction);
			}
		});
		return valueLeaves;
	}

	private static Deque<BooleanValueWrapperNode> normalizeExpressionSubtree(TdlExpression expression) {
		return normalizeExpressionSubtree(expression, expression.getRootNode());
	}

	private static List<AbsTrapsetQuantifierNode> extractTrapsetQuantifiers(TdlExpression expression) {
		List<AbsTrapsetQuantifierNode> trapsetQuantifiers = new LinkedList<>();
		expression.getRootNode().accept(new BaseBooleanNodeVisitor<Void>() {
			@Override
			public Void visitExistentialQuantification(ExistentialQuantificationNode operator) {
				trapsetQuantifiers.add(operator);
				return null;
			}

			@Override
			public Void visitUniversalQuantification(UniversalQuantificationNode operator) {
				trapsetQuantifiers.add(operator);
				return null;
			}
		});
		return trapsetQuantifiers;
	}

	private static Map<TrapsetNode, BaseTrapset> constructBaseTrapsets(UtaSystem system, Set<TrapsetNode> trapsetSymbols) {
		Map<TrapsetNode, BaseTrapset> trapsetMap = new LinkedHashMap<>();

		for (AbsDeclarationStatement declarationStatement : system.getDeclarations()) {
			if (declarationStatement instanceof VariableDeclaration) {
				VariableDeclaration variableDeclaration = (VariableDeclaration) declarationStatement;
				Type type = variableDeclaration.getType();
				BaseType baseType = type.getBaseType();
				if (!BaseTypeIdentifiers.BOOLEAN.equals(baseType.getTypeId()))
					continue;
				TrapsetNode trapsetCandidate = TrapsetNode.of(variableDeclaration.getIdentifier().toString());
				if (!trapsetSymbols.contains(trapsetCandidate))
					continue;
				trapsetMap.put(
						trapsetCandidate,
						(BaseTrapset) new BaseTrapset()
								.setDeclaration(variableDeclaration)
								.setName(Identifier.of(trapsetCandidate.getName()))
				);
			} else if (declarationStatement instanceof VariableDeclarationGroup) {
				VariableDeclarationGroup variableDeclarationGroup = (VariableDeclarationGroup) declarationStatement;
				BaseType baseType = variableDeclarationGroup.getBaseType();
				if (!BaseTypeIdentifiers.BOOLEAN.equals(baseType.getTypeId()))
					continue;
				variableDeclarationGroup.getBaseTypeExtensionMap().streamView().forEachOrdered(ext -> {
					TrapsetNode trapsetCandidate = TrapsetNode.of(ext.getIdentifier().toString());
					if (!trapsetSymbols.contains(trapsetCandidate))
						return;
					trapsetMap.put(
							trapsetCandidate,
							(BaseTrapset) new BaseTrapset()
									.setDeclaration(variableDeclarationGroup)
									.setName(Identifier.of(trapsetCandidate.getName()))
					);
				});
			}
		}

		for (Template template : system.getTemplates()) {
			Set<Transition> edges = template.getLocationGraph().getEdges();
			for (Transition transition : edges) {
				TransitionLabels labels;
				if ((labels = transition.getLabels()) != null && labels.getAssignmentsLabel() != null) {
					AssignmentsLabel label = labels.getAssignmentsLabel();
					for (AbsExpression expr : label.getContent()) {
						if (expr instanceof AssignmentExpression) {
							// We will only accept basic BoolVar = (expr) updates, not chains like BoolVar1=Boolvar2=(expr) etc.
							AssignmentExpression assgExpr = (AssignmentExpression) expr;
							AbsExpression leftExpr = assgExpr.getLeftChild();
							if (!(leftExpr instanceof IdentifierExpression))
								continue;
							IdentifierExpression idExpr = (IdentifierExpression) leftExpr;
							TrapsetNode trapsetCandidate = TrapsetNode.of(idExpr.getIdentifier().toString());
							if (!trapsetMap.containsKey(trapsetCandidate))
								continue;
							boolean newTransition = trapsetMap.get(trapsetCandidate)
									.addTrap(BaseTrap.of(template, transition, assgExpr));
							// FIXME: Duplication is invalid.
						}
					}
				}
			}
		}

		return trapsetMap;
	}

	private static Map<AbsDerivedTrapsetNode, AbsTrapset> constructDerivedTrapsets(
			UtaSystem system,
			List<AbsDerivedTrapsetNode> trapsetOperators,
			Map<TrapsetNode, BaseTrapset> baseTrapsets
	) {
		Map<AbsDerivedTrapsetNode, AbsTrapset> derivedTrapsets = new LinkedHashMap<>();
		for (AbsDerivedTrapsetNode<?> trapsetOperator : trapsetOperators) {
			derivedTrapsets.put(trapsetOperator, trapsetOperator.accept(new IDerivedTrapsetVisitor<AbsTrapset>() {
				@Override
				public AbsTrapset visitAbsoluteComplement(AbsoluteComplementNode absoluteComplement) {
					BaseTrapset ts = baseTrapsets.get(absoluteComplement.getChildContainer().getChild());
					Identifier trapsetName = Identifier.of("AbsoluteComplementOf_" + ts.getName());
					AbsoluteComplementTrapset derivedTrapset = new AbsoluteComplementTrapset();
					derivedTrapset.setName(trapsetName);

					for (Template tpl : system.getTemplates()) {
						for (Transition tr : tpl.getLocationGraph().getEdges()) {
							if (ts.contains(tr)) {
								if (!ts.isConditional(tr))
									continue;
								AssignmentExpression markerExpr = (AssignmentExpression) new AssignmentExpression()
										.setLeftChild(IdentifierExpression.of(trapsetName))
										.setRightChild(new NegationExpression().setChild(ts.getMarkerCondition(tr)));
								derivedTrapset.addTrap(BaseTrap.of(tpl, tr, markerExpr));
							} else {
								AssignmentExpression markerExpr = (AssignmentExpression) new AssignmentExpression()
										.setLeftChild(IdentifierExpression.of(trapsetName))
										.setRightChild(LiteralConsts.TRUE);
								derivedTrapset.addTrap(BaseTrap.of(tpl, tr, markerExpr));
							}
						}
					}
					return derivedTrapset;
				}

				@Override
				public AbsTrapset visitLinkedPair(LinkedPairNode linkedPair) {
					BaseTrapset ingressTrapset = baseTrapsets.get(linkedPair.getChildContainer().getLeftChild());
					BaseTrapset egressTrapset = baseTrapsets.get(linkedPair.getChildContainer().getRightChild());

					Identifier trapsetName = Identifier.of(ingressTrapset.getName() + "_LinkedPair_" + egressTrapset.getName());
					LinkedPairTrapset derivedTrapset = new LinkedPairTrapset();

					for (Transition ingressTransition : ingressTrapset) {
						Template ingressParentTpl = ingressTrapset.getParentTemplate(ingressTransition);
						Location target = ingressParentTpl.getLocationGraph().getTargetVertex(ingressTransition);
						Set<Transition> egressTransitions = ingressParentTpl.getLocationGraph().getEdgesFrom(target);
						for (Transition egressTransition : egressTransitions) {
							if (!egressTrapset.contains(egressTransition))
								continue;
							AssignmentExpression markerExpr = (AssignmentExpression) new AssignmentExpression()
									.setLeftChild(IdentifierExpression.of(trapsetName))
									.setRightChild(ingressTrapset.getMarkerCondition(egressTransition));
							derivedTrapset.addTrap(LinkedPairTrap.of(
									ingressTrapset, ingressTransition, ingressParentTpl, egressTransition, markerExpr
							));
						}
					}

					return derivedTrapset;
				}

				@Override
				public AbsTrapset visitRelativeComplement(RelativeComplementNode relativeComplement) {
					BaseTrapset includedTrapset = baseTrapsets.get(relativeComplement.getChildContainer().getLeftChild());
					BaseTrapset excludedTrapset = baseTrapsets.get(relativeComplement.getChildContainer().getRightChild());
					Identifier trapsetName = Identifier.of(includedTrapset.getName() + "_RelativeComplement_" + excludedTrapset.getName());
					RelativeComplementTrapset derivedTrapset = new RelativeComplementTrapset();
					derivedTrapset.setName(trapsetName);

					for (Transition includedTransition : includedTrapset) {
						Template tplA = includedTrapset.getParentTemplate(includedTransition);
						AbsExpression conditionExpr = includedTrapset.getMarkerCondition(includedTransition);
						if (excludedTrapset.contains(includedTransition)) {
							if (!excludedTrapset.isConditional(includedTransition))
								continue;

							conditionExpr = new ConjunctionExpression()
									.setLeftChild(conditionExpr)
									.setRightChild(
											new NegationExpression()
													.setChild(excludedTrapset.getMarkerCondition(includedTransition))
									);
						}

						AssignmentExpression markerExpr = (AssignmentExpression) new AssignmentExpression()
								.setLeftChild(IdentifierExpression.of(trapsetName))
								.setRightChild(conditionExpr);
						derivedTrapset.addTrap(BaseTrap.of(tplA, includedTransition, markerExpr));
					}

					return derivedTrapset;
				}
			}));
		}
		return derivedTrapsets;
	}

	private static Set<TrapsetNode> extractTrapsetNodes(TdlExpression expression) {
		Set<TrapsetNode> trapsetNodes = new LinkedHashSet<>();
		expression.getRootNode().accept(new BaseTdlExpressionVisitor<Void>() {
			@Override
			public Void visitTrapset(TrapsetNode node) {
				trapsetNodes.add(node);
				return null;
			}
		});
		return trapsetNodes;
	}

	private static List<AbsDerivedTrapsetNode> extractTrapsetOperators(TdlExpression expression) {
		List<AbsDerivedTrapsetNode> trapsetOperators = new LinkedList<>();
		expression.getRootNode().accept(new BaseTdlExpressionVisitor<Void>() {
			@Override
			public Void visitAbsoluteComplement(AbsoluteComplementNode node) {
				trapsetOperators.add(node);
				return null;
			}

			@Override
			public Void visitRelativeComplement(RelativeComplementNode node) {
				trapsetOperators.add(node);
				return null;
			}

			@Override
			public Void visitLinkedPair(LinkedPairNode node) {
				trapsetOperators.add(node);
				return null;
			}
		});
		return trapsetOperators;
	}

	public static void main(String... args) throws ParseException, MarshallingException, InvalidSystemStructureException, EmbeddedCodeSyntaxException {
		TdlExpression expression = TdlExpressionParser.getInstance().parseInput("E(TS1; TS2) | E(!TS3)");
		UtaSystem system = UtaParser.newInstance().parse(GeneratorStub.class.getResourceAsStream("/SampleSystem.xml"));
		generate(ScenarioSpecification.of(system, expression));
	}
}
