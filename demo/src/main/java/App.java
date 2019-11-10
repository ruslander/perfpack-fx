import com.codahale.metrics.Meter;
import com.codahale.metrics.Timer;
import com.signalfx.codahale.SfxMetrics;
import com.signalfx.codahale.metrics.SettableLongGauge;
import demo.HttpNetClient;

import java.util.ArrayList;
import java.util.List;

public class App {
    static long RUN_TIME_MSEC = 1000*40;

    public static void main(String[] args) throws Exception {

        SfxMetrics metrics = SfxMetricsRegistry.wireMetrics();
        SettableLongGauge gouge = metrics.longGauge("concurrencyLevel","consumer", "1");
        Meter meter = metrics.meter("throughput","consumer", "1");
        Timer timer = metrics.timer("responseTime", "consumer", "1");


        int maxConcurrency = 11;

        for (int i = 1; i < maxConcurrency; i=i+2) {

            gouge.setValue(i);

            List<Thread> threads = new ArrayList<>();

            for (int thrs = 0; thrs < i; thrs++) {

                Thread thread = new Thread(() -> {
                    long startTime = System.currentTimeMillis();
                    long now;

                    do {

                        Timer.Context c = timer.time();
                        try {
                            HttpNetClient.get();
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            c.close();
                        }

                        meter.mark();

                        now = System.currentTimeMillis();
                    } while (now - startTime < RUN_TIME_MSEC);
                });

                threads.add(thread);
                thread.start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            gouge.setValue(0);
            Thread.sleep(1000*30);
        }

        System.out.println("done moving on");
    }
}
