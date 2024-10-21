package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScriptHandler {
  private final File file;
  public ScriptHandler(String path) throws FileNotFoundException {
    file = new File(path);
  }

  public List<String> getCommands() throws FileNotFoundException {
    List<String> commands = new ArrayList<>();
    try(Scanner sc = new Scanner(file)){
      while (sc.hasNextLine()) {
        String line = sc.nextLine().trim();
        if (!line.startsWith("#") && !line.isEmpty()) {
          commands.add(line);
        }
      }
    }
    catch(FileNotFoundException e){
        throw new FileNotFoundException("File not found: The specified path is invalid.");
    }
    return commands;
  }
}
