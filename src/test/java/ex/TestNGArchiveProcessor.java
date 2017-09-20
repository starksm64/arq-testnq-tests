package ex;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveProcessor;
import org.jboss.shrinkwrap.api.Archive;

public class TestNGArchiveProcessor implements AuxiliaryArchiveProcessor {
    @Override
    public void process(Archive<?> auxiliaryArchive) {
        if(auxiliaryArchive.getName().contains("testng")) {
            System.out.printf("*-testng.jar contents: %s\n", auxiliaryArchive.toString(true));
        }
    }
}
