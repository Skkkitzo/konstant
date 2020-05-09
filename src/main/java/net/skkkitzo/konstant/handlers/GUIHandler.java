package net.skkkitzo.konstant.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.skkkitzo.konstant.gui.GUIDrill;

public class GUIHandler implements IGuiHandler {
	
	public static final int MOD_GUI = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	// all server side stuff
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    	// all client side stuff
        if (ID == MOD_GUI)
        	// if player wants drill GUI
            return new GUIDrill();

        return null;
    }

}
