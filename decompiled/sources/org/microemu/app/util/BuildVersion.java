package org.microemu.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* loaded from: avacs.jar:org/microemu/app/util/BuildVersion.class */
public class BuildVersion {
    public static String getVersion() throws IOException {
        InputStream mavenDataInputStream = BuildVersion.class.getResourceAsStream("/META-INF/microemulator-build.version");
        if (mavenDataInputStream != null) {
            Properties projectProperties = new Properties();
            try {
                projectProperties.load(mavenDataInputStream);
                String version = projectProperties.getProperty("build.version");
                if (version != null) {
                    String buildNumber = projectProperties.getProperty("build.buildNum");
                    if (buildNumber != null) {
                        version = String.valueOf(version) + "." + buildNumber;
                    }
                    return version;
                }
            } catch (IOException e) {
            } finally {
            }
        }
        mavenDataInputStream = BuildVersion.class.getResourceAsStream("/META-INF/maven/org.microemu/microemu-javase/pom.properties");
        if (mavenDataInputStream == null) {
            return "n/a";
        }
        Properties projectProperties2 = new Properties();
        try {
            projectProperties2.load(mavenDataInputStream);
            String version2 = projectProperties2.getProperty("version");
            return version2 != null ? version2 : "n/a";
        } catch (IOException e2) {
            return "n/a";
        } finally {
        }
    }
}
