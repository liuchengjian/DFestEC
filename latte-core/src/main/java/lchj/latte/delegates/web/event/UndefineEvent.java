package lchj.latte.delegates.web.event;


/**
 * Created by 傅令杰
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
//        LatteLogger.e("UndefineEvent", params);
        return null;
    }
}
