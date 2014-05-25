import java.lang.Math;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collections;

/**
 * Actors produce and consume, but they don't all produce or consume or want the same amounts of the same things.
 * Actors exchange with other actors. They create from their list of wants a set of transactions they would make.
 *
 */
public class Actor {
    private int id;
    private ArrayList<Product> productArrayList;
    private Hashtable<Product, Long> inventory = new Hashtable<Product, Long>();
    private Hashtable<Product, Long> productionrates = new Hashtable<Product, Long>();
    private Hashtable<Product, Long> consumptionrates = new Hashtable<Product,Long>();
    private Hashtable<Product, Product> willingtrades = new Hashtable<Product, Product>();
    private ArrayList<Product> wants = new ArrayList<Product>();

    Actor(int id,
          double inventorySlope,
          double productionratesSlope,
          double consumptionratesSlope,
          ArrayList<Product> products) {
        this.id = id;
        productArrayList = products;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            inventory.put(product, Math.round(Math.pow(i, inventorySlope)));
            productionrates.put(product, Math.round(Math.pow(i, productionratesSlope)));
            consumptionrates.put(product, Math.round(Math.pow(i, consumptionratesSlope)));
            wants.add(product);
        }

        // Variable "wants" represents each Actor's ordinal ranking of how much they desire each product.
        // When possible willingtrades are created, products with lower indexes are treated as more highly sought.
        Collections.shuffle(wants);

        //Generate willingtrades
        setWillingTrades();
    }

    /**
     * In this version, willingtrades are merely 1 for 1.
     * Actor is willing to give 1 of any lower-ranked Product for 1 of any higher-ranked Product.
     * Prices will be hard to implement, so do it this way for now.
     */
    private void setWillingTrades() {
        willingtrades.clear();
        for (int i = 0; i < wants.size()-1; i++) { //Loop through list of Wants starting with most-desired
            if (inventory.get(wants.get(i+1)) > 0) { //If product in inventory, offer it in exchange for a product desired more.
                Product desired = wants.get(i);
                Product offered = wants.get(i+1);
                willingtrades.put(desired, offered);
            }
        }
    }

    public void produce() {
        /** Raises quantities of products in inventory proportionally to the production rate. */
        for (Product p : productArrayList) {
            Long rate = productionrates.get(p);
            Long qty = inventory.get(p) + Math.round((1 * rate));

            inventory.put(p, qty);
        }
        setWillingTrades();

    }

    public void consume() {
        /** Reduces quantities of products in inventory proportionally to the consumption rate. */
        for (Product p : productArrayList) {
            Long rate = consumptionrates.get(p);
            Long qty = inventory.get(p) - Math.round((1 * rate));
            if (qty < 1L) {
                qty = 0L;
            }

            inventory.put(p, qty);
        }
        setWillingTrades();
    }
    
    public Hashtable<Product, Product> getWillingtrades() {
        /** returns Actor's willingtrades */
        return this.willingtrades;
    }
    public int getId() { return this.id;}
    public Hashtable<Product, Long> getInventory() {return this.inventory;}
    public Hashtable<Product, Long> getProductionrates() {return this.productionrates;}
    public Hashtable<Product, Long> getConsumptionrates() {return this.consumptionrates;}


}
