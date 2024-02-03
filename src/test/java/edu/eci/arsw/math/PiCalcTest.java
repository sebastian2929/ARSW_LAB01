/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.math;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class PiCalcTest {

    public PiCalcTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void piGenTest() throws Exception {

        byte[] expected = new byte[]{
            0x2, 0x4, 0x3, 0xF, 0x6, 0xA, 0x8, 0x8,
            0x8, 0x5, 0xA, 0x3, 0x0, 0x8, 0xD, 0x3,
            0x1, 0x3, 0x1, 0x9, 0x8, 0xA, 0x2, 0xE,
            0x0, 0x3, 0x7, 0x0, 0x7, 0x3, 0x4, 0x4,
            0xA, 0x4, 0x0, 0x9, 0x3, 0x8, 0x2, 0x2,
            0x2, 0x9, 0x9, 0xF, 0x3, 0x1, 0xD, 0x0,
            0x0, 0x8, 0x2, 0xE, 0xF, 0xA, 0x9, 0x8,
            0xE, 0xC, 0x4, 0xE, 0x6, 0xC, 0x8, 0x9,
            0x4, 0x5, 0x2, 0x8, 0x2, 0x1, 0xE, 0x6,
            0x3, 0x8, 0xD, 0x0, 0x1, 0x3, 0x7, 0x7,};

        for (int start = 0; start < expected.length; start++) {
            for (int count = 0; count < expected.length - start; count++) {
                byte[] digits = PiDigits.getDigits(start, count);
                assertEquals(count, digits.length);

                for (int i = 0; i < digits.length; i++) {
                    assertEquals(expected[start + i], digits[i]);
                }
            }
        }
    }
    @Test
	//  Amount of threads working is correct
		public void Case1() {
			System.out.println("Test Case 1");
			int threads = 3;
			PiDigits CaseNumber1 = new PiDigits(0,10,3);
			try {
				CaseNumber1.Orchestor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertEquals(CaseNumber1.ThreadsAmount(),threads);
			
		}


        @Test
        //    1 Thread case
            public void Case2() {
                System.out.println("Test Case 2");
        //    	100 first PI digits
                String expected = "243F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89452821E638D01377BE5466CF34E90C6CC0AC";
                PiDigits CaseNumber2 = new PiDigits(0,100,1);
                String result = "";
                try {
                    result = CaseNumber2.Orchestor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                assertEquals(expected,result);
            }

            @Test
            //    2 Threads case
                public void Case3() {
                    System.out.println("Test Case 3");
            //    	50 primeros dígitos en formato hexadecimal de los decimales de pi obtenidos por internet 
                    String expected = "243F6A8885A308D313198A2E03707344A4093822299F31D008";
                    PiDigits CaseNumber3 = new PiDigits(0,50,2);
                    String result = "";
                    try {
                        result = CaseNumber3.Orchestor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    assertEquals(expected,result);
                }

                @Test
                //    3 Threads starting from the second digit
                    public void Case4() {
                        System.out.println("Test Case 4");
                //    	100 first PI digits
                        String expected = "43F6A8885A308D313198A2E03707344A4093822299F31D0082EFA98EC4E6C89452821E638D01377BE5466CF34E90C6CC0AC";
                        PiDigits CaseNumber4 = new PiDigits(1,100,3);
                        String result = "";
                        try {
                            result = CaseNumber4.Orchestor();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        assertEquals(expected,result);
                    }


}
