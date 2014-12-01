package mzvoicecontrol.items;


import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.LineUnavailableException;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mzvoicecontrol.Microphone;
import mzvoicecontrol.Reference;
import mzvoicecontrol.Tutorial;
import mzvoicecontrol.network.MyMessage;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
public class ItemPhone extends Item {
	
	private String name = "phoneItem";
	private Microphone recording = new Microphone(AudioFileFormat.Type.WAVE);
	
	public ItemPhone() {
		setUnlocalizedName(Reference.MODID + "_" + name);
		GameRegistry.registerItem(this, name);
		setCreativeTab(CreativeTabs.tabMaterials);
		setTextureName(Reference.MODID + ":" + name);
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		icons = new IIcon[2];
		icons[0] = iconRegister.registerIcon(Reference.MODID + ":" + name + 0);
		icons[1] = iconRegister.registerIcon(Reference.MODID + ":" + name + 1);
	}
	
	@Override
	public IIcon getIconFromDamage(int damage) {
		return icons[damage];
	}
	
	public ItemStack onItemRightClick(ItemStack itemPhone, World world, EntityPlayer player) {
		// If on a server
		if (!world.isRemote) {
			if (itemPhone.getItemDamage() == 1){
				recording.close();
				Tutorial.network.sendToServer(new MyMessage("Randomized filename should go here"));//TODO: Add random filename and player XYZ?
				
				player.addChatMessage(new ChatComponentTranslation("msg.phoneRecordOff.txt"));
				itemPhone.setItemDamage(0);
			} else {
				player.addChatMessage(new ChatComponentTranslation("msg.phoneRecordOn.txt"));
				try {
					recording.captureAudioToFile("/home/mike/Documents/repos/minecraft/audio.wav");//TODO: Random Filename
					recording.open();
					System.out.print("You are recording");
				} catch (LineUnavailableException e) {
					System.out.print("You are NOT recording ERROR");
					e.printStackTrace();
				}
				itemPhone.setItemDamage(1);
			}
		}
		return itemPhone;
	}
}