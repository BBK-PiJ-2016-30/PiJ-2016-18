package ex09;

import java.util.List;
import java.util.ArrayList;

public class DiningPhilosophers {
    static final int philosopherCount = 5; //  token +2 behaviour good for odd #s
    static final int runSeconds = 15;
    static List<Fork> forks = new ArrayList<>();
    static List<Philosopher> philosophers = new ArrayList<>();
 
    public static void main(String[] args) {
        for (int i = 0 ; i < philosopherCount ; i++) forks.add(new Fork());
        for (int i = 0 ; i < philosopherCount ; i++)
            philosophers.add(new Philosopher());
        for (Philosopher p : philosophers) new Thread(p).start();
        long endTime = System.currentTimeMillis() + (runSeconds * 1000);
 
        do {                                                    //  print status
            StringBuilder sb = new StringBuilder("|");
 
            for (Philosopher p : philosophers) {
                sb.append(p.state.toString());
                sb.append("|");            //  This is a snapshot at a particular
            }                              //  instant.  Plenty happens between.
 
            sb.append("     |");
 
            for (Fork f : forks) {
                int holder = f.holder.get();
                sb.append(holder==-1?"   ":String.format("P%02d",holder));
                sb.append("|");
            }
 
            System.out.println(sb.toString());
            try {Thread.sleep(1000);} catch (Exception ex) {}
        } while (System.currentTimeMillis() < endTime);
 
        for (Philosopher p : philosophers) p.end.set(true);
        for (Philosopher p : philosophers)
            System.out.printf("P%02d: ate %,d times, %,d/sec\n",
                p.id, p.timesEaten, p.timesEaten/runSeconds);
    }
}