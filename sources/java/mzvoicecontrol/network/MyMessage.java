package mzvoicecontrol.network;

import java.util.Iterator;
import java.util.Random;

import mzvoicecontrol.BlockLocationData;
import mzvoicecontrol.DictationHTTPClient;
import mzvoicecontrol.Tutorial;
import mzvoicecontrol.Vognition;
import mzvoicecontrol.blocks.VoiceLightBlock;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MyMessage implements IMessage {
    
    private String text;

    public MyMessage() { }
    
	public static void translateNbtIntoMap(NBTTagCompound compound) {
//		if (compound.hasKey("chunkX") && compound.hasKey("chunkZ") && compound.hasKey("bossType")) {
			System.out.println(compound.getInteger("xCoord"));
			System.out.println(compound.getInteger("yCoord"));
			System.out.println(compound.getInteger("zCoord"));
//			structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)), bossType);
//			//LogHelper.finer("Loaded roomList data for chunk " + i + "/" + j);
//		} else {
//			LogHelper.warning("Failed to translate Boss Room NBT compound into structure map");
//		}
	}
	
    public MyMessage(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
    }
    
    public static String processVoiceInput(){
    	//TODO: Clean up code
		String nuanceResponse = null;
		String vognitionResponse = null;
    	
    	String[] dictationArgs = {"-a","NMDPTRIAL_codemonkeymike20141025164923",
                "-k","7509ca0cd428e9d8497b48adf0bab30457515ebaefe9c58ed2ce11a16509cfda1399e6e7c2cfbf0b4b07b93bc26c3f19bd9ed3ac87983a5a664bfbcb55cba668",
                "-w","/home/mike/Documents/repos/minecraft/audio.wav",
                "-f","audio/x-wav;codec=pcm;bit=16;rate=8000"};
		try {
			nuanceResponse = DictationHTTPClient.main(dictationArgs);
		} catch (Exception e) {
			//return null;
			e.printStackTrace();
		}
		System.out.println(nuanceResponse);

		
		try {
			vognitionResponse = Tutorial.vog.transText(nuanceResponse, "useenglishfemale", "en-US");
		} catch (Exception e) {
			//return null;
			e.printStackTrace();
		}
		return vognitionResponse;
    }
    
    public static class Handler implements IMessageHandler<MyMessage, IMessage> {
        
        @Override
        public IMessage onMessage(MyMessage message, MessageContext ctx) {

        	String voiceResponse = processVoiceInput();
        	if(voiceResponse != null){
        		int xCoord = 0, yCoord = 0, zCoord = 0;
        		
        		System.out.println(voiceResponse);
        		System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));//TODO: Remove after Debugging
                
                World world = ctx.getServerHandler().playerEntity.worldObj;                
                String voiceIntent = voiceResponse.substring(1,5);
                
                // Lighting
                if(voiceIntent.equals("0001")){
                	BlockLocationData blockLocationData = (BlockLocationData) world.perWorldStorage.loadData(BlockLocationData.class, "LightBlock");

                	NBTTagCompound compound = blockLocationData.getBlockLocationData();
					// func_150296_c is getTags()
					Iterator iterator = compound.func_150296_c().iterator();

					while (iterator.hasNext()) {
						String s = (String) iterator.next();
						NBTBase nbtbase = compound.getTag(s);
						if (nbtbase.getId() == Constants.NBT.TAG_COMPOUND) {
							//translateNbtIntoMap((NBTTagCompound) nbtbase);
							NBTTagCompound innerTags = (NBTTagCompound) nbtbase;
							xCoord = innerTags.getInteger("xCoord");
							yCoord = innerTags.getInteger("yCoord");
							zCoord = innerTags.getInteger("zCoord");
						}
					}
                	
					Block blockAtLocation = world.getBlock(xCoord,yCoord,zCoord);
                	
                	if(blockAtLocation == null){
                    	System.out.println("Block at X,Y,Z doesn't Exist");
                    } else {
                    	Random someRand = new Random();
                    	((VoiceLightBlock) blockAtLocation).updateTick(world, xCoord, yCoord, zCoord, someRand);
                    }
                }
                //Change Volume
                else if(voiceIntent.equals("0801")){} 
                //Change Music
                else if(voiceIntent.equals("0802")){}
        	}
            
            
            return null; // no response in this case
        }
    }
}

//12:04 < Blank> CodeMonkeyMike, getDescriptionPacket or something
//12:04 < Ri5ux> ^
//12:04 < CodeMonkeyMike> is that a function of tileentity/
//12:04 < Ri5ux> Yes
//12:05 < CodeMonkeyMike> so i would do 
//                        tileentitynote.triggernote(world,x,y,z).getDescriptionPacket()?
//12:05 < Ri5ux> Not quite....
//12:05 < Ri5ux> You need to override that in your tile entity
//12:06 < Ri5ux> there's another method that goes along with it
//12:06 < Ri5ux> onDataPacket
//12:06 < Ri5ux> In onDataPacket call this.readFromNBT(packet.func_148857_g());
//12:07 < Ri5ux> In getDescriptionPacket, create a new NBTTagCompound and then call 
//               this.writeToNBT(nbtTag); After that return new 
//               S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
//12:08 < Ri5ux> Then it'll sync the fields you read/write in the tile entity.
//12:08 < Aquazus> Ri5ux, I found
//12:08 < Ri5ux> You also gotta mark the block for an update or something.
//12:08 < CodeMonkeyMike> ok dope, will try in a few minutes
