package dev.vital.smithing;

import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.PluginDescriptor;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.commons.Rand;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.game.Game;
import net.unethicalite.api.input.Keyboard;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.plugins.LoopedPlugin;
import net.unethicalite.api.widgets.Widgets;
import org.pf4j.Extension;

@PluginDescriptor(name = "vital-smithing", enabledByDefault = false)
@Extension
@Slf4j
public class VitalSmithing extends LoopedPlugin
{
	public int ticker = 0;
	public int oldticker = 0;
	@Inject
	private VitalSmithingConfig config;
	private boolean smithing = false;

	@Override
	protected int loop()
	{
		if (!Game.isLoggedIn())
		{

			return -1;
		}

		var local_player = LocalPlayer.get();
		if (Movement.isWalking() || local_player.isAnimating() || smithing)
		{

			return -1;
		}

		if (Inventory.getCount(false, config.barID()) == 27)
		{

			if (Bank.isOpen())
			{

				Bank.close();

				return Rand.nextInt(600, 1200);
			}
			else
			{

				var widgets = Widgets.get(WidgetID.SMITHING_GROUP_ID);

				if (widgets.isEmpty())
				{

					TileObjects.getNearest("Anvil").interact("Smith");
				}
				else if (Widgets.isVisible(widgets.get(0)))
				{

					Keyboard.sendSpace();
				}

				return Rand.nextInt(2600, 3200);
			}
		}
		else
		{
			if (!Bank.isOpen())
			{
				TileObjects.getNearest("Bank booth").interact("Bank");
				return Rand.nextInt(2600, 3200);
			}
			else
			{

				if (Inventory.getFreeSlots() == 27)
				{
					if (Bank.getCount(false, config.barID()) >= 27)
					{
						Bank.withdraw(config.barID(), 27, Bank.WithdrawMode.ITEM);
					}
				}
				else
				{
					Bank.depositAllExcept(ItemID.HAMMER);
				}
				return Rand.nextInt(600, 1200);
			}
		}
	}

	@Provides
	VitalSmithingConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VitalSmithingConfig.class);
	}

	@Subscribe
	private void onGameTick(GameTick event)
	{
		ticker++;
		var local_player = LocalPlayer.get();
		if (local_player.isAnimating())
		{

			oldticker = ticker;
			smithing = true;
		}
		else if (smithing && ticker - oldticker > 3)
		{
			smithing = false;
		}
	}
}
