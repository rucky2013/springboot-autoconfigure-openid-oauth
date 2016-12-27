package com.alexbt.appdirect.notifications.builder;

import java.util.Collections;

import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.enu.Flag;
import com.alexbt.appdirect.notifications.model.enu.NoticeType;
import com.alexbt.appdirect.notifications.model.enu.Status;
import com.alexbt.appdirect.notifications.model.enu.Type;

public abstract class NotificationForTestFactory {
    public static Notification getTestNotification() {
        NotificationBuilder builder = NotificationBuilder.getBuilder();

        PayloadBuilder payloadBuilder = builder.getPayloadBuilder();
        payloadBuilder.addonInstanceId("addonInstanceId");

        payloadBuilder.getUserBuilder()//
                .attributes(Collections.singletonMap("key", "value"))//
                .email("payload-email")//
                .firstName("payload-firstName")//
                .language("payload-language")//
                .lastName("payload-lastName")//
                .locale("payload-locale")//
                .openId("payload-openId")//
                .uuid("payload-uuid")//

                .getAddressBuilder()//
                .city("payload-city")//
                .country("payload-country")//
                .state("payload-state")//
                .street1("payload-street1")//
                .street2("payload-street2")//
                .zip("payload-zip");

        payloadBuilder.getAccountBuilder()//
                .accountIdentifier("accountIdentifier")//
                .status(Status.ACTIVE);
        payloadBuilder.getCompanyBuilder()//
                .country("country")//
                .email("email")//
                .externalId("externalId")//
                .name("name")//
                .uuid("uuid")//
                .phoneNumber("phoneNumber")//
                .website("website");

        payloadBuilder.getNoticeBuilder()//
                .message("message")//
                .type(NoticeType.UPCOMING_INVOICE);

        payloadBuilder.getOrderBuilder()//
                .addonOfferingCode("addonOfferingCode")//
                .editionCode("editionCode")//
                .pricingDuration("pricingDuration");

        builder.getMarketPlaceBuilder()//
                .baseUrl("baseUrl")//
                .partner("partner");

        builder.getUserBuilder()//
                .attributes(Collections.singletonMap("key", "value"))//
                .email("creator-email")//
                .firstName("creator-firstName")//
                .language("creator-language")//
                .lastName("creator-lastName")//
                .locale("creator-locale")//
                .openId("creator-openId")//
                .uuid("creator-uuid")//

                .getAddressBuilder()//
                .city("creator-city")//
                .country("creator-country")//
                .state("creator-state")//
                .street1("creator-street1")//
                .street2("creator-street2")//
                .zip("creator-zip");

        return builder//
                .applicationUuid("applicationUuid")//
                .flag(Flag.DEVELOPMENT)//
                .returnUrl("returnUrl")//
                .type(Type.SUBSCRIPTION_CHANGE)//
                .get();
    }

}
