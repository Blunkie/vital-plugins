package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.widgets.Widgets;

public class OpenFriends implements ScriptTask
{
	VitalQuesterConfig config;

	public OpenFriends(VitalQuesterConfig config)
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
				return widget_child.getText().contains("You should now see another new icon");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		var widget = Widgets.get(164, 39);
		if (widget != null)
		{
			widget.interact("Friends List");
		}

		return -2;
	}
}
