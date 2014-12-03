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
	public void addToPrevious(JFrame view)
	{
		view.setVisible(false);
		previousPages.push(view);
		
	}
	public static void closeDB()
	{
		BasicModel.closeDbConnection();
	}
	public void resetForwardAndBack()
	{
		previousPages.clear();
		nextPages.clear();
	}
}
