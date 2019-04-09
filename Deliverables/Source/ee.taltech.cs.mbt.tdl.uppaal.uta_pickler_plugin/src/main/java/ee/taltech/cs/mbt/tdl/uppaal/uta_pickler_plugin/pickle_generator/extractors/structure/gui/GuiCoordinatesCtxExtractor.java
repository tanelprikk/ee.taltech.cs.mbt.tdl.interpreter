package ee.taltech.cs.mbt.tdl.uppaal.uta_pickler_plugin.pickle_generator.extractors.structure.gui;

import ee.taltech.cs.mbt.tdl.commons.st_utils.context_mapping.ContextBuilder;
import ee.taltech.cs.mbt.tdl.commons.utils.collections.CollectionUtils;
import ee.taltech.cs.mbt.tdl.uppaal.uta_pickler_plugin.pickle_generator.extractors.IPicklerContextExtractor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.structural_model.gui.GuiCoordinates;

import java.util.Set;

public class GuiCoordinatesCtxExtractor implements IPicklerContextExtractor<GuiCoordinates> {
	public static GuiCoordinatesCtxExtractor getInstance() {
		return new GuiCoordinatesCtxExtractor();
	}

	private Set<Class> requiredClasses = CollectionUtils.newSet();

	private GuiCoordinatesCtxExtractor() { }

	@Override
	public ContextBuilder extract(GuiCoordinates coordinates) {
		return ContextBuilder.newBuilder()
				.put("x", coordinates.getX())
				.put("y", coordinates.getY());
	}

	@Override
	public Set<Class> getRequiredClasses() {
		return requiredClasses;
	}
}