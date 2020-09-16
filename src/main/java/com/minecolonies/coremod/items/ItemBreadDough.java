package com.minecolonies.coremod.items;

import com.minecolonies.api.creativetab.ModCreativeTabs;

import static com.minecolonies.api.util.constant.Constants.STACKSIZE;

/**
 * Class handling the Santa hat.
 */
public class ItemBreadDough extends AbstractItemMinecolonies
{
    /**
     * Sets the name, creative tab, and registers the Bread Dough item.
     *
     * @param properties the properties.
     */
    public ItemBreadDough(final Properties properties)
    {
        super("bread_dough", properties.maxStackSize(STACKSIZE).group(ModCreativeTabs.MINECOLONIES));
    }
}
