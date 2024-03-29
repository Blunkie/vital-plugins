package dev.vital.quester.quests.restless_ghost.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.BasicTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.quests.QuestVarPlayer;

public class ReturnSkull implements ScriptTask
{
	VitalQuesterConfig config;
	BasicTask open_coffin = new BasicTask(() ->
	{

		var coffin = TileObjects.getFirstAt(new WorldPoint(3249, 3192, 0), "Coffin");
		if (coffin != null && Reachable.isInteractable(coffin))
		{
			if (coffin.hasAction("Search"))
			{
				coffin.interact("Search");
			}
			else if (coffin.hasAction("Open"))
			{
				coffin.interact("Open");
			}
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(new WorldPoint(3249, 3192, 0));
		}

		return -5;
	});

	public ReturnSkull(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getVarp(QuestVarPlayer.QUEST_THE_RESTLESS_GHOST.getId()) == 4;
	}

	@Override
	public int execute()
	{
		if (!open_coffin.taskCompleted())
		{
			return open_coffin.execute();
		}

		return -1;
	}
}
