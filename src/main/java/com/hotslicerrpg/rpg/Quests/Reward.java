package com.hotslicerrpg.rpg.Quests;

import com.hotslicerrpg.rpg.Items.Item;
import com.hotslicerrpg.rpg.Main;
import com.hotslicerrpg.rpg.Utils;
import me.lokka30.treasury.api.economy.account.PlayerAccount;
import me.lokka30.treasury.api.economy.response.EconomyException;
import me.lokka30.treasury.api.economy.response.EconomySubscriber;
import me.lokka30.treasury.api.economy.transaction.EconomyTransactionInitiator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Reward {
    private final int coins;
    private final Item[] items;
    public Reward(int coins) {
        this.coins = coins;
        this.items = new Item[0];
    }
    public Reward(Item... items) {
        this.coins = 0;
        this.items = items;
    }
    public Reward(int coins, Item... items) {
        this.coins = coins;
        this.items = items;
    }
    public void give(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(),() -> Main.getEconomy().retrievePlayerAccount(player.getUniqueId(), new EconomySubscriber<PlayerAccount>() {
            @Override
            public void succeed(@NotNull PlayerAccount account) {
                account.depositBalance(BigDecimal.valueOf(coins), EconomyTransactionInitiator.SERVER, Main.getEconomy().getPrimaryCurrency(), new EconomySubscriber<BigDecimal>() {
                    @Override
                    public void succeed(@NotNull BigDecimal bigDecimal) {
                        Utils.sendLog(Level.INFO, "Paid " + player.getName() + " " + coins);
                    }
                    @Override
                    public void fail(@NotNull EconomyException e) {
                        Utils.sendLog(Level.INFO, "ASDnaisdj iuaJSDiu ");
                        e.printStackTrace();
                    }
                });
            }
            @Override
            public void fail(@NotNull EconomyException e) {
                e.printStackTrace();;
            }
        }));
        for (Item item : items) {
            player.getInventory().addItem(item.getItem());
        }
    }
    public String[] prettyArray() {
        List<String> strings = new ArrayList<>();
        if (coins > 0) strings.add("&8| &a+ " + coins + " coins");
        if (items.length > 0) {
            for (Item item : items) {
                strings.add("&8| &a+ " + item.getName());
            }
        }
        return strings.toArray(new String[0]);
    }
}
