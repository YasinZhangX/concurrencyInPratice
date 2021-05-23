package com.github.yasinzhangx.threadpool.concurrentsolution;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yasin Zhang
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle) {
        super(puzzle);
    }

    @Override
    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new CountingSolverTask(p, m, n);
    }

    class CountingSolverTask extends SolverTask {

        CountingSolverTask(P p, M m, Node<P, M> n) {
            super(p, m, n);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }
}
