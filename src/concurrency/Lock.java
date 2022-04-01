/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 *
 * @author nacho
 */
public class Lock {
    
    private boolean isLocked = false;
    private boolean failed = false;
    private Exception exception;
    
    public synchronized void lock() throws InterruptedException, Exception {
        while(isLocked) {
            System.out.println("lock ocupado, esperando");
            wait();
        }
        if (failed) {   //throws the exception of the fail
            throw exception;
        }
        System.out.println("lock libre, ahora en uso");
        isLocked = true;
    }
    
    //unlocks and calls wait to give time other thread to catch the lock
    public synchronized void unlock() throws InterruptedException {
        isLocked = false;
        System.out.println("lock desbloqueado, notificando a los demas y esperando");
        notify();
        wait();
    }
    
    //unlocks but don't wait
    public synchronized void finalUnlock() {
        isLocked = false;
        System.out.println("lock desbloqueado, notificando a los demas usuarios");
        notify();
    }
    
    //set the lock as process fail and saves the exception thrown
    public synchronized void fail(Exception e) {
        failed = true;
        this.exception = e;
        System.out.println("fallo el proceso");
    }
}
