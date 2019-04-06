package rana.jatin.core.util.dialog;

import androidx.annotation.DrawableRes;

/**
 * Created by jatin on 5/23/2017.
 */

public class DialogData {
    private String image;
    private String name;
    private int txtColor = -1;
    private boolean divider = false;
    private boolean underLine = false;
    private int resId = -1;
    private int underlineRes;

    public DialogData(String image, String name, int resId) {
        this.image = image;
        this.name = name;
        this.resId = resId;
    }

    public DialogData(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public DialogData(String name, int txtColor) {
        this.name = name;
        this.txtColor = txtColor;
    }

    public DialogData(String name) {
        this.name = name;
    }

    public DialogData(String name, boolean divider) {
        this.name = name;
        this.divider = divider;
    }

    public DialogData(String name, int txtColor, boolean divider) {
        this.name = name;
        this.txtColor = txtColor;
        this.divider = divider;
    }

    public DialogData(String name, int txtColor, boolean underLine, @DrawableRes int underlineRes, boolean divider) {
        this.name = name;
        this.txtColor = txtColor;
        this.divider = divider;
        this.underLine = underLine;
        this.underlineRes = underlineRes;
    }

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

    public boolean isUnderLine() {
        return underLine;
    }

    public int getUnderlineRes() {
        return underlineRes;
    }

    public void setUnderlineRes(int underlineRes) {
        this.underlineRes = underlineRes;
    }

    public void setUnderLine(boolean underLine) {
        this.underLine = underLine;
    }
}
