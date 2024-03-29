package dev.vital.quester.quests.romeo_and_juliet.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.game.Vars;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.quests.QuestVarPlayer;

public class TalkToJuliet implements ScriptTask
{
	VitalQuesterConfig config;
	DialogTask talk_to_juliet = null;

	public TalkToJuliet(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		return Vars.getVarp(QuestVarPlayer.QUEST_ROMEO_AND_JULIET.getId()) == 10;
	}

	@Override
	public int execute()
	{

		var juliet = NPCs.getNearest("Juliet");
		if (juliet == null)
		{
			if (!Movement.isWalking())
			{
				Movement.walkTo(new WorldPoint(3155, 3433, 1));
			}

			return -1;
		}

		if (talk_to_juliet == null)
		{
			talk_to_juliet = new DialogTask("Juliet", juliet.getWorldLocation(),
					(String) null);
			return -1;
		}

		if (!talk_to_juliet.taskCompleted())
		{
			return talk_to_juliet.execute();
		}

		return -1;
	}
}
