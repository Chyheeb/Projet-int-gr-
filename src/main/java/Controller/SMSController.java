package Controller;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

public class SMSController {

    private static final String VONAGE_API_KEY = "fee90d3d";
    private static final String VONAGE_API_SECRET = "reJkdLEAng8vTw99";
    private static final String SENDER_ID = "SmartCity";

    private static final VonageClient client = VonageClient.builder()
            .apiKey(VONAGE_API_KEY)
            .apiSecret(VONAGE_API_SECRET)
            .build();

    public static void sendSMS(String phoneNumber, String message) {

        TextMessage textMessage = new TextMessage(SENDER_ID, phoneNumber, message);

        try {

            SmsSubmissionResponse response = client.getSmsClient().submitMessage(textMessage);

            if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {

                System.out.println(" SMS envoyer avec succes via Vonage !  "
                        + response.getMessages().get(0));
            } else {

                System.out.println(" Echec de l'envoi du SMS via Vonage : "
                        + response.getMessages().get(0).getErrorText());
            }
        } catch (Exception e) {

            System.out.println(" Exception lors de l'envoi du SMS via Vonage !");
            e.printStackTrace();
        }
    }
}
