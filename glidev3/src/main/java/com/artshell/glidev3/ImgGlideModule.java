package com.artshell.glidev3;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.InputStream;

/**
 * 版本为：glide-3.8.0
 * @author artshell on 20/03/2017
 */

public class ImgGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        /** 为GlideModule设置单独的tag以免占用View的{@link View#setTag(Object)}  */
        ViewTarget.setTagId(R.id.util_glide_tag_id);
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        /* 注册ImgUrlLoader */
        glide.register(ImgUrl.class, InputStream.class, new ImgUrlLoader.Factory());
    }
}
