package org.sonnet.utils;

import java.util.List;
import java.util.Map;

public class Print
{
    public static void print(String s)
    {
        System.out.println(s);
    }
    public static void print(Integer i)
    {
        System.out.println(i);
    }
    public static void print(Double d)
    {
        System.out.println(d);
    }
    public static void print(Float f)
    {
        System.out.println(f);
    }
    public static void print(char c)
    {
        System.out.println(c);
    }
    public static void print(boolean b)
    {
        System.out.println(b);
    }
    public static void printError(String s)
    {
        System.err.println(s);
    }
    public static void printSize(List list)
    {
        System.out.println(list.size());
    }

    public static <T> void printList(List<T> list)
    {
        for(T listItem : list)
        {
            System.out.println(listItem);
        }
    }

    public static void printAtIndex(List list, int index)
    {
        if (list == null || list.isEmpty())
//      if (list.isEmpty() || list == null)   // if null comes last, it tries to run the code below on a null list and throws a NullPointerException (NPE)
        {
            System.out.println("WARNING! LIST IS EMPTY!");
            return;
        }
        try
        {
            System.out.println(list.get(index));
        }
        catch (IndexOutOfBoundsException ioobe)
        {
            System.out.println("ERROR! INVALID INDEX! : " + index);
        }
    }

    public static void printValue(Map map, String key)
    {
        System.out.println(map.get(key));
    }

    public static void printArray(int[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            System.out.println("index: " + i + " value: " + array[i]);
        }
    }

    public static void printArrayBackwards(int[] array)
    {
        for (int i = array.length-1; i >= 0; i--)
        {
            System.out.println("index: " + i + " value: " + array[i]);
        }
    }

    public static void printPartition()
    {
        System.out.println("------------------------");
    }

    public static void printPartitionWithTitle(String title)
    {
        System.out.println("------------ " + title + " ------------");
    }

    public static void printErrorPartitionWithTitle(String title)
    {
        System.err.println("------------ " + title + " ------------");
    }
}
