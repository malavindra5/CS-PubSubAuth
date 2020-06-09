package hivemqCruxPubSubAuthExtension;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.auth.SimpleAuthenticator;
import com.hivemq.extension.sdk.api.auth.parameter.SimpleAuthInput;
import com.hivemq.extension.sdk.api.auth.parameter.SimpleAuthOutput;
import com.hivemq.extension.sdk.api.packets.connect.ConnectPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectAuthenticator implements SimpleAuthenticator {
    public static final ConnectAuthenticator INSTANCE = new ConnectAuthenticator();
    private static final @NotNull Logger log = LoggerFactory.getLogger(ConnectAuthenticator.class);

    @Override
    public void onConnect(@NotNull final SimpleAuthInput simpleAuthInput, @NotNull final SimpleAuthOutput simpleAuthOutput) {

        final ConnectPacket connectPacket = simpleAuthInput.getConnectPacket();
        log.info("Connect Packet::getClientId: {}", connectPacket.getClientId());
        //authenticate the client successfully
        simpleAuthOutput.authenticateSuccessfully();
    }

}

