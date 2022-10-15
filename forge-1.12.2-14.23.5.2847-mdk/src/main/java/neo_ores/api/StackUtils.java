package neo_ores.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class StackUtils 
{
	public static List<ItemStack> asList(ItemStack stack,int clampsize)
    {
    	int csize = stack.getCount();
        List<ItemStack> list = new ArrayList<ItemStack>();
        for(int i = 0;i < csize / clampsize;i++)
        {
            ItemStack copied = stack.copy();
            copied.setCount(clampsize);
            list.add(copied);
        }

        if(csize % clampsize > 0)
        {
            ItemStack copied = stack.copy();
            copied.setCount(csize % clampsize);
            list.add(copied);
        }
        return list;
    }
}
