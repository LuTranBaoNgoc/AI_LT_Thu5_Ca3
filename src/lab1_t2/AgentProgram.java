package lab1_t2;

import java.util.Random;

import lab1_t2.Environment.LocationState;

public class AgentProgram {

	public Action execute(Percept p) {// location, status
		Action[] action = {Environment.MOVE_UP,Environment.MOVE_DOWN,Environment.MOVE_LEFT,Environment.MOVE_RIGHT};
		Random rd = new Random();
		int i = rd.nextInt(4);
		if (p.getLocationState() == LocationState.DIRTY) {
			return Environment.SUCK_DIRT;
		} else if (p.getLocationState() == LocationState.CLEAN) {
			return action[i];
		}
		return NoOpAction.NO_OP;
		
	}
}