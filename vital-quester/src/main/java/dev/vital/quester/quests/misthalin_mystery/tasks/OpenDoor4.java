package dev.vital.quester.quests.misthalin_mystery.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import dev.vital.quester.tools.Tools;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.quests.QuestVarbits;

public class OpenDoor4 implements ScriptTask
{
	WorldPoint door_point = new WorldPoint(1631, 4842, 0);

	VitalQuesterConfig config;
	BasicTask open_door = new BasicTask(() ->
	{
		if (Tools.interactWith(30118, "Open", door_point, Tools.EntityType.TILE_OBJECT) == -5)
		{
			return 0;
		}

		return -5;
	});

	public OpenDoor4(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getBit(QuestVarbits.QUEST_MISTHALIN_MYSTERY.getId()) == 85;
	}

	@Override
	public int execute()
	{

		if (!open_door.taskCompleted())
		{
			return open_door.execute();
		}

		return -1;
	}
}
