package examples;

import com.harium.pak.PakFile;
import com.harium.pak.PakFileEntry;
import com.harium.pak.PakLoader;
import java.io.IOException;

public class ListExample {

  private static final String INPUT_FOLDER = "src/test/resources/";

  public static void main(String[] args) throws IOException {
    PakLoader loader = new PakLoader();
    PakFile file = loader.load(INPUT_FOLDER + "housp-pak0.pak");

    listAllFiles(file);
  }

  public static void listAllFiles(PakFile file) {
    for (PakFileEntry entry : file.getFileEntries().values()) {
      System.out.println("Name: " + entry.getName());
      System.out.println("Offset: " + entry.getOffset());
      System.out.println("Size: " + entry.getSize());
      System.out.println();
    }
  }

}
