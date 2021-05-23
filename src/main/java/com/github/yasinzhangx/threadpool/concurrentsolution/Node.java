package com.github.yasinzhangx.threadpool.concurrentsolution;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Yasin Zhang
 */
public class Node<P, M> {

    final P pos;
    final M move;
    final Node<P, M> prev;

    Node(P pos, M move, Node<P, M> prev) {
        this.prev = prev;
        this.pos = pos;
        this.move = move;
    }

    List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (Node<P, M> n = this; n.move != null; n = n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }

}
