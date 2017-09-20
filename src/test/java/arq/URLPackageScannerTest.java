package arq;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.impl.base.URLPackageScanner;
import org.jboss.shrinkwrap.impl.base.asset.AssetUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests of the URLPackageScanner
 */
public class URLPackageScannerTest {
    private ArrayList<ArchivePath> classes = new ArrayList<>();

    private class AddClassCallback implements URLPackageScanner.Callback {
        @Override
        public void classFound(String className, Asset asset) {
            ArchivePath classNamePath = AssetUtil.getFullPathForClassResource(className);
            classes.add(classNamePath);
        }
    }
    private URLPackageScanner.Callback callback = new AddClassCallback();
    private ArchivePath suiteRunner = AssetUtil.getFullPathForClassResource("org.testng.SuiteRunner");
    @Test
    public void testURLPackageScannerIDE() {
        classes.clear();
        ClassLoader loader = URLPackageScannerTest.class.getClassLoader();
        URLPackageScanner scanner = URLPackageScanner.newInstance(true, loader, callback, "org.testng");
        scanner.scanPackage();
        System.out.printf("testng classes count: %d\n", classes.size());
        Assert.assertTrue(classes.contains(suiteRunner), "classes contains org.testng.SuiteRunner");
    }

    @Test
    public void testURLPackageScannerUCL() throws Exception {
        String home = System.getenv("HOME");
        File testNGJar = new File(home, ".m2/repository/org/testng/testng/6.10/testng-6.10.jar");
        Assert.assertTrue(testNGJar.canRead());
        System.out.printf("testng-6.10.jar: %s\n", testNGJar.getAbsolutePath());
        URL[] cp = {testNGJar.toURI().toURL()};
        URLClassLoader loader = new URLClassLoader(cp, URLPackageScannerTest.class.getClassLoader());

        classes.clear();
        URLPackageScanner scanner = URLPackageScanner.newInstance(true, loader, callback, "org.testng");
        scanner.scanPackage();
        System.out.printf("testng classes count: %d\n", classes.size());
        Assert.assertTrue(classes.contains(suiteRunner), "classes contains org.testng.SuiteRunner");
    }

    @Test
    public void testURLPackageScannerNullParent() throws Exception {
        String home = System.getenv("HOME");
        File testNGJar = new File(home, ".m2/repository/org/testng/testng/6.10/testng-6.10.jar");
        Assert.assertTrue(testNGJar.canRead());
        System.out.printf("testng-6.10.jar: %s\n", testNGJar.getAbsolutePath());
        URL[] cp = {testNGJar.toURI().toURL()};
        URLClassLoader loader = new URLClassLoader(cp, null);

        classes.clear();
        URLPackageScanner scanner = URLPackageScanner.newInstance(true, loader, callback, "org.testng");
        scanner.scanPackage();
        System.out.printf("testng classes count: %d\n", classes.size());
        Assert.assertTrue(classes.contains(suiteRunner), "classes contains org.testng.SuiteRunner");
    }
}
