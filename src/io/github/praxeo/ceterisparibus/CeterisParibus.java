package io.github.praxeo.ceterisparibus; /**
 * Created by Benjamin Thompson on 2/4/14.
 */


/**
 * This program will simulate an economy by creating a population of producer/consumers that can trade with each other.
 * Initial distribution of wealth will be random. The simulated world contains 10 distinct "goods".
 * Each actor will have an ordered list of preferred goods. What they are willing to pay or accept for those goods
 * will be directly connected to this ranking.
 * Each actor will be a producer of some goods, and a consumer of all goods.
 * Rates of production and consumption among economic actors will be randomly assigned.
 * Iterations will consist of each actor producing and consuming one round's worth of their particular good,
 * then trading with other actors any goods they have for other goods they value more.
 * The defaultSimulation has all actors trading with all other actors.
 */
public class CeterisParibus {


    public static void main(String[] args) {
        Simulation defaultSimulation = new Simulation(10, 100, 100) {
            @Override
            public void Simulate() {
                reportEconomyState(0);
                for (long i = 1; i < TOTAL_ITERATIONS; i++) {

                    for (Actor actor1 : ACTORS) {
                        for (Actor actor2 : ACTORS) {
                            if (!actor1.equals(actor2)) {
                                trade(actor1, actor2);
                            }
                        }
                    }
                    for (Actor actor : ACTORS) {
                        actor.produce();
                        actor.consume();
                    }
                    reportEconomyState(i);
                }
            }
        };

        defaultSimulation.Simulate();
    }
}