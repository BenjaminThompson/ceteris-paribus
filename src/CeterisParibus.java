/**
 * Created by Benjamin Thompson on 2/4/14.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This program will simulate an economy by creating a population of producer-consumers that can trade with each other.
 * Initial distribution of wealth will be random. The simulated world contains 10 distinct "goods".
 * Each actor will have an ordered list of preferred goods. The price they are willing to pay or accept for those goods
 * will be directly tied to this ranking.
 * Each actor will be a producer of some goods, and a consumer of all goods.
 * Rates of production and consumption among economic actors will be randomly assigned.
 * Rounds will consist of each actor producing and consuming one round's worth of their particular good,
 * then they will trade with the first available other actor any goods they have for other goods they value more.
 */
public class CeterisParibus {

    public static void main(String[] args) {
        //First generate the library of products that will be used
        ArrayList<Product> productsMasterList = new ArrayList<Product>(10);
        for (int i=0; i < 10; i++) {
            productsMasterList.add(i, new Product(i));
        }

        //Generate the population, randomly assigning production and consumption rates
        ArrayList<Actor> actors = new ArrayList<Actor>(1000); //Creating set of actors
        for (int i=0; i < 1000; i++) { //Start setting their production and consumption rates

            int id = i;
            double inventorySlope = Math.random()*10;
            double productionratesSlope = Math.random()*10;
            double consumptionratesSlope = Math.random()*10;
            ArrayList<Product> products = productsMasterList;
            Collections.shuffle(products);

            actors.add(i, new Actor(id, inventorySlope, productionratesSlope, consumptionratesSlope, products));

        }


        //TODO: Write the procedure for arranging trades and reporting on the state of the economy at each iteration.
    }
    
    public static void trade(Actor actor1, Actor actor2) {
        //Function represents a voluntary trade with zero interference.
        Hashtable <Product, Product> actor1_willingtrades = actor1.getWillingTrades();
        Hashtable <Product, Product> actor2_willingtrades = actor2.getWillingTrades();

        for (Entry<Product,Product> actor1_willingtrade : actor1_willingtrades.entrySet()) {
            for (Entry<Product, Product> actor2_willingtrade : actor2_willingtrades.entrySet()) {
                        //In any case where the two willing trades are the inverse of each other:
                        //              <Product1, Product2> vs <Product2, Product1>
                        //Then trade items between the Actors
                        if (actor1_willingtrade.getKey() == actor2_willingtrade.getValue())
                        {
                            //Increment actor1 key Product by 1, decrement actor2 value Product by 1
                        }
                        if (actor1_willingtrade.getValue() == actor2_willingtrade.getKey()) {
                            //Decrement actor1 value Product by 1, increment actor2 key Product by 1
                        }
            }
        }
        
        actor1.setWillingTrades();
        actor2.setWillingTrades();
    }

}
