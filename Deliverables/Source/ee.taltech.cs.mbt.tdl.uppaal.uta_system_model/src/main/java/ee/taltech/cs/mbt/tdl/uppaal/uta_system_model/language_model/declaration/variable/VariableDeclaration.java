package ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.variable;

import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.declaration.variable.initializer.AbsVariableInitializer;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.identifier.Identifier;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.type.Type;
import ee.taltech.cs.mbt.tdl.uppaal.uta_system_model.language_model.visitors.IDeclarationVisitor;

/**
 * Represents a variable declaration.<br/>
 * A variable can be declared with an initializing expression.<br/>
 * The initializing expression can be structured (i.e. an array or struct initializer)
 * or a flat expression.<br/>
 * <br/>
 * Syntax:<br/>
 * <pre>
 * VariableDecl ::= Type VariableID (',' VariableID)* ';'
 * VariableID   ::= ID ArrayDecl* [ '=' Initialiser ]
 * Initialiser  ::= Expression
 *               |  '{' Initialiser (',' Initialiser)* '}'
 * </pre>
 * Examples:<br/>
 * <ul>
 *   <li><i>const int a = 1;</i></li>
 *   <li><i>int a[2][3] = { { 1, 2, 3 }, { 4, 5, 6} };</i></li>
 * </ul>
 * Note that the language allows variable declarations for a type to be grouped together.<br/>
 * This class represents a single non-grouped variable declaration.
 */
public class VariableDeclaration extends AbsVariableDeclaration {
	private Type type;
	private Identifier identifier;
	private AbsVariableInitializer initializer;

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

	public AbsVariableInitializer getInitializer() {
		return initializer;
	}

	public void setInitializer(AbsVariableInitializer initializer) {
		this.initializer = initializer;
	}

	@Override
	public <T> T accept(IDeclarationVisitor<T> visitor) {
		return visitor.visitVariableDeclaration(this);
	}
}
