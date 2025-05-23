package scripts.dax.walker.handlers.passive_action;

import scripts.dax.walker.models.enums.ActionResult;

public interface PassiveAction {

    default boolean shouldActivate() {
        return true;
    }

    ActionResult activate();

}
