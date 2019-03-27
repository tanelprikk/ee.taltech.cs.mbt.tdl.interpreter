package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.misc.array_size_modifier;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.type.Type;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.visitors.IArrayModifierVisitor;

/**
 * An {@link AbsArrayModifier} that specifies the array size
 * using a scalar set type.
 */
public class SizeTypeArrayModifier extends AbsArrayModifier<Type> {
	@Override
	public <T> T accept(IArrayModifierVisitor<T> visitor) {
		return visitor.visitSizeTypeModifier(this);
	}
}