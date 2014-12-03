package databaseObjects.beans.DailySaleMVC;
import databaseObjects.beans.PersonMVC.PersonController;
import BaseMVC.BasicController;

public class DailySaleController extends BasicController {
	private PersonController controller;
	public DailySaleController(PersonController personController)
	{
		controller = personController;
	}
}