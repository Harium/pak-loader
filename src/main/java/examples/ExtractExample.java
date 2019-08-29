package examples;

import com.harium.pak.PakFile;
import com.harium.pak.PakFileEntry;
import com.harium.pak.PakLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ExtractExample {

  // Folder where the source file is located
  private static final String INPUT_FOLDER = "src/test/resources/";

  // Folder where files will be placed after extracting TARGET_FOLDER
  private static final String TARGET_FOLDER = "src/main/resources/housp/";

  public static void main(String[] args) throws IOException {
    PakLoader loader = new PakLoader();
    PakFile file = loader.load(INPUT_FOLDER + "house-spider.pak");

    extractAllFiles(file);
  }

  public static void extractAllFiles(PakFile file) throws IOException {
    for (PakFileEntry entry : file.getFileEntries().values()) {

      InputStream stream = file.getFile(entry.getName());

      byte[] buffer = new byte[stream.available()];
      stream.read(buffer);

      File targetFile = new File(TARGET_FOLDER + entry.getName());

      targetFile.getParentFile().mkdirs();
      OutputStream outStream = new FileOutputStream(targetFile);
      outStream.write(buffer);
    }
  }

}
