package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.composite.parsing.conversion;

import ee.taltech.cs.mbt.tdl.generic.antlr_facade.AbsAntlrParserFacade.ParseException;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.language.parsing.UtaLanguageParserFactory;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.structure.jaxb.*;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.gui.GuiCoordinates;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.labels.*;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.labels.generic.AbsUtaLabel;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.locations.ELocationExitPolicy;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.locations.UtaLocation;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.locations.UtaLocation.LocationName;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.locations.UtaLocationLabels;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.templates.UtaTemplate;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.transitions.UtaTransition;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.transitions.UtaTransition.TransitionNail;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structure.transitions.UtaTransitionLabels;

import java.util.HashMap;
import java.util.Map;

public class NtaTemplateConverter {
	public static NtaTemplateConverter newInstance(UtaLanguageParserFactory parserFactory) {
		return new NtaTemplateConverter(parserFactory);
	}

	private UtaLanguageParserFactory languageParserFactory;

	private NtaTemplateConverter(UtaLanguageParserFactory languageParserFactory) {
		this.languageParserFactory = languageParserFactory;
	}

	private void injectParameters(UtaTemplate template, XmlNodeTemplate templateXml) throws NtaEmbeddedCodeParseException {
		if (!templateXml.isSetParameter() || !templateXml.getParameter().isSetValue())
			return;

		String parameterString = templateXml.getParameter().getValue();
		try {
			template.setParameters(
				getLanguageParserFactory().parameterListParser()
					.parseInput(parameterString)
			);
		} catch (ParseException ex) {
			throw new NtaEmbeddedCodeParseException("Failed to parse parameters.", ex)
				.setEmbeddedCode(parameterString);
		}
	}

	private void injectLocalDeclarations(UtaTemplate utaTemplate, XmlNodeTemplate templateXml) throws NtaEmbeddedCodeParseException {
		if (!templateXml.isSetDeclaration() || !templateXml.getDeclaration().isSetValue())
			return;

		String declarations = templateXml.getDeclaration().getValue();
		try {
			utaTemplate.setLocalDeclarations(
				getLanguageParserFactory().declarationsParser()
					.parseInput(declarations)
			);
		} catch (ParseException ex) {
			throw new NtaEmbeddedCodeParseException("Failed to parse local declarations.", ex)
				.setEmbeddedCode(declarations);
		}
	}

	private void injectLocationData(UtaLocation location, XmlNodeLocation locationXml) throws NtaEmbeddedCodeParseException {
		location.setId(locationXml.getId());
		location.setCoordinates(new GuiCoordinates(locationXml.getX(), locationXml.getY()));

		location.setColor(NtaConversionUtils.parseColor(locationXml.getColor()));

		XmlNodeName nameXml;
		if (locationXml.isSetName() && (nameXml = locationXml.getName()).isSetX() && nameXml.isSetY()) {
			LocationName locationName = new UtaLocation.LocationName();
			locationName.setName(nameXml.getValue());
			locationName.setCoordinates(new GuiCoordinates(nameXml.getX(), nameXml.getY()));
			location.setName(locationName);
		}

		ELocationExitPolicy exitPolicy = ELocationExitPolicy.NORMAL;
		if (locationXml.isSetCommitted()) {
			exitPolicy = ELocationExitPolicy.COMMITTED;
		} else if (locationXml.isSetUrgent()) {
			exitPolicy = ELocationExitPolicy.URGENT;
		}
		location.setExitPolicy(exitPolicy);

		injectLocationLabels(location, locationXml);
	}

	private void injectLocationLabels(UtaLocation location, XmlNodeLocation locationXml) throws NtaEmbeddedCodeParseException {
		UtaLocationLabels labelContainer = new UtaLocationLabels();
		for (XmlNodeLocationLabel locationLabelXml : locationXml.getLabels()) {
			if (!locationLabelXml.isSetValue())
				continue;

			AbsUtaLabel<?> label = null;
			switch (locationLabelXml.getKind()) {
			case COMMENTS:
				UtaCommentLabel commentLabel;
				label = (commentLabel = new UtaCommentLabel());
				commentLabel.setContent(locationLabelXml.getValue());
				labelContainer.setCommentLabel(commentLabel);
				break;
			case INVARIANT:
				UtaInvariantLabel invariantLabel;
				label = (invariantLabel = new UtaInvariantLabel());
				String invariantCondition = locationLabelXml.getValue();
				try {
					invariantLabel.setContent(
						getLanguageParserFactory().invariantParser()
							.parseInput(invariantCondition)
					);
				} catch (ParseException ex) {
					throw new NtaEmbeddedCodeParseException("Failed to parse invariant condition.", ex)
						.setEmbeddedCode(invariantCondition);
				}
				labelContainer.setInvariantLabel(invariantLabel);
				break;
			default:
				break;
			}
			label.setCoordinates(new GuiCoordinates(locationLabelXml.getX(), locationLabelXml.getY()));
		}
		location.setLabelContainer(labelContainer);
	}

	private void injectTransitionLabels(UtaTransition transition, XmlNodeTransition transitionXml) throws NtaEmbeddedCodeParseException {
		UtaTransitionLabels labelContainer = new UtaTransitionLabels();
		for (XmlNodeTransitionLabel transitionLabelXml : transitionXml.getLabels()) {
			if (!transitionLabelXml.isSetValue())
				continue;
			AbsUtaLabel<?> label = null;
			String errMsg = "";
			String embeddedCode = "";
			try {
				switch (transitionLabelXml.getKind()) {
					case COMMENTS:
						UtaCommentLabel commentLabel;
						label = (commentLabel = new UtaCommentLabel());
						commentLabel.setContent(transitionLabelXml.getValue());
						labelContainer.setCommentLabel(commentLabel);
						break;
					case GUARD:
						UtaGuardLabel guardLabel;
						label = (guardLabel = new UtaGuardLabel());
						errMsg = "Failed to parse guard.";
						embeddedCode = transitionLabelXml.getValue();
						guardLabel.setContent(
								getLanguageParserFactory().guardParser()
										.parseInput(embeddedCode)
						);
						labelContainer.setGuardLabel(guardLabel);
						break;
					case SELECT:
						UtaSelectLabel selectLabel;
						label = (selectLabel = new UtaSelectLabel());
						errMsg = "Failed to parse select.";
						embeddedCode = transitionLabelXml.getValue();
						selectLabel.setContent(
							getLanguageParserFactory().selectionParser()
								.parseInput(embeddedCode)
						);
						labelContainer.setSelectLabel(selectLabel);
						break;
					case ASSIGNMENT:
						UtaAssignmentsLabel assignmentsLabel;
						label = (assignmentsLabel = new UtaAssignmentsLabel());
						embeddedCode = transitionLabelXml.getValue();
						errMsg = "Failed to parse assignments.";
						assignmentsLabel.setContent(
							getLanguageParserFactory().assignmentsParser()
								.parseInput(embeddedCode)
						);
						labelContainer.setAssignmentsLabel(assignmentsLabel);
						break;
					case SYNCHRONISATION:
						UtaSynchronizationLabel synchronizationLabel;
						label = (synchronizationLabel = new UtaSynchronizationLabel());
						embeddedCode = transitionLabelXml.getValue();
						errMsg = "Failed to parse synchronization.";
						synchronizationLabel.setContent(
							getLanguageParserFactory().synchronizationParser()
								.parseInput(embeddedCode)
						);
						labelContainer.setSynchronizationLabel(synchronizationLabel);
					default:
						break;
				}
			} catch (ParseException ex) {
				throw new NtaEmbeddedCodeParseException(errMsg, ex)
					.setEmbeddedCode(embeddedCode);
			}

			label.setCoordinates(new GuiCoordinates(transitionLabelXml.getX(), transitionLabelXml.getY()));
		}

		transition.setLabels(labelContainer);
	}

	private void injectTransitionNails(UtaTransition transition, XmlNodeTransition transitionXml) {
		for (XmlNodeTransitionNail nailXml : transitionXml.getNails()) {
			TransitionNail nail = new TransitionNail();
			nail.setCoordinates(new GuiCoordinates(nailXml.getX(), nailXml.getY()));
			transition.getNails().add(nail);
		}
	}

	private void injectTransitionData(UtaTransition transition, XmlNodeTransition transitionXml) throws NtaEmbeddedCodeParseException {
		injectTransitionLabels(transition, transitionXml);
		injectTransitionNails(transition, transitionXml);
		transition.setColor(NtaConversionUtils.parseColor(transitionXml.getColor()));
	}

	private void injectLocations(UtaTemplate template, XmlNodeTemplate templateXml) throws NtaEmbeddedCodeParseException {
		XmlNodeInitialLocation initLocXml = templateXml.getInit();
		String initialLocationID = initLocXml.getRef();
		Map<String, UtaLocation> locationMap = new HashMap<>();
		UtaLocation initialLocation = null;

		for (XmlNodeLocation locationXml : templateXml.getLocations()) {
			UtaLocation location = new UtaLocation();
			injectLocationData(location, locationXml);
			locationMap.put(location.getId(), location);
			if (initialLocationID.equals(location.getId())) {
				initialLocation = location;
			}
		}

		template.setInitialLocation(initialLocation);
		for (XmlNodeTransition transitionXml : templateXml.getTransitions()) {
			UtaTransition transition = new UtaTransition();
			injectTransitionData(transition, transitionXml);
			UtaLocation sourceLocation = locationMap.get(transitionXml.getSource().getRef());
			UtaLocation targetLocation = locationMap.get(transitionXml.getTarget().getRef());
			template.getLocationGraph().addEdge(sourceLocation, targetLocation, transition);
		}
	}

	private UtaLanguageParserFactory getLanguageParserFactory() {
		return languageParserFactory;
	}

	public UtaTemplate parse(XmlNodeTemplate templateXml) throws NtaEmbeddedCodeParseException {
		UtaTemplate utaTemplate = new UtaTemplate();
		utaTemplate.setName(templateXml.getName().getValue());
		injectParameters(utaTemplate, templateXml);
		injectLocalDeclarations(utaTemplate, templateXml);
		injectLocations(utaTemplate, templateXml);
		return utaTemplate;
	}
}
