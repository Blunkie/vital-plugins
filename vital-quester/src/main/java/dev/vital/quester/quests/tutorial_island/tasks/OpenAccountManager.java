package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.widgets.Widgets;

public class OpenAccountManager implements ScriptTask
{
	VitalQuesterConfig config;

	public OpenAccountManager(VitalQuesterConfig config)
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
				return widget_child.getText().contains("Click on the flashing icon to open your Account Management");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		var widget = Widgets.get(164, 38);
		if (widget != null)
		{
			widget.interact("Account Management");
		}

		return -2;
	}
}
