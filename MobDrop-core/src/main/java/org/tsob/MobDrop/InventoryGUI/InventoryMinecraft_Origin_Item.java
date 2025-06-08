package org.tsob.MobDrop.InventoryGUI;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tsob.MobDrop.DataBase.DataBase;
import org.tsob.MobDrop.HashMap.HashMapSortItemStackList;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;

public class InventoryMinecraft_Origin_Item implements InventoryProvider {

	public static final SmartInventory INVENTORY = SmartInventory.builder()
					.id("Minecraft_Items")
					.provider(new InventoryMinecraft_Origin_Item())
					.size(6, 9)
					.title(ChatColor.BLUE + DataBase.fileInventory.getInventorTitle("Minecraft_Items"))
					.build();

	@Override
	public void init(Player player, InventoryContents contents) {
		Pagination pagination = contents.pagination();
    
    Map<String, ItemStack> materials = getMaterials();
    
    ClickableItem[] items = new ClickableItem[materials.size()];
    int index = 0;
    HashMapSortItemStackList ItemList = new HashMapSortItemStackList((HashMap<String, ItemStack>) materials);
    
    for (Map.Entry<String, ItemStack> entry:ItemList.list_Data) {
      items[index] = ClickableItem.of(entry.getValue(), e -> {});
      index++;
    }
        
    pagination.setItems(items);
    pagination.setItemsPerPage(45);

    pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
        
    // TODO Auto-generated method stub
    contents.set(5, 0, ClickableItem.of(DataBase.fileInventory.getbutton("Back"),
                e -> InventoryMenu.INVENTORY.open(player)));
    contents.set(5, 3, ClickableItem.of(DataBase.fileInventory.getbutton("Previous"),
                e -> InventoryMinecraft_Origin_Item.INVENTORY.open(player, pagination.previous().getPage())));
    contents.set(5, 4, ClickableItem.empty(InventoryTools.createPageButton(Material.PAPER,"§a - " + (pagination.getPage() + 1) + " - ")));
    contents.set(5, 5, ClickableItem.of(DataBase.fileInventory.getbutton("Next"),
                e -> InventoryMinecraft_Origin_Item.INVENTORY.open(player, pagination.next().getPage())));
	}

	@Override
	public void update(Player player, InventoryContents contents) {

	}

	private Map<String, ItemStack> getMaterials(){
		Map<String, ItemStack> materials = new HashMap<String, ItemStack>();
		for (Material material : Material.values()) {
			if (material.isItem()) {
				materials.put(material.name(), new ItemStack(material));
			}
		}

		return materials;
	}
}
