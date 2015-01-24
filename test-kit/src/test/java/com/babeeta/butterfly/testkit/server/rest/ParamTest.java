package com.babeeta.butterfly.testkit.server.rest;

/**
 * Created by user on 2014/12/15.
 */
public class ParamTest {
    public static void main(String[] args){
        int a = 3;
        Test test = new Test();
        System.out.println(a == test.b);
        change(a, test);
        System.out.println(a == test.b);
    }
    private static void change(int a, Test t){
        a++;
        t.b++;
        System.out.println(a==t.b);
    }
}

class Test{
    public int b = 3;
}
