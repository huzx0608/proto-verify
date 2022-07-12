package io.code47.mokito;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MokitoBasicTests {

    @Test
    public void testCreateMockClass() {
        List mockedList = mock(List.class);
        Assert.assertTrue(mockedList instanceof List);

        ArrayList mockedArrayList = mock(ArrayList.class);
        Assert.assertTrue(mockedArrayList instanceof ArrayList);
        Assert.assertTrue(mockedArrayList instanceof List);
    }

    @Test
    public void testCreateMockObject() {
        List mockedList = mock(List.class);
        when(mockedList.add("one")).thenReturn(true);
        when(mockedList.size()).thenReturn(1);

        Assert.assertTrue(mockedList.add("two") == false);
        Assert.assertTrue(mockedList.size() == 1);
        Assert.assertTrue(mockedList.add("one") == true);

        Iterator iter = mock(Iterator.class);
        when(iter.next()).thenReturn("one").thenReturn("two");
        String result = iter.next() + " " + iter.next();
        Assert.assertTrue(result.equals("one two"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testCreateMockObjectWithException() {
        Iterator iter = mock(Iterator.class);
        when(iter.next()).thenReturn("one").thenReturn("two");
        String result = iter.next() + " " + iter.next();
        Assert.assertTrue(result.equals("one two"));

        doThrow(new NoSuchElementException()).when(iter).next();
        iter.next();
        iter.next();
    }

    @Test
    public void testCallMokitoMethod() {
        List mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.add("one");
        mockedList.add("two");
        mockedList.add("three");
        mockedList.add("three");
        mockedList.add("three");

        when(mockedList.size()).thenReturn(5);
        Assert.assertEquals(mockedList.size(), 5);
        verify(mockedList, atLeastOnce()).add("one");
        verify(mockedList, atLeastOnce()).add("two");
        verify(mockedList, times(3)).add("three");
    }

    @Test
    public void testMockitoSubObject() {
        List linkedList = new LinkedList();
        List spyList = spy(linkedList);

        spyList.add("one");
        spyList.add("two");

        when(spyList.size()).thenReturn(100);
        Assert.assertEquals(spyList.size(), 100);
        Assert.assertEquals(spyList.get(0), "one");
        Assert.assertEquals(spyList.get(1), "two");
    }

    @Test
    public void testMockitoCaptureArgument() {
        List<String> strList = Arrays.asList("one", "two", "three");
        List mockedList = mock(List.class);
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        mockedList.addAll(strList);
        verify(mockedList).addAll(argumentCaptor.capture());

        Assert.assertEquals(3, argumentCaptor.getValue().size());
        Assert.assertEquals(strList, argumentCaptor.getValue());
    }

    @Test
    public void testMockitoList() {
        LinkedList<String> mockedList = mock(LinkedList.class);
        when(mockedList.get(anyInt())).thenReturn("element");
        when(mockedList.contains(argThat(e -> e.equals("element")))).thenReturn(true);
        Assert.assertEquals("element", mockedList.get(1000));
        mockedList.add("1");

        verify(mockedList).get(anyInt());
        verify(mockedList).add(argThat(argument -> argument.length() < 10));
    }

}
