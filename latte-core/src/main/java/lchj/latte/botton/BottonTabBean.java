package lchj.latte.botton;

/**
 * Created by XiMiTwo on 2017/10/17.
 */

public final class BottonTabBean {
    private final CharSequence ICON;
//    private final int IMG;
    private final CharSequence TITLE;
    public BottonTabBean(CharSequence icon, CharSequence Title) {
        ICON = icon;
        this.TITLE = Title;
    }

//    public BottonTabBean(int img, CharSequence Title) {
//        IMG = img;
//        this.TITLE = Title;
//    }

    public CharSequence getIcon() {
        return ICON;
    }
//    public int getImg() {
//        return IMG;
//    }
    public CharSequence getTitle() {
        return TITLE;
    }
}
