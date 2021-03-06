package ee.taltech.cs.mbt.tdl.commons.utils.validation;

public class ValidationError {
	private String errorMessage;

	public static ValidationError forMessage(String errorMessage) {
		return new ValidationError().setErrorMessage(errorMessage);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public ValidationError setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
}
