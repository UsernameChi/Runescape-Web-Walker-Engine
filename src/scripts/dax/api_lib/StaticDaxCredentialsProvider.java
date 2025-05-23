package scripts.dax.api_lib;

import scripts.dax.api_lib.models.DaxCredentials;
import scripts.dax.api_lib.models.DaxCredentialsProvider;

public class StaticDaxCredentialsProvider implements DaxCredentialsProvider {
    private final DaxCredentials credentials;

    public StaticDaxCredentialsProvider(String apiKey, String secretKey) {
        this.credentials = new DaxCredentials(apiKey, secretKey);
    }

    @Override
    public DaxCredentials getDaxCredentials() {
        return credentials;
    }
}