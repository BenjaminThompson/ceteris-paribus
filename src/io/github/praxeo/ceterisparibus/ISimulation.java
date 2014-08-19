package io.github.praxeo.ceterisparibus;

/**
 * Created by bjt on 8/19/14.
 */
public interface ISimulation {
    void trade(Actor actor1, Actor actor2);
    void redistributeProducts();
    void reportEconomyState(long iteration);
    void Simulate();
}
