package scripts.dax.walker.utils.camera;


import org.tribot.api.General;
import org.tribot.api.ScriptCache;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static dax.walker.utils.camera.CameraCalculations.getRotationToTile;

public class AsynchronousCamera {

    private ExecutorService executorService;
    private Future angleTask, rotationTask;

    private static AsynchronousCamera getInstance(){
        return (AsynchronousCamera) ScriptCache.get().computeIfAbsent("DaxWalker.AsynchronousCamera", k -> new AsynchronousCamera());
    }

    private AsynchronousCamera(){
        executorService = Executors.newFixedThreadPool(2);
    }

    public static Future focus(Positionable positionable){
        Future rotation = setCameraRotation(CameraCalculations.getRotationToTile(positionable), 0);
        Future angle = setCameraAngle(CameraCalculations.getAngleToTile(positionable), 0);
        return rotation;
    }

    public static synchronized Future setCameraAngle(int angle, int tolerance){
        if (getInstance().angleTask != null && !getInstance().angleTask.isDone()){
            return null;
        }
        Camera.setRotationMethod(Camera.ROTATION_METHOD.ONLY_KEYS);
            return getInstance().angleTask = getInstance().executorService.submit(() -> Camera.setCameraAngle(
		            CameraCalculations.normalizeAngle(angle + General.random(-tolerance, tolerance))));
    }

    public static synchronized Future setCameraRotation(int degrees, int tolerance){
        if (getInstance().rotationTask != null && !getInstance().rotationTask.isDone()){
            return null;
        }
        Camera.setRotationMethod(Camera.ROTATION_METHOD.ONLY_KEYS);
        return getInstance().rotationTask = getInstance().executorService.submit(() -> Camera.setCameraRotation(
		        CameraCalculations.normalizeRotation(degrees + General.random(-tolerance, tolerance))));
    }

}
