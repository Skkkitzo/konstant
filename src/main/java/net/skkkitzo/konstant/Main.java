package net.skkkitzo.konstant;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.skkkitzo.konstant.items.DrillTool;
import net.skkkitzo.konstant.items.ItemBase;

import org.apache.logging.log4j.Logger;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
	@Instance
	public static Main instance = new Main();
	
    public static final String MODID = "konstant";
    public static final String NAME = "Konstant";
    public static final String VERSION = "1.1.0";

    private static Logger logger;
    
    public static ToolMaterial konstant_material;
    
    public static Item drill;
    public static Item drill_bit;
    public static Item drill_parts;
    public static Item drill_handle;
    
    public static Item diamond_infused_powder;
    
    /**
     * The global mode of the drill
     */
    public static String drill_mode = "normal";
    

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        
        // add base material for main tool
        konstant_material = EnumHelper.addToolMaterial("konstant", 10, 2031, 5.0F, 3.0F, 5);
        
        // initialise the items
        drill = new DrillTool(konstant_material, "drill");
        drill_bit = new ItemBase("drill_bit");
        drill_parts = new ItemBase("drill_parts");
        drill_handle = new ItemBase("drill_handle");
        
        // initialise the item-resources
        diamond_infused_powder = new ItemBase("diamond_infused_powder");
    }
}
