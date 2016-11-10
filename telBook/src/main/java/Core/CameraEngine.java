package Core;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.security.Policy;
import java.util.List;

/**
 * Created by Fadi on 5/11/2014.
 */
public class CameraEngine {

    static final String TAG = "DBG_" + CameraUtils.class.getName();

    boolean on;
    Camera camera;
    SurfaceHolder surfaceHolder;

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {

        }
    };

    public boolean isOn() {
        return on;
    }

    private CameraEngine(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    static public CameraEngine New(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "Creating camera engine");
        return new CameraEngine(surfaceHolder);
    }

    public void requestFocus() {
        if (camera == null)
            return;

        if (isOn()) {
            camera.autoFocus(autoFocusCallback);
        }
    }

    public void start() {

        Log.d(TAG, "Entered CameraEngine - start()");
        this.camera = CameraUtils.getCamera();

        if (this.camera == null)
            return;

        Log.d(TAG, "Got camera hardware");

        try {

            this.camera.setPreviewDisplay(this.surfaceHolder);//把摄像头获得的画面显示在SurfaceView里面
            this.camera.setDisplayOrientation(90);//调整画面角度
            this.camera.startPreview();           //开始预览

            on = true;

            Log.d(TAG, "CameraEngine preview started");

        } catch (IOException e) {
            Log.e(TAG, "Error in setPreviewDisplay");
        }
    }

    public void stop() {

        if (camera != null) {
            //this.autoFocusEngine.stop();
            camera.release();
            camera = null;
        }

        on = false;

        Log.d(TAG, "CameraEngine Stopped");
    }

    public void takeShot(Camera.ShutterCallback shutterCallback,
                         Camera.PictureCallback rawPictureCallback,
                         Camera.PictureCallback jpegPictureCallback) {
        if (isOn()) {
            camera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
        }
    }

    private void turnLightOff() {
        if (camera == null) {
            return;
        }

        Camera.Parameters parameters = camera.getParameters();
        if (parameters == null) {
            return;
        }

        List<String> flashModes = parameters.getSupportedFlashModes();
        String mode = parameters.getFlashMode();
        if (mode == null) {
            return;
        }

        if (!Camera.Parameters.FLASH_MODE_OFF.equals(mode)) {
            // Turn off the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
            } else {
                Log.e(TAG, "FLASH_MODE_OFF not supported");
            }
        }

    }//turn off flash light

    private void turnLightOn() {
        if (camera == null) {
            return;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (parameters == null) {
            return;
        }

        List<String> list = parameters.getSupportedFlashModes();

        if (list == null) {
            return;
        }

        String mode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(mode)) {
            // Turn on the flash
            if (list.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
            } else {

            }
        }
    }//turn on flash light

    boolean isFlashLightOn = false;

    public void takeFlashLight() {
        if (!isFlashLightOn) {
            turnLightOn();
            isFlashLightOn = true;
        } else {
            turnLightOff();
            isFlashLightOn = false;
        }
    }

}
