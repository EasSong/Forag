package cuit.util;

/**
 * Created by Esong on 2017/6/18.
 */
public class TaskTimer implements Runnable{
    private static int SUBMIT_COUNT = 0;
    private static int TIME_DELAY = 30;

    @Override
    public void run() {
        while (true){
            System.out.println("Time Delay: "+TIME_DELAY);
            if (--TIME_DELAY > 1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }else {
                System.out.println("Submit state: "+MyUtil.submitUserInterest());
                if (MyUtil.submitUserInterest()){
                    if (!Thread.currentThread().isInterrupted()){
                        System.out.println("Thread is "+Thread.currentThread().isAlive());
                        Thread.currentThread().interrupt();
                        SUBMIT_COUNT = 0;
                        break;
                    }
                }
            }
        }
    }
    public static void resetTimeDelay(){
        TIME_DELAY = 30;
    }
    public static void addSubmitCount(){
        ++SUBMIT_COUNT;
    }
    public static int getSubmitCount(){
        return SUBMIT_COUNT;
    }
}
