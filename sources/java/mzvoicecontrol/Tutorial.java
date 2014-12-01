package mzvoicecontrol;

import mzvoicecontrol.blocks.ModBlocks;
import mzvoicecontrol.items.ModItems;
import mzvoicecontrol.network.MyMessage;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod
(
	modid						= Reference.MODID,
	name						= Reference.NAME,
	version						= Reference.VERSION,
	dependencies 				= Reference.DEPENDENCIES,
	acceptedMinecraftVersions	= Reference.MINECRAFT
)
	
public class Tutorial {
	@Mod.Instance (value = Reference.MODID)
	public static Tutorial instance;
	
	public static SimpleNetworkWrapper network;
	
	public static Vognition vog = new Vognition("59c000476cf2a00b9f5bc1585c177276d8a4d75f", "01d3cb9455722c2f6760e11f14a05711796abc1a", "09326ebc1e2cad2ae9f4c0fd5d4c7b71db2b0e48","7092379b31cd621c2c1b574e47ec956733fabc1a");
	
	@Mod.EventHandler
	public void preInitialize (FMLPreInitializationEvent event) {
		vog.deserialze(); //TODO: Dont forget to serialize first!
		network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
		network.registerMessage(MyMessage.Handler.class, MyMessage.class, 0, Side.SERVER);
		
		ModItems.init();
		ModBlocks.init();
	}
	
	@Mod.EventHandler
	public void initialize (FMLInitializationEvent event) {
		
	}
	
	@Mod.EventHandler
	public void postInitialization (FMLPostInitializationEvent event) {
		
	}
}