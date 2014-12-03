package databaseObjects.beans.AppointmentMVC;
import databaseObjects.beans.PersonMVC.PersonController;
import BaseMVC.BasicController;

public class AppointmentController extends BasicController {
	private PersonController personController;
	
	public AppointmentController(PersonController controller)
	{
		personController =  controller;
	}
}
