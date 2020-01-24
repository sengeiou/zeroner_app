package com.iwown.device_module.device_guide;

import com.iwown.device_module.device_guide.a_interface.IGuideAnimController;
import java.util.List;

public class GuideVpShowHelper {
    public List<IGuideAnimController> mAnimControllers;

    public List<IGuideAnimController> getAnimControllers() {
        return this.mAnimControllers;
    }

    public void setAnimControllers(List<IGuideAnimController> animControllers) {
        this.mAnimControllers = animControllers;
    }

    public void initAll() {
        if (this.mAnimControllers != null && this.mAnimControllers.size() != 0) {
            for (IGuideAnimController controller : this.mAnimControllers) {
                controller.init();
            }
        }
    }

    public void initAll(List<IGuideAnimController> animControllers) {
        this.mAnimControllers = animControllers;
        if (this.mAnimControllers != null && this.mAnimControllers.size() != 0) {
            for (IGuideAnimController controller : this.mAnimControllers) {
                controller.init();
            }
        }
    }

    public void cancelAll() {
        if (this.mAnimControllers != null && this.mAnimControllers.size() != 0) {
            for (IGuideAnimController controller : this.mAnimControllers) {
                controller.cancel();
            }
        }
    }

    public void start(int... index) {
        for (int i : index) {
            if (!(this.mAnimControllers == null || this.mAnimControllers.size() == 0 || i >= this.mAnimControllers.size())) {
                ((IGuideAnimController) this.mAnimControllers.get(i)).start();
            }
        }
    }

    public void stop(int... index) {
        for (int i : index) {
            if (!(this.mAnimControllers == null || this.mAnimControllers.size() == 0 || i >= this.mAnimControllers.size())) {
                ((IGuideAnimController) this.mAnimControllers.get(i)).stop();
            }
        }
    }

    public void stopAll() {
        if (this.mAnimControllers != null && this.mAnimControllers.size() != 0) {
            for (IGuideAnimController controller : this.mAnimControllers) {
                controller.stop();
            }
        }
    }
}
