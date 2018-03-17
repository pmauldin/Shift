package pmauldin.shift

import groovy.transform.CompileStatic

@CompileStatic
class Constants {

	static final int MILLIS_PER_LOGIC_TICK = 2
	static final float TIMESTEP_IN_SECONDS =MILLIS_PER_LOGIC_TICK * 0.001 as float
	static final float WIDTH = 1280
	static final float HEIGHT = 768
	static final int PPM = 64 // Pixels per meter

}
