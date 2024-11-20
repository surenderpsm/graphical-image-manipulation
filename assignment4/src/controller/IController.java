package controller;

public interface IController extends IControllerModel, IControllerView{

  /**
   * This method retrieves the required commands by the view and compares with the model upon instantiation.
   */
  void runCommandCompatibilityTest();

}
