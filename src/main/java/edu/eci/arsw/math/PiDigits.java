package edu.eci.arsw.math;

import java.util.ArrayList;

import edu.eci.arsw.threads.ThreadCalculate;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;
    private int N = 0;
    private int digits;
    private final int[] EachThread;
    private ArrayList<String> resultsHex = new ArrayList<String>();
    private int start;


    public PiDigits(int start,int digits,int N){
        this.N = N;
        this.start = start;
        this.digits = (digits-start);
        this.EachThread = new int[N];
        int pair = this.digits / N;
        int odd = this.digits % N ;
        for(int i = 0; i < N; i++){
            if(i+1 == N){
                this.EachThread[i]=pair+odd;
            }else{
                this.EachThread[i]=pair;
            }
        }
        
    }
    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        byte[] digits = new byte[count];
        double sum = 0;

        for (int i = 0; i < count; i++) {
            if (i % DigitsPerSum == 0) {
                sum = 4 * sum(1, start)
                        - 2 * sum(4, start)
                        - sum(5, start)
                        - sum(6, start);

                start += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) sum;
        }

        return digits;
    }

    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<hexChars.length;i=i+2){
            //sb.append(hexChars[i]);
            sb.append(hexChars[i+1]);            
        }
        return sb.toString();
    }

    //Thread's Orchestor , starts each thread and returns the hexadecimal result
    public String Orchestor() throws InterruptedException{
        ThreadCalculate threads[] = new ThreadCalculate[N];
        String result = "";
        for (int thread=0; thread<N ; thread++){
            System.out.println(Integer.toString(thread+1));
            Integer inicio = (thread * (this.digits/N)) + start;
            System.out.println("EsteSiEsElinicioPa" + Integer.toString(inicio));
            System.out.println("Estaeslacantidaddedigitospa : " + Integer.toString(this.EachThread[thread]));
            System.out.println("Finalmente : " + Integer.toString(inicio + (this.EachThread[thread])));
            threads[thread] = new ThreadCalculate(Integer.toString(thread+1),inicio,(int)this.EachThread[thread]);
            threads[thread].start();

            threads[thread].join();
            String toHex = threads[thread].getResult();
            this.resultsHex.add(toHex);
            System.out.println("Thread number: "+threads[thread].getNameOfThread()+" : "+toHex);
            result = result + toHex;

        }

        System.out.println(result);

        return result;

    }

    public int ThreadsAmount() {
        return N;
    }

}
