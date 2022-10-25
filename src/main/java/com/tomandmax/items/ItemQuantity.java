package com.tomandmax.items;

/**
 * Represent an item quantity
 * is used in the Chest's hashtable
 * @author Felipe Escárate
 */
public class ItemQuantity {
    int number;

    /**
     * Creates a quantity initialized in zero
     */
    ItemQuantity(){
        number = 0;
    }

    /**
     * This method is used when you add items to the chest
     */
    void add(int quantity){
        number += quantity;
    }

    /**
     * Checks if the quantity of an item is more than zero at that moment
     * @return true if there are more than zero items and false if not
     */
    boolean thereAre() {
        return number>0;
    }

    /**
     * This method is used when someone takes out an item
     * In that case, the quantity decreases in one
     */
    void takeOne(){
        number -= 1;
    }

    /**
     * Gives the item quantity
     * @return the number of items
     */
    public int getQuantity(){
        return number;
    }

}
