package ee.taltech.cs.mbt.tdl.commons.facades.antlr_facade;

import ee.taltech.cs.mbt.tdl.commons.facades.antlr_facade.AbsAntlrParserFacade.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public abstract class AbsAntlrParser<ResultType> {
	public abstract AbsAntlrParserFacade<ResultType, ?, ?, ?> getFacade();

	public ResultType parseInput(String inputString) throws ParseException {
		return parseInput(inputString, StandardCharsets.UTF_8);
	}

	public ResultType parseInput(String inputString, Charset charset) throws ParseException {
		try {
			return parseInput(new ByteArrayInputStream(inputString.getBytes(charset)));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public ResultType parseInput(InputStream in) throws ParseException, IOException {
		return getFacade().parse(in);
	}
}
