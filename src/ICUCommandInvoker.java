import java.util.Stack;

public class ICUCommandInvoker {
    private final Stack<ICUCommand> history = new Stack<>();

    public void execute(ICUCommand command) {
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
