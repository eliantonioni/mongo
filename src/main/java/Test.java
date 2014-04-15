/**
 * Created by aeliseev on 07.04.2014
 */
public class Test {

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable(){
            public void run() {
                throw new RuntimeException();
            }
        });

        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("exception ! " + e + " from thread " + t);
            }
        });

        t.start();
    }
}
