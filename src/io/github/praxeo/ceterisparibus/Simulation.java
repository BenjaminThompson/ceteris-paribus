package io.github.praxeo.ceterisparibus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by bjt on 8/19/14.
 */
public class Simulation implements ISimulation {
    protected static ArrayList<Product> PRODUCTS;
    protected static ArrayList<Actor> ACTORS;
    protected static long TOTAL_ITERATIONS;

    Simulation(int nProducts, int nActors, long nIterations) {
        TOTAL_ITERATIONS = nIterations;

        //First generate the library of products that will be used
        PRODUCTS = new ArrayList<Product>(nProducts);
        for (int i = 0; i < nProducts; i++) {
            PRODUCTS.add(i, new Product(i));
        }

        //Generate the population, randomly assigning production and consumption rates
        ACTORS = new ArrayList<Actor>(nActors); //Creating set of actors
        for (int i = 0; i < nActors; i++) { //Start setting their production and consumption rates

            int id = i;
            double inventorySlope = Math.random() * 10;
            double productionratesSlope = Math.random() * 10;
            double consumptionratesSlope = Math.random() * 10;

            //Create a separate list of products to shuffle, so each actor has them in a random order
            ArrayList<Product> products = new ArrayList<Product>(PRODUCTS.size());
            for (Product p : PRODUCTS) {
                products.add(p);
            }
            Collections.shuffle(products);

            ACTORS.add(i, new Actor(id, inventorySlope, productionratesSlope, consumptionratesSlope, products));

        }
    }

    @Override
    public void trade(Actor actor1, Actor actor2) {

        //Function represents a voluntary trade with zero interference.
        Hashtable<Product, Product> actor1_willing_trades = actor1.getWillingTrades();
        Hashtable <Product, Product> actor2_willing_trades = actor2.getWillingTrades();

        for (Map.Entry<Product,Product> actor1_willing_trade : actor1_willing_trades.entrySet()) {
            for (Map.Entry<Product, Product> actor2_willing_trade : actor2_willing_trades.entrySet()) {
                //In any case where the two willing trades are the inverse of each other:
                //              <Product1, Product2> vs <Product2, Product1>
                //Then trade items between the Actors
                if (actor1_willing_trade.getKey() == actor2_willing_trade.getValue() &&
                        actor1_willing_trade.getValue() == actor2_willing_trade.getKey())
                {
                    //Increment actor1 key io.github.praxeo.ceterisparibus.Product by 1
                    //System.out.println("Actor "+actor1.getId()+
                    //        ", Product "+actor1_willingtrade.getKey().TYPE +": "+
                    actor1.incrementProduct(actor1_willing_trade.getKey());
                    //));
                    //decrement actor2 value io.github.praxeo.ceterisparibus.Product by 1
                    try {
                        //System.out.println("Actor "+actor2.getId()+
                        //        ", Product "+actor2_willingtrade.getValue().TYPE +": "+
                        actor2.decrementProduct(actor2_willing_trade.getValue());
                        //));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("If I'm doing this right," +
                                " I should never try to decrement an inventory that is already zero." +
                                " Aborting.");
                        return;
                    }
                }
                if (actor1_willing_trade.getValue() == actor2_willing_trade.getKey() &&
                        actor1_willing_trade.getKey() == actor2_willing_trade.getValue()) {
                    //Decrement actor1 value io.github.praxeo.ceterisparibus.Product by 1
                    try {
                        //System.out.println("Actor "+actor1.getId()+
                        //        ", Product "+actor1_willingtrade.getValue().TYPE +": "+
                        actor1.decrementProduct(actor1_willing_trade.getValue());
                        //));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("If I'm doing this right," +
                                " I should never try to decrement an inventory that is already zero." +
                                " Aborting.");
                        return;
                    }
                    //increment actor2 key io.github.praxeo.ceterisparibus.Product by 1
                    //System.out.println("Actor "+actor2.getId()+
                    //        ", Product "+actor2_willingtrade.getKey().TYPE +": "+
                    actor2.incrementProduct(actor2_willing_trade.getKey());
                    //));
                }
            }
        }

        //Now that they've traded everything they are willing to trade,
        // update their list of willing trades.
        actor1.setWillingTrades();
        actor2.setWillingTrades();
    }

    @Override
    public void redistributeProducts() {

    }

    @Override
    public void reportEconomyState(long iteration) {
        for (Actor actor : ACTORS) {
            String row = iteration + "\t";
            row += actor.getId() + "\t";
            row += actor.getAverageProductionToConsumptionRatio() + "\t";
            for (Long qty : actor.getInventoriesByWantRank()) {
                row += qty + "\t";
            }
            System.out.println(row);
        }

    }

    @Override
    public void Simulate() {

    }
}
