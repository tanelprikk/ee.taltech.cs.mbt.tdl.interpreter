package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.type;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.identifier.Identifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.type.Type;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.visitors.IDeclarationVisitor;

/**
 * Adapter from UPPAAL documentation:<br/>
 * The <i>typedef</i> keyword is used to name types.<br/>
 * Syntax:<br/>
 * <pre>
 * TypeDecl     ::= 'typedef' Type ID ArrayDecl* (',' ID ArrayDecl*)* ';'
 * </pre>
 * Note that the language allows type declarations to be grouped.<br/>
 * This class represents a single non-grouped type declaration.
 */
public class TypeDeclaration extends AbsTypeDeclaration {
	private Type type;
	private Identifier identifier;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public <T> T accept(IDeclarationVisitor<T> visitor) {
		return visitor.visitTypeDeclaration(this);
	}
}