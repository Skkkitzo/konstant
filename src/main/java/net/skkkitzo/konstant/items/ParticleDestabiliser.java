package net.skkkitzo.konstant.items;

import net.minecraft.item.ItemPickaxe;
import net.skkkitzo.konstant.Main;

/**
 * Class for registering main tool
 * @author Skkkitzo
 *
 */
public class ParticleDestabiliser extends ItemPickaxe {

	public ParticleDestabiliser(ToolMaterial material, String name) {
		super(material);
		
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(Main.tabKonstant);
	}

}
