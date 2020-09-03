package com.jmeterx.async;

import java.util.Random;
public class Phone {
    public static String randomCellphone() {
        StringBuilder phone = new StringBuilder("+86");
        Random random = new Random();
        int idx = random.nextInt(PData.PhoneSegment.length);
        phone.append(PData.PhoneSegment[idx]);

        for (int i = 0; i < 8; ++i) {
            phone.append((int)(Math.random() * 10));
        }

        return phone.toString();
    }
}
