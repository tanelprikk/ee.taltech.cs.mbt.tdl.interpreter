package ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.composite.parsing.language;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import ee.taltech.cs.mbt.tdl.commons.antlr_facade.AbsAntlrParserFacade.ParseException;
import ee.taltech.cs.mbt.tdl.commons.parser.AbsAntlrParser;
import ee.taltech.cs.mbt.tdl.commons.utils.collections.operations.IOperation;
import ee.taltech.cs.mbt.tdl.commons.utils.collections.operations.OperationQueue;
import ee.taltech.cs.mbt.tdl.commons.utils.strings.LineExtractor;
import ee.taltech.cs.mbt.tdl.commons.utils.strings.StringUtils;
import ee.taltech.cs.mbt.tdl.uppaal.tdl_parser.composite.parsing.language.ParseQueue.ParseOperation;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseQueue extends OperationQueue<ParseOperation<?>, EmbeddedCodeSyntaxException> {
	public static class ParseOperation<T> implements IOperation<EmbeddedCodeSyntaxException> {
		private String code;
		private Consumer<T> resultConsumer;
		private AbsAntlrParser<T> parser;

		public ParseOperation(String code, AbsAntlrParser<T> parser, Consumer<T> resultConsumer) {
			this.code = code;
			this.parser = parser;
			this.resultConsumer = resultConsumer;
		}

		@Override
		public void execute() throws EmbeddedCodeSyntaxException {
			try {
				T output = parser.parseInput(code);
				resultConsumer.accept(output);
				if (parser.hasSyntaxErrors()) {
					throw new EmbeddedCodeSyntaxException(code, parser.getSyntaxErrors());
				}
			} catch (ParseException ex) {
				throw new EmbeddedCodeSyntaxException(code, parser.getSyntaxErrors(), ex);
			}
		}
	}

	private static final Pattern multiLineCommentPattern = Pattern
			.compile("/\\*.*\\*/", Pattern.DOTALL);

	private static final Predicate<String> singleLineCommentTest = Pattern
			.compile("^\\s*//.*$", Pattern.DOTALL)
			.asPredicate();

	private static String removeMultiLineComments(String code) {
		return multiLineCommentPattern.matcher(code)
				.replaceAll("");
	}

	/**
	 * The grammar implementation currently cannot cope with code snippets which only contain comments.
	 * @param input UTA code snippet.
	 * @return Whether the code snippet only contains comments.
	 */
	private static boolean onlyContainsComments(String input) {
		String simpleInput = removeMultiLineComments(input);
		Iterator<String> lineIterator = LineExtractor.forString(simpleInput);
		while (lineIterator.hasNext()) {
			String line = lineIterator.next();
			if (StringUtils.isEmpty(line))
				continue;
			if (!singleLineCommentTest.test(line))
				return false;
		}
		return true;
	}

	public <T> void enqueue(String input, AbsAntlrParser<T> parser, Consumer<T> resultConsumer) {
		if (StringUtils.isEmpty(input))
			return;

		if (onlyContainsComments(input))
			return;

		enqueue(new ParseOperation<>(input, parser, resultConsumer));
	}
}
