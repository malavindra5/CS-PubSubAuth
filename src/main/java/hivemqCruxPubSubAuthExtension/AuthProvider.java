package hivemqCruxPubSubAuthExtension;

import com.hivemq.extension.sdk.api.auth.Authorizer;
import com.hivemq.extension.sdk.api.auth.parameter.AuthorizerProviderInput;
import com.hivemq.extension.sdk.api.services.auth.provider.AuthorizerProvider;

public class AuthProvider implements AuthorizerProvider {

    private final GenericAuthorizer genericAuthorizer = new GenericAuthorizer();

    @Override
    public Authorizer getAuthorizer(AuthorizerProviderInput authorizerProviderInput) {
        return genericAuthorizer;
    }
}
