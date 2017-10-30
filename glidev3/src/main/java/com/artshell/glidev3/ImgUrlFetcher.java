package com.artshell.glidev3;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 版本为：glide-3.8.0
 * @author artshell on 20/03/2017
 */

public class ImgUrlFetcher implements DataFetcher<InputStream> {
    private static final OkHttpClient CLIENT;
    private static final Request.Builder REQUEST_BUILDER;

    private volatile boolean mCanceled;     /* 任务状态标识 */
    private final ImgUrl mImgUrl;
    private Call mCall;
    private ResponseBody mBody;
    private InputStream mStream;

    static {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        mBuilder.readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        mBuilder.retryOnConnectionFailure(true);
        CLIENT = mBuilder.build();
        REQUEST_BUILDER = new Request.Builder();
    }

    public ImgUrlFetcher(ImgUrl imgUrl) {
        mImgUrl = imgUrl;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        String url = mImgUrl.getUrl();
        if (mCanceled) {
            return null;
        }
        mCall = CLIENT.newCall(REQUEST_BUILDER.url(mImgUrl.getUrl()).build());
        Response response = mCall.execute();
        if (response != null && response.isSuccessful() && response.code() == 200) {
            mBody = response.body();
            if (mBody != null) {
                mStream = ContentLengthInputStream.obtain(mBody.byteStream(), mBody.contentLength());
            }
        }
        return mStream;
    }

    @Override
    public void cleanup() {
        mCanceled = true;
        if (mCall != null) {
            mCall.cancel();
        }

        if (mStream != null) {
            try {
                mStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mBody != null) {
            mBody.close();
        }
    }

    @Override
    public String getId() {
        return mImgUrl.getId();
    }

    @Override
    public void cancel() {
        mCanceled = true;
    }
}
