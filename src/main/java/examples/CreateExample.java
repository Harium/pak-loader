package examples;

import com.harium.pak.PakWriter;
import java.io.IOException;

public class CreateExample {

  private static final String TARGET_FOLDER = "src/main/resources/housp/";

  public static void main(String[] args) throws IOException {
    PakWriter writer = new PakWriter();
    writer.write(TARGET_FOLDER, TARGET_FOLDER + "../house-spider.pak");
  }

}
