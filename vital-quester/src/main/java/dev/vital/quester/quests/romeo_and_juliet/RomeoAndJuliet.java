package dev.vital.quester.quests.romeo_and_juliet;

import dev.vital.quester.QuestList;
import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.quests.romeo_and_juliet.tasks.GetBerries;
import dev.vital.quester.quests.romeo_and_juliet.tasks.StartQuest;
import dev.vital.quester.quests.romeo_and_juliet.tasks.TalkToApothecary;
import dev.vital.quester.quests.romeo_and_juliet.tasks.TalkToFatherLawrence;
import dev.vital.quester.quests.romeo_and_juliet.tasks.TalkToJuliet;
import dev.vital.quester.quests.romeo_and_juliet.tasks.TalkToJuliet2;
import dev.vital.quester.quests.romeo_and_juliet.tasks.TalkToRomeo;
import dev.vital.quester.quests.romeo_and_juliet.tasks.TalkToRomeo2;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.unethicalite.api.quests.Quests;

import java.util.ArrayList;
import java.util.List;

public class RomeoAndJuliet implements ScriptTask
{
	static List<ScriptTask> tasks = new ArrayList<>();
	VitalQuesterConfig config;

	public RomeoAndJuliet(VitalQuesterConfig config)
	{
		this.config = config;

		tasks.clear();

		tasks.add(new StartQuest(config));
		tasks.add(new TalkToJuliet(config));
		tasks.add(new TalkToRomeo(config));
		tasks.add(new TalkToFatherLawrence(config));
		tasks.add(new GetBerries(config));
		tasks.add(new TalkToApothecary(config));
		tasks.add(new TalkToJuliet2(config));
		tasks.add(new TalkToRomeo2(config));
	}

	@Override
	public boolean validate()
	{
		return (config.currentQuest().equals(QuestList.ROMEO_AND_JULIET) || config.automaticOptimal()) && Quests.getState(Quest.ROMEO__JULIET) != QuestState.FINISHED;
	}

	@Override
	public int execute()
	{

		for (ScriptTask task : tasks)
		{
			if (task.validate())
			{
				int sleep = task.execute();
				if (task.blocking())
				{
					return sleep;
				}
			}
		}

		return -1;
	}
}
