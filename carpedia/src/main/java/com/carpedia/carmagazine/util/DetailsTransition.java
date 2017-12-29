package com.carpedia.carmagazine.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeTransform;
import android.support.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Created by d264 on 12/29/17.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class DetailsTransition extends TransitionSet {
    public DetailsTransition() {
        init();
    }

    /**
     * This constructor allows us to use this transition in XML
     */
    public DetailsTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}