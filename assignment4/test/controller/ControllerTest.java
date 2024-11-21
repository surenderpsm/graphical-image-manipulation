package controller;


import controller.viewhandler.ViewHandler;
import java.util.Map;
import java.util.NoSuchElementException;
import model.IModel;
import org.junit.Test;
import utils.arguments.ArgumentWrapper;
import utils.arguments.Signature;

public class ControllerTest {


  private static class MockModel implements IModel {

    public StringBuilder log = new StringBuilder();

    @Override
    public Map<String, Signature> getCommandSignatures() {
      return Map.of();
    }

    @Override
    public boolean isHistogram(String name) {
      return false;
    }

    @Override
    public int[][] getHistogram(String name) throws NoSuchElementException {
      return new int[0][];
    }

    @Override
    public int[][][] getImage(String name) throws NoSuchElementException {
      return new int[0][][];
    }

    @Override
    public void setImage(int[][][] image, String name) {

    }

    @Override
    public void execute(String command, ArgumentWrapper args) throws UnsupportedOperationException {

    }
    public String get
  }

  @Test
  public void test() {

  }


}
