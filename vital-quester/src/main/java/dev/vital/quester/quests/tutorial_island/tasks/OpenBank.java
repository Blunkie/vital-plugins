package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import dev.vital.quester.tasks.CameraTask;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.widgets.Widgets;

public class OpenBank implements ScriptTask
{
	VitalQuesterConfig config;
	CameraTask camera_task = new CameraTask(Rand.nextInt(0, 4));

	public OpenBank(VitalQuesterConfig config)
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
				return widget_child.getText().contains("Follow the path and you will come to the front");
			}
		}
		return false;
	}

	@Override
	public int execute()
	{
		if (!camera_task.taskCompleted())
		{
			camera_task.moveRight();
			return -2;
		}

		var booth = TileObjects.getNearest("Bank booth");
		if (booth != null)
		{

			booth.interact("Use");
		}
		return -5;
	}
}
