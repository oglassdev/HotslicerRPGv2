package com.hotslicerrpg.rpg;


import com.hotslicerrpg.rpg.Items.Item;
import com.hotslicerrpg.rpg.Items.Registry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InvUtils {
    public static boolean removeItem(Player player, Item item, int itemAmount) {
        HashMap<Item, HashMap<Integer,Integer>> itemsList = new HashMap<>();
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!Registry.hasItem(stack) || !item.getID().equals(Registry.getItem(stack).getID())) continue;

            Item customItem = Registry.getItem(player.getInventory().getItem(i));
            HashMap<Integer, Integer> intMap;

            if (itemsList.containsKey(customItem)) intMap = itemsList.get(customItem);
            else intMap = new HashMap<>();

            intMap.put(i,stack.getAmount());
            itemsList.put(customItem,intMap);
        }

        if (!itemsList.containsKey(item)) return false;
        int itemAmt = 0;
        for (Integer amt : itemsList.get(item).values()) { itemAmt+=amt; }
        if (itemAmt < itemAmount) return false;

        for (Iterator<Map.Entry<Item, HashMap<Integer,Integer>>> it = itemsList.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Item,HashMap<Integer,Integer>> entry = it.next();
            int amt = itemAmount;
            for (Iterator<Map.Entry<Integer,Integer>> it2 = entry.getValue().entrySet().iterator(); it2.hasNext();) {
                Map.Entry<Integer,Integer> entry2 = it2.next();
                if (amt <= 0) {
                    it.remove();
                    break;
                }
                int invNum = entry2.getKey();
                int amount = entry2.getValue();
                ItemStack stack = player.getInventory().getItem(invNum);
                if (amount > amt) {
                    stack.setAmount(amount-amt);
                    it.remove();
                    break;
                }
                else if (amount == amt) {
                    player.getInventory().setItem(invNum, null);
                    it.remove();
                    break;
                }
                else {
                    amt-=amount;
                    player.getInventory().setItem(invNum, null);
                    it2.remove();
                }
            }
        }
        return true;
    }

    public static void addItem(Player player, Item item, int amount) {
        ItemStack it = item.getItem();
        it.setAmount(amount);
        player.getInventory().addItem(it);
    }
}