package dev.vital.quester.quests.pirates_treasure.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Inventory;

public class FillCrate implements ScriptTask
{
	private final WorldPoint luthas_location = new WorldPoint(2938, 3152, 0);

	VitalQuesterConfig config;
	BasicTask fill_crate = new BasicTask(() ->
	{
		if (!Inventory.contains(ItemID.KARAMJAN_RUM))
		{
			return 0;
		}

		if (Inventory.getCount(false, ItemID.BANANA) >= 10)
		{
			return Tools.interactWith("Crate", "Fill", luthas_location, Tools.EntityType.TILE_OBJECT);
		}
		else
		{
			Inventory.getFirst(ItemID.KARAMJAN_RUM).useOn(TileObjects.getNearest(x -> x.hasAction("Fill")));
		}

		return -1;
	});

	public FillCrate(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return !fill_crate.taskCompleted();
	}

	@Override
	public int execute()
	{


		return fill_crate.execute();

	}
}
