import java.util.Hashtable;

/**
 * Created by bjt on 5/24/14.
 */
public class Order {  //An order is a set of willing trades associated with a given actor
    Actor actor;
    Hashtable<Product, Product> willingTrades;

    Order(Actor actor, Hashtable<Product, Product> willingTrades) {
        this.actor = actor;
        this.willingTrades = willingTrades;
    }
}
