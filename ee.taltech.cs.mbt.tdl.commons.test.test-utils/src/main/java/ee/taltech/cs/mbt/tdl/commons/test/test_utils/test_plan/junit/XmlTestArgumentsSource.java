package ee.taltech.cs.mbt.tdl.commons.test.test_utils.test_plan.junit;

import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ArgumentsSource(XmlTestArgumentsProvider.class)
public @interface XmlTestArgumentsSource {
	String path();
}
