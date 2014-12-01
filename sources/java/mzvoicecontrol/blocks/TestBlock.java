package mzvoicecontrol.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import mzvoicecontrol.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class TestBlock extends Block {

	private String name = "testBlock";
	
	protected TestBlock() {
		super(Material.rock);
		setBlockName(Reference.MODID + "_" + name);
		GameRegistry.registerBlock(this, name);
		setCreativeTab(CreativeTabs.tabMaterials);
		setBlockTextureName(Reference.MODID + ":" + name);
	}
	
	public boolean onBlockActivated(){
		System.out.println("You right clicked the block");
		return false;
	}
	
	public int onBlockPlaced(World world, int X, int Y, int Z, int side, float hitX, float hitY, float hitZ, int metadata) {
		System.out.println("X,Y,Z" +X+" "+Y+" "+Z);
		System.out.println("Metadata "+metadata);
        return metadata;
    }
}
