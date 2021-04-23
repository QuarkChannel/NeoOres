package neo_ores.study;

import neo_ores.main.NeoOres;
import neo_ores.main.Reference;
import neo_ores.mana.packet.PacketManaDataToServer;
import neo_ores.spell.SpellUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StudyItemManagerClient 
{
	private NBTTagCompound nbt = new NBTTagCompound();
	private NBTTagCompound neo_ores = new NBTTagCompound();
	private NBTTagCompound data = new NBTTagCompound();
	
	public StudyItemManagerClient(EntityPlayerSP player)
	{	
		if(player.getEntityData().hasKey(Reference.MOD_ID, 10))
		{
			this.data = player.getEntityData().copy();
			this.neo_ores = player.getEntityData().getCompoundTag(Reference.MOD_ID).copy();
			if(!neo_ores.hasKey(SpellUtils.NBTTagUtils.STUDY))
			{
				this.nbt = new NBTTagCompound();
			}
			else
			{
				this.nbt = neo_ores.getCompoundTag(SpellUtils.NBTTagUtils.STUDY).copy();
			}
		}
	}
	
	public boolean didGet(String modid,String id)
	{
		if(nbt.hasKey(modid, 9))
		{
			for(int i = 0;i < nbt.getTagList(modid, 8).tagCount();i++)
			{
				String s = nbt.getTagList(modid, 8).getStringTagAt(i);
				if(id.equals(s))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean canGet(String pid,String parent,String kid,String key)
	{
		if(parent.equals(key) && pid.equals(kid)) 
		{
			return !didGet(pid,key);
		}
		else if(didGet(pid,parent))
		{
			return !didGet(kid,key);
		}
		else
		{
			return false;
		}
	}
	
	public boolean canGetRoot(String modid,String id)
	{
		return this.canGet(modid,id,modid,id);
	}
	
	public void set(String modid,String id)
	{
		if(!nbt.hasKey(modid, 9)) nbt.setTag(modid, new NBTTagList());
		nbt.getTagList(modid, 8).appendTag(new NBTTagString(id));
		neo_ores.setTag(SpellUtils.NBTTagUtils.STUDY, (NBTBase)nbt.copy());
		data.setTag(Reference.MOD_ID, (NBTBase)neo_ores.copy());
		
		PacketManaDataToServer pmds = new PacketManaDataToServer(data.copy());
		NeoOres.PACKET.sendToServer((IMessage)pmds);
	}
}
