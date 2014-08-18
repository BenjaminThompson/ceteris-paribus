package io.github.praxeo.ceterisparibus; /**
 * Created by Benjamin Thompson on 2/4/14.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * This program will simulate an economy by creating a population of producer-consumers that can trade with each other.
 * Initial distribution of wealth will be random. The simulated world contains 10 distinct "goods".
 * Each actor will have an ordered list of preferred goods. What they are willing to pay or accept for those goods
 * will be directly tied to this ranking.
 * Each actor will be a producer of some goods, and a consumer of all goods.
 * Rates of production and consumption among economic actors will be randomly assigned.
 * Rounds will consist of each actor producing and consuming one round's worth of their particular good,
 * then they will trade with the first available other actor any goods they have for other goods they value more.
 */
public class CeterisParibus {
    private static ArrayList<Product> PRODUCTS;
    private static ArrayList<Actor> ACTORS;


    public static void main(String[] args) {
        Setup();
        long TOTAL_ITERATIONS = 1000000;
        long ITERATIONS = 0;

        //TODO: Write the procedure for arranging trades and reporting on the state of the economy at each iteration.

        do {
            //TODO: Report on state of economy.

            //TODO: Arrange trades.

            //TODO: Each Actor produces and consumes.

            ITERATIONS++;
        } while (ITERATIONS < TOTAL_ITERATIONS);

    }

    private static void Setup() {
        //First generate the library of products that will be used
        PRODUCTS = new ArrayList<Product>(10);
        for (int i=0; i < 10; i++) {
            PRODUCTS.add(i, new Product(i));
        }

        //Generate the population, randomly assigning production and consumption rates
        ACTORS = new ArrayList<Actor>(1000); //Creating set of actors
        for (int i=0; i < 1000; i++) { //Start setting their production and consumption rates

            int id = i;
            double inventorySlope = Math.random()*10;
            double productionratesSlope = Math.random()*10;
            double consumptionratesSlope = Math.random()*10;

            //Create a separate list of products to shuffle, so each actor has them in a random order
            ArrayList<Product> products = new ArrayList<Product>(PRODUCTS.size());
            for(Product p: PRODUCTS) {
                products.add(p);
            }
            Collections.shuffle(products);

            ACTORS.add(i, new Actor(id, inventorySlope, productionratesSlope, consumptionratesSlope, products));

        }
    }

    public static void trade(Actor actor1, Actor actor2) {
        //Function represents a voluntary trade with zero interference.
        Hashtable <Product, Product> actor1_willingtrades = actor1.getWillingTrades();
        Hashtable <Product, Product> actor2_willingtrades = actor2.getWillingTrades();

        for (Map.Entry<Product,Product> actor1_willingtrade : actor1_willingtrades.entrySet()) {
            for (Map.Entry<Product, Product> actor2_willingtrade : actor2_willingtrades.entrySet()) {
                        //In any case where the two willing trades are the inverse of each other:
                        //              <Product1, Product2> vs <Product2, Product1>
                        //Then trade items between the Actors
                        if (actor1_willingtrade.getKey() == actor2_willingtrade.getValue())
                        {
                            //Increment actor1 key io.github.praxeo.ceterisparibus.Product by 1
                            System.out.println("Actor "+actor1.getId()+
                                    ", Product "+actor1_willingtrade.getKey().TYPE +": "+
                                    actor1.incrementProduct(actor1_willingtrade.getKey()));
                            //decrement actor2 value io.github.praxeo.ceterisparibus.Product by 1
                            try {
                                System.out.println("Actor "+actor2.getId()+
                                        ", Product "+actor2_willingtrade.getValue().TYPE +": "+
                                        actor2.decrementProduct(actor2_willingtrade.getValue()));
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("If I'm doing this right," +
                                        " I should never try to decrement an inventory that is already zero." +
                                        " Aborting.");
                                return;
                            }
                        }
                        if (actor1_willingtrade.getValue() == actor2_willingtrade.getKey()) {
                            //Decrement actor1 value io.github.praxeo.ceterisparibus.Product by 1
                            try {
                                System.out.println("Actor "+actor1.getId()+
                                        ", Product "+actor1_willingtrade.getValue().TYPE +": "+
                                        actor1.decrementProduct(actor1_willingtrade.getValue()));
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("If I'm doing this right," +
                                        " I should never try to decrement an inventory that is already zero." +
                                        " Aborting.");
                                return;
                            }
                            //increment actor2 key io.github.praxeo.ceterisparibus.Product by 1
                            System.out.println("Actor "+actor2.getId()+
                                    ", Product "+actor2_willingtrade.getKey().TYPE +": "+
                                    actor2.incrementProduct(actor2_willingtrade.getKey()));
                        }
            }
        }

        //Now that they've traded everything they are willing to trade,
        // update their list of willing trades.
        actor1.setWillingTrades();
        actor2.setWillingTrades();
    }

}
