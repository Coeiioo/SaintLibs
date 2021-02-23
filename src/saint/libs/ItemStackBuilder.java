package saint.libs;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ItemStackBuilder {

    private final ItemStack itemStack;

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder(Material material, int amount, int data) {
        this(new ItemStack(material, amount, (short) data));
    }

    public ItemStackBuilder(Material material, int amount) {
        this(material, amount, 0);
    }

    public ItemStackBuilder(Material material) {
        this(material, 1, 0);
    }

    public ItemStackBuilder setMeta(Consumer<ItemMeta> consumer) {
        final ItemMeta meta = itemStack.getItemMeta();
        consumer.accept(meta);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder name(String name) {
        return setMeta(meta -> meta.setDisplayName(name));
    }

    public ItemStackBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemStackBuilder lore(List<String> lore) {
        return setMeta(meta -> meta.setLore(lore));
    }

    public ItemStackBuilder texture(String target) {
         return setMeta(meta -> {
            final SkullMeta skullMeta = (SkullMeta) meta;

            final GameProfile gameProfile = new GameProfile(Bukkit.getOfflinePlayer(target).getUniqueId(), null);
            gameProfile.getProperties().put("textures", new Property("textures", target));

            try {
                Field field = skullMeta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(skullMeta, gameProfile);
            } catch (Exception e) {
            }
        });
    }

    public ItemStackBuilder head(OfflinePlayer player) {
        return setMeta(meta -> ((SkullMeta) meta).setOwner(player.getName()));
    }

    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        return setMeta(meta -> meta.addEnchant(enchantment, level, true));
    }

    public ItemStack build() {
        return itemStack;
    }
}