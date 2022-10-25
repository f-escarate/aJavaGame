package com.tomandmax.items;

import javafx.util.Pair;

import java.util.*;

/**
 * Class that represents the chest where the items are stored
 * Is composed by a hashtable with the items names, the items and the items quantity
 * @author Felipe Escárate
 */
public class Chest {
    private final Hashtable<String, Pair<Item, ItemQuantity>> items;

    /**
     * Creates a void Chest
     * we initialize all the items quantities in 0
     * (because there are no items)
     */
    public Chest(){
        items = new Hashtable<>();
        Pair<Item, ItemQuantity> healingPotionsPair = new Pair<> (new HealingPotion(), new ItemQuantity());
        items.put("HealingPotion", healingPotionsPair);
        Pair<Item, ItemQuantity> fightingPotionsPair = new Pair<> (new FightingPotion(), new ItemQuantity());
        items.put("FightingPotion", fightingPotionsPair);
    }

    /**
     * Add items to the group of items
     * @param itemName is the of the item that will be added to the chest
     * @param quantity the quantity of items that will be added
     */
    public void addItems(String itemName, int quantity){
        ItemQuantity itemQuantity = items.get(itemName).getValue();
        itemQuantity.add(quantity);
    }

    /**
     * Take out an item of the chest and returns it
     * @param itemName is the of the item that will be taken out from the chest
     * @return the item with that name
     * @throws NullPointerException if there is no item with that name in the chest
     */
    public Item takeOutItem(String itemName) throws NullPointerException{
        Pair<Item, ItemQuantity> itemPair = items.get(itemName);
        Item item = itemPair.getKey();
        ItemQuantity itemQuantity = itemPair.getValue();
        if (itemQuantity.thereAre()) {
            itemQuantity.takeOne(); //le baja uno a la cantidad
            return item;
        }
        throw new NullPointerException("No hay items con ese nombre en el baúl");
    }

    /**
     * Method that gives the hashtable with the items
     * @return a hashtable where the key is the item name and the value is the item with its quantity
     */
    public Hashtable<String, Pair<Item, ItemQuantity>> getItems(){
        return this.items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chest chest = (Chest) o;

        return Objects.equals(items, chest.items);
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }
}
