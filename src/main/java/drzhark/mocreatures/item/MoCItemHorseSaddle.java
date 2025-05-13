/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import net.minecraft.item.Item;

public class MoCItemHorseSaddle extends MoCItem {

    public MoCItemHorseSaddle(Item.Properties properties, String name) {
        super(properties.maxStackSize(32), name);
    }
}
