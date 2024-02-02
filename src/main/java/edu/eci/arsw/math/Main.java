/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.math;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 *
 * @author hcadavid
 */
public class Main {

    public static void main(String a[]) {
        //System.out.println(bytesToHex(PiDigits.getDigits(0, 10)));
        //System.out.println(bytesToHex(PiDigits.getDigits(1, 100)));
        //System.out.println(bytesToHex(PiDigits.getDigits(1, 1000000)));

        Instant starttime = Instant.now();
        PiDigits Case1 = new PiDigits(0,100,2);
        try{
            Case1.Orchestor();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Instant exectime = Instant.now();

        System.out.println(" Execution time in seconds: " + Duration.between(exectime,starttime).toString());

    }

    

    

}
