package scripts.dax.walker.handlers.special_cases.impl;

import scripts.dax.walker.handlers.move_task.MoveTaskHandler;
import scripts.dax.walker.handlers.passive_action.PassiveAction;
import scripts.dax.walker.models.DaxLogger;
import scripts.dax.walker.models.MoveTask;
import scripts.dax.walker.models.enums.MoveActionResult;
import scripts.dax.walker.handlers.special_cases.SpecialCaseHandler;

import java.util.List;

public class SecurityStrongholdHandler implements MoveTaskHandler, DaxLogger, SpecialCaseHandler {

    @Override
    public boolean shouldHandle(MoveTask moveTask) {
        return false;
    }

    @Override
    public MoveActionResult handle(MoveTask moveTask, List<PassiveAction> passiveActionList) {
        return null;
    }

}
