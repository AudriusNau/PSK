package security.control;

import security.boundary.HashGenerator;
import security.entity.HashServiceType;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

@Stateless
public class SHAGenerator implements HashGenerator {

    public String algorithmName;

    protected SHAGenerator() {
        // EJB need this.
    }

    public SHAGenerator(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String getHashedText(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.algorithmName);
            byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);

            return encoded;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isHashedTextMatch(String text, String hashedText) {
        String tempHashedText = getHashedText(text);
        return tempHashedText.equals(hashedText);
    }
}
