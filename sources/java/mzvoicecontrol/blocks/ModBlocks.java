package mzvoicecontrol.blocks;

import net.minecraft.block.Block;

public final class ModBlocks {
	
	public static Block testBlock;
	public static Block voiceLightBlockOff;
	public static Block voiceLightBlockOn;
	public static void init() {
		
		testBlock = new TestBlock();
		voiceLightBlockOff = new VoiceLightBlock(false);
		voiceLightBlockOn = new VoiceLightBlock(true);
	}

}
