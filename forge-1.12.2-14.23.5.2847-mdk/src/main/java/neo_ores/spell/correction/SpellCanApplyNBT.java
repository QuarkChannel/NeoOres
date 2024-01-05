package neo_ores.spell.correction;

import neo_ores.api.spell.Spell.SpellCorrectionSingle;
import neo_ores.spell.SpellItemInterfaces.HasCanApplyNBT;

public class SpellCanApplyNBT extends SpellCorrectionSingle<HasCanApplyNBT>
{

	public SpellCanApplyNBT()
	{
		super(0);
	}

	@Override
	protected void onApply(HasCanApplyNBT spell)
	{
		spell.setCanApplyNBT();
	}
}
