package com.futengwl.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Random;
import java.util.UUID;

/**
 * @author yuanwy
 * @date 2018/6/5 11:11
 * @desc
 * @modified
 */
public class UUIDUtils {

    public UUIDUtils() {
    }

    public static String newGUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }

    public static String getId() {
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            UUID uuid = UUID.randomUUID();
            String guidStr = uuid.toString();
            e.update(guidStr.getBytes(), 0, guidStr.length());
            return (new BigInteger(1, e.digest())).toString(16);
        } catch (NoSuchAlgorithmException var3) {
            return null;
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(getId());
        System.out.println(newGUID());
    }

    public static Long getNumId() {
        Long simple = Long.valueOf(System.currentTimeMillis());
        int random = (new Random()).nextInt(900000) + 100000;
        return Long.decode(simple.toString() + random);
    }

    public static String getNumStringId() {
        Long simple = Long.valueOf(System.currentTimeMillis());
        int random = (new Random()).nextInt(900000) + 100000;
        return simple.toString() + random;
    }


}
