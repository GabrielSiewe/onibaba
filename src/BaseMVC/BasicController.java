package BaseMVC;
import java.util.Stack;

import javax.swing.JFrame;

	public class BasicController {
	private static Stack<JFrame> previousPages;
	private static Stack<JFrame> nextPages;
	public BasicController()
	{
		if(previousPages == null) {
			previousPages = new Stack<JFrame>();
			nextPages = new Stack<JFrame>();
		}
	}
	public JFrame forward(JFrame view)
	{
		JFrame toReturn = null;
		if (!nextPages.isEmpty())
		{
			view.hide();
			previousPages.push(view);
			toReturn = nextPages.pop();
		}
		return toReturn;
		
	}
	public JFrame back(JFrame view)
	{
		JFrame toReturn = null;
		if (!previousPages.isEmpty())
		{
			view.hide();
			nextPages.push(view);
			toReturn = previousPages.pop();
		}
		return toReturn;
		
	}
	public void addToPrevious(JFrame view)
	{
		view.hide();
		previousPages.push(view);
		
	}
	public static void closeDB()
	{
		BasicModel.closeDbConnection();
	}
}
