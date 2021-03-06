package ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.evaluated.impl;

import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.base.BaseTrapset;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.ITrapsetVisitor;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trap.LinkedPairTrap;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.evaluated.AbsTrapsetEvaluation;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapset.model.trapset.evaluated.IEvaluatedTrapsetVisitor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_model.structure.transitions.Transition;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

public class LinkedPairsTrapsetEvaluation extends AbsTrapsetEvaluation<LinkedPairTrap> {
	public LinkedPairsTrapsetEvaluation() { }

	// An egress transition in this trapset can have multiple ingress transitions - these actually represent distinct traps.
	private Map<Transition, Vector<BaseTrapset>> mapIngressTrapsetVectors = new LinkedHashMap<>();
	private Map<Transition, Vector<Transition>> mapIngressTransitionVectors = new LinkedHashMap<>();

	@Override
	public boolean addTrap(LinkedPairTrap trap) {
		boolean newTrap = super.addTrap(trap);
		Vector<BaseTrapset> ingressTrapsetVector = mapIngressTrapsetVectors
				.computeIfAbsent(trap.getTransition(), k -> new Vector<>());
		Vector<Transition> ingressTransitionVector = mapIngressTransitionVectors
				.computeIfAbsent(trap.getTransition(), k -> new Vector<>());
		ingressTrapsetVector.add(trap.getIngressTrapset());
		ingressTransitionVector.add(trap.getIngressTransition());
		return newTrap;
	}

	@Override
	public <T> T accept(ITrapsetVisitor<T> visitor) {
		return visitor.visitLinkedPairs(this);
	}

	public Vector<BaseTrapset> getIngressTrapsetVector(Transition transition) {
		return mapIngressTrapsetVectors.get(transition);
	}

	public Vector<Transition> getIngressTransitionVector(Transition transition) {
		return mapIngressTransitionVectors.get(transition);
	}

	@Override
	public int getTrapCount() {
		int trapCount = 0;

		for (Vector<Transition> ingressTransitionVector : mapIngressTransitionVectors.values()) {
			trapCount += ingressTransitionVector.size();
		}

		return trapCount;
	}

	@Override
	public <T> T accept(IEvaluatedTrapsetVisitor<T> visitor) {
		return visitor.visitLinkedPairs(this);
	}
}
