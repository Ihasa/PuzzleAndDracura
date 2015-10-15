package rpg;

import java.util.ArrayList;
import java.util.List;

public class CommandList {
	public List<Command> commands;
	public CommandList(){
		this(new Command[0]);
	}
	public CommandList(Command[] aInfo){
		commands = new ArrayList<Command>();
		for(Command c : aInfo){
			commands.add(c);
		}
	}
	public void add(Command cmd){
		commands.add(cmd);
	}
}
