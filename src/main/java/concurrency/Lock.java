/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

/**
 * Class used for controlling threads execution by locking its usage
 */
public class Lock {
    
    private boolean isLocked = false;
    private boolean failed = false; //in case proces in thread failed
    private Exception exception;    //exception thrown in case failed
    
    /** 
     * locks the current thread, in case other thread tries to lock it will wait until
     * current thread in use unlocks
     * @throws InterruptedException in case failed
     * @throws Exception in case failed
     */
    public synchronized void lock() throws InterruptedException, Exception {
        while(isLocked) {
            wait();
        }
        if (failed) {   //throws the exception of the fail
            throw exception;
        }
        isLocked = true;
    }
    
    /** 
     * unlocks the current thread use, notifies other threads waiting and waits so others
     * can lock
     * @throws InterruptedException 
     */
    public synchronized void unlock() throws InterruptedException {
        isLocked = false;
        notify();
        wait();
    }
    
    /** 
     * unlocks the current thread use and notifies other threads
     */
    public synchronized void finalUnlock() {
        isLocked = false;
        notify();
    }
    
    /**
     * sets the process as failed and saves the exception to be thrown by other threads
     * that try to lock
     * @param e is the exception thrown when process failed
     */
    public synchronized void fail(Exception e) {
        failed = true;
        this.exception = e;
    }
}
