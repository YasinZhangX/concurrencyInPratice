package com.github.yasinzhangx.safeclosing.poison;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * @author Yasin Zhang
 */
public class IndexingService {

    private static final File POISON = new File("");
    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();
    private final BlockingQueue<File> queue;
    private final File root;

    public IndexingService(BlockingQueue<File> queue, File root) {
        this.queue = queue;
        this.root = root;
    }

    public void start() {
        producer.start();
        consumer.start();
    }

    public void stop() {
        producer.interrupt();
    }

    public void awaitTermination() throws InterruptedException {
        consumer.join();
    }

    public class CrawlerThread extends Thread {

        @Override
        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                /* stop */
            } finally {
                while (true) {
                    try {
                        queue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        /* try again */
                    }
                }
            }
        }

        private void crawl(File root) throws InterruptedException {
            for (File f : Objects.requireNonNull(root.listFiles())) {
                if (f.getAbsolutePath().equals("")) {
                    throw new InterruptedException();
                } else {
                    System.out.println(f.getAbsolutePath());
                }
            }
        }

    }

    public class IndexerThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    File file = queue.take();
                    if (file == POISON) {
                        break;
                    } else {
                        System.out.println(file.getAbsolutePath());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
