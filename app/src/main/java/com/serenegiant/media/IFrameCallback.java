package com.serenegiant.media;

/**
 * callback interface
 */
public interface IFrameCallback {
    /**
     * called when preparing finshed
     */
    void onPrepared();
    /**
     * called when playing finished
     */
    void onFinished();
    /**
     * called every frame before time adjusting
     * return true if you don't want to use internal time adjustment
     */
    boolean onFrameAvailable(long presentationTimeUs);
}