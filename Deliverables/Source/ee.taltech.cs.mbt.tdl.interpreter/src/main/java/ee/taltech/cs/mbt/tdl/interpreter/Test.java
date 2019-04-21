package ee.taltech.cs.mbt.tdl.interpreter;

import java.io.ByteArrayInputStream;

public class Test {
	public static void main(String... args) {
		Interpreter.getInstance().interpret(
				Test.class.getResourceAsStream("/SampleSystem.xml"),
				new ByteArrayInputStream(
						"E(TS1; TS2)".getBytes()
				),
				System.out
		);
	}
}