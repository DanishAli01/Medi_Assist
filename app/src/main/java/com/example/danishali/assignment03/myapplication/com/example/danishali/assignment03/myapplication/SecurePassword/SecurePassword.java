package com.example.danishali.assignment03.myapplication.com.example.danishali.assignment03.myapplication.SecurePassword;

import android.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurePassword {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "x+QKG>wRtw2}6++9ulA_N~K}cHEI`dxO";

    public static String FOLD(String value) throws Exception
    {
        Key mykey = PRIVATEKEYGENERATOR();
        Cipher mycipher = Cipher.getInstance(SecurePassword.ALGORITHM);
        mycipher.init(Cipher.ENCRYPT_MODE, mykey);
        byte [] foldByteValue = mycipher.doFinal(value.getBytes("utf-32"));
        String foldValue64 = Base64.encodeToString(foldByteValue, Base64.DEFAULT);
        return foldValue64;

    }
//
    public static String UNFOLD(String value) throws Exception
    {
        Key key = PRIVATEKEYGENERATOR();
        Cipher cipher = Cipher.getInstance(SecurePassword.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-32");
        return decryptedValue;

    }

    private static Key PRIVATEKEYGENERATOR() throws Exception
    {
        Key key = new SecretKeySpec(SecurePassword.KEY.getBytes(),SecurePassword.ALGORITHM);
        return key;
    }

}

