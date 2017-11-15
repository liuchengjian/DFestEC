package lchj.latte.ui.camera;

import android.net.Uri;

import lchj.latte.delegates.PermissionCheckerDelegate;
import lchj.latte.utils.file.FileUtil;


/**
 * Created by 傅令杰
 * 照相机调用类
 */

public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
