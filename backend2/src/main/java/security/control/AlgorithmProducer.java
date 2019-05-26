package security.control;

import security.boundary.HashGenerator;
import security.entity.HashServiceType;
import security.entity.Sha;
import java.lang.annotation.Annotation;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class AlgorithmProducer {

    @Produces
    @HashServiceType(HashServiceType.HashType.SHA)
    @Sha
    public HashGenerator produceHashGenerator(InjectionPoint ip) {
        HashGenerator hashGenerator = null;

        for (Annotation annotation : ip.getAnnotated().getAnnotations()) {
            if (annotation instanceof Sha) {

                Sha shaAnnotation = (Sha) annotation;
                hashGenerator = new SHAGenerator(shaAnnotation.algorithm().getAlgorithmName());
            }
        }

        return hashGenerator;
    }
}
