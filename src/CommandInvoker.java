import java.util.Stack;

public class CommandInvoker {
    private final Stack<Command> history = new Stack<>();

    public void execute(Command command) {
        command.execute();
        history.push(command);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            history.pop().undo();
        } else {
            System.out.println("No commands to undo.");
        }
    }
}
