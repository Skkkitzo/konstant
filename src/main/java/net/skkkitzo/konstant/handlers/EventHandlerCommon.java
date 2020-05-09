package net.skkkitzo.konstant.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.skkkitzo.konstant.Main;

@Mod.EventBusSubscriber
public class EventHandlerCommon {
	
	@SubscribeEvent
	public static void onMining(BreakSpeed event) {	
		EntityPlayer player = (EntityPlayer) event.getEntity();
		
		// if player is holding the drill
		if (player.getHeldItemMainhand().getItem() == Main.drill) {
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			World world = server.getWorld(0);
			BlockPos pos = event.getPos();
			
			// get the hardness of the tile
			float hardness = event.getState().getBlockHardness(world, pos);
			
			// if the hardness of the material is very low, just instant-break it
			if (hardness < 0.5f) {
				event.setNewSpeed(200);
			} else {
				float speed = 4 * (hardness / 0.4f);
				
				// set the mining speed of the item
				event.setNewSpeed(speed);
			}			
		}
	}
	
	@SubscribeEvent
	public static void onBreak(BreakEvent event) {
		EntityPlayer player = (EntityPlayer) event.getPlayer();
		
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		World world = server.getWorld(0);
		BlockPos pos = event.getPos();
		
		String mode = Main.drill_mode;
		
		if (player.getHeldItemMainhand().getItem() == Main.drill) {
			if (mode.startsWith("wide")){
				
				// calculate the start and end positions for ray tracing
				// p.s. this section's code is fucked, don't touch it
				double a = player.posX;
				double b = player.posY + (double)player.getEyeHeight();
				double c = player.posZ;
				Vec3d vec3start = new Vec3d(a, b, c);
				
				float f = player.rotationPitch;
				float f1 = player.rotationYaw;
				float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
				float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
				float f4 = -MathHelper.cos(-f * 0.017453292F);
				float f5 = MathHelper.sin(-f * 0.017453292F);
				float f6 = f3 * f4;
				float f7 = f2 * f4;
				double d3 = 5.0D;
				Vec3d vec3end = vec3start.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
				
				// perform ray trace from face to block
				EnumFacing face = world.rayTraceBlocks(vec3start, vec3end).sideHit;
				// you can get the face hit by using face.sideHit
				// if you want to get string value of face, use face.toString()
				
				// get radius from drill mode
				int radius = (Integer.parseInt(mode.substring(mode.length() - 1)) - 1) / 2;
				
				// mine out the specified area
				mineArea(getCoordinates(pos, face, radius), player, world);
				
			} else if (mode.equalsIgnoreCase("vein")) {
				
				if (event.getState().getBlock() instanceof BlockOre || event.getState().getBlock() == Blocks.LIT_REDSTONE_ORE) {
					
					mineArea(detectVein(pos), player, world);
					
				}
			}
		}
		
		return;
	}
	
	@SubscribeEvent
	public static void onRightClick(PlayerInteractEvent event) {
		// if the event is fired by a player
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			
			// if player is sneaking and holding the drill...
			if (player.isSneaking() && player.getHeldItemMainhand().getItem() == Main.drill) {
				
				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
				World world = server.getWorld(0);
				
				// open the gui
				player.openGui(Main.instance, GUIHandler.MOD_GUI, world, 0, 0, 0);
			}
		}
	}
	
	/**
     * Generate list of co-ordinates in a square shape around a centre point
     * @param pos The position of the centre block
     * @param face The face of the block that fired the event
     * @param radius The radius of the square, 0 being a singular block
     * @return list of block positions
     */
	private static List<BlockPos> getCoordinates(BlockPos pos, EnumFacing face, int radius) {
		int xRad = 0, yRad = 0, zRad = 0;

		if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
			xRad = radius;
			zRad = radius;
		} else if (face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
			xRad = radius;
			yRad = radius;
		} else if (face == EnumFacing.EAST || face == EnumFacing.WEST) {
			yRad = radius;
			zRad = radius;
		}
		
		return generateCuboid(pos, xRad, yRad, zRad);
	}
	
	/**
	 * Generate a list of block positions around a centre point in cube fashion
	 * @param center The center of the cube
	 * @param radius The radius around the center, a radius of 1 meaning a 3x3x3 cube
	 * @return A list of block positions
	 */
	private static List<BlockPos> generateCuboid(BlockPos center, int radius){
		return generateCuboid(center, radius, radius, radius);
	}
	
	/**
	 * Generate a list of block positions in a rectangular cuboid around a center point
	 * @param center The centre of the rectangular cuboid
	 * @param xRad The radius of the cuboid in the x-dimension
	 * @param yRad The radius of the cuboid in the y-dimension
	 * @param zRad The radius of the cuboid in the z-dimension
	 * @return
	 */
	private static List<BlockPos> generateCuboid(BlockPos center, int xRad, int yRad, int zRad){
		List<BlockPos> lx = new ArrayList<>();
		
		for (int x = center.getX() - xRad; x <= center.getX() + xRad; x++) {
			for (int y = center.getY() - yRad; y <= center.getY() + yRad; y++) {
				for (int z = center.getZ() - zRad; z <= center.getZ() + zRad; z++) {
					lx.add(new BlockPos(x, y, z));
				}
			}
		}
		
		return lx;
	}
	
	/**
	 * Special method for finding a vein
	 * @param pos Position of the origin block
	 * @return A list of block positions in the vein
	 */
	private static List<BlockPos> detectVein(BlockPos pos){
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		World world = server.getWorld(0);
		
		return detectVein(new ArrayList<>(), new ArrayList<>(), pos, world.getBlockState(pos), 0);
	}
	
	/**
	 * Special recursive method for finding a vein
	 * @param veinList List of all blocks in vein currently
	 * @param checkedList List of all blocks checked
	 * @param pos Position to check
	 * @param veinState The IBlockState of the vein type 
	 * @param depth How deep into the algorithm you can go
	 * @return A list of block positions in the vein
	 */
	private static List<BlockPos> detectVein(List<BlockPos> veinList, List<BlockPos> checkedList, BlockPos pos, IBlockState veinState, int depth){
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		World world = server.getWorld(0);
		IBlockState state = world.getBlockState(pos);
		
		if (state == veinState && depth < 10 && !checkedList.contains(pos)) {
			// add this block's position to vein list
			veinList.add(pos);
			checkedList.add(pos);
			
			for (BlockPos position : generateCuboid(pos, 1)) {
				veinList = detectVein(veinList, checkedList, position, veinState, depth + 1);
			}
			
			return veinList;
		}
		
		return veinList;
	}
	
	/**
	 * Mine out a specified area
	 * @param vein List List of block positions to be mined
	 * @param player Player that mines the blocks
	 * @param world World in which blocks are located
	 */
	private static void mineArea(List<BlockPos> veinList, EntityPlayer player, World world) {
		for (BlockPos position : veinList) {
			IBlockState state = world.getBlockState(position);
			Block block = state.getBlock();
			
			if (block.removedByPlayer(state, world, position, player, true)) {
				block.onBlockDestroyedByPlayer(world, position, state);
				block.harvestBlock(world, player, position, state, null, new ItemStack(Items.AIR));
			}
		}
	}
}
