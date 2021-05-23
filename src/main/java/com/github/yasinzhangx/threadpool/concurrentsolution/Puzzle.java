package com.github.yasinzhangx.threadpool.concurrentsolution;

import java.util.Set;

/**
 * @author Yasin Zhang
 */
public interface Puzzle<P, M> {

    P initialPosition();
    boolean isGoal(P position);
    Set<M> legalMoves(P position);
    P move(P position, M move);

}
