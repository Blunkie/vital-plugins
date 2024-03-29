package dev.vital.quester.tools;

import net.runelite.api.Item;
import net.runelite.api.coords.WorldPoint;
import net.unethicalite.api.SceneEntity;
import net.unethicalite.api.account.LocalPlayer;
import net.unethicalite.api.entities.NPCs;
import net.unethicalite.api.entities.TileItems;
import net.unethicalite.api.entities.TileObjects;
import net.unethicalite.api.items.Bank;
import net.unethicalite.api.items.Equipment;
import net.unethicalite.api.items.Inventory;
import net.unethicalite.api.items.Shop;
import net.unethicalite.api.movement.Movement;
import net.unethicalite.api.movement.Reachable;
import net.unethicalite.api.movement.pathfinder.model.BankLocation;
import net.unethicalite.api.widgets.Dialog;
import net.unethicalite.client.Static;

import java.util.List;
import java.util.function.Predicate;

import static java.lang.Math.abs;

public class Tools
{
	static int animation_tick = 0;

	public static boolean localHas(Predicate<Item> item)
	{

		return Inventory.contains(item) || Equipment.contains(item);
	}

	/*public static String getDialogueHeader()
	{
		Widget widget = Widgets.get(WidgetID.DIALOG_OPTION_GROUP_ID, 1);
		if (!Widgets.isVisible(widget))
		{
			return "";
		}

		Widget[] children = widget.getChildren();
		if (children == null)
		{
			return "";
		}

		return children[0].getText();
	}*/

	/*public static int pickUpItem(int id, WorldPoint point) {
		var item = TileItems.getNearest(id);
		if(item != null && Reachable.isInteractable(item)) {
			item.interact("Take");
			return -5;
		}
		else if(!Movement.isWalking()){
			Movement.walkTo(point);
		}

		return -1;
	}*/

	public static boolean openNearestBank()
	{
		if (Bank.isOpen())
		{
			return true;
		}

		var nearest_bank = BankLocation.getNearest();
		if (nearest_bank.getArea().contains(LocalPlayer.get().getWorldLocation()))
		{
			var bank_booth = TileObjects.getNearest("Bank booth");
			var banker = NPCs.getNearest("Banker");
			if (bank_booth != null)
			{
				bank_booth.interact("Bank");
			}
			else if (banker != null)
			{
				banker.interact("Bank");
			}
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(nearest_bank);
		}

		return false;
	}

	public static int talkTo(String name, WorldPoint point, List<String> dialog)
	{

		if (Dialog.canContinue())
		{
			Dialog.continueSpace();
			return -1;
		}

		if (!dialog.isEmpty() && !dialog.contains(null) && Dialog.isViewingOptions())
		{
			for (var option : dialog)
			{
				if (Dialog.chooseOption(option))
				{
					dialog.remove(option);
					return -2;
				}
			}
		}

		var entity = NPCs.getNearest(x -> x.getName().equals(name));
		if (entity != null && Reachable.isInteractable(entity) && LocalPlayer.get().getWorldLocation().distanceTo2D(entity.getWorldLocation()) < 10)
		{

			entity.interact("Talk-to");
			return -5;
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(point);
		}

		return -1;
	}

	public static int interactWith(String name, String action, WorldPoint point, EntityType type)
	{

		SceneEntity entity;
		switch (type)
		{
			case NPC:
			{
				entity = NPCs.getNearest(x -> x.hasAction(action) && x.getName().equals(name));
				break;
			}
			case TILE_ITEM:
			{
				entity = TileItems.getNearest(x -> x.hasAction(action) && x.getName().equals(name));
				break;
			}
			case TILE_OBJECT:
			{
				entity = TileObjects.getNearest(x -> x.hasAction(action) && x.getName().equals(name));
				break;
			}
			default:
			{
				entity = null;
			}
		}

		if (entity != null && Reachable.isInteractable(entity) && entity.getWorldLocation().distanceTo2D(LocalPlayer.get().getWorldLocation()) < 5)
		{

			entity.interact(action);
			return -5;
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(point);
		}

		return -1;
	}

	public static int interactWith(int id, String action, WorldPoint point, EntityType type)
	{

		SceneEntity entity;
		switch (type)
		{
			case NPC:
			{
				entity = NPCs.getNearest(x -> x.hasAction(action) && x.getId() == id);
				break;
			}
			case TILE_ITEM:
			{
				entity = TileItems.getNearest(x -> x.hasAction(action) && x.getId() == id);
				break;
			}
			case TILE_OBJECT:
			{
				entity = TileObjects.getNearest(x -> x.hasAction(action) && x.getId() == id);
				break;
			}
			default:
			{
				entity = null;
			}
		}

		if (entity != null && Reachable.isInteractable(entity))
		{

			entity.interact(action);
			return -5;
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(point);
		}

		return -1;
	}

	public static boolean isAnimating(int delta)
	{
		var local_player = LocalPlayer.get();
		int tick_count = Static.getClient().getTickCount();
		if (local_player.isAnimating() || local_player.getPoseAnimation() == 824 || local_player.getPoseAnimation() == 819)
		{
			animation_tick = tick_count;
		}

		return (tick_count - animation_tick) <= delta;
	}

	public static int sellTo(String name, WorldPoint point, int id, int amount, boolean stack)
	{
		int current_amount = Inventory.getCount(stack, id);
		if (current_amount == amount)
		{
			return 0;
		}

		if (Shop.isOpen())
		{

			int amount_needed = abs(amount - current_amount);
			if (amount_needed >= 50)
			{
				Shop.sellFifty(id);
			}
			else if (amount_needed >= 10)
			{
				Shop.sellTen(id);
			}
			else if (amount_needed >= 5)
			{
				Shop.sellFifty(id);
			}
			else if (amount_needed >= 1)
			{
				Shop.sellOne(id);
			}

			return -2;
		}

		var shop = NPCs.getNearest(x -> x.hasAction("Trade") && x.getName().equals(name));
		if (shop != null && Reachable.isInteractable(shop))
		{
			shop.interact("Trade");
			return -4;
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(point);
		}

		return -1;
	}

	public static int purchaseFrom(String name, WorldPoint point, int id, int amount, boolean stack)
	{

		int current_amount = Inventory.getCount(stack, id);
		if (current_amount == amount)
		{
			return 0;
		}

		if (Shop.isOpen())
		{

			int amount_needed = amount - current_amount;
			if (amount_needed >= 50)
			{
				Shop.buyFifty(id);
			}
			else if (amount_needed >= 10)
			{
				Shop.buyTen(id);
			}
			else if (amount_needed >= 5)
			{
				Shop.buyFifty(id);
			}
			else if (amount_needed >= 1)
			{
				Shop.buyOne(id);
			}

			return -2;
		}

		var shop = NPCs.getNearest(x -> x.hasAction("Trade") && x.getName().equals(name));
		if (shop != null && Reachable.isInteractable(shop))
		{
			shop.interact("Trade");
			return -4;
		}
		else if (!Movement.isWalking())
		{
			Movement.walkTo(point);
		}

		return -1;
	}

	public enum EntityType
	{
		NPC,
		TILE_OBJECT,
		TILE_ITEM
	}
}
