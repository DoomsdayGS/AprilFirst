package ru.glavset.aprilfirst;

import java.util.Random;

/**
 * Created by Roman on 26.01.2016.
 */
public class CodeString {

    private static final char[] CO = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();


    public static String getCode(){
        String RW = "";
        Float K1;
        Float K2;
        final Random random = new Random();
        //K1 = random.nextFloat()*360 - 180;
        //K2 = random.nextFloat()*180-90;
        //RW = K1+","+K2+" ";
        for (int i=0; i<4; i++)
        {
            for(int j=0;j<5;j++)
            {
                RW = RW+CO[random.nextInt(61)];
            }
            RW = RW+" ";
        }

        return(RW);
    }


}


