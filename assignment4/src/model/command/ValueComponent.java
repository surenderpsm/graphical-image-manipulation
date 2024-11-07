package model.command;

import model.Cache;

class ValueComponent extends Abstract2ArgSimpleImageProcessor {


  public ValueComponent(String rawArguments, Cache cache) {
    super(rawArguments, cache, (r, g, b) ->
    {
      int value = Math.max(Math.max(r, g), b);
      return new int[]{
          value,
          value,
          value};
    });
  }

}
