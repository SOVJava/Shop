package util;

public class RandomNumberGenerator {

    public static int genInt(int a, int b){
        return (int)(a+Math.random()*(b-a+1));
    }


}
