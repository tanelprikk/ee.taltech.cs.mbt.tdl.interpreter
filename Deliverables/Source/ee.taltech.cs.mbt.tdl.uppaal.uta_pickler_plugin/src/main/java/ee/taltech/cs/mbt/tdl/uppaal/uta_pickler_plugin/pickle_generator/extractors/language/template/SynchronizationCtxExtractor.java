package ee.taltech.cs.mbt.tdl.uppaal.uta_pickler_plugin.pickle_generator.extractors.language.template;

import ee.taltech.cs.mbt.tdl.commons.st_utils.context_mapping.ContextBuilder;
import ee.taltech.cs.mbt.tdl.commons.utils.collections.CollectionUtils;
import ee.taltech.cs.mbt.tdl.uppaal.uta_pickler_plugin.pickle_generator.extractors.IPicklerContextExtractor;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.template.Synchronization;

import java.util.Set;

public class SynchronizationCtxExtractor implements IPicklerContextExtractor<Synchronization> {
	public static SynchronizationCtxExtractor getInstance() {
		return new SynchronizationCtxExtractor();
	}

	private Set<Class> requiredClasses = CollectionUtils.newSet();

	private SynchronizationCtxExtractor() { }

	@Override
	public ContextBuilder extract(Synchronization synchronization) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Class> getRequiredClasses() {
		return requiredClasses;
	}
}