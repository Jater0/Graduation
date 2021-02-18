package cn.jater.graduation.forum.utils.id;

import java.util.Random;

public class IDHandler {
    public static String getUid() {
        String random = String.valueOf(Math.random());
        StringBuffer sb = new StringBuffer();
        sb.append(random, 3, 7);
        sb.append(System.currentTimeMillis());
        return sb.toString().substring(0, 7);
    }

    public static String getRandomID(String type, int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        sb.append(type);
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
