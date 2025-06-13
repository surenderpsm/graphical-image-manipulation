import controller.viewhandler.ViewUpdater;
import model.SingleSessionModel;
import model.Model;
import model.ModelReceiver;
import model.ModelSharer;
import org.junit.Test;


public class GUIModelTest {

  static class ModelSharerMock implements ModelSharer {

    @Override
    public void shareWith(ModelReceiver receiver) {
      Model model = new Model();
      receiver.share(model);
    }
  }

  static class ViewUpdaterMock implements ViewUpdater {

    @Override
    public void updateDisplay(int[][][] image) {
      System.out.println("The image is updating.");
    }

    @Override
    public void updateHistogram(int[][] histogram) {
      System.out.println("The histogram is updating.");
    }
  }

  @Test
  public void test() {

    ModelSharerMock mock = new ModelSharerMock();
    ViewUpdaterMock viewUpdater = new ViewUpdaterMock();

    SingleSessionModel model = new SingleSessionModel(mock, viewUpdater, "ALIAS", "preview");




  }
}
