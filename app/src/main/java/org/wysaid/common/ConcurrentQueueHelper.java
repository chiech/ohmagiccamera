package org.wysaid.common;

import android.util.Log;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentQueueHelper {
    protected ConcurrentLinkedQueue<Runnable> mQueue = new ConcurrentLinkedQueue();

    public void offer(Runnable runnable) {
        this.mQueue.offer(runnable);
    }

    public void consume() {
        Runnable runnable;
        do {
            try {
                runnable = (Runnable) this.mQueue.poll();
                if (runnable != null) {
                    runnable.run();
                    continue;
                }
            } catch (Throwable throwable) {
                Log.e("libCGE_java", "ConcurrentQueueHelper: " + throwable.getLocalizedMessage());
                return;
            }
        } while (runnable != null);
    }

    public boolean isEmpty() {
        return this.mQueue.isEmpty();
    }

    public void consumeLast() {
        Runnable runnable;
        do {
            try {
                runnable = (Runnable) this.mQueue.poll();
                if (runnable != null) {
                    runnable.run();
                    continue;
                }
            } catch (Throwable throwable) {
                Log.e("libCGE_java", "ConcurrentQueueHelper: " + throwable.getLocalizedMessage());
                return;
            }
        } while (runnable != null);
    }
}
