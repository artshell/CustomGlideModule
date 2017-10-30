package com.artshell.glidev3;

/**
 * 版本为：glide-3.8.0
 * @author artshell on 20/03/2017
 */

public class ImgUrl {

    private String mId;
    private String mUrl;
    private int mW, mH;

    public ImgUrl() {}

    public ImgUrl(String id, String url, int w, int h) {
        mId = id;
        mUrl = url;
        mW = w;
        mH = h;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
        mId = String.valueOf(url.hashCode());
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getW() {
        return mW;
    }

    public void setW(int w) {
        mW = w;
    }

    public int getH() {
        return mH;
    }

    public void setH(int h) {
        mH = h;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        ImgUrl img = (ImgUrl) obj;
        return mUrl.equals(img.mUrl);
    }

    @Override
    public int hashCode() {
        return mUrl.hashCode() * 31;
    }
}
