package com.alexbt.appdirect.notifications.model.deseralization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import com.alexbt.appdirect.notifications.jsr303.BeanValidatorUtil;
import com.alexbt.appdirect.notifications.model.Notification;
import com.alexbt.appdirect.notifications.model.enu.Flag;
import com.alexbt.appdirect.notifications.model.enu.NoticeType;
import com.alexbt.appdirect.notifications.model.enu.Status;
import com.alexbt.appdirect.notifications.model.enu.Type;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationJsonConversionTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void testAllJsonPayloadSamples() throws IOException, URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("payload-samples");
        List<Path> samplePayloads = Files.walk(Paths.get(resource.toURI())).filter(Files::isRegularFile).collect(Collectors.toList());

        samplePayloads.forEach(p -> {
            try {
                testResponse(p.toFile());
            } catch (IOException e) {
                fail();
            }
        });

        // sanityCheck
        assertTrue(samplePayloads.size() > 20);

    }

    @Test
    public void testAllFieldsDeserializedNoNull()
            throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException {
        Notification notification = testResponse("payload-samples/fake-payload-all-fields.json");
        assertNoNullFields(notification, Notification.class);
    }

    @Test
    public void testAllFieldsValuesOk() throws IOException {
        Notification notification = testResponse("payload-samples/fake-payload-all-fields.json");

        assertEquals(Type.SUBSCRIPTION_CHANGE, notification.getType());
        assertEquals(Flag.DEVELOPMENT, notification.getFlag());
        assertEquals("returnUrl", notification.getReturnUrl());
        assertEquals("appUuid", notification.getApplicationUuid());

        // Marketplace
        assertEquals("https://marketplace.appdirect.com", notification.getMarketplace().getBaseUrl());
        assertEquals("APPDIRECT", notification.getMarketplace().getPartner());

        // Creator
        assertEquals("Montreal", notification.getCreator().getAddress().getCity());
        assertEquals("CA", notification.getCreator().getAddress().getCountry());
        assertEquals("QC", notification.getCreator().getAddress().getState());
        assertEquals("1234 bd Mont-Royal", notification.getCreator().getAddress().getStreet1());
        assertEquals("street2-value", notification.getCreator().getAddress().getStreet2());
        assertEquals("H4E 3C8", notification.getCreator().getAddress().getZip());
        assertEquals("alex.belisleturcot@gmail.com", notification.getCreator().getEmail());
        assertEquals("Alex", notification.getCreator().getFirstName());
        assertEquals("en", notification.getCreator().getLanguage());
        assertEquals("Belisle Turcot", notification.getCreator().getLastName());
        assertEquals("en-US", notification.getCreator().getLocale());
        assertEquals("https://marketplace.appdirect.com/openid/id/90f7c0a6-f59a-419a-926a-c6e9af28c10e", notification.getCreator().getOpenId());
        assertEquals("90f7c0a6-f59a-419a-926a-c6e9af28c10e", notification.getCreator().getUuid());
        assertEquals("accId", notification.getPayload().getAccount().getAccountIdentifier());
        assertEquals(Status.ACTIVE, notification.getPayload().getAccount().getStatus());

        // Order
        assertEquals("offeringCode", notification.getPayload().getOrder().getAddonOfferingCode());
        assertEquals("FREE", notification.getPayload().getOrder().getEditionCode());
        assertEquals("20", notification.getPayload().getOrder().getItems().get(0).getQuantity());
        assertEquals("USER", notification.getPayload().getOrder().getItems().get(0).getUnit());
        assertEquals("MONTHLY", notification.getPayload().getOrder().getPricingDuration());

        // Account
        assertEquals("accId", notification.getPayload().getAccount().getAccountIdentifier());
        assertEquals(Status.ACTIVE, notification.getPayload().getAccount().getStatus());

        // Addon
        assertEquals("5", notification.getPayload().getAddonInstance().getId());

        // company
        assertEquals("US", notification.getPayload().getCompany().getCountry());
        assertEquals("alex.belisleturcot@gmail.com", notification.getPayload().getCompany().getEmail());
        assertEquals("extId", notification.getPayload().getCompany().getExternalId());
        assertEquals("Proserus", notification.getPayload().getCompany().getName());
        assertEquals("5147955769", notification.getPayload().getCompany().getPhoneNumber());
        assertEquals("d241c44b-16ca-4c12-aa4a-34959066a577", notification.getPayload().getCompany().getUuid());
        assertEquals("ca.linkedin.com/in/alexbt", notification.getPayload().getCompany().getWebsite());

        // Configuration
        assertEquals("value", notification.getPayload().getConfiguration().get("config1"));
        assertEquals("value", notification.getPayload().getConfiguration().get("config2"));

        // notice
        assertEquals(NoticeType.REACTIVATED, notification.getPayload().getNotice().getType());
        assertEquals("msg", notification.getPayload().getNotice().getMessage());

        // User
        assertEquals("Montreal", notification.getPayload().getUser().getAddress().getCity());
        assertEquals("CA", notification.getPayload().getUser().getAddress().getCountry());
        assertEquals("QC", notification.getPayload().getUser().getAddress().getState());
        assertEquals("1234 bd Mont-Royal", notification.getPayload().getUser().getAddress().getStreet1());
        assertEquals("street2-value", notification.getPayload().getUser().getAddress().getStreet2());
        assertEquals("H4E 3C8", notification.getPayload().getUser().getAddress().getZip());
        assertEquals("alex.belisleturcot@gmail.com", notification.getPayload().getUser().getEmail());
        assertEquals("Alex", notification.getPayload().getUser().getFirstName());
        assertEquals("en", notification.getPayload().getUser().getLanguage());
        assertEquals("Belisle Turcot", notification.getPayload().getUser().getLastName());
        assertEquals("en-US", notification.getPayload().getUser().getLocale());
        assertEquals("https://marketplace.appdirect.com/openid/id/90f7c0a6-f59a-419a-926a-c6e9af28c10e", notification.getPayload().getUser().getOpenId());
        assertEquals("90f7c0a6-f59a-419a-926a-c6e9af28c10e", notification.getPayload().getUser().getUuid());
    }

    private void assertNoNullFields(Object object, Class<?> clazz) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        for (PropertyDescriptor propertyDescriptors : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
            Object result = propertyDescriptors.getReadMethod().invoke(object);
            assertTrue("missing " + object.getClass().getSimpleName() + "." + propertyDescriptors.getReadMethod().getName(), result != null);

            if (result.getClass().getPackage() != null && result.getClass().getPackage().getName().startsWith("com.alexbt")) {
                assertNoNullFields(result, result.getClass());
            }
        }
    }

    public Notification testResponse(String responseFile) throws IOException {
        String content = IOUtils.toString(getClass().getClassLoader().getResourceAsStream(responseFile), "UTF-8");
        Notification notification = objectMapper.readValue(content, Notification.class);

        Set<ConstraintViolation<Notification>> violations = BeanValidatorUtil.getInstance().validate(notification);
        assertTrue(String.valueOf(violations), violations.isEmpty());

        return notification;
    }

    public Notification testResponse(File responseFile) throws IOException {
        String content = FileUtils.readFileToString(responseFile, "UTF-8");
        Notification notification = objectMapper.readValue(content, Notification.class);

        Set<ConstraintViolation<Notification>> violations = BeanValidatorUtil.getInstance().validate(notification);
        assertTrue(String.valueOf(violations), violations.isEmpty());

        return notification;
    }
}
