package dev.vital.quester.quests.cooks_assistant.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.items.Inventory;

public class TalkToCook implements ScriptTask
{
	private final WorldPoint cooks_point = new WorldPoint(3210, 3213, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_cook = new DialogTask("Cook", cooks_point, "What's wrong?", "Yes.");

	public TalkToCook(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Inventory.contains(ItemID.EGG) && Inventory.contains(ItemID.BUCKET_OF_MILK) && Inventory.contains(ItemID.POT_OF_FLOUR);
	}

	@Override
	public int execute()
	{
		if (!talk_to_cook.taskCompleted())
		{
			return talk_to_cook.execute();
		}

		return -1;
	}
}
