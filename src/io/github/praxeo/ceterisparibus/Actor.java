package io.github.praxeo.ceterisparibus;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collections;

/**
 * Actors produce and consume, but they don't all produce or consume or want the same amounts of the same things.
 * Actors exchange with other actors. They create from their list of WANTS a set of transactions they would make.
 *
 */
public class Actor {
    private int ID;
    private ArrayList<Product> PRODUCT_SET;
    private Hashtable<Product, Long> INVENTORY = new Hashtable<Product, Long>();
    private Hashtable<Product, Long> PRODUCTION_RATES = new Hashtable<Product, Long>();
    private Hashtable<Product, Long> CONSUMPTION_RATES = new Hashtable<Product,Long>();
    private Hashtable<Product, Product> WILLING_TRADES = new Hashtable<Product, Product>();
    private ArrayList<Product> WANTS = new ArrayList<Product>();

    Actor(int id,
          double inventorySlope,
          double productionratesSlope,
          double consumptionratesSlope,
          ArrayList<Product> products) {
        this.ID = id;
        PRODUCT_SET = products;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            INVENTORY.put(product, Math.round(Math.pow(i, inventorySlope)));
            PRODUCTION_RATES.put(product, Math.round(Math.pow(i, productionratesSlope)));
            CONSUMPTION_RATES.put(product, Math.round(Math.pow(i, consumptionratesSlope)));
            WANTS.add(product);
        }

        // Variable "WANTS" represents each io.github.praxeo.ceterisparibus.Actor's ordinal ranking of how much they desire each product.
        // When possible WILLING_TRADES are created, products with lower indexes are treated as more highly sought.
        Collections.shuffle(WANTS);

        //Generate WILLING_TRADES
        setWillingTrades();
    }

    /**
     * In this version, WILLING_TRADES are merely 1 for 1.
     * io.github.praxeo.ceterisparibus.Actor is willing to give 1 of any lower-ranked io.github.praxeo.ceterisparibus.Product for 1 of any higher-ranked io.github.praxeo.ceterisparibus.Product.
     * Prices will be hard to implement, so do it this way for now.
     */
    protected void setWillingTrades() {
        WILLING_TRADES.clear();
        for (int i = 0; i < WANTS.size()-1; i++) { //Loop through list of Wants starting with most-desired
            if (INVENTORY.get(WANTS.get(i+1)) > 0) { //If product in INVENTORY, offer it in exchange for a product desired more.
                Product desired = WANTS.get(i);
                Product offered = WANTS.get(i+1);
                WILLING_TRADES.put(desired, offered);
            }
        }
    }

    public void produce() {
        /** Raises quantities of products in INVENTORY proportionally to the production rate. */
        for (Product p : PRODUCT_SET) {
            Long rate = PRODUCTION_RATES.get(p);
            Long qty = INVENTORY.get(p) + Math.round((1 * rate));

            INVENTORY.put(p, qty);
        }
        setWillingTrades();

    }

    public void consume() {
        /** Reduces quantities of products in INVENTORY proportionally to the consumption rate. */
        for (Product p : PRODUCT_SET) {
            Long rate = CONSUMPTION_RATES.get(p);
            Long qty = INVENTORY.get(p) - Math.round((1 * rate));
            if (qty < 1L) {
                qty = 0L;
            }

            INVENTORY.put(p, qty);
        }
        setWillingTrades();
    }

    protected Long incrementProduct(Product product) {
        Long qty = INVENTORY.get(product);
        Long new_qty = qty+1;
        INVENTORY.put(product, new_qty);
        return new_qty;
    }

    protected Long decrementProduct(Product product) throws Exception {
        Long qty = INVENTORY.get(product);
        Long new_qty = qty-1;
        if (qty < 1L) {
            throw new Exception("You can't decrement INVENTORY already at zero.");
        } else {
            INVENTORY.put(product, new_qty);
            return new_qty;
        }
    }
    
    public Hashtable<Product, Product> getWillingTrades() {
        /** returns io.github.praxeo.ceterisparibus.Actor's WILLING_TRADES */
        return this.WILLING_TRADES;
    }
    
    public int getId() { return this.ID;}
    
    public Hashtable<Product, Long> getInventory() {return this.INVENTORY;}
    
    public Hashtable<Product, Long> getProductionrates() {return this.PRODUCTION_RATES;}
    
    public Hashtable<Product, Long> getConsumptionrates() {return this.CONSUMPTION_RATES;}

    public ArrayList<Long> getSatisfactionReport () {
        /** Reports the io.github.praxeo.ceterisparibus.Actor's INVENTORY quantities in order of greatest desired to least.
         ** Does not reveal what those products are **/
        ArrayList<Long> satisfactionReport = new ArrayList<Long>(WANTS.size());
        for (int i = 0; i < WANTS.size(); i++) {
            Product p = WANTS.get(i);
            satisfactionReport.add(INVENTORY.get(p));
        }
        
        return satisfactionReport;
    }

}
