package io.code47.mokito;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoClassMembers {

    @Test
    public void testClassMembers() {
        Second second = mock(Second.class);
        when(second.doSecond()).thenReturn("Stubbed Second");

        First first = new First();
        first.setSecond(second);
        Assert.assertEquals("Stubbed Second", first.doSecond());
    }

    @Test
    public void testClassMembersWithPrivate() throws Exception {
        Second second = mock(Second.class);
        when(second.doSecond()).thenReturn("Stubbed Second");

        First first = new First();
        Field privateField = First.class.getDeclaredField("second");
        privateField.setAccessible(true);
        privateField.set(first, second);
        Assert.assertEquals("Stubbed Second", first.doSecond());
    }

    @Test
    public void testClassMembersWithPrivate2() throws Exception {
        Person person = new Person("name", 15);
        Person person1 = Mockito.spy(person);
        // Mockito.doReturn(true).when(person1).runInGround("ground");
        Mockito.doReturn(true).when(person1).runInGround(any(), any(), any());
        Assert.assertEquals(true, person1.isPlay());
    }

}
