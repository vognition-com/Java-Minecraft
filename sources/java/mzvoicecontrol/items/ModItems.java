package mzvoicecontrol.items;

import net.minecraft.item.Item;

public final class ModItems {
	
	public static Item phone;
	
	public static void init() {
		
		phone = new ItemPhone();
	}
}
