package me.yokeyword.fragmentation.helper.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.iwown.lib_common.R;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public final class AnimatorHelper {
    private Context context;
    public Animation enterAnim;
    public Animation exitAnim;
    private FragmentAnimator fragmentAnimator;
    private Animation noneAnim;
    private Animation noneAnimFixed;
    public Animation popEnterAnim;
    public Animation popExitAnim;

    public AnimatorHelper(Context context2, FragmentAnimator fragmentAnimator2) {
        this.context = context2;
        notifyChanged(fragmentAnimator2);
    }

    public void notifyChanged(FragmentAnimator fragmentAnimator2) {
        this.fragmentAnimator = fragmentAnimator2;
        initEnterAnim();
        initExitAnim();
        initPopEnterAnim();
        initPopExitAnim();
    }

    public Animation getNoneAnim() {
        if (this.noneAnim == null) {
            this.noneAnim = AnimationUtils.loadAnimation(this.context, R.anim.no_anim);
        }
        return this.noneAnim;
    }

    public Animation getNoneAnimFixed() {
        if (this.noneAnimFixed == null) {
            this.noneAnimFixed = new Animation() {
            };
        }
        return this.noneAnimFixed;
    }

    @Nullable
    public Animation compatChildFragmentExitAnim(Fragment fragment) {
        if ((fragment.getTag() == null || !fragment.getTag().startsWith("android:switcher:") || !fragment.getUserVisibleHint()) && (fragment.getParentFragment() == null || !fragment.getParentFragment().isRemoving() || fragment.isHidden())) {
            return null;
        }
        Animation animation = new Animation() {
        };
        animation.setDuration(this.exitAnim.getDuration());
        return animation;
    }

    private Animation initEnterAnim() {
        if (this.fragmentAnimator.getEnter() == 0) {
            this.enterAnim = AnimationUtils.loadAnimation(this.context, R.anim.no_anim);
        } else {
            this.enterAnim = AnimationUtils.loadAnimation(this.context, this.fragmentAnimator.getEnter());
        }
        return this.enterAnim;
    }

    private Animation initExitAnim() {
        if (this.fragmentAnimator.getExit() == 0) {
            this.exitAnim = AnimationUtils.loadAnimation(this.context, R.anim.no_anim);
        } else {
            this.exitAnim = AnimationUtils.loadAnimation(this.context, this.fragmentAnimator.getExit());
        }
        return this.exitAnim;
    }

    private Animation initPopEnterAnim() {
        if (this.fragmentAnimator.getPopEnter() == 0) {
            this.popEnterAnim = AnimationUtils.loadAnimation(this.context, R.anim.no_anim);
        } else {
            this.popEnterAnim = AnimationUtils.loadAnimation(this.context, this.fragmentAnimator.getPopEnter());
        }
        return this.popEnterAnim;
    }

    private Animation initPopExitAnim() {
        if (this.fragmentAnimator.getPopExit() == 0) {
            this.popExitAnim = AnimationUtils.loadAnimation(this.context, R.anim.no_anim);
        } else {
            this.popExitAnim = AnimationUtils.loadAnimation(this.context, this.fragmentAnimator.getPopExit());
        }
        return this.popExitAnim;
    }
}
