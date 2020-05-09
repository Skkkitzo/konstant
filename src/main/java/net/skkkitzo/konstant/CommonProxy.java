package net.skkkitzo.konstant;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.skkkitzo.konstant.handlers.GUIHandler;

@Mod.EventBusSubscriber
public class CommonProxy {
	
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		// register the rendering of items
		registerRender(Main.drill);
		registerRender(Main.drill_bit);
		registerRender(Main.drill_parts);
		registerRender(Main.drill_handle);
		
		// register the rendering of item-resources
		registerRender(Main.diamond_infused_powder);
	}
	
	/**
	 * Register the rendering for an item
	 * @param item The item to render
	 */
	private static void registerRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		
		// register the items
		event.getRegistry().registerAll(Main.drill);
		event.getRegistry().registerAll(Main.drill_bit);
		event.getRegistry().registerAll(Main.drill_parts);
		event.getRegistry().registerAll(Main.drill_handle);
		
		// register the item-resources
		event.getRegistry().registerAll(Main.diamond_infused_powder);
	
		// register the GUI handler
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GUIHandler());
	}
	
}