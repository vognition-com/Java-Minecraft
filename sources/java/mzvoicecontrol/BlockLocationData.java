package mzvoicecontrol;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class BlockLocationData extends WorldSavedData {
	private NBTTagCompound blockLocationData = new NBTTagCompound();

	
	protected void translateNbtIntoMap(NBTTagCompound compound) {
//		if (compound.hasKey("chunkX") && compound.hasKey("chunkZ") && compound.hasKey("bossType")) {
			System.out.println(compound.getInteger("xCoord"));
			System.out.println(compound.getInteger("xCoord"));
			System.out.println(compound.getInteger("xCoord"));
//			structureMap.put(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(i, j)), bossType);
//			//LogHelper.finer("Loaded roomList data for chunk " + i + "/" + j);
//		} else {
//			LogHelper.warning("Failed to translate Boss Room NBT compound into structure map");
//		}
	}
	
	public BlockLocationData(String tagName) {
		super(tagName);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		blockLocationData = compound.getCompoundTag("MZVoiceControl");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		compound.setTag("MZVoiceControl", blockLocationData);
	}
	
	public void addLightBlock(int xCoord, int yCoord, int zCoord) {
		System.out.println("Got inside MyWorldData.addLightBlock");
		System.out.println("______From BlockLocationData X: "+xCoord+"Y: "+yCoord);
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("xCoord", xCoord);
		compound.setInteger("yCoord", yCoord);
		compound.setInteger("zCoord", zCoord);
		this.blockLocationData.setTag("LightBlock", compound);

	}
	
	public NBTTagCompound getBlockLocationData() {
		return blockLocationData;
	}
}