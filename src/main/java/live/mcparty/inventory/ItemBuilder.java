package live.mcparty.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@SuppressWarnings("unused")
public class ItemBuilder {

    private final Material material;
    private Component name = Component.empty();
    private List<Component> lore = new ArrayList<>();

    private Set<ItemFlag> itemFlags = new HashSet<>();
    private final List<ItemEnchant> itemEnchants = new ArrayList<>();

    private boolean unbreakable;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(ItemStack itemStack) {
        this.material = itemStack.getType();
        this.name = itemStack.getItemMeta().displayName();
        this.lore = itemStack.getItemMeta().lore();
        this.itemFlags = itemStack.getItemMeta().getItemFlags();
        itemStack.getItemMeta().getEnchants().forEach((enchantment, integer) -> itemEnchants.add(new ItemEnchant(enchantment, integer)));
    }

    public ItemBuilder name(Component name) {
        this.name = name;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = Component.text(name);
        return this;
    }

    public ItemBuilder setLore(Component... lines) {
        // Thing that I lost so many hours of sleep over...
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET)
                lines[i] = lines[i].decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }

        this.lore.addAll(Arrays.asList(lines));
        return this;
    }

    public ItemBuilder addLoreLine(Component line) {
        // Thing that I lost so many hours of sleep over...
        if (line.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET)
            line = line.decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        this.lore.add(line);
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemBuilder addEnchantment(ItemEnchant itemEnchant) {
        itemEnchants.add(itemEnchant);
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.displayName(name);
        itemMeta.lore(lore);
        itemMeta.setUnbreakable(unbreakable);
        itemMeta.getItemFlags().addAll(itemFlags);
        for (ItemEnchant itemEnchant : itemEnchants) {
            itemMeta.addEnchant(itemEnchant.enchantment, itemEnchant.level, true);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public record ItemEnchant(Enchantment enchantment, int level) {}

}