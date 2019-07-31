import java.lang.Math;
import java.util.*;

public class RL_Brain {

	private static int action_space;
	private static double e;
	private static double alpha;
	private static double gamma;
	private static Map<String, double[]> table;
	private static Random rand = new Random();

	public RL_Brain(int action_space, double alpha, double gamma, double epsilon){
		this.action_space = action_space;
		this.e = epsilon;
		this.alpha = alpha;
		this.gamma = gamma;
		this.table = new HashMap<>();
	}

	public int max_value_index(String index){
		double[] s_a = this.table.get(index);
		int action = 0;
		for(int i = 0; i < s_a.length; i++)
			if(s_a[i] > s_a[action])
				action = i;
		return action;
	}

	public int action_chooser(String _s){
		check_table_for_state(_s);
		int action;
		if(Math.random() < this.e)
			action = max_value_index(_s);
		else
			action = rand.nextInt(this.action_space);
		return action;
	}

	public void update(String s, int a, double r, String _s){
		check_table_for_state(s);
		check_table_for_state(_s);
		int max_index = max_value_index(_s);
		double max = this.table.get(_s)[max_index];
		double q_predict = this.table.get(s)[a];
		double q_target = r + this.gamma * max;
		this.table.get(s)[a] = q_predict + this.alpha * (q_target - q_predict);
	}

	public void check_table_for_state(String s){
		double[] key = this.table.get(s);
		if(key == null)
			this.table.put(s, new double[this.action_space]);
	}
}