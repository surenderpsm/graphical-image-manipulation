package model;

import java.util.NoSuchElementException;

public interface HistogramCacheable {

  boolean isHistogram(String name);

  Histogram getHistogram(String name) throws NoSuchElementException;

  void set(String name, Histogram histogram);
}
