package Core.TessTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by Fadi on 6/11/2014.
 */
public class TessEngine {

    static final String TAG = "DBG_" + TessEngine.class.getName();

    private Context context;

    private TessEngine(Context context){
        this.context = context;
    }

    public static TessEngine Generate(Context context) {
        return new TessEngine(context);
    }

    public String detectText(Bitmap bitmap) {


        TessDataManager.initTessTrainedData(context);
        TessBaseAPI tessBaseAPI = new TessBaseAPI();
        String path = TessDataManager.getTesseractFolder();

//        Sets debug mode. This controls how much information is displayed in the
//                 log during recognition.
        tessBaseAPI.setDebug(true);

//        Initializes the Tesseract engine with a specified language model.
        tessBaseAPI.init(path, "eng");
//        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
//        tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "1234567890!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
//                "YTREWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");
        /** Run both and combine results - best accuracy */
        tessBaseAPI.setPageSegMode(TessBaseAPI.OEM_TESSERACT_CUBE_COMBINED);
        Log.d(TAG, "Ended initialization of TessEngine");
        Log.d(TAG, "Running inspection on bitmap");

        /**
         * Provides an image for Tesseract to recognize. Copies the image buffer.
         * The source image may be destroyed immediately after SetImage is called.
         * SetImage clears all recognition results, and sets the rectangle to the
         * full image, so it may be followed immediately by a GetUTF8Text, and it
         * will automatically perform recognition.
         *设计 测试文档
         * @param bmp bitmap representation of the image
         */
        tessBaseAPI.setImage(bitmap);

        /**
         * The recognized text is returned as a String which is coded as UTF8.
         *
         * @return the recognized text
         */
        String inspection = tessBaseAPI.getUTF8Text();
        Log.d(TAG, "Got data: " + inspection);

        /**
         * Closes down tesseract and free up all memory. End() is equivalent to
         * destructing and reconstructing your TessBaseAPI.
         * <p>
         * Once End() has been used, none of the other API functions may be used
         * other than Init and anything declared above it in the class definition.
         */
        tessBaseAPI.end();

        /**
         * Indicates to the VM that it would be a good time to run the
         * garbage collector. Note that this is a hint only. There is no guarantee
         * that the garbage collector will actually be run.
         */
        System.gc();
        return inspection;

    }

}
