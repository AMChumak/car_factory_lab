import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import ru.nsu.ccfit.chumak.threadPool.Task;
import ru.nsu.ccfit.chumak.threadPool.ThreadPool;

public class ThreadPoolUnitTests {

    private final static Logger logger = LogManager.getLogger(ThreadPoolUnitTests.class);


    static class FindPrimeNumbersTask implements Task {

        int begin;
        int end;

        public FindPrimeNumbersTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        int find(){
            int cnt = 0;
            for (int i = begin; i <= end; i++) {
                int increase = 1;
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        increase = 0;
                        break;
                    }
                }
                cnt += increase;
            }
            return cnt;
        }

        @Override
        public String getName() {
            return "primeNumbers";
        }

        @Override
        public void performWork() {
            System.out.println(find());
        }
    }

    @Test
    void findPrimeNumbers() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(10);
        for (int i = 0; i < 50; i++) {
            FindPrimeNumbersTask task = new FindPrimeNumbersTask(100000*i*i, 100000*(i + 1)*(i+1));
            threadPool.addTask(task);
        }
        Thread.sleep(20000L);
        threadPool.interruptAll();
        threadPool.joinAll();
    }
}
