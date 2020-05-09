package net.skkkitzo.konstant.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

/**
 * Class for registering main tool
 * @author Skkkitzo
 *
 */
public class DrillTool extends ItemPickaxe {

	public DrillTool(ToolMaterial material, String name) {
		super(material);
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.TOOLS);
	}

}
