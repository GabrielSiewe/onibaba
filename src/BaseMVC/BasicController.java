package BaseMVC;
import java.sql.SQLException;
import java.util.Stack;
import javax.swing.JFrame;
/*
 * Class Basic Controller
 * Handles the basic functionality of going back and forth between pages,
 * A functionality all pages must have along with the ability to close the database connection.
 */
	public class BasicController {
	private static Stack<JFrame> previousPages;
	private static Stack<JFrame> nextPages;
	
	/*Instantiates the previous and nextpages, the stacks that will be saving the appropriates pages, depending on the direction
	 * The user wishes to move
	 */
	public BasicController()
	{
		if(previousPages == null) {
			previousPages = new Stack<JFrame>();
			nextPages = new Stack<JFrame>();
		}
	}
	// Takes in the current view of the user and hides it. Then returns the next view from the current, previously entered by te user
	public JFrame forward(JFrame view)
	{
		JFrame toReturn = view;
		if (!nextPages.isEmpty() || view == null)
		{
			toReturn = nextPages.pop();
			if (view != null) {
				view.setVisible(false);
				previousPages.push(view);
			}
		}
		return toReturn;
		
	}
	
	// Takes in the current view of the user and hides it. Then returns a view previously visited by the user
	public JFrame back(JFrame view)
	{
		JFrame toReturn = view;
		if (!previousPages.isEmpty())
		{
			toReturn = previousPages.pop();
			if (view != null) {
				view.setVisible(false);
				nextPages.push(view);
			}
		}
		return toReturn;
		
	}
	
	// takes a view and hides it, before pushing it on the stack of views representing previously visited frames.
	public void addToPrevious(JFrame view)
	{
		view.setVisible(false);
		previousPages.push(view);
		
	}
	// Closes the database connection
	public static void closeDB() throws SQLException
	{
			BasicModel.closeDbConnection();

	}
	
	// resets the stacks of previously visited frames and next to be visited frames.
	public void resetForwardAndBack()
	{
		previousPages.clear();
		nextPages.clear();
	}
}
