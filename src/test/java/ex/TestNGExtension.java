package ex;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveProcessor;
import org.jboss.arquillian.core.spi.LoadableExtension;

public class TestNGExtension implements LoadableExtension {
    @Override
    public void register(ExtensionBuilder builder) {
        builder.service(AuxiliaryArchiveProcessor.class, TestNGArchiveProcessor.class);
    }
}
