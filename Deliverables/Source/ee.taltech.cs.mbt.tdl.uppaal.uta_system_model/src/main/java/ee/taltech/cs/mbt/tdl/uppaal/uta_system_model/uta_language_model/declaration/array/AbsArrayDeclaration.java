package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.uta_language_model.declaration.array;

import java.util.Objects;

public class AbsArrayDeclaration<SizeSpecifier> {
	private SizeSpecifier sizeSpecifier;

	public SizeSpecifier getSizeSpecifier() {
		return sizeSpecifier;
	}

	public void setSizeSpecifier(SizeSpecifier sizeSpecifier) {
		this.sizeSpecifier = sizeSpecifier;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSizeSpecifier());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbsArrayDeclaration))
			return false;
		AbsArrayDeclaration other = (AbsArrayDeclaration) obj;
		return Objects.equals(other.sizeSpecifier, this.sizeSpecifier);
	}
}
