package scripts.dax.shared.helpers;

import org.tribot.api2007.types.RSNPC;


public class RSNPCHelper {

    public static String getName(RSNPC rsnpc){
        if (rsnpc == null){
            return null;
        }
        String name = rsnpc.getName();
        return name != null ? name : "null";
    }

    public static String[] getActions(RSNPC rsnpc){
        String[] actions = rsnpc.getActions();
        return actions != null ? actions : new String[0];
    }

}
