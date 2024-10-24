package controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import model.Model;
import org.junit.Test;
import org.junit.Test.None;

/**
 * Test class for the IOHandler in the controller package. This class contains unit tests for
 * loading and saving image files of various formats.
 */
public class IOHandlerTest {

  Model model = new Model();
  String root = "res/img/other/";
  String jpg = root + "parrot.jpg";
  String ppm = root + "parrot.ppm";
  String png = root + "parrot.png";

  /**
   * Test loading an existing image file.
   *
   * @throws IOException if the image file cannot be loaded
   */
  @Test(expected = None.class)
  public void loadExistingFile() throws IOException {
    IOHandler ih = new IOHandler(model, "load", jpg + " parrot");
  }

  /**
   * Test loading a non-existing image file. Expects an {@code IOException} to be thrown.
   *
   * @throws IOException if the image file cannot be loaded
   */
  @Test(expected = IOException.class)
  public void loadNonExistingFile() throws IOException {
    IOHandler ih = new IOHandler(model, "load", root + "notparrot.jpg parrot");
  }

  /**
   * Test saving an existing image to a new file. Verifies that the file was created successfully.
   *
   * @throws IOException if there is an issue saving the image
   */
  @Test
  public void saveExistingImageToFile() throws IOException {
    IOHandler ih = new IOHandler(model, "load", jpg + " parrot");
    ih = new IOHandler(model, "save", root + "newParrot.png parrot");
    File file = new File(root + "newParrot.png");
    assertTrue(file.exists());
  }

  /**
   * Test saving a non-existing image. Expects a {@code NoSuchElementException} to be thrown.
   *
   * @throws IOException if there is an issue with the save operation
   */
  @Test(expected = NoSuchElementException.class)
  public void saveNonExistingImageToFile() throws IOException {
    IOHandler ih = new IOHandler(model, "load", jpg + " parrot");
    ih = new IOHandler(model, "save", root + "newParrot.png noparrot");
    File file = new File(root + "newParrot.png");
    assertFalse(file.exists());

  }

  /**
   * Test loading a JPG image file.
   *
   * @throws IOException if the image file cannot be loaded
   */
  @Test(expected = None.class)
  public void loadJPG() throws IOException {
    IOHandler ih = new IOHandler(model, "load", jpg + " parrot");
  }

  /**
   * Test saving an image as a JPG file. Verifies that the file was created successfully.
   *
   * @throws IOException if there is an issue saving the image
   */
  @Test
  public void saveJPG() throws IOException {
    IOHandler ih = new IOHandler(model, "load", png + " parrot");
    ih = new IOHandler(model, "save", root + "newParrot.jpg parrot");
    File file = new File(root + "newParrot.jpg");
    assert file.exists();
  }

  /**
   * Test loading a PNG image file.
   *
   * @throws IOException if the image file cannot be loaded
   */
  @Test(expected = None.class)
  public void loadPNG() throws IOException {
    IOHandler ih = new IOHandler(model, "load", png + " parrot");
  }

  /**
   * Test saving an image as a PNG file. Verifies that the file was created successfully.
   *
   * @throws IOException if there is an issue saving the image
   */
  @Test
  public void savePNG() throws IOException {
    IOHandler ih = new IOHandler(model, "load", ppm + " parrot");
    ih = new IOHandler(model, "save", root + "newParrot.png parrot");
    File file = new File(root + "newParrot.png");
    assertTrue(file.exists());
  }

  /**
   * Test loading a PPM image file.
   *
   * @throws IOException if the image file cannot be loaded
   */
  @Test(expected = None.class)
  public void loadPPM() throws IOException {
    IOHandler ih = new IOHandler(model, "load", ppm + " parrot");
  }

  /**
   * Test saving an image as a PPM file. Verifies that the file was created successfully.
   *
   * @throws IOException if there is an issue saving the image
   */
  @Test
  public void savePPM() throws IOException {
    IOHandler ih = new IOHandler(model, "load", png + " parrot");
    ih = new IOHandler(model, "save", root + "newParrot.ppm parrot");
    File file = new File(root + "newParrot.ppm");
    assertTrue(file.exists());
  }

  /**
   * Test loading an image with an invalid file format. Expects an {@code IllegalArgumentException}
   * to be thrown.
   *
   * @throws IOException if the image file cannot be loaded
   */
  @Test(expected = IllegalArgumentException.class)
  public void loadInvalidFormat() throws IOException {
    IOHandler ih = new IOHandler(model, "load", root + "parrot.invalid parrot");
  }

  /**
   * Test saving an image with an invalid file format. Expects an {@code IllegalArgumentException}
   * to be thrown.
   *
   * @throws IOException if there is an issue saving the image
   */
  @Test(expected = IllegalArgumentException.class)
  public void saveInvalidFormat() throws IOException {
    IOHandler ih = new IOHandler(model, "load", png + " parrot");
    ih = new IOHandler(model, "save", root + "parrot.invalid parrot");
  }

  /**
   * Test loading an image with an unsupported file format. Expects an
   * {@code IllegalArgumentException} to be thrown.
   *
   * @throws IOException if the image file cannot be loaded
   */
  @Test(expected = IllegalArgumentException.class)
  public void loadUnsupportedFormat() throws IOException {
    IOHandler ih = new IOHandler(model, "load", root + "parrot.gif parrot");
  }

  /**
   * Test saving an image with an unsupported file format. Expects an
   * {@code IllegalArgumentException} to be thrown.
   *
   * @throws IOException if there is an issue saving the image
   */
  @Test(expected = IllegalArgumentException.class)
  public void saveUnsupportedFormat() throws IOException {
    IOHandler ih = new IOHandler(model, "load", ppm + " parrot");
    ih = new IOHandler(model, "save", root + "parrot.gif parrot");
  }

}
