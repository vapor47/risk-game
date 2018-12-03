import java.util.Stack;

public class CommandManager {
    private static Stack commandStack = new Stack();
    
    public void executeCommand(Command cmd) {
        cmd.execute();
        if (cmd instanceof UndoableCommand) {
            commandStack.push(cmd);
        }
    }
    
    public void undo() {
        if (commandStack.size() > 0) {
            UndoableCommand cmd = (UndoableCommand)commandStack.pop();
            cmd.undo();
        }
    }
}
