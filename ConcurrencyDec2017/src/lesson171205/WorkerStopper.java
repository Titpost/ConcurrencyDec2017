package lesson171205;

public class WorkerStopper implements PoisonPill, Runnable {

    @Override
    public void stop() {
        System.out.println("Worker have to stop due to reason: << stop-demonstration >>");
    }


    @Override
    public void run() {}
}
