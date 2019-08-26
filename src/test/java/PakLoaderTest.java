import static org.junit.Assert.assertEquals;

import com.harium.pak.PakFile;
import com.harium.pak.PakLoader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class PakLoaderTest {

  String path;
  PakLoader loader;

  @Before
  public void setUp() {
    loader = new PakLoader();

    path = System.getProperty("user.dir");
    path += "/src/test/resources/";
  }

  @Test
  public void testLoad() throws IOException {
    PakFile file = loader.load(path + "housp-pak0.pak");
    assertEquals(256, file.getSize());
    assertEquals(4, file.getFiles().size());
  }

}
