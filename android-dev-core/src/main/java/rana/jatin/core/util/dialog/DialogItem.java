package rana.jatin.core.util.dialog;

/**
 * Created by jatin on 5/23/2017.
 */

public class DialogItem {
    String image;
    String name;
    int txtColor=-1;
    boolean divider=false;

    public boolean isDivider() {
        return this.divider;
    }

    public void setDivider(boolean divider) {
        this.divider = divider;
    }

    public int getTxtColor() {
        return this.txtColor;
    }

    public void setTxtColor(int txtColor) {
        this.txtColor = txtColor;
    }

    int resId=-1;

    public DialogItem(String image, String name, int resId) {
        this.image = image;
        this.name = name;
        this.resId = resId;
    }

    public DialogItem(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public DialogItem(String name, int txtColor) {
        this.name = name;
        this.txtColor = txtColor;
    }

    public DialogItem(String name) {
        this.name = name;
    }

    public DialogItem(String name, boolean divider) {
        this.name = name;
        this.divider = divider;
    }

    public DialogItem(String name, int txtColor, boolean divider) {
        this.name = name;
        this.txtColor = txtColor;
        this.divider = divider;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getImage() {
        return image;

    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
