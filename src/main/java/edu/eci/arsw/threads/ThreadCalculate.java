package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;

public class ThreadCalculate extends Thread {
    private final int start;
    private final int count;
    private String thread;
    private String result;

    public ThreadCalculate(String NameOfThread, int a, int b) {
        this.start = a;
        this.count = b;
        this.thread = NameOfThread;
    }

    @Override
    public void run(){
        //Start an empty array where we're going to store the process's digits
        byte[] digits;
        //Invoke the function which obtains the digits of the proccess specifying the range of numbers
        digits = PiDigits.getDigits(start, count);
        //The local variable is going to store the Hexadecimal equivalency
        this.result = PiDigits.bytesToHex(digits);
    }

    //Return the hexadecimal value of the group's threads range digits
    public String getResult(){
        return this.result;
    }

    //Return the thread's name
    public String getNameOfThread(){
        return this.thread;
    }
}
