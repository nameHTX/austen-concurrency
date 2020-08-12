package com.austen.lockExample.sync.layout1;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.openjdk.jol.info.ClassLayout;
import sun.security.provider.MD2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class JOLExample12 {
    public static void main(String[] args) throws Exception {
        String result = "0.84";
        for (int i = 0; i < 100000000; i++) {
            result = md2(result);
        }
        out.println(result);
    }

    private static String md2(String src) {
        try {
            MessageDigest md2 = MessageDigest.getInstance("MD2");
            byte[] digest = md2.digest(src.getBytes());
            return HexBin.encode(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}