package com.rg.aws.ses.services.serviceImpl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.ListIdentitiesResult;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.ListIdentitiesResponse;

public class SubscriptionBasedService {

    String endpoint = "/v2/email/contact-lists/ContactListName/contacts";

    public static void main(String[] args) {
        createContact();
    }

    public static void createContact(){
        Region region = Region.AP_NORTHEAST_1;
        SesClient client = SesClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        ListIdentitiesResponse response = client.listIdentities();
        System.out.println(response.identities());


    }
}
