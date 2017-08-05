package pmauldin.shift.entities

import com.artemis.BaseSystem
import com.artemis.SystemInvocationStrategy
import com.artemis.utils.BitVector
import com.badlogic.gdx.utils.Array
import groovy.transform.CompileStatic

import java.util.concurrent.TimeUnit


/**
 * https://github.com/TomGrill/logic-render-game-loop
 *
 * Implements a game loop based on this excellent blog post:
 * http://gafferongames.com/game-physics/fix-your-timestep/
 *
 * To avoid floating point rounding errors we only use fixed point numbers for calculations.
 */
@CompileStatic
class GameLoopInvoker extends SystemInvocationStrategy {
    private final Array<BaseSystem> logicMarkedSystems
    private final Array<BaseSystem> otherSystems

    private long nanosPerLogicTick // ~ dt
    private long currentTime = System.nanoTime()

    private long accumulator

    private boolean systemsSorted = false

    private final BitVector disabledLogicMarkedSystems = new BitVector()
    private final BitVector disabledOtherSystems = new BitVector()

    @Override
    protected void initialize() {
        /** Sort Systems here in case {@link #setEnabled(BaseSystem, boolean)} is called prior to first {@link #process()} */
        if (!systemsSorted) {
            sortSystems()
        }
    }

    GameLoopInvoker() {
        this(40)
    }

    GameLoopInvoker(int millisPerLogicTick) {
        this.nanosPerLogicTick = TimeUnit.MILLISECONDS.toNanos(millisPerLogicTick)
        logicMarkedSystems = new Array<BaseSystem>()
        otherSystems = new Array<BaseSystem>()
    }

    private void sortSystems() {
        if (!systemsSorted) {
            def systemsData = systems.getData()
            def numSystems = systems.size()
            for (int i = 0; i < numSystems; i++) {
                def system = (BaseSystem) systemsData[i]
                if (system instanceof LogicSystem) {
                    logicMarkedSystems.add(system as BaseSystem)
                } else {
                    otherSystems.add(system)
                }
            }
            systemsSorted = true
        }
    }

    @Override
    protected void process() {
        if (!systemsSorted) {
            sortSystems()
        }

        def newTime = System.nanoTime()
        def frameTime = newTime - currentTime

        if (frameTime > 250000000) {
            frameTime = 250000000    // Note: Avoid spiral of death
        }

        currentTime = newTime
        accumulator += frameTime

        // required since artemis-odb-2.0.0-RC4, updateEntityStates() must be called
        // before processing the first system - in case any entities are
        // added outside the main process loop
        updateEntityStates()

        /**
         * Uncomment this line if you use the world's delta within your systems.
         * I recommend to use a fixed value for your logic delta like millisPerLogicTick or nanosPerLogicTick
         */
//		world.setDelta(nanosPerLogicTick * 0.000000001f as float)

        while (accumulator >= nanosPerLogicTick) {
            /** Process all entity systems inheriting from {@link LogicSystem} */
            for (int i = 0; i < logicMarkedSystems.size; i++) {
                /**
                 * Make sure your systems keep the current state before calculating the new state
                 * else you cannot interpolate later on when rendering
                 */
                if (disabledLogicMarkedSystems.get(i)) {
                    continue
                }
                logicMarkedSystems.get(i).process()
                updateEntityStates()
            }

            accumulator -= nanosPerLogicTick
        }

        /**
         * Uncomment this line if you use the world's delta within your systems.
         */
//		world.setDelta(frameTime * 0.000000001f as float)

        /**
         * When you divide accumulator by nanosPerLogicTick you get your alpha.
         * You can store the alpha value in a GameStateComponent f.e.
         */
//		float alpha = (float) accumulator / nanosPerLogicTick


        /** Process all NON {@link LogicSystem} inheriting entity systems */
        for (int i = 0; i < otherSystems.size; i++) {
            /**
             * Use the kept state from the logic above and interpolate with the current state within your render systems.
             */
            if (disabledOtherSystems.get(i)) {
                continue
            }
            otherSystems.get(i).process()
            updateEntityStates()
        }
    }

    @Override
    boolean isEnabled(BaseSystem target) {
        def systems = (target instanceof LogicSystem) ? logicMarkedSystems : otherSystems
        def disabledSystems = (target instanceof LogicSystem) ? disabledLogicMarkedSystems : disabledOtherSystems
        def targetClass = target.getClass()
        for (int i = 0; i < systems.size; i++) {
            if (targetClass == systems.get(i).getClass())
                return !disabledSystems.get(i)
        }
        throw new RuntimeException("System not found in this world")
    }

    @Override
    void setEnabled(BaseSystem target, boolean value) {
        def systems = (target instanceof LogicSystem) ? logicMarkedSystems : otherSystems
        def disabledSystems = (target instanceof LogicSystem) ? disabledLogicMarkedSystems : disabledOtherSystems
        def targetClass = target.getClass()
        for (int i = 0; i < systems.size; i++) {
            if (targetClass == systems.get(i).getClass()) {
                disabledSystems.set(i, !value)
                break
            }
        }
    }
}