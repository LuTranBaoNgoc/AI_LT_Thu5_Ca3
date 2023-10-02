package lab1_t2; 

public class Environment {
	public static final Action MOVE_LEFT = new DynamicAction("LEFT");
	public static final Action MOVE_RIGHT = new DynamicAction("RIGHT");
	public static final Action MOVE_UP = new DynamicAction("UP");
	public static final Action MOVE_DOWN = new DynamicAction("DOWN");
	public static final Action SUCK_DIRT = new DynamicAction("SUCK");
	public static final String LOCATION_A = "A";
	public static final String LOCATION_B = "B";
	public static final String LOCATION_C = "C";
	public static final String LOCATION_D = "D";
	
	public int performanceScore;

	public enum LocationState {
		CLEAN, DIRTY
	}


	private EnvironmentState envState;
	private boolean isDone = false;// all squares are CLEAN
	private Agent agent = null;

	public Environment(LocationState locAState, LocationState locBState,LocationState locCState, LocationState locDState) {
		envState = new EnvironmentState(locAState, locBState, locCState, locDState);
	}

	// add an agent into the enviroment
	public void addAgent(Agent agent, String location) {
		this.agent = agent;
		envState.setAgentLocation(location);
	}

	public EnvironmentState getCurrentState() {
		return this.envState;
	}

	// Update enviroment state when agent do an action
	public EnvironmentState executeAction(Action action) {
		String Location = envState.getAgentLocation();
		if (action == MOVE_DOWN) {
			if (Location == LOCATION_D || Location == LOCATION_C) {
				this.performanceScore -= 100;
				return envState;
			}
			if (Location == LOCATION_A) {
				envState.setAgentLocation(LOCATION_D);
			}
			if (Location == LOCATION_B) {
				envState.setAgentLocation(LOCATION_C);
			}
				this.performanceScore -= 10;
				return envState;
		}
		
		if (action == MOVE_UP) {
			if (Location == LOCATION_A || Location == LOCATION_B) {
				this.performanceScore -= 100;
				return envState;
			}
			if (Location == LOCATION_D) {
				envState.setAgentLocation(LOCATION_A);
			}
			if (Location == LOCATION_C) {
				envState.setAgentLocation(LOCATION_B);
			}
				this.performanceScore -= 10;
				return envState;
		}
		
		if (action == MOVE_LEFT) {
			if (Location == LOCATION_A || Location == LOCATION_D) {
				this.performanceScore -= 100;
				return envState;
			}
			if (Location == LOCATION_B) {
				envState.setAgentLocation(LOCATION_A);
			}
			if (Location == LOCATION_C) {
				envState.setAgentLocation(LOCATION_D);
			}
				this.performanceScore -= 10;
				return envState;
		}
		
		if (action == MOVE_RIGHT) {
			if (Location == LOCATION_B || Location == LOCATION_C) {
				this.performanceScore -= 100;
				return envState;
			}
			if (Location == LOCATION_A) {
				envState.setAgentLocation(LOCATION_B);
			}
			if (Location == LOCATION_D) {
				envState.setAgentLocation(LOCATION_C);
			}
				this.performanceScore -= 10;
				return envState;
		}
		
		if (action == SUCK_DIRT) {
			envState.setLocationState(envState.getAgentLocation(), LocationState.CLEAN);
			this.performanceScore += 500;
		}
		
		return envState;
	}

	// get percept<AgentLocation, LocationState> at the current location where agent
	// is in.
	public Percept getPerceptSeenBy() {
		return new Percept(envState.getAgentLocation(), envState.getLocationState(envState.getAgentLocation()));
	}

	public void step() {
		envState.display();
		int beforePerformanceScore = this.performanceScore;
		String agentLocation = this.envState.getAgentLocation();
		Action anAction = agent.execute(getPerceptSeenBy());
		EnvironmentState es = executeAction(anAction);
		int plusScore = this.performanceScore - beforePerformanceScore;

		System.out.println("Agent Loc.: " + agentLocation + "\tAction: " + anAction);

		if ((es.getLocationState(LOCATION_A) == LocationState.CLEAN)
				&& (es.getLocationState(LOCATION_B) == LocationState.CLEAN)
				&& (es.getLocationState(LOCATION_C) == LocationState.CLEAN)
				&& (es.getLocationState(LOCATION_D) == LocationState.CLEAN)) {
			isDone = true;// if 4 squares are clean, then agent do not need to do any action	
		}
		es.display();
		System.out.println("Current performance point = " +beforePerformanceScore + " + " +plusScore+" = "+ this.performanceScore);
		
	}

	public void step(int n) {
		for (int i = 0; i < n; i++) {
			step();
			System.out.println("-------------------------");
		}
	}

	public void stepUntilDone() {
		int i = 0;

		while (!isDone) {
			System.out.println("step: " + i++);
			step();
			System.out.println("--------------------------");
		}
	}
}
