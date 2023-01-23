package com.rg.aws.ses.services.serviceImpl;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.ListIdentitiesResponse;
import software.amazon.awssdk.services.ses.model.SesException;

import java.util.ArrayList;
import java.util.List;


public class ListEmailIdentities {

    public static List<String> listSESIdentities() {
        List<String> identities = new ArrayList<>();
        Region region = Region.AP_NORTHEAST_1;
        SesClient client = SesClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        try {
            ListIdentitiesResponse identitiesResponse = client.listIdentities();
            identities = identitiesResponse.identities();
        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
        return identities;
    }

}
