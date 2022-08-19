package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class EnterCave implements ScriptTask
{
	VitalQuesterConfig config;

	public EnterCave(VitalQuesterConfig config)
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
				return widget_child.getText().contains("It's time to enter some caves");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		TileObjects.getNearest("Ladder").interact("Climb-down");

		return -5;
	}
}
