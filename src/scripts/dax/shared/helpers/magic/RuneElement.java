package scripts.dax.shared.helpers.magic;


import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;

import java.util.Arrays;

public enum RuneElement {

    AIR("Air", "Smoke", "Mist", "Dust"),
    EARTH("Earth", "Lava", "Mud", "Dust"),
    FIRE("Fire", "Lava", "Smoke", "Steam"),
    WATER("Water", "Mud", "Steam", "Mist"),
    LAW("Law"),
    BODY("Body"),
    MIND("Mind"),
    NATURE("Nature"),
    COSMIC("Cosmic"),
    CHAOS("Chaos"),
    ASTRAL("Astral"),
    DEATH("Death"),
    BLOOD("Blood"),
    WRATH("Wrath"),
    SOUL("Soul");

    private final String[] alternativeNames;

    RuneElement(String... alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public String[] getAlternativeNames() {
        return alternativeNames;
    }

    public int getCount() {
        if (haveStaff()) {
            return Integer.MAX_VALUE;
        }
        final boolean inMembersWorld = WorldHopper.isCurrentWorldMembers().orElse(false);
        RSItem[] items = Inventory.find(new Filter<RSItem>() {
            @Override
            public boolean accept(RSItem rsItem) {
                if (rsItem.getDefinition().isMembersOnly() && !inMembersWorld)
                    return false;
                String name = getItemName(rsItem).toLowerCase();

                if (!name.contains("rune")) {
                    return false;
                }

                for (String alternativeName : alternativeNames) {
                    if (name.startsWith(alternativeName.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        });
        return Arrays.stream(items).mapToInt(RSItem::getStack).sum() + RunePouch.getQuantity(this);
    }

    private boolean haveStaff() {
        return Equipment.find(new Filter<RSItem>() {
            @Override
            public boolean accept(RSItem rsItem) {
                if (rsItem.getDefinition().isMembersOnly() && !WorldHopper.isCurrentWorldMembers().orElse(false))
                    return false;
                String name = getItemName(rsItem).toLowerCase();
                if (!name.contains("staff")) {
                    return false;
                }
                for (String alternativeName : alternativeNames) {
                    if (name.contains(alternativeName.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        }).length > 0;
    }

    /**
     * @param item
     * @return item name. Never null. "null" if no name.
     */
    private static String getItemName(RSItem item) {
        RSItemDefinition definition = item.getDefinition();
        String name;
        return definition == null || (name = definition.getName()) == null ? "null" : name;
    }


}
