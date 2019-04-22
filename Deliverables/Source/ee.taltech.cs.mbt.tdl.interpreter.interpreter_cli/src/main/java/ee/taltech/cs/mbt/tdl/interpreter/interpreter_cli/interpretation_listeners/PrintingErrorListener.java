package ee.taltech.cs.mbt.tdl.interpreter.interpreter_cli.interpretation_listeners;

import ee.taltech.cs.mbt.tdl.commons.antlr_facade.AbsAntlrParserFacade.ParseException;
import ee.taltech.cs.mbt.tdl.commons.antlr_facade.configuration.base.ErrorListener.SyntaxError;
import ee.taltech.cs.mbt.tdl.interpreter.interpreter_cli.EReturnStatus;
import ee.taltech.cs.mbt.tdl.interpreter.interpreter_core.listeners.IInterpretationErrorListener;
import ee.taltech.cs.mbt.tdl.scenario.scenario_composer.trapsets.extraction.BaseTrapsetsExtractor.InvalidBaseTrapsetDefinitionException;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.composite.InvalidSystemStructureException;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.composite.parsing.language.EmbeddedCodeSyntaxException;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.composite.serialization.language.SyntaxRepresentationException;
import ee.taltech.cs.mbt.tdl.uppaal.uta_parser.structure.UtaNodeMarshaller.MarshallingException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Consumer;

public class PrintingErrorListener implements IInterpretationErrorListener {
	private boolean printTraces;
	private PrintStream out;
	private Consumer<EReturnStatus> errorStatusHandler;

	private void handleFailure(Throwable t, EReturnStatus returnStatus) {
		if (printTraces)
			t.printStackTrace(out);
		errorStatusHandler.accept(returnStatus);
	}

	public PrintingErrorListener(boolean printTraces, PrintStream out, Consumer<EReturnStatus> errorStatusHandler) {
		this.printTraces = printTraces;
		this.out = out;
		this.errorStatusHandler = errorStatusHandler;
	}

	@Override
	public void onExpressionParseFailure(ParseException ex) {
		out.println("ERROR: TDL expression contains syntax error(s):");
		for (SyntaxError syntaxError : ex.getSyntaxErrors()) {
			out.println(
					syntaxError.getLine() + ":" + syntaxError.getCharPositionInLine() + " - " + syntaxError.getMessage()
			);
		}
		handleFailure(ex, EReturnStatus.EXPRESSION_PARSING_FAILED);
	}

	@Override
	public void onExpressionParseFailure(IOException ex) {
		onUnexpectedFailure(ex);
	}

	@Override
	public void onModelParseFailure(MarshallingException ex) {
		out.println("ERROR: Cannot unmarshal input system.");
		handleFailure(ex, EReturnStatus.MODEL_PARSING_FAILED);
	}

	@Override
	public void onModelParseFailure(InvalidSystemStructureException ex) {
		out.println("ERROR: Input system contains structural errors:");
		for (String errorMessage : ex.getErrorMessages()) {
			out.println(errorMessage);
		}
		handleFailure(ex, EReturnStatus.MODEL_PARSING_FAILED);
	}

	@Override
	public void onModelParseFailure(EmbeddedCodeSyntaxException ex) {
		out.println("ERROR: Invalid code snippet in input model:");
		out.println(ex.getOffendingCodeSnippet());
		out.println();
		out.println("Syntax errors:");
		for (SyntaxError syntaxError : ex.getSyntaxErrors()) {
			out.println(
					syntaxError.getLine() + ":" + syntaxError.getCharPositionInLine() + " - " + syntaxError.getMessage()
			);
		}
		handleFailure(ex, EReturnStatus.MODEL_PARSING_FAILED);
	}

	@Override
	public void onScenarioCompositionFailure(InvalidBaseTrapsetDefinitionException ex) {
		out.println("ERROR: " + ex.getMessage());
		handleFailure(ex, EReturnStatus.SCENARIO_COMPOSITION_FAILED);
	}

	@Override
	public void onScenarioSerializationFailure(MarshallingException ex) {
		out.println("INTERNAL ERROR: Unable to marshal output system.");
		handleFailure(ex, EReturnStatus.SCENARIO_SERIALIZATION_FAILED);
	}

	@Override
	public void onScenarioSerializationFailure(SyntaxRepresentationException ex) {
		out.println("INTERNAL ERROR: Unable to generate embedded code snippet for:");
		out.println(ex.getClass().getSimpleName() + ":" + ex.getInvalidInstance());
		handleFailure(ex, EReturnStatus.SCENARIO_SERIALIZATION_FAILED);
	}

	@Override
	public void onScenarioSerializationFailure(InvalidSystemStructureException ex) {
		out.println("INTERNAL ERROR: Output system contains structural errors:");
		for (String errorMessage : ex.getErrorMessages()) {
			out.println(errorMessage);
		}
		handleFailure(ex, EReturnStatus.SCENARIO_SERIALIZATION_FAILED);
	}

	@Override
	public void onUnexpectedFailure(Throwable t) {
		out.println("INTERNAL ERROR: Unexpected " + t.getClass().getSimpleName() + ".");
		handleFailure(t, EReturnStatus.UNEXPECTED_ERROR);
	}
}