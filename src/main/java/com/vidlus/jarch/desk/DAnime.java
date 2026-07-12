package com.vidlus.jarch.desk;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 * A powerful utility class for handling smooth Swing-based animations and game loops.
 * Features built-in FPS tracking, tick listeners, and mathematical easing functions.
 */
public class DAnime {

    private Timer animTimer;
    private boolean isAnimating = false;
    private long frameCount = 0;
    
    // Time tracking
    private long animStartTime = 0; // When the animation started in real time
    private long simulatedAnimTime = 0; // Accumulated time respecting timeScale and pausing
    private long realAnimTime = 0; // Accumulated time respecting pausing but NOT timeScale
    private long lastTickRealTime = 0; // Real world time of the last frame
    private float timeScale = 1.0f; // 1.0 = normal, 0.5 = slow motion, 2.0 = fast forward
    
    // FPS tracking
    private int currentFps = 0;
    private long lastFpsTime = 0;
    private int frameCountSinceLastSec = 0;
    
    // Listeners and Tweens
    private final List<Runnable> tickListeners = new ArrayList<>();
    private final List<AnimListener> advancedTickListeners = new ArrayList<>();
    private final List<Tween> activeTweens = new ArrayList<>();
    
    // Optional Duration
    private long durationLimitMillis = -1;
    private Runnable onCompleteCallback = null;
    
    private Component targetComponent;

    /**
     * Advanced listener interface that receives the animation state on every tick.
     */
    public interface AnimListener {
        /**
         * Called on every frame of the animation loop.
         * 
         * @param anim                   the DAnime instance firing the tick
         * @param frameCount             the total number of frames rendered so far
         * @param elapsedSimulatedMillis the total simulated time elapsed (affected by time scale)
         * @param deltaTimeSec           the simulated delta time since the last frame in seconds
         */
        void onTick(DAnime anim, long frameCount, long elapsedSimulatedMillis, float deltaTimeSec);
    }

    /**
     * Creates a standalone animation controller.
     * You will need to manually call repaint() on your components inside the onTick() listener.
     */
    public DAnime() {
    }

    /**
     * Creates an animation controller that automatically repaints the specified target component on every tick.
     * 
     * @param targetComponent the component to automatically repaint (e.g. a DCanvas or DPane)
     */
    public DAnime(Component targetComponent) {
        this.targetComponent = targetComponent;
    }

    /**
     * Adds a simple callback that executes every tick of the animation loop.
     * 
     * @param listener the Runnable to execute every frame
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime onTick(Runnable listener) {
        if (listener != null) tickListeners.add(listener);
        return this;
    }

    /**
     * Adds an advanced callback that receives frame count, elapsed time, and delta time.
     * 
     * @param listener the AnimListener to execute every frame
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime onTick(AnimListener listener) {
        if (listener != null) advancedTickListeners.add(listener);
        return this;
    }

    /**
     * Plays a smooth tween (transition) from a start value to an end value over a specific duration.
     * The callback is fired on every frame with the smoothly interpolated value.
     * 
     * @param start          The starting numeric value
     * @param end            The ending numeric value
     * @param durationMillis How long the transition should take in simulated milliseconds
     * @param easing         The mathematical easing function to apply (e.g., DAnime.Easing::easeOutQuad), or null for linear
     * @param onUpdate       The callback executed every frame receiving the interpolated value
     * @return The created Tween object so you can chain configuration like .delay(), .repeat(), .yoyo()
     */
    public Tween playTween(float start, float end, long durationMillis, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<Float> onUpdate) {
        Tween t = new Tween(start, end, durationMillis, easing, onUpdate);
        activeTweens.add(t);
        return t;
    }

    /**
     * Plays a smooth transition between two Colors over time, automatically interpolating RGB and Alpha values.
     * 
     * @param start          The starting color
     * @param end            The target color
     * @param durationMillis How long the color transition should take in simulated milliseconds
     * @param easing         The mathematical easing function to apply
     * @param onUpdate       The callback executed every frame receiving the interpolated Color object
     * @return The created Tween object so you can chain configuration like .delay(), .repeat(), .yoyo()
     */
    public Tween playColorTween(java.awt.Color start, java.awt.Color end, long durationMillis, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<java.awt.Color> onUpdate) {
        Tween t = new Tween(0f, 1f, durationMillis, easing, fraction -> {
            int r = (int) (start.getRed() + (end.getRed() - start.getRed()) * fraction);
            int g = (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * fraction);
            int b = (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * fraction);
            int a = (int) (start.getAlpha() + (end.getAlpha() - start.getAlpha()) * fraction);
            onUpdate.accept(new java.awt.Color(r, g, b, a));
        });
        activeTweens.add(t);
        return t;
    }

    /**
     * Plays a smooth tween (transition) from a start integer value to an end integer value.
     * Handy for animating coordinates or dimensions without manual casting.
     * 
     * @param start          The starting integer value
     * @param end            The ending integer value
     * @param durationMillis How long the transition should take in simulated milliseconds
     * @param easing         The mathematical easing function to apply
     * @param onUpdate       The callback executed every frame receiving the interpolated value
     * @return The created Tween object
     */
    public Tween playIntTween(int start, int end, long durationMillis, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<Integer> onUpdate) {
        Tween t = new Tween((float)start, (float)end, durationMillis, easing, fraction -> {
            onUpdate.accept(Math.round(fraction));
        });
        activeTweens.add(t);
        return t;
    }

    /**
     * Plays a smooth tween (transition) from a start double value to an end double value.
     * Handy for precise animations or complex math involving floating point precision.
     * 
     * @param start          The starting double value
     * @param end            The ending double value
     * @param durationMillis How long the transition should take in simulated milliseconds
     * @param easing         The mathematical easing function to apply
     * @param onUpdate       The callback executed every frame receiving the interpolated value
     * @return The created Tween object
     */
    public Tween playDoubleTween(double start, double end, long durationMillis, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<Double> onUpdate) {
        Tween t = new Tween(0f, 1f, durationMillis, easing, fraction -> {
            onUpdate.accept(start + (end - start) * fraction);
        });
        activeTweens.add(t);
        return t;
    }

    /**
     * Executes a callback exactly once after a specified delay in simulated milliseconds.
     * Because this uses the animation loop, it flawlessly respects pausing and time scale!
     * 
     * @param delayMillis the delay before execution
     * @param action the runnable to execute
     * @return The internal Tween object handling the timeout
     */
    public Tween setTimeout(long delayMillis, Runnable action) {
        Tween t = new Tween(0f, 1f, 0, null, val -> {}).delay(delayMillis).onComplete(action);
        activeTweens.add(t);
        return t;
    }

    /**
     * Removes a specific tween from the active animation loop immediately.
     * 
     * @param t the tween to remove
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime removeTween(Tween t) {
        if (t != null) activeTweens.remove(t);
        return this;
    }

    /**
     * Removes all active tweens, halting all currently running transitions.
     * 
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime clearTweens() {
        activeTweens.clear();
        return this;
    }

    // ==========================================
    // --- Swing Component Utilities
    // ==========================================

    /**
     * Smoothly animates a Swing component's location from a starting point to an ending point.
     * 
     * @param c              The Swing component to animate
     * @param startX         Starting X coordinate
     * @param startY         Starting Y coordinate
     * @param endX           Ending X coordinate
     * @param endY           Ending Y coordinate
     * @param durationMillis Duration of the tween
     * @param easing         The easing function
     * @return The created Tween
     */
    public Tween tweenLocation(Component c, int startX, int startY, int endX, int endY, long durationMillis, java.util.function.Function<Float, Float> easing) {
        return playTween(0f, 1f, durationMillis, easing, fraction -> {
            int curX = (int) (startX + (endX - startX) * fraction);
            int curY = (int) (startY + (endY - startY) * fraction);
            c.setLocation(curX, curY);
        });
    }

    /**
     * Smoothly animates a Swing component's bounds (location and size) simultaneously.
     * 
     * @param c              The Swing component to animate
     * @param startBounds    The starting bounds
     * @param endBounds      The ending bounds
     * @param durationMillis Duration of the tween
     * @param easing         The easing function
     * @return The created Tween
     */
    public Tween tweenBounds(Component c, java.awt.Rectangle startBounds, java.awt.Rectangle endBounds, long durationMillis, java.util.function.Function<Float, Float> easing) {
        return playTween(0f, 1f, durationMillis, easing, fraction -> {
            int curX = (int) (startBounds.x + (endBounds.x - startBounds.x) * fraction);
            int curY = (int) (startBounds.y + (endBounds.y - startBounds.y) * fraction);
            int curW = (int) (startBounds.width + (endBounds.width - startBounds.width) * fraction);
            int curH = (int) (startBounds.height + (endBounds.height - startBounds.height) * fraction);
            c.setBounds(curX, curY, curW, curH);
        });
    }

    /**
     * Smoothly animates a Swing component's size (width and height).
     * 
     * @param c              The Swing component to animate
     * @param startW         Starting width
     * @param startH         Starting height
     * @param endW           Ending width
     * @param endH           Ending height
     * @param durationMillis Duration of the tween
     * @param easing         The easing function
     * @return The created Tween
     */
    public Tween tweenSize(Component c, int startW, int startH, int endW, int endH, long durationMillis, java.util.function.Function<Float, Float> easing) {
        return playTween(0f, 1f, durationMillis, easing, fraction -> {
            int curW = (int) (startW + (endW - startW) * fraction);
            int curH = (int) (startH + (endH - startH) * fraction);
            c.setSize(curW, curH);
        });
    }

    /**
     * Smoothly animates a Swing component's background color.
     * 
     * @param c              The Swing component to animate
     * @param startColor     The starting color
     * @param endColor       The ending color
     * @param durationMillis Duration of the tween
     * @param easing         The easing function
     * @return The created Tween
     */
    public Tween tweenBackground(Component c, java.awt.Color startColor, java.awt.Color endColor, long durationMillis, java.util.function.Function<Float, Float> easing) {
        return tweenColor(startColor, endColor, durationMillis, easing, c::setBackground);
    }

    /**
     * Smoothly animates a Swing component's foreground color (text color).
     * 
     * @param c              The Swing component to animate
     * @param startColor     The starting color
     * @param endColor       The ending color
     * @param durationMillis Duration of the tween
     * @param easing         The easing function
     * @return The created Tween
     */
    public Tween tweenForeground(Component c, java.awt.Color startColor, java.awt.Color endColor, long durationMillis, java.util.function.Function<Float, Float> easing) {
        return tweenColor(startColor, endColor, durationMillis, easing, c::setForeground);
    }

    /**
     * Smoothly interpolates between two colors (including alpha transparency).
     * 
     * @param startColor     The starting color
     * @param endColor       The ending color
     * @param durationMillis Duration of the tween
     * @param easing         The easing function
     * @param onUpdate       Consumer that receives the blended Color each frame
     * @return The created Tween
     */
    public Tween tweenColor(java.awt.Color startColor, java.awt.Color endColor, long durationMillis, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<java.awt.Color> onUpdate) {
        return playTween(0f, 1f, durationMillis, easing, fraction -> {
            int r = (int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * fraction);
            int g = (int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * fraction);
            int b = (int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * fraction);
            int a = (int) (startColor.getAlpha() + (endColor.getAlpha() - startColor.getAlpha()) * fraction);
            
            // Clamp values between 0 and 255 (necessary for bounce/elastic easings)
            r = Math.max(0, Math.min(255, r));
            g = Math.max(0, Math.min(255, g));
            b = Math.max(0, Math.min(255, b));
            a = Math.max(0, Math.min(255, a));
            
            onUpdate.accept(new java.awt.Color(r, g, b, a));
        });
    }

    /**
     * Animates through a sequence of values evenly distributed across the duration.
     * 
     * @param durationMillis The duration of the tween
     * @param easing         The easing function
     * @param onUpdate       The lambda to call every frame with the interpolated value
     * @param values         The array of values to keyframe through
     * @return The created Tween
     */
    public Tween playKeyframes(long durationMillis, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<Float> onUpdate, float... values) {
        if (values == null || values.length == 0) return null;
        if (values.length == 1) return playTween(values[0], values[0], durationMillis, easing, onUpdate);
        
        java.util.TreeMap<Float, Float> map = new java.util.TreeMap<>();
        for (int i = 0; i < values.length; i++) {
            float percent = (float) i / (values.length - 1);
            map.put(percent, values[i]);
        }
        return playKeyframes(map, durationMillis, easing, onUpdate);
    }

    /**
     * Creates a tween based on a timeline of keyframes.
     * 
     * @param keyframes      A map of percentages (0.0f to 1.0f) to their target values.
     * @param durationMillis The duration of the tween in simulated milliseconds.
     * @param easing         The easing function applied to the overall timeline progress.
     * @param onUpdate       The lambda to call every frame with the interpolated value.
     * @return The created Tween.
     */
    public Tween playKeyframes(java.util.TreeMap<Float, Float> keyframes, long durationMillis, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<Float> onUpdate) {
        if (keyframes == null || keyframes.isEmpty()) return null;
        if (keyframes.size() == 1) return playTween(keyframes.firstEntry().getValue(), keyframes.firstEntry().getValue(), durationMillis, easing, onUpdate);
        
        return playTween(0f, 1f, durationMillis, easing, fraction -> {
            java.util.Map.Entry<Float, Float> floor = keyframes.floorEntry(fraction);
            java.util.Map.Entry<Float, Float> ceiling = keyframes.ceilingEntry(fraction);
            
            // Handle bounds extrapolation (e.g. for bounce/elastic easing)
            if (floor == null) { // fraction < first key
                java.util.Iterator<java.util.Map.Entry<Float, Float>> it = keyframes.entrySet().iterator();
                java.util.Map.Entry<Float, Float> first = it.next();
                java.util.Map.Entry<Float, Float> second = it.next();
                float localProgress = (fraction - first.getKey()) / (second.getKey() - first.getKey());
                onUpdate.accept(first.getValue() + (second.getValue() - first.getValue()) * localProgress);
                return;
            }
            if (ceiling == null) { // fraction > last key
                java.util.Map.Entry<Float, Float> last = keyframes.lastEntry();
                java.util.Map.Entry<Float, Float> secondToLast = keyframes.lowerEntry(last.getKey());
                float localProgress = (fraction - secondToLast.getKey()) / (last.getKey() - secondToLast.getKey());
                onUpdate.accept(secondToLast.getValue() + (last.getValue() - secondToLast.getValue()) * localProgress);
                return;
            }
            if (floor.getKey().equals(ceiling.getKey())) { 
                onUpdate.accept(floor.getValue()); 
                return; 
            }
            
            // Standard interpolation between two keyframes
            float localProgress = (fraction - floor.getKey()) / (ceiling.getKey() - floor.getKey());
            float interpolatedVal = floor.getValue() + (ceiling.getValue() - floor.getValue()) * localProgress;
            onUpdate.accept(interpolatedVal);
        });
    }

    /**
     * Sets the time scale of the animation. 
     * 1.0 is normal speed. 0.5 is half-speed (slow motion). 2.0 is double speed.
     * 
     * @param scale the global speed multiplier
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime setTimeScale(float scale) {
        this.timeScale = Math.max(0, scale);
        return this;
    }

    /**
     * Smoothly animates the global time scale of the entire engine over a duration.
     * This creates a Matrix-style "bullet time" effect if tweened down to a low value!
     * 
     * @param targetScale    The desired time scale to reach (e.g. 0.2 for 20% speed)
     * @param durationMillis The duration of the transition in REAL milliseconds
     * @param easing         The easing function to use
     * @return The created Tween
     */
    public Tween tweenTimeScale(float targetScale, long durationMillis, java.util.function.Function<Float, Float> easing) {
        return playTween(this.timeScale, targetScale, durationMillis, easing, val -> this.timeScale = Math.max(0, val))
               .setUseRealTime(true);
    }

    /**
     * Starts the automatic animation loop at the specified frames per second.
     * 
     * @param fps frames per second (e.g., 60)
     * @return This DAnime instance.
     */
    public DAnime start(int fps) {
        return start(fps, -1, null);
    }

    /**
     * Starts the animation loop and automatically stops it after a specified duration.
     * 
     * @param fps            frames per second
     * @param durationMillis duration to run the animation in real milliseconds
     * @param onComplete     optional callback to fire when the animation finishes
     * @return This DAnime instance.
     */
    public DAnime start(int fps, long durationMillis, Runnable onComplete) {
        if (animTimer != null) animTimer.stop();
        this.durationLimitMillis = durationMillis;
        this.onCompleteCallback = onComplete;
        
        frameCount = 0;
        animStartTime = System.currentTimeMillis();
        lastTickRealTime = animStartTime;
        simulatedAnimTime = 0;
        realAnimTime = 0;
        lastFpsTime = animStartTime;
        frameCountSinceLastSec = 0;
        isAnimating = true;
        
        animTimer = new Timer(1000 / fps, e -> {
            long now = System.currentTimeMillis();
            
            // Calculate delta time
            long realDelta = now - lastTickRealTime;
            lastTickRealTime = now;
            realAnimTime += realDelta;
            long simDelta = (long) (realDelta * timeScale);
            simulatedAnimTime += simDelta;
            float deltaSec = simDelta / 1000.0f;
            
            // Update FPS
            frameCount++;
            frameCountSinceLastSec++;
            if (now - lastFpsTime >= 1000) {
                currentFps = frameCountSinceLastSec;
                frameCountSinceLastSec = 0;
                lastFpsTime = now;
            }
            
            // Update Tweens
            activeTweens.removeIf(Tween::isFinished);
            for (Tween t : activeTweens) {
                t.update(t.useRealTime ? realAnimTime : simulatedAnimTime);
            }
            
            // Fire Listeners
            for (Runnable listener : tickListeners) {
                listener.run();
            }
            for (AnimListener advancedListener : advancedTickListeners) {
                advancedListener.onTick(this, frameCount, simulatedAnimTime, deltaSec);
            }
            
            // Repaint Target Component
            if (targetComponent != null) {
                targetComponent.repaint();
            }
            
            // Check Duration Limit
            if (durationLimitMillis > 0 && simulatedAnimTime >= durationLimitMillis) {
                stop();
                if (onCompleteCallback != null) {
                    onCompleteCallback.run();
                }
            }
        });
        animTimer.start();
        return this;
    }

    /**
     * Stops and completely kills the animation loop.
     * 
     * @return This DAnime instance.
     */
    public DAnime stop() {
        if (animTimer != null) animTimer.stop();
        isAnimating = false;
        return this;
    }

    /**
     * Pauses the animation loop without resetting time or frame count.
     * 
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime pause() {
        if (animTimer != null && isAnimating) {
            animTimer.stop();
            isAnimating = false;
        }
        return this;
    }

    /**
     * Resumes the animation loop from where it was paused.
     * 
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime resume() {
        if (animTimer != null && !isAnimating) {
            // Prevent huge delta jump
            lastTickRealTime = System.currentTimeMillis();
            animTimer.start();
            isAnimating = true;
        }
        return this;
    }

    /**
     * Returns true if the animation loop is currently running and unpaused.
     * 
     * @return true if animating, false otherwise
     */
    public boolean isAnimating() {
        return isAnimating;
    }

    /**
     * Restarts the animation simulation time to 0, resetting and re-activating all tweens.
     * 
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime restart() {
        simulatedAnimTime = 0;
        realAnimTime = 0;
        lastTickRealTime = System.currentTimeMillis();
        for (Tween t : activeTweens) {
            t.reset();
        }
        return this;
    }

    /**
     * Kills a specific tween, instantly stopping it and marking it for removal.
     * 
     * @param t the tween to kill
     * @return This DAnime instance
     */
    public DAnime killTween(Tween t) {
        if (t != null) t.kill();
        return this;
    }

    /**
     * Kills all currently active tweens, clearing the entire animation queue.
     * 
     * @return This DAnime instance
     */
    public DAnime killAllTweens() {
        for (Tween t : activeTweens) {
            t.kill();
        }
        return this;
    }

    /**
     * Instantly jumps the animation timeline to the specified millisecond mark,
     * immediately updating all active tweens to their correct state at that time.
     * 
     * @param millis the simulated millisecond mark to jump to
     * @return This DAnime instance for fluent chaining.
     */
    public DAnime seek(long millis) {
        simulatedAnimTime = millis;
        realAnimTime = millis;
        for (Tween t : activeTweens) {
            t.update(simulatedAnimTime);
        }
        if (targetComponent != null) targetComponent.repaint();
        return this;
    }

    /**
     * Returns the actual tracked frames per second over the last second.
     * 
     * @return the current FPS
     */
    public int getFPS() {
        return currentFps;
    }

    /**
     * Returns the total number of frames rendered since start() was called.
     * 
     * @return the total frame count
     */
    public long getFrameCount() {
        return frameCount;
    }

    /**
     * Returns the total simulated elapsed time in milliseconds since start() was called,
     * factoring in pauses and time scale.
     * 
     * @return the simulated elapsed time in milliseconds
     */
    public long getAnimTimeMillis() {
        return simulatedAnimTime;
    }

    /**
     * Internal representation of a value interpolation over time.
     * Stores the lifecycle state of a transition and provides a fluent API for configuration.
     */
    public static class Tween {
        float startValue;
        float endValue;
        long durationMillis;
        java.util.function.Function<Float, Float> easing;
        java.util.function.Consumer<Float> onUpdate;
        
        long delayMillis = 0;
        int repeatCount = 0; // 0 = play once, -1 = infinite
        boolean yoyo = false;
        Runnable onCompleteCallback = null;
        
        long startTime = -1;
        int currentLoop = 0;
        boolean finished = false;
        
        Tween nextTween = null;
        boolean isWaitingForChain = false;
        
        boolean reverseRequested = false;
        boolean manuallyReversed = false;
        boolean useRealTime = false;

        Tween(float start, float end, long duration, java.util.function.Function<Float, Float> easing, java.util.function.Consumer<Float> onUpdate) {
            this.startValue = start;
            this.endValue = end;
            this.durationMillis = duration;
            this.easing = easing;
            this.onUpdate = onUpdate;
        }

        /**
         * Adds a delay before this tween begins to execute.
         * 
         * @param delayMillis the delay in simulated milliseconds
         * @return This Tween instance for chaining
         */
        public Tween delay(long delayMillis) { this.delayMillis = delayMillis; return this; }
        
        /**
         * Configures this tween to run on real unscaled time rather than the engine's simulated time.
         * 
         * @param useRealTime true to run in real time
         * @return This Tween instance for chaining
         */
        public Tween setUseRealTime(boolean useRealTime) { this.useRealTime = useRealTime; return this; }
        
        /**
         * Chains another Tween to start exactly when this Tween completely finishes.
         * 
         * @param nextTween the Tween to execute next
         * @return This Tween instance for chaining
         */
        public Tween chain(Tween nextTween) {
            this.nextTween = nextTween;
            if (nextTween != null) {
                nextTween.isWaitingForChain = true;
            }
            return this;
        }
        
        /**
         * Sets how many times this tween should repeat after finishing.
         * 
         * @param count number of repeats, or -1 to loop infinitely
         * @return This Tween instance for chaining
         */
        public Tween repeat(int count) { this.repeatCount = count; return this; }
        
        /**
         * Sets whether the tween should ping-pong (reverse direction) every other loop.
         * Only effective if repeat count is > 0 or infinite (-1).
         * 
         * @param yoyo true to enable the ping-pong effect
         * @return This Tween instance for chaining
         */
        public Tween yoyo(boolean yoyo) { this.yoyo = yoyo; return this; }
        
        /**
         * Assigns a callback to be executed exactly once when this tween completely finishes all its loops.
         * Note: If repeat(-1) is set, this callback will never fire.
         * 
         * @param r the runnable to execute upon completion
         * @return This Tween instance for chaining
         */
        public Tween onComplete(Runnable r) { this.onCompleteCallback = r; return this; }

        /**
         * Instantly stops this tween from animating and marks it as finished.
         * The tween will be removed from the engine on the next frame.
         */
        public void kill() {
            this.finished = true;
        }

        /**
         * Immediately reverses the direction of the active tween mid-flight.
         * It will smoothly travel back to its starting position from wherever it currently is.
         * 
         * @return This Tween instance for chaining
         */
        public Tween reverse() {
            this.reverseRequested = true;
            return this;
        }

        void reset() {
            startTime = -1;
            currentLoop = 0;
            finished = false;
            reverseRequested = false;
            manuallyReversed = false;
        }

        void update(long currentSimTime) {
            if (finished || isWaitingForChain) return;
            if (startTime == -1) startTime = currentSimTime + delayMillis;
            
            if (currentSimTime < startTime) return; // Still waiting for delay
            
            long elapsed = currentSimTime - startTime;
            
            if (reverseRequested) {
                reverseRequested = false;
                manuallyReversed = !manuallyReversed;
                elapsed = durationMillis - elapsed;
                if (elapsed < 0) elapsed = 0;
                if (elapsed > durationMillis) elapsed = durationMillis;
                startTime = currentSimTime - elapsed;
            }
            
            if (elapsed >= durationMillis) {
                if (repeatCount == -1 || currentLoop < repeatCount) {
                    currentLoop++;
                    startTime = currentSimTime; // loop restarts here
                    elapsed = 0;
                } else {
                    // Tween completely done
                    boolean isYoyoReversing = yoyo && currentLoop % 2 != 0;
                    boolean isReversing = isYoyoReversing ^ manuallyReversed;
                    onUpdate.accept(isReversing ? startValue : endValue);
                    finished = true;
                    if (onCompleteCallback != null) onCompleteCallback.run();
                    if (nextTween != null) {
                        nextTween.isWaitingForChain = false;
                        nextTween.reset();
                    }
                    return;
                }
            }
            
            float t = (float) elapsed / durationMillis;
            boolean isYoyoReversing = yoyo && currentLoop % 2 != 0;
            boolean isReversing = isYoyoReversing ^ manuallyReversed;
            if (isReversing) t = 1.0f - t; // Ping-pong effect
            
            float easedT = easing != null ? easing.apply(t) : t;
            float currentVal = startValue + (endValue - startValue) * easedT;
            onUpdate.accept(currentVal);
        }

        /**
         * Returns whether this tween has completely finished running.
         * 
         * @return true if finished, false otherwise
         */
        boolean isFinished() {
            return finished;
        }
    }

    /**
     * Built-in math utilities for smooth animation easing and interpolation.
     */
    public static class Easing {
        /** Linear interpolation between a and b by fraction t (0.0 to 1.0) */
        public static float lerp(float a, float b, float t) { return a + (b - a) * Math.max(0, Math.min(1, t)); }
        
        /** Quadratic Easing (power of 2) */
        public static float easeInQuad(float t) { return t * t; }
        public static float easeOutQuad(float t) { return t * (2 - t); }
        public static float easeInOutQuad(float t) { return t < .5f ? 2 * t * t : -1 + (4 - 2 * t) * t; }
        
        /** Cubic Easing (power of 3) */
        public static float easeInCubic(float t) { return t * t * t; }
        public static float easeOutCubic(float t) { return (float) (1 - Math.pow(1 - t, 3)); }
        public static float easeInOutCubic(float t) { return t < .5f ? 4 * t * t * t : (float) (1 - Math.pow(-2 * t + 2, 3) / 2); }
        
        /** Quartic Easing (power of 4) */
        public static float easeInQuart(float t) { return t * t * t * t; }
        public static float easeOutQuart(float t) { return (float) (1 - Math.pow(1 - t, 4)); }
        public static float easeInOutQuart(float t) { return t < .5f ? 8 * t * t * t * t : (float) (1 - Math.pow(-2 * t + 2, 4) / 2); }
        
        /** Quintic Easing (power of 5) */
        public static float easeInQuint(float t) { return t * t * t * t * t; }
        public static float easeOutQuint(float t) { return (float) (1 - Math.pow(1 - t, 5)); }
        public static float easeInOutQuint(float t) { return t < .5f ? 16 * t * t * t * t * t : (float) (1 - Math.pow(-2 * t + 2, 5) / 2); }
        
        /** Sine Easing */
        public static float easeInSine(float t) { return (float) (1 - Math.cos((t * Math.PI) / 2)); }
        public static float easeOutSine(float t) { return (float) Math.sin((t * Math.PI) / 2); }
        public static float easeInOutSine(float t) { return (float) -(Math.cos(Math.PI * t) - 1) / 2; }
        
        /** Exponential Easing */
        public static float easeInExpo(float t) { return t == 0 ? 0 : (float) Math.pow(2, 10 * t - 10); }
        public static float easeOutExpo(float t) { return t == 1 ? 1 : (float) (1 - Math.pow(2, -10 * t)); }
        public static float easeInOutExpo(float t) { return t == 0 ? 0 : t == 1 ? 1 : t < 0.5 ? (float) Math.pow(2, 20 * t - 10) / 2 : (float) (2 - Math.pow(2, -20 * t + 10)) / 2; }

        /** Circular Easing */
        public static float easeInCirc(float t) { return (float) (1 - Math.sqrt(1 - Math.pow(t, 2))); }
        public static float easeOutCirc(float t) { return (float) Math.sqrt(1 - Math.pow(t - 1, 2)); }
        public static float easeInOutCirc(float t) { return t < .5f ? (float) (1 - Math.sqrt(1 - Math.pow(2 * t, 2))) / 2 : (float) (Math.sqrt(1 - Math.pow(-2 * t + 2, 2)) + 1) / 2; }
        
        /** Back Easing */
        public static float easeInBack(float t) { float c1 = 1.70158f; float c3 = c1 + 1; return c3 * t * t * t - c1 * t * t; }
        public static float easeOutBack(float t) { float c1 = 1.70158f; float c3 = c1 + 1; return (float) (1 + c3 * Math.pow(t - 1, 3) + c1 * Math.pow(t - 1, 2)); }
        public static float easeInOutBack(float t) { float c1 = 1.70158f; float c2 = c1 * 1.525f; return t < .5f ? (float) (Math.pow(2 * t, 2) * ((c2 + 1) * 2 * t - c2)) / 2 : (float) (Math.pow(2 * t - 2, 2) * ((c2 + 1) * (t * 2 - 2) + c2) + 2) / 2; }

        /** Elastic Easing (Wobble) */
        public static float easeInElastic(float t) {
            float c4 = (float) (2 * Math.PI) / 3;
            return t == 0 ? 0 : t == 1 ? 1 : (float) (-Math.pow(2, 10 * t - 10) * Math.sin((t * 10 - 10.75) * c4));
        }
        public static float easeOutElastic(float t) {
            float c4 = (float) (2 * Math.PI) / 3;
            return t == 0 ? 0 : t == 1 ? 1 : (float) (Math.pow(2, -10 * t) * Math.sin((t * 10 - 0.75) * c4) + 1);
        }
        public static float easeInOutElastic(float t) {
            float c5 = (float) (2 * Math.PI) / 4.5f;
            return t == 0 ? 0 : t == 1 ? 1 : t < 0.5 ? (float) -(Math.pow(2, 20 * t - 10) * Math.sin((20 * t - 11.125) * c5)) / 2 : (float) (Math.pow(2, -20 * t + 10) * Math.sin((20 * t - 11.125) * c5)) / 2 + 1;
        }

        /** Bounce Easing */
        public static float easeInBounce(float t) { return 1 - easeOutBounce(1 - t); }
        public static float easeOutBounce(float t) {
            float n1 = 7.5625f, d1 = 2.75f;
            if (t < 1 / d1) { return n1 * t * t; }
            else if (t < 2 / d1) { return n1 * (t -= 1.5f / d1) * t + 0.75f; }
            else if (t < 2.5 / d1) { return n1 * (t -= 2.25f / d1) * t + 0.9375f; }
            else { return n1 * (t -= 2.625f / d1) * t + 0.984375f; }
        }
        public static float easeInOutBounce(float t) { return t < 0.5f ? (1 - easeOutBounce(1 - 2 * t)) / 2 : (1 + easeOutBounce(2 * t - 1)) / 2; }
    }
}
