package com.stfalcon.chatkit.sample.utils.ui;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

/*
 * Created by troy379 on 27.03.17.
 */
public class WrappedSimpleDraweeView extends SimpleDraweeView {

    public WrappedSimpleDraweeView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public WrappedSimpleDraweeView(Context context) {
        super(context);
    }

    public WrappedSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (uri != null) {
            updateController(uri.toString());
        }
    }

    @Override
    public void setImageURI(String uriString) {
        super.setImageURI(uriString);
        updateController(uriString);
    }

    @Override
    public void setImageURI(Uri uri, Object callerContext) {
        super.setImageURI(uri, callerContext);
        if (uri != null) {
            updateController(uri.toString());
        }
    }

    @Override
    public void setImageURI(String uriString, Object callerContext) {
        super.setImageURI(uriString, callerContext);
        updateController(uriString);
    }

    private void updateController(String url) {
        super.setController(
                Fresco.newDraweeControllerBuilder()
                        .setUri(url)
                        .setControllerListener(getControllerListener())
                        .setOldController(getController())
                        .build()
        );
    }

    private void updateViewSize(@Nullable ImageInfo imageInfo) {
        if (imageInfo != null) {
            getLayoutParams().width = imageInfo.getWidth();
            getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            setAspectRatio((float) imageInfo.getWidth() / imageInfo.getHeight());
        }
    }

    private ControllerListener getControllerListener() {
        return new BaseControllerListener<ImageInfo>() {
            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                updateViewSize(imageInfo);
            }

            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                updateViewSize(imageInfo);
            }
        };
    }
}
