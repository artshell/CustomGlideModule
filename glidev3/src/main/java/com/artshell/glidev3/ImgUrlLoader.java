package com.artshell.glidev3;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

/**
 * 版本为：glide-3.8.0
 * @author artshell on 20/03/2017
 */

public class ImgUrlLoader implements ModelLoader<ImgUrl, InputStream> {

    private final ModelCache<ImgUrl, ImgUrl> mModelCache;

    public ImgUrlLoader(ModelCache<ImgUrl, ImgUrl> modelCache) {
        mModelCache = modelCache;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(ImgUrl model, int width, int height) {
        ImgUrl url = null;
        // 从缓存中取出ImgUrl
        if (mModelCache != null) {
            url = mModelCache.get(model, model.getW(), model.getH());
            if (url == null) {
                mModelCache.put(model, model.getW(), model.getH(), model);
                url = model;
            }
        }
        return new ImgUrlFetcher(url);
    }

    /**
     * ModelLoader工厂，在向GlideModule中注册自定义ModelLoader时使用到
     * 随后GlideModule被注册到AndroidManifest.xml的<application>内的<meta-data>标签中
     */
    public static class Factory implements ModelLoaderFactory<ImgUrl, InputStream> {
        /* 可缓存大小 500 byte */
        private final ModelCache<ImgUrl, ImgUrl> mModelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<ImgUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new ImgUrlLoader(mModelCache);
        }

        @Override
        public void teardown() {

        }
    }
}
