/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

public class CountThread extends Thread{
    private final int in;
    private final int out;

    public CountThread(int in, int out){
        this.in = in;
        this.out = out;
    }

    @Override
    public void run(){
        for(int i = in; i <= out; i++){
            System.out.println(i);
        }
    }
}
