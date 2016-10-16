package com.koens.herbivorecarnivore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    private List<ItemStack> carnivore = new ArrayList<ItemStack>();
    private List<ItemStack> herbivore = new ArrayList<ItemStack>();
    private List<PotionEffect> carnivoreeffects = new ArrayList<PotionEffect>();
    private List<PotionEffect> herbivoreeffects = new ArrayList<PotionEffect>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Loading config...");
        loadConfig();
        getLogger().info("Config loaded!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        carnivore.clear();
        herbivore.clear();
        carnivoreeffects.clear();
        herbivoreeffects.clear();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hc")) {
            if (sender.hasPermission("hc.reload")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        loadConfig();
                        sender.sendMessage(ChatColor.GREEN + "Config has been reloaded!");
                    }
                }
            }
        }
        return true;
    }

    private void loadConfig() {
        if (getConfig().isSet("carnivore")) {
            for (String s : getConfig().getStringList("carnivore")) {
                carnivore.add(new ItemStack(Material.valueOf(s)));
            }
        }
        if (getConfig().isSet("herbivore")) {
            for (String s : getConfig().getStringList("herbivore")) {
                herbivore.add(new ItemStack(Material.valueOf(s)));
            }
        }
        if (getConfig().isSet("carnivore-effects")) {
            for (String s : getConfig().getStringList("carnivore-effects")) {
                String[] parts = s.split(";");
                if (parts.length > 0) {
                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(parts[0]), Integer.parseInt(parts[1]) * 20, Integer.parseInt(parts[2]));
                    carnivoreeffects.add(effect);
                } else {
                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(parts[0]), 10 * 20, 0);
                    carnivoreeffects.add(effect);
                }
            }
        }
        if (getConfig().isSet("herbivore-effects")) {
            for (String s : getConfig().getStringList("herbivore-effects")) {
                String[] parts = s.split(";");
                if (parts.length > 0) {
                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(parts[0]), Integer.parseInt(parts[1]) * 20, Integer.parseInt(parts[2]));
                    herbivoreeffects.add(effect);
                } else {
                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(parts[0]), 10 * 20, 0);
                    herbivoreeffects.add(effect);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player p = event.getPlayer();
        ItemStack food = event.getItem();
        if (p.hasPermission("hc.carnivore")) {
            if (carnivore.contains(food)) {
                for (PotionEffect effect : carnivoreeffects) {
                    p.addPotionEffect(new org.bukkit.potion.PotionEffect(effect.getType(), effect.getDuration(), effect.getLevel()));
                }
            }
        } else if (p.hasPermission("hc.herbivore")) {
            if (herbivore.contains(food)) {
                for (PotionEffect effect : herbivoreeffects) {
                    p.addPotionEffect(new org.bukkit.potion.PotionEffect(effect.getType(), effect.getDuration(), effect.getLevel()));
                }
            }
        }
    }

}
