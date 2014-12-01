package mzvoicecontrol.blocks;

import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mzvoicecontrol.BlockLocationData;
import mzvoicecontrol.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class VoiceLightBlock extends Block {
	   private final boolean isOn;
	    private static final String __OBFID = "CL_00000297";
	    
	    private String name = "voiceLightBlock";
	    
	    public VoiceLightBlock(boolean signal)
	    {
	        super(Material.redstoneLight);
	        this.isOn = signal;

	        if (signal)
	        {
	            this.setLightLevel(1.0F);
	        }
	        if(signal){
	        	setBlockName(Reference.MODID + "_" + name +"On");
	        	GameRegistry.registerBlock(this, name+"On");
	        	setBlockTextureName(Reference.MODID + ":" + name+"On");
	        } else {
	        	setBlockName(Reference.MODID + "_" + name +"Off");
	        	GameRegistry.registerBlock(this, name+"Off");
	        	setBlockTextureName(Reference.MODID + ":" + name+"Off");
	        }
			setCreativeTab(CreativeTabs.tabMaterials);
			
	    }

	    /**
	     * Called whenever the block is added into the world. Args: world, x, y, z
	     */
	    public void onBlockAdded(World world, int xCoord, int yCoord, int zCoord) {
	    	System.out.println("You got in VoiceLightBlock.onBlockAdded");
	        if (!world.isRemote) {
	        	BlockLocationData blockLocationData = (BlockLocationData) world.perWorldStorage.loadData(BlockLocationData.class, "LightBlock");
	        	
	        	if (blockLocationData == null) {
					blockLocationData = new BlockLocationData("LightBlock");
					world.perWorldStorage.setData("LightBlock", blockLocationData);
					System.out.println("______From VoiceLightBlock X: "+xCoord+"Y: "+yCoord);
					blockLocationData.addLightBlock(xCoord, yCoord, zCoord);
					blockLocationData.markDirty();
					
//					BlockLocationData testPleaseDelete = (BlockLocationData) world.perWorldStorage.loadData(BlockLocationData.class, "MZVoiceControl");
//					
//					System.out.println(testPleaseDelete.getBlockLocationData().hasNoTags());
//					System.out.println(testPleaseDelete.getBlockLocationData().toString());

				} /* else {
					NBTTagCompound compound = blockLocationData.getBlockLocationData();
					// func_150296_c is getTags()
					Iterator iterator = compound.func_150296_c().iterator();

					while (iterator.hasNext()) {
						String s = (String) iterator.next();
						NBTBase nbtbase = compound.getTag(s);
						if (nbtbase.getId() == Constants.NBT.TAG_COMPOUND) {
							translateNbtIntoMap((NBTTagCompound) nbtbase);
						}
					}
				}*/
	        }
	    }

	    /**
	     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	     * their own) Args: x, y, z, neighbor Block
	     */
//	    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
//	    {
//	        if (!p_149695_1_.isRemote)
//	        {
//	            if (this.isOn && !p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))
//	            {
//	                p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, 4);
//	            }
//	            else if (!this.isOn && p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))
//	            {
//	                p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, ModBlocks.voiceLightBlockOn, 0, 2);
//	            }
//	        }
//	    }

	    /**
	     * Ticks the block if it's been scheduled
	     */
	    public void updateTick(World world, int xCoord, int yCoord, int zCoord, Random p_149674_5_)
	    {
	    	System.out.println("Hey you updated the Voice control block");
	        if (!world.isRemote && this.isOn)// && !world.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)
	        {
	            world.setBlock(xCoord, yCoord, zCoord, ModBlocks.voiceLightBlockOff, 0, 2);
	        }
	        else if(!world.isRemote && !this.isOn)
	        {
	        	world.setBlock(xCoord, yCoord, zCoord, ModBlocks.voiceLightBlockOn, 0, 2);
	        }
	    }

	    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	    {
	        return Item.getItemFromBlock(ModBlocks.voiceLightBlockOff);
	    }

	    /**
	     * Gets an item for the block being called on. Args: world, x, y, z
	     */
	    @SideOnly(Side.CLIENT)
	    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	    {
	        return Item.getItemFromBlock(ModBlocks.voiceLightBlockOff);
	    }

	    /**
	     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
	     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
	     */
	    protected ItemStack createStackedBlock(int p_149644_1_)
	    {
	        return new ItemStack(ModBlocks.voiceLightBlockOff);
	    }

}
