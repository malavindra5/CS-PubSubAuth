package hivemqCruxPubSubAuthExtension;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.auth.PublishAuthorizer;
import com.hivemq.extension.sdk.api.auth.SubscriptionAuthorizer;
import com.hivemq.extension.sdk.api.auth.parameter.PublishAuthorizerInput;
import com.hivemq.extension.sdk.api.auth.parameter.PublishAuthorizerOutput;
import com.hivemq.extension.sdk.api.auth.parameter.SubscriptionAuthorizerInput;
import com.hivemq.extension.sdk.api.auth.parameter.SubscriptionAuthorizerOutput;
import com.hivemq.extension.sdk.api.packets.publish.PublishPacket;
import com.hivemq.extension.sdk.api.packets.subscribe.SubackReasonCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GenericAuthorizer implements SubscriptionAuthorizer, PublishAuthorizer {
    public static final GenericAuthorizer INSTANCE = new GenericAuthorizer();
    private static final @NotNull Logger log = LoggerFactory.getLogger(GenericAuthorizer.class);

    @Override
    public void authorizeSubscribe(@NotNull final SubscriptionAuthorizerInput input, @NotNull final SubscriptionAuthorizerOutput output) {

//        final UserProperties userProperties = input.getUserProperties();
//        log.info("UserProperties: " + userProperties + input.getClientInformation().getClientId());
        if (!topicVerification(input.getSubscription().getTopicFilter(), input.getClientInformation().getClientId())) {
            log.info("Invalid topic subscription");
            output.failAuthorization(SubackReasonCode.UNSPECIFIED_ERROR, "Invalid topic subscription");
            return;
        }
        log.info(input.getClientInformation().getClientId() + " subscribed to " + input.getSubscription().getTopicFilter() + " successfully");
        output.authorizeSuccessfully();
    }

    private boolean topicVerification(String topic, String cruxId) {
//        log.info("Inside topicVerification::" + topic + cruxId);
        return getCruxIdFromTopic(topic).equals(cruxId);
    }

    public void authorizePublish(final @NotNull PublishAuthorizerInput input, final @NotNull PublishAuthorizerOutput output) {

        //get the PUBLISH packet contents
        final PublishPacket publishPacket = input.getPublishPacket();
        log.info("Publish Packet::getTopic: {}", publishPacket.getTopic());
        //allow every Topic Filter starting with "admin"
//        if (!checkPublisherAccess( , publishPacket.getTopic())) {
//            output.failAuthorization();
//            return;
//        }
        output.authorizeSuccessfully();

    }

    private String getCruxIdFromTopic(String topic){
        return topic.split("_")[1];
    }

    private boolean checkPublisherAccess(List<String> whitelistedCruxIDs, String cruxId) {
        return whitelistedCruxIDs.contains(cruxId);
    }
}
