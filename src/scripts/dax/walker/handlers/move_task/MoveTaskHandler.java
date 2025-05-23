package scripts.dax.walker.handlers.move_task;

import scripts.dax.walker.handlers.passive_action.PassiveAction;
import scripts.dax.walker.models.MoveTask;
import scripts.dax.walker.models.WaitCondition;
import scripts.dax.walker_engine.WaitFor;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import scripts.dax.walker.models.enums.ActionResult;
import scripts.dax.walker.models.enums.MoveActionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public interface MoveTaskHandler {

    MoveActionResult handle(MoveTask moveTask, List<PassiveAction> passiveActionList);

    /**
     * @param waitCondition
     * @param timeout
     * @param passiveActionList
     * @return If player stops moving, return fail condition.
     */
    default ActionResult waitForConditionOrNoMovement(WaitCondition waitCondition, long timeout,
                                                      List<PassiveAction> passiveActionList) {
        if (passiveActionList == null) passiveActionList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        AtomicLong lastMoved = new AtomicLong(System.currentTimeMillis());
        passiveActionList.add(() -> {
            if (Player.isMoving() || Timing.timeFromMark(startTime) < 1750) {
                lastMoved.set(System.currentTimeMillis());
                return ActionResult.CONTINUE;
            }

            if (Timing.timeFromMark(lastMoved.get()) > 1250) return ActionResult.FAILURE;
            return ActionResult.CONTINUE;
        });
        return waitFor(waitCondition, timeout, passiveActionList);
    }

    default ActionResult waitFor(WaitCondition waitCondition, long timeout, List<PassiveAction> passiveActionList) {
        long end = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < end) {
            if (waitCondition.action()) return ActionResult.CONTINUE;

            for (PassiveAction passiveAction : passiveActionList) {
                if (!passiveAction.shouldActivate()) continue;

                ActionResult actionResult = passiveAction.activate();
                switch (actionResult) {
                    case EXIT_SUCCESS:
                    case EXIT_FAILURE:
                        return actionResult;
                }

            }
            WaitFor.milliseconds(80, 150);
        }
        return ActionResult.FAILURE;
    }

}
