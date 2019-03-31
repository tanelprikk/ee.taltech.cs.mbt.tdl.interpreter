package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.type;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.misc.ITypeExtensionGroup;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.misc.BaseTypeExtensionMap;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.misc.BaseTypeExtensionMap.BaseTypeExtension;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.visitors.IDeclarationVisitor;

public class TypeDeclarationGroup extends AbsTypeDeclaration implements ITypeExtensionGroup<AbsTypeDeclaration, TypeDeclaration> {
	private BaseTypeExtensionMap baseTypeExtensionMap = new BaseTypeExtensionMap();

	@Override
	public BaseTypeExtensionMap getBaseTypeExtensionMap() {
		return baseTypeExtensionMap;
	}

	@Override
	public TypeDeclaration mapToIndependent(BaseTypeExtension baseTypeExtension) {
		TypeDeclaration typeDeclaration = new TypeDeclaration();
		typeDeclaration.setIdentifier(baseTypeExtension.getIdentifier());
		typeDeclaration.setType(baseTypeExtension.toDetachedInstance());
		return typeDeclaration;
	}

	public AbsTypeDeclaration reduceToOnlyEntryIfApplicable() {
		return getOnlyEntry().orElse(this);
	}

	@Override
	public <T> T accept(IDeclarationVisitor<T> visitor) {
		return visitor.visitTypeDeclarationGroup(this);
	}
}