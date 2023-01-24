package com.rg.aws.ses.services.serviceImpl;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.ListIdentitiesResponse;
import software.amazon.awssdk.services.ses.model.SesException;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsRequest;
import software.amazon.awssdk.services.sns.model.ListSubscriptionsResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.ArrayList;
import java.util.List;


public class ListEmailIdentities {

    public static void main(String[] args) {
//        listSESIdentities();
//        listTopics();
//        createContactList();
//        updateContactList();
//        addEmailToContactList();
//        getContact();
//        getContactList();

    }

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

    public static List<String> listTopics() {
        List<String> identities = new ArrayList<>();
        Region region = Region.AP_NORTHEAST_1;
        SnsClient client = SnsClient.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        try {
            ListSubscriptionsRequest request = ListSubscriptionsRequest.builder()
                    .build();

            ListSubscriptionsResponse result = client.listSubscriptions(request);
            System.out.println(result.subscriptions());
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return identities;
    }

    public static void createContactList() {
        Region region = Region.AP_NORTHEAST_1;
        Topic topic = Topic.builder()
                .topicName("Football")
                .displayName("Football")
                .defaultSubscriptionStatus("OPT_IN")
                .build();
        SesV2Client client = SesV2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        CreateContactListRequest request = CreateContactListRequest.builder()
                .contactListName("Rupesh")
                .topics(topic)
                .build();
        CreateContactListResponse response = client.createContactList(request);
        System.out.println(response);
    }

    public static void updateContactList() {
        Region region = Region.AP_NORTHEAST_1;
        Topic topic = Topic.builder()
                .topicName("BasketBall")
                .displayName("BasketBall")
                .defaultSubscriptionStatus("OPT_IN")
                .build();
        SesV2Client client = SesV2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        UpdateContactListRequest request = UpdateContactListRequest.builder()
                .contactListName("Rupesh")
                .topics(topic)
                .build();
        UpdateContactListResponse response = client.updateContactList(request);
        System.out.println(response);
    }

    public static void addTopicToContactList() {
        Region region = Region.AP_NORTHEAST_1;
        Topic topic = Topic.builder()
                .topicName("BasketBall")
                .displayName("BasketBall")
                .defaultSubscriptionStatus("OPT_IN")
                .build();
        SesV2Client client = SesV2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        ListContactListsRequest request = ListContactListsRequest.builder()
                .build();
        ListContactListsResponse response = client.listContactLists(request);
        System.out.println(response);
    }




    public static void addEmailToContactList() {
        TopicPreference topicPreference = TopicPreference.builder()
                .topicName("Football")
                .subscriptionStatus("OPT_IN")
                .build();
        Region region = Region.AP_NORTHEAST_1;
        SesV2Client client = SesV2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        CreateContactRequest request = CreateContactRequest.builder()
                .contactListName("Rupesh")
                .emailAddress("rupeshregmi001@gmail.com")
                .topicPreferences(topicPreference)
                .build();
        CreateContactResponse response = client.createContact(request);
        System.out.println(response);
    }

    public static void getContact() {
        Region region = Region.AP_NORTHEAST_1;
        SesV2Client client = SesV2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        GetContactRequest request = GetContactRequest.builder()
                .contactListName("Rupesh")
                .emailAddress("rupeshgaudel3@gmail.com")
                .build();
        GetContactResponse response = client.getContact(request);
        System.out.println(response);
    }

    public static void getContactList() {
        Region region = Region.AP_NORTHEAST_1;
        SesV2Client client = SesV2Client.builder()
                .region(region)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        GetContactListRequest request = GetContactListRequest.builder()
                .contactListName("Rupesh")
                .build();
        GetContactListResponse response = client.getContactList(request);
        System.out.println(response);
    }

    public static void sendMail() {
        Content content = Content.builder()
                .data("AAA")
                .build();


    }

}
