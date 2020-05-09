package net.skkkitzo.konstant.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Base class for all non-tool items
 * @author Skkkitzo
 *
 */
public class ItemBase extends Item {
	
	public ItemBase(String name) {
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.MISC);
	}

}
