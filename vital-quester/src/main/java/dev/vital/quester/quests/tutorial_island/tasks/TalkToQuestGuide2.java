package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.DialogTask;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.widgets.Widgets;

public class TalkToQuestGuide2 implements ScriptTask
{
	private final WorldPoint quest_guide_point = new WorldPoint(3085, 3122, 0);

	VitalQuesterConfig config;
	DialogTask talk_to_quest_guide = new DialogTask("Quest Guide", quest_guide_point, (String) null);

	public TalkToQuestGuide2(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		var widget = Widgets.get(263, 1);
		if (widget != null)
		{
			var widget_child = widget.getChild(0);
			if (widget_child != null)
			{
				return widget_child.getText().contains("This is your quest journal");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!talk_to_quest_guide.taskCompleted())
		{
			return talk_to_quest_guide.execute();
		}
		return -1;
	}
}
