package com.babeeta.butterfly.testkit.server.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class SomeTest {
    private static AtomicInteger nextInc = new AtomicInteger((new java.util.Random()).nextInt());
    public static void main(String[] args){

        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("date--->"+simpleDateFormat.format(date));
        try {
            String s = URLEncoder.encode("您的验证码：" + "2432" + "【XX游戏】", "utf-8");
            System.out.println(s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        long nt = new Date().getTime();
        System.out.println(Math.abs((new Random()).nextInt())%10000);
        new  Random().nextLong();
    }
}
