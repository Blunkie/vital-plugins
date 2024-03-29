package dev.vital.quester.quests.tutorial_island.tasks;

import dev.vital.quester.ScriptTask;
import dev.vital.quester.VitalQuesterConfig;
import net.unethicalite.api.commons.Time;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.input.Mouse;
import net.unethicalite.api.widgets.Widgets;

public class ChooseName implements ScriptTask
{
	VitalQuesterConfig config;
	String vitalflea = "Vitalflea";

	public ChooseName(VitalQuesterConfig config)
	{
		this.config = config;
	}

	@Override
	public boolean validate()
	{
		var widget = Widgets.get(558, 13);
		if (widget != null)
		{

			return widget.getText().contains("Please look up a name to see") || widget.getText().contains("Please enter a name to look up");
		}
		return false;
	}

	@Override
	public int execute()
	{
		var name_text = Widgets.get(558, 12);
		var look_up_name = Widgets.get(558, 18);
		if (name_text != null)
		{

			if (!name_text.getText().equals("Vitalflea*"))
			{

				Mouse.click(Widgets.get(558, 7).getClickPoint().getAwtPoint(), true);

				Time.sleepTicks(2);

				for (var character : vitalflea.toCharArray())
				{
					Keyboard.type(character);
					Time.sleep(220, 420);
				}
			}
			else
			{

				if (look_up_name != null)
				{
					look_up_name.interact("Look up name");
				}
			}
		}
		return -5;
	}
}
