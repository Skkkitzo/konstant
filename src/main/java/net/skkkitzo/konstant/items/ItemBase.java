package net.skkkitzo.konstant.items;

import net.minecraft.item.Item;
import net.skkkitzo.konstant.Main;

/**
 * Base class for all non-tool items
 * @author Skkkitzo
 *
 */
public class ItemBase extends Item {
	
	public ItemBase(String name) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Main.tabKonstant);
	}

}
