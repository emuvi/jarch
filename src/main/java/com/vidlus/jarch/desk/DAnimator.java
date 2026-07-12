package com.vidlus.jarch.desk;

import java.awt.Component;

/**
 * A helper class that links a DVector with a DAnime engine to easily apply
 * continuous
 * or tweened transformations (like spinning, moving, or scaling).
 */
public class DAnimator {

    private final DExcited target;
    private final DAnime anime;

    private DAnime.Tween lastTween = null;
    private boolean chainNext = false;
    private final java.util.List<DAnime.Tween> activeTweens = new java.util.ArrayList<>();

    /**
     * Creates a new DAnimator that manages its own DAnime engine.
     * 
     * @param target        the vector to animate
     * @param repaintTarget the UI component to repaint automatically (can be null)
     */
    public DAnimator(DExcited target, Component repaintTarget) {
        this.target = target;
        this.anime = new DAnime(repaintTarget);
    }

    /**
     * Creates a new DAnimator that hooks into an existing DAnime engine.
     * 
     * @param target        the vector to animate
     * @param existingAnime the existing animation engine
     */
    public DAnimator(DExcited target, DAnime existingAnime) {
        this.target = target;
        this.anime = existingAnime;
    }

    /**
     * Flags the animator so that the VERY NEXT tween created will automatically
     * chain
     * off of the previously created tween, instead of playing instantly.
     * 
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator chainNext() {
        this.chainNext = true;
        return this;
    }

    private DAnime.Tween registerTween(DAnime.Tween t) {
        if (chainNext && lastTween != null) {
            lastTween.chain(t);
            chainNext = false;
        }
        lastTween = t;

        // Prune finished tweens to prevent memory leaks
        activeTweens.removeIf(tween -> tween.finished);
        activeTweens.add(t);

        return t;
    }

    /**
     * Instantly reverses the direction of the last created tween.
     * If the tween is mid-flight, it will smoothly animate backward to its starting
     * value.
     * 
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator reverseLast() {
        if (lastTween != null) {
            lastTween.reverse();
        }
        return this;
    }

    /**
     * Instantly kills all active tweens owned by this specific animator.
     * This freezes the vector in place without affecting other vectors on the same
     * engine.
     * 
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator killTweens() {
        for (DAnime.Tween t : activeTweens) {
            t.kill();
        }
        activeTweens.clear();
        return this;
    }

    // ==========================================
    // --- Continuous Animations
    // ==========================================

    /**
     * Continuously rotates the vector by the specified degrees per second around
     * its origin (0,0).
     * 
     * @param degreesPerSecond the number of degrees to rotate every second
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator spin(double degreesPerSecond) {
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            target.rotate(degreesPerSecond * deltaTimeSec);
        });
        return this;
    }

    /**
     * Continuously rotates the vector by the specified degrees per second around an
     * anchor point.
     * 
     * @param degreesPerSecond the number of degrees to rotate every second
     * @param anchorX          the X coordinate of the center of rotation
     * @param anchorY          the Y coordinate of the center of rotation
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator spin(double degreesPerSecond, double anchorX, double anchorY) {
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            target.rotate(degreesPerSecond * deltaTimeSec, anchorX, anchorY);
        });
        return this;
    }

    /**
     * Continuously moves the vector by (vx, vy) pixels per second.
     * 
     * @param vx the velocity along the X axis in pixels per second
     * @param vy the velocity along the Y axis in pixels per second
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator slide(double vx, double vy) {
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            target.translate(vx * deltaTimeSec, vy * deltaTimeSec);
        });
        return this;
    }

    /**
     * Continuously shakes the vector by a random pixel intensity.
     * Guaranteed to be drift-free (it always snaps back relative to its origin).
     * 
     * @param intensity the maximum pixel offset in any direction
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator shake(double intensity) {
        double[] lastOffset = { 0, 0 };
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            // Revert previous offset
            target.translate(-lastOffset[0], -lastOffset[1]);
            // Apply new random offset
            lastOffset[0] = (Math.random() - 0.5) * intensity * 2;
            lastOffset[1] = (Math.random() - 0.5) * intensity * 2;
            target.translate(lastOffset[0], lastOffset[1]);
        });
        return this;
    }

    /**
     * Continuously scales the vector by a specific multiplier every second.
     * For example, scalePerSec = 2.0 will double the size every second.
     * 
     * @param scaleXPerSec the multiplier for the X axis per second
     * @param scaleYPerSec the multiplier for the Y axis per second
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator scale(double scaleXPerSec, double scaleYPerSec) {
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            // Formula for continuous compounding scale: scaleFactor ^ deltaTime
            double sx = Math.pow(scaleXPerSec, deltaTimeSec);
            double sy = Math.pow(scaleYPerSec, deltaTimeSec);
            target.scale(sx, sy);
        });
        return this;
    }

    /**
     * Continuously alters the vector's opacity (alpha) over time.
     * 
     * @param alphaPerSec the amount of alpha to add/subtract per second (e.g., -0.5 to fade out over 2 seconds)
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator fade(double alphaPerSec) {
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            float currentAlpha = target.getAlpha();
            float newAlpha = (float) (currentAlpha + alphaPerSec * deltaTimeSec);
            target.setAlpha(Math.max(0f, Math.min(1f, newAlpha)));
        });
        return this;
    }

    /**
     * Continuously shears (skews) the vector by the specified amount per second.
     * 
     * @param shearXPerSec the shear multiplier for the X axis per second
     * @param shearYPerSec the shear multiplier for the Y axis per second
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator shear(double shearXPerSec, double shearYPerSec) {
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            target.shear(shearXPerSec * deltaTimeSec, shearYPerSec * deltaTimeSec);
        });
        return this;
    }

    // ==========================================
    // --- Tweened Animations
    // ==========================================

    /**
     * Smoothly rotates the vector by a total amount of degrees over a duration.
     * Uses relative delta rotation, allowing seamless yoyo and repeat looping.
     * 
     * @param totalDegrees   The total amount of degrees to rotate (e.g., 360 for a
     *                       full spin)
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function (null for linear)
     * @return The created Tween object so you can chain .delay(), .repeat(), etc.
     */
    public DAnime.Tween tweenRotate(double totalDegrees, long durationMillis,
            java.util.function.Function<Float, Float> easing) {
        double[] lastVal = { 0 };
        return registerTween(anime.playTween(0f, (float) totalDegrees, durationMillis, easing, currentVal -> {
            double delta = currentVal - lastVal[0];
            target.rotate(delta);
            lastVal[0] = currentVal;
        }).onComplete(() -> lastVal[0] = 0));
    }

    /**
     * Smoothly translates the vector by a total distance over a duration.
     * 
     * @param totalDx        The total X distance to move
     * @param totalDy        The total Y distance to move
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenTranslate(double totalDx, double totalDy, long durationMillis,
            java.util.function.Function<Float, Float> easing) {
        double[] lastX = { 0 };
        double[] lastY = { 0 };

        return registerTween(anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            double curX = totalDx * fraction;
            double curY = totalDy * fraction;
            double dx = curX - lastX[0];
            double dy = curY - lastY[0];

            target.translate(dx, dy);

            lastX[0] = curX;
            lastY[0] = curY;
        }).onComplete(() -> {
            lastX[0] = 0;
            lastY[0] = 0;
        }));
    }

    /**
     * Smoothly translates the vector so its center point moves exactly to the absolute target coordinates.
     * The movement is calculated dynamically when the tween actually starts playing.
     * 
     * @param targetX        The absolute X coordinate to move the center to
     * @param targetY        The absolute Y coordinate to move the center to
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenMoveTo(double targetX, double targetY, long durationMillis, java.util.function.Function<Float, Float> easing) {
        double[] totalDx = { 0 };
        double[] totalDy = { 0 };
        double[] lastX = { 0 };
        double[] lastY = { 0 };
        boolean[] initialized = { false };

        return registerTween(anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            if (!initialized[0]) {
                java.awt.geom.Rectangle2D bounds = target.getBounds2D();
                totalDx[0] = targetX - bounds.getCenterX();
                totalDy[0] = targetY - bounds.getCenterY();
                initialized[0] = true;
            }

            double curX = totalDx[0] * fraction;
            double curY = totalDy[0] * fraction;
            double dx = curX - lastX[0];
            double dy = curY - lastY[0];

            target.translate(dx, dy);

            lastX[0] = curX;
            lastY[0] = curY;
        }).onComplete(() -> {
            lastX[0] = 0;
            lastY[0] = 0;
            initialized[0] = false;
        }));
    }

    /**
     * Navigates the vector through a list of X and Y waypoints in a single smooth
     * sequence.
     * Uses relative offsets, so [0, 100, 50] means start at 0, move +100 relative,
     * then move to +50 relative.
     * 
     * @param durationMillis The duration of the entire sequence
     * @param easing         The mathematical easing function applied to the overall
     *                       sequence progress
     * @param xWaypoints     An array of X coordinate waypoints
     * @param yWaypoints     An array of Y coordinate waypoints
     * @return The created Tween object
     */
    public DAnime.Tween keyframeTranslate(long durationMillis, java.util.function.Function<Float, Float> easing,
            float[] xWaypoints, float[] yWaypoints) {
        if (xWaypoints == null || yWaypoints == null || xWaypoints.length != yWaypoints.length)
            return null;

        double[] lastX = { 0 };
        double[] lastY = { 0 };

        return registerTween(anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            int len = xWaypoints.length;
            if (len == 0)
                return;
            if (len == 1) {
                target.translate(xWaypoints[0] - lastX[0], yWaypoints[0] - lastY[0]);
                lastX[0] = xWaypoints[0];
                lastY[0] = yWaypoints[0];
                return;
            }

            // Map fraction (0.0 to 1.0) to an index and a local progress
            float scaled = fraction * (len - 1);
            int floorIndex = (int) Math.floor(scaled);

            // Bounds logic for elastic/bounce curves (extrapolation)
            if (floorIndex < 0)
                floorIndex = 0;
            if (floorIndex >= len - 1)
                floorIndex = len - 2;

            float localProgress = scaled - floorIndex;

            double targetX = xWaypoints[floorIndex]
                    + (xWaypoints[floorIndex + 1] - xWaypoints[floorIndex]) * localProgress;
            double targetY = yWaypoints[floorIndex]
                    + (yWaypoints[floorIndex + 1] - yWaypoints[floorIndex]) * localProgress;

            target.translate(targetX - lastX[0], targetY - lastY[0]);

            lastX[0] = targetX;
            lastY[0] = targetY;
        }).onComplete(() -> {
            lastX[0] = 0;
            lastY[0] = 0;
        }));
    }

    /**
     * Smoothly scales the vector. Note: This applies a compounding relative scale.
     * 
     * @param targetScaleX   The total X scaling factor (e.g., 2.0 to double the
     *                       size)
     * @param targetScaleY   The total Y scaling factor
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenScale(double targetScaleX, double targetScaleY, long durationMillis,
            java.util.function.Function<Float, Float> easing) {
        double[] lastScaleX = { 1.0 };
        double[] lastScaleY = { 1.0 };

        return registerTween(anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            double curSX = 1.0 + (targetScaleX - 1.0) * fraction;
            double curSY = 1.0 + (targetScaleY - 1.0) * fraction;

            double deltaSX = curSX / lastScaleX[0];
            double deltaSY = curSY / lastScaleY[0];

            target.scale(deltaSX, deltaSY);

            lastScaleX[0] = curSX;
            lastScaleY[0] = curSY;
        }).onComplete(() -> {
            lastScaleX[0] = 1.0;
            lastScaleY[0] = 1.0;
        }));
    }

    /**
     * Smoothly shears the vector. Note: This applies a compounding relative shear.
     * 
     * @param targetShearX   The total X shearing factor
     * @param targetShearY   The total Y shearing factor
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenShear(double targetShearX, double targetShearY, long durationMillis, java.util.function.Function<Float, Float> easing) {
        double[] lastShX = { 0.0 };
        double[] lastShY = { 0.0 };

        return registerTween(anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            double curSX = targetShearX * fraction;
            double curSY = targetShearY * fraction;

            double deltaSX = curSX - lastShX[0];
            double deltaSY = curSY - lastShY[0];

            target.shear(deltaSX, deltaSY);

            lastShX[0] = curSX;
            lastShY[0] = curSY;
        }).onComplete(() -> {
            lastShX[0] = 0.0;
            lastShY[0] = 0.0;
        }));
    }

    /**
     * Smoothly interpolates the vector's fill color over a duration.
     * 
     * @param targetColor    The end color to transition to
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenFill(java.awt.Color targetColor, long durationMillis,
            java.util.function.Function<Float, Float> easing) {
        java.awt.Color start = target.getFill();
        if (start == null)
            start = new java.awt.Color(targetColor.getRed(), targetColor.getGreen(), targetColor.getBlue(), 0);

        return registerTween(anime.tweenColor(start, targetColor, durationMillis, easing, target::setFill));
    }

    /**
     * Smoothly interpolates the vector's stroke color over a duration.
     * 
     * @param targetColor    The end color to transition to
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenStroke(java.awt.Color targetColor, long durationMillis,
            java.util.function.Function<Float, Float> easing) {
        java.awt.Color start = target.getStroke();
        if (start == null)
            start = new java.awt.Color(targetColor.getRed(), targetColor.getGreen(), targetColor.getBlue(), 0);

        return registerTween(anime.tweenColor(start, targetColor, durationMillis, easing, target::setStroke));
    }

    /**
     * Smoothly animates the vector's stroke (border) width.
     * The stroke width is evaluated dynamically when the tween starts.
     * 
     * @param targetWidth    The end stroke width
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenStrokeWidth(float targetWidth, long durationMillis, java.util.function.Function<Float, Float> easing) {
        float[] startWidth = { 0 };
        boolean[] initialized = { false };
        
        return registerTween(anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            if (!initialized[0]) {
                startWidth[0] = target.getStrokeWidth();
                initialized[0] = true;
            }
            float curWidth = startWidth[0] + (targetWidth - startWidth[0]) * fraction;
            target.setStrokeWidth(curWidth);
        }).onComplete(() -> initialized[0] = false));
    }

    /**
     * Smoothly animates the vector's opacity (alpha) to a specific target.
     * The starting alpha is evaluated dynamically when the tween starts.
     * 
     * @param targetAlpha    The end alpha value (0.0 to 1.0)
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween tweenAlpha(float targetAlpha, long durationMillis, java.util.function.Function<Float, Float> easing) {
        float[] startAlpha = { 0 };
        boolean[] initialized = { false };
        
        return registerTween(anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            if (!initialized[0]) {
                startAlpha[0] = target.getAlpha();
                initialized[0] = true;
            }
            float curAlpha = startAlpha[0] + (targetAlpha - startAlpha[0]) * fraction;
            target.setAlpha(Math.max(0f, Math.min(1f, curAlpha)));
        }).onComplete(() -> initialized[0] = false));
    }

    /**
     * Smoothly fades the vector in to full opacity (alpha = 1.0).
     * 
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween fadeIn(long durationMillis, java.util.function.Function<Float, Float> easing) {
        return tweenAlpha(1.0f, durationMillis, easing);
    }

    /**
     * Smoothly fades the vector out to full transparency (alpha = 0.0).
     * 
     * @param durationMillis The duration of the tween
     * @param easing         The mathematical easing function
     * @return The created Tween object
     */
    public DAnime.Tween fadeOut(long durationMillis, java.util.function.Function<Float, Float> easing) {
        return tweenAlpha(0.0f, durationMillis, easing);
    }

    /**
     * Creates a continuous pulsing effect by smoothly scaling the vector up and
     * back down infinitely.
     * 
     * @param maxScale       The peak scale multiplier (e.g. 1.2 to grow 20%)
     * @param durationMillis The duration of one full pulse cycle (grow and shrink)
     * @return The created Tween object
     */
    public DAnime.Tween pulse(double maxScale, long durationMillis) {
        return tweenScale(maxScale, maxScale, durationMillis / 2, DAnime.Easing::easeInOutQuad)
                .yoyo(true)
                .repeat(-1);
    }

    /**
     * Creates a pendulum swinging effect, rotating the vector back and forth
     * infinitely.
     * 
     * @param peakDegrees    The maximum angle it reaches on one side before
     *                       swinging back
     * @param durationMillis The duration of one full swing cycle (there and back)
     * @return The created Tween object
     */
    public DAnime.Tween swing(double peakDegrees, long durationMillis) {
        return tweenRotate(peakDegrees, durationMillis / 2, DAnime.Easing::easeInOutSine)
                .yoyo(true)
                .repeat(-1);
    }

    /**
     * Creates a bouncing effect, translating the vector up and back down
     * infinitely.
     * 
     * @param peakDistanceY  The peak Y offset distance (negative is up)
     * @param durationMillis The duration of one full bounce cycle
     * @return The created Tween object
     */
    public DAnime.Tween bounce(double peakDistanceY, long durationMillis) {
        return tweenTranslate(0, peakDistanceY, durationMillis / 2, DAnime.Easing::easeOutQuad)
                .yoyo(true)
                .repeat(-1);
    }

    /**
     * Simulates jelly-like physics by squashing down and stretching out, then
     * returning to normal.
     * This is the foundational principle of Disney-style animation (Squash and
     * Stretch).
     * 
     * @param stretchScaleX  the scale multiplier for the X axis (e.g., 1.5 to
     *                       widen)
     * @param squashScaleY   the scale multiplier for the Y axis (e.g., 0.5 to
     *                       flatten)
     * @param durationMillis the duration of one full cycle (squash and return)
     * @return The created Tween object
     */
    public DAnime.Tween squashAndStretch(double stretchScaleX, double squashScaleY, long durationMillis) {
        return tweenScale(stretchScaleX, squashScaleY, durationMillis / 2, DAnime.Easing::easeOutQuad)
                .yoyo(true)
                .repeat(-1);
    }

    /**
     * Continuously rotates the vector around an external center point,
     * mathematically forcing it
     * into a circular orbit at a specific radius.
     * 
     * @param centerX       the X coordinate of the gravitational center
     * @param centerY       the Y coordinate of the gravitational center
     * @param radius        the exact distance from the center to orbit at
     * @param degreesPerSec the speed of the orbit in degrees per second
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator orbit(double centerX, double centerY, double radius, double degreesPerSec) {
        double[] angle = { 0.0 };
        boolean[] firstFrame = { true };
        double[] lastPos = { 0, 0 };

        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            angle[0] += degreesPerSec * deltaTimeSec;

            double rad = Math.toRadians(angle[0]);
            double targetX = centerX + Math.cos(rad) * radius;
            double targetY = centerY + Math.sin(rad) * radius;

            if (firstFrame[0]) {
                // Snap to orbit track on the first frame
                java.awt.geom.Rectangle2D bounds = target.getShape().getBounds2D();
                double currentX = bounds.getCenterX();
                double currentY = bounds.getCenterY();
                target.translate(targetX - currentX, targetY - currentY);
                firstFrame[0] = false;
            } else {
                // Apply relative translation for subsequent frames
                target.translate(targetX - lastPos[0], targetY - lastPos[1]);
            }

            lastPos[0] = targetX;
            lastPos[1] = targetY;
        });
        return this;
    }

    /**
     * Continuously translates the vector forward on the X-axis while oscillating on
     * the Y-axis.
     * 
     * @param speedX     the forward movement speed in pixels per second
     * @param amplitudeY the height of the wave oscillation
     * @param frequency  the speed of the oscillation (waves per second)
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator wave(double speedX, double amplitudeY, double frequency) {
        double[] time = { 0.0 };
        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            time[0] += deltaTimeSec;

            double dx = speedX * deltaTimeSec;

            // Current Y based on sine wave
            double currentY = Math.sin(time[0] * Math.PI * 2 * frequency) * amplitudeY;
            // Previous Y based on sine wave
            double prevY = Math.sin((time[0] - deltaTimeSec) * Math.PI * 2 * frequency) * amplitudeY;

            double dy = currentY - prevY;

            target.translate(dx, dy);
        });
        return this;
    }

    /**
     * Creates a flashing/blinking effect by rapidly toggling the vector's scale.
     * Note: Since vectors do not have native opacity, this uses scale to simulate
     * invisibility.
     * 
     * @param blinksPerSecond how many times it flashes per second
     * @param durationMillis  the total duration of the flicker effect
     * @return The created Tween object
     */
    public DAnime.Tween flicker(int blinksPerSecond, long durationMillis) {
        long cycleTime = 1000 / Math.max(1, blinksPerSecond);
        return tweenScale(0.001, 0.001, cycleTime / 2, null)
                .yoyo(true)
                .repeat((int) (durationMillis / cycleTime));
    }

    /**
     * Continuously acts as a homing missile, steering the vector to chase another
     * moving DVector.
     * 
     * @param prey      the target DVector to chase
     * @param speed     the movement speed in pixels per second
     * @param turnSpeed the maximum rotation speed in degrees per second
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator chase(DVector prey, double speed, double turnSpeed) {
        double[] currentAngle = { 0.0 };

        anime.onTick((anim, frameCount, elapsedSimulatedMillis, deltaTimeSec) -> {
            if (prey == null)
                return;
            java.awt.geom.Rectangle2D preyBounds = prey.getShape().getBounds2D();
            double targetX = preyBounds.getCenterX();
            double targetY = preyBounds.getCenterY();

            java.awt.geom.Rectangle2D bounds = target.getShape().getBounds2D();
            double cx = bounds.getCenterX();
            double cy = bounds.getCenterY();

            double dx = targetX - cx;
            double dy = targetY - cy;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist > 1.0) {
                // Calculate desired angle
                double desiredAngle = Math.toDegrees(Math.atan2(dy, dx));

                // Calculate difference and handle 360 wrap
                double angleDiff = desiredAngle - currentAngle[0];
                while (angleDiff <= -180)
                    angleDiff += 360;
                while (angleDiff > 180)
                    angleDiff -= 360;

                // Limit turn by turnSpeed
                double maxTurn = turnSpeed * deltaTimeSec;
                double actualTurn = Math.max(-maxTurn, Math.min(maxTurn, angleDiff));

                target.rotate(actualTurn);
                currentAngle[0] += actualTurn;

                // Move forward along current facing angle
                double rad = Math.toRadians(currentAngle[0]);
                double moveDist = Math.min(speed * deltaTimeSec, dist);
                target.translate(Math.cos(rad) * moveDist, Math.sin(rad) * moveDist);
            }
        });
        return this;
    }

    /**
     * Smoothly translates the vector along the geometric outline of another DVector
     * path.
     * Optionally auto-rotates the vector to perfectly face the direction of the
     * path!
     * 
     * @param pathVector     the DVector path to follow
     * @param durationMillis the time it takes to travel the full path
     * @param easing         the mathematical easing function
     * @param autoRotate     if true, the vector will automatically rotate to follow
     *                       the curves
     * @return The created Tween object
     */
    public DAnime.Tween tweenFollowPath(DVector pathVector, long durationMillis,
            java.util.function.Function<Float, Float> easing, boolean autoRotate) {
        if (pathVector == null)
            return null;

        java.awt.geom.FlatteningPathIterator iter = new java.awt.geom.FlatteningPathIterator(
                pathVector.getShape().getPathIterator(null), 1.0);
        java.util.List<java.awt.geom.Point2D.Double> points = new java.util.ArrayList<>();
        double[] coords = new double[6];

        while (!iter.isDone()) {
            int type = iter.currentSegment(coords);
            if (type == java.awt.geom.PathIterator.SEG_MOVETO || type == java.awt.geom.PathIterator.SEG_LINETO) {
                points.add(new java.awt.geom.Point2D.Double(coords[0], coords[1]));
            } else if (type == java.awt.geom.PathIterator.SEG_CLOSE) {
                if (!points.isEmpty()) {
                    points.add(new java.awt.geom.Point2D.Double(points.get(0).x, points.get(0).y));
                }
            }
            iter.next();
        }

        if (points.size() < 2)
            return null;

        double[] cumulativeDistances = new double[points.size()];
        cumulativeDistances[0] = 0;
        double totalDistance = 0;

        for (int i = 1; i < points.size(); i++) {
            java.awt.geom.Point2D.Double p1 = points.get(i - 1);
            java.awt.geom.Point2D.Double p2 = points.get(i);
            totalDistance += p1.distance(p2);
            cumulativeDistances[i] = totalDistance;
        }

        final double finalTotalDist = totalDistance;
        double[] lastPos = { points.get(0).x, points.get(0).y };
        double[] lastAngle = { 0.0 };

        return anime.playTween(0f, 1f, durationMillis, easing, fraction -> {
            double targetDist = finalTotalDist * fraction;

            int segmentIndex = 1;
            for (int i = 1; i < points.size(); i++) {
                if (cumulativeDistances[i] >= targetDist || i == points.size() - 1) {
                    segmentIndex = i;
                    break;
                }
            }

            double segmentStartDist = cumulativeDistances[segmentIndex - 1];
            double segmentEndDist = cumulativeDistances[segmentIndex];
            double segmentLength = segmentEndDist - segmentStartDist;

            double t = (segmentLength == 0) ? 1.0 : (targetDist - segmentStartDist) / segmentLength;

            java.awt.geom.Point2D.Double p1 = points.get(segmentIndex - 1);
            java.awt.geom.Point2D.Double p2 = points.get(segmentIndex);

            double currentX = p1.x + (p2.x - p1.x) * t;
            double currentY = p1.y + (p2.y - p1.y) * t;

            target.translate(currentX - lastPos[0], currentY - lastPos[1]);
            lastPos[0] = currentX;
            lastPos[1] = currentY;

            if (autoRotate && segmentLength > 0) {
                double currentAngle = Math.toDegrees(Math.atan2(p2.y - p1.y, p2.x - p1.x));
                // Handle 360 wrap-around for smooth rotation
                double deltaAngle = currentAngle - lastAngle[0];
                if (deltaAngle > 180)
                    deltaAngle -= 360;
                else if (deltaAngle < -180)
                    deltaAngle += 360;

                target.rotate(deltaAngle);
                lastAngle[0] += deltaAngle;
            }
        });
    }

    // ==========================================
    // --- Lifecycle
    // ==========================================

    /**
     * Starts the underlying animation engine.
     * 
     * @param fps the target frames per second for the animation loop
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator start(int fps) {
        anime.start(fps);
        return this;
    }

    /**
     * Stops the underlying animation engine.
     * 
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator stop() {
        anime.stop();
        return this;
    }

    /**
     * Pauses the underlying animation engine.
     * 
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator pause() {
        anime.pause();
        return this;
    }

    /**
     * Resumes the underlying animation engine.
     * 
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator resume() {
        anime.resume();
        return this;
    }

    /**
     * Halts and removes all currently running tweened animations (pulse, swing,
     * etc).
     * 
     * @return This DAnimator instance for fluent chaining
     */
    public DAnimator clearTweens() {
        anime.clearTweens();
        return this;
    }

    /**
     * Returns the underlying DAnime engine for advanced configuration.
     * 
     * @return the underlying DAnime engine instance
     */
    public DAnime getAnime() {
        return anime;
    }

    /**
     * Returns the target DAnimatable being animated.
     * 
     * @return the DAnimatable instance
     */
    public DExcited getTarget() {
        return target;
    }
}
