package com.austen.lockExample.sync.layout;

/**
 * @author Austen.Huang
 * @date 2020/8/12
 */
public class B {

    private int i = 0;

    public synchronized void parse() {
        i++;
    }
}
