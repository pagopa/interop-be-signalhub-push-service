package it.pagopa.interop.signalhub.push.service.cache.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EServiceCacheTest {

    @Test
    void testNotEquals() {
        EServiceCache x = new EServiceCache();
        x.setProducerId("123");
        x.setEserviceId("1235");
        x.setDescriptorId("123");
        EServiceCache y = new EServiceCache();
        y.setProducerId("123");
        y.setEserviceId("123");
        y.setDescriptorId("124");
        Assertions.assertFalse(x.equals(y) && y.equals(x));
        Assertions.assertFalse(x.equals(null) && y.equals(null));

    }

    @Test
    void testEqualsAndHashCode() {
        EServiceCache x = new EServiceCache();
        x.setProducerId("123");
        x.setEserviceId("123");
        x.setDescriptorId("123");
        EServiceCache y = new EServiceCache();
        y.setProducerId("123");
        y.setEserviceId("123");
        y.setDescriptorId("123");
        Assertions.assertTrue(x.equals(y) && y.equals(x));
        Assertions.assertTrue(x.hashCode() == y.hashCode());
    }
}