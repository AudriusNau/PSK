package security.entity;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Documented
@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface Sha {

    @Nonbinding
    SHAAlgorithm algorithm() default SHAAlgorithm.SHA512;

    public enum SHAAlgorithm {
        SHA256("SHA-256"),
        SHA512("SHA-512");

        private final String name;

        private SHAAlgorithm(String name) {
            this.name = name;
        }

        public String getAlgorithmName() {
            return this.name;
        }
    }
}
