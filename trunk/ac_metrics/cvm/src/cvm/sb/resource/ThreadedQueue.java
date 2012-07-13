package cvm.sb.resource;

import cvm.sb.manager.SignalInstance;

import java.util.Queue;

public class ThreadedQueue implements Runnable {
    private Effector effector;
    private Queue<SignalInstance> queue;

    private boolean run = true;

    public ThreadedQueue(Queue<SignalInstance> queue) {
        this.queue = queue;
    }

    public void enqueue(SignalInstance signal) {
        queue.offer(signal);
        wake();
    }

    public void start(Effector effector) {
        this.effector = effector;
        new Thread(this).start();
    }

    public void stop() {
        run = false;
    }

    public void run() {
        while (run) {
            while (!queue.isEmpty()) {
                process(queue.poll());
            }
            doWait();
        }
    }

    private void doWait() {
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void process(SignalInstance signal) {
        effector.execute(signal);
    }

    public void wake() {
        synchronized (this) {
            this.notify();
        }
    }
}