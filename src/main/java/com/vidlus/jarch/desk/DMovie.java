package com.vidlus.jarch.desk;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A specialized DCanvas that acts as a unified container for animated scenes.
 * It manages its own internal DAnime engine and seamlessly hooks registered
 * DVector actors into it, allowing you to control the entire scene's playback
 * (play, pause, seek) with a single method call.
 */
public class DMovie extends DCanvas {

    private final DAnime engine;
    private final List<DAnimator> actors = new ArrayList<>();

    private final Map<DExcited, Map<DEventType, List<Consumer<DEvent>>>> eventRegistry = new HashMap<>();
    private DExcited hoveredActor = null;
    private DExcited focusedActor = null;

    /**
     * Creates a new DMovie with an internal DAnime engine bound to this canvas.
     */
    public DMovie() {
        super();
        this.engine = new DAnime(this);
        this.setFocusable(true);
        setupEventListeners();
    }

    private void setupEventListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseEvent(e, DEventType.MOUSE_CLICK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                handleMouseEvent(e, DEventType.MOUSE_PRESS);
                requestFocusInWindow();
                focusedActor = getActorAt(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseEvent(e, DEventType.MOUSE_RELEASE);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseEvent(e, DEventType.MOUSE_MOVE);
                DExcited actor = getActorAt(e.getX(), e.getY());
                if (actor != hoveredActor) {
                    if (hoveredActor != null) {
                        fireEvent(hoveredActor, DEventType.MOUSE_EXIT, e);
                    }
                    hoveredActor = actor;
                    if (hoveredActor != null) {
                        fireEvent(hoveredActor, DEventType.MOUSE_ENTER, e);
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseEvent(e, DEventType.MOUSE_DRAG);
            }
        };
        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e, DEventType.KEY_PRESS);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyEvent(e, DEventType.KEY_RELEASE);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyEvent(e, DEventType.KEY_TYPE);
            }
        });
    }

    private void handleMouseEvent(MouseEvent e, DEventType type) {
        DExcited actor = getActorAt(e.getX(), e.getY());
        if (actor != null) {
            fireEvent(actor, type, e);
        }
    }

    private void handleKeyEvent(KeyEvent e, DEventType type) {
        if (focusedActor != null) {
            fireEvent(focusedActor, type, e);
        }
    }

    private void fireEvent(DExcited actor, DEventType type, java.awt.event.InputEvent rawEvent) {
        Map<DEventType, List<Consumer<DEvent>>> actorEvents = eventRegistry.get(actor);
        if (actorEvents != null) {
            List<Consumer<DEvent>> actions = actorEvents.get(type);
            if (actions != null) {
                DEvent dEvent = new DEvent(actor, type, rawEvent);
                for (Consumer<DEvent> action : actions) {
                    action.accept(dEvent);
                }
            }
        }
    }

    /**
     * Registers an event action for a specific actor.
     * 
     * @param actor  the actor to bind the event to
     * @param type   the type of event
     * @param action the action to execute when the event occurs
     * @return This DMovie instance for fluent chaining
     */
    public DMovie addEvent(DExcited actor, DEventType type, Consumer<DEvent> action) {
        if (actor != null && type != null && action != null) {
            eventRegistry.computeIfAbsent(actor, k -> new HashMap<>())
                    .computeIfAbsent(type, k -> new ArrayList<>())
                    .add(action);
        }
        return this;
    }

    /**
     * Convenience method to register a mouse click event for an actor.
     * 
     * @param actor  the actor to bind the event to
     * @param action the action to execute
     * @return This DMovie instance for fluent chaining
     */
    public DMovie onClick(DExcited actor, Consumer<DEvent> action) {
        return addEvent(actor, DEventType.MOUSE_CLICK, action);
    }

    /**
     * Adds an actor (DVector or DSprite) to the movie. The actor is automatically
     * drawn on this canvas,
     * and a new DAnimator tied to the movie's master engine is returned.
     * 
     * @param actor the DAnimatable to add to the scene
     * @return a DAnimator bound to the master engine for fluent setup
     */
    public DAnimator addActor(DExcited actor) {
        if (actor != null) {
            this.draw(actor);
            DAnimator animator = new DAnimator(actor, engine);
            actors.add(animator);
            return animator;
        }
        return null;
    }

    /**
     * Starts playing the movie at the specified frames per second.
     * 
     * @param fps the frames per second
     * @return This DMovie instance
     */
    public DMovie play(int fps) {
        engine.start(fps);
        return this;
    }

    /**
     * Pauses the movie.
     * 
     * @return This DMovie instance
     */
    public DMovie pause() {
        engine.pause();
        return this;
    }

    /**
     * Resumes the movie from where it was paused.
     * 
     * @return This DMovie instance
     */
    public DMovie resume() {
        engine.resume();
        return this;
    }

    /**
     * Completely stops the movie and halts the engine.
     * 
     * @return This DMovie instance
     */
    public DMovie stop() {
        engine.stop();
        return this;
    }

    /**
     * Restarts the movie from the beginning, resetting all actors to their initial
     * state.
     * 
     * @return This DMovie instance
     */
    public DMovie restart() {
        engine.restart();
        return this;
    }

    /**
     * Jumps the movie to a specific time in simulated milliseconds.
     * 
     * @param millis the time to seek to
     * @return This DMovie instance
     */
    public DMovie seek(long millis) {
        engine.seek(millis);
        return this;
    }

    /**
     * Returns the master animation engine controlling this movie.
     * Use this to configure advanced features like time scaling.
     * 
     * @return the DAnime engine
     */
    public DAnime getEngine() {
        return engine;
    }
}
