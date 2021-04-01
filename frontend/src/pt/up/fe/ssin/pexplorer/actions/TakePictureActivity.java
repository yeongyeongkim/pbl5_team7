package pt.up.fe.ssin.pexplorer.actions;

import java.io.IOException;

import pt.up.fe.ssin.pexplorer.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

public class TakePictureActivity extends Activity implements
        SurfaceHolder.Callback {
    private ImageView imageView;
    private SurfaceView surfView;

    private Bitmap bmp;

    private SurfaceHolder surfHolder;
    private Camera camera;
    private Parameters parameters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takepicture);

        imageView = (ImageView) findViewById(R.id.picImage);
        surfView = (SurfaceView) findViewById(R.id.surfImage);

        surfHolder = surfView.getHolder();
        surfHolder.addCallback(this);
        surfHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        parameters = camera.getParameters();
        camera.setParameters(parameters);
        camera.startPreview();

        Camera.PictureCallback mCall = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                if (data != null) {
                    bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imageView.setImageBitmap(bmp);
                }
            }
        };

        if (bmp == null)
            camera.takePicture(null, null, mCall);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(holder);

        } catch (IOException exception) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }
}