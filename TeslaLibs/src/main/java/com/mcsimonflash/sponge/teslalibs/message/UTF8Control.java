package com.mcsimonflash.sponge.teslalibs.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
/*
 * From my research I found that multiple people are using this. And I have no idea on who develop this concept. Just going give credit 
 * on where i found the source. 
 * https://gist.github.com/DemkaAge/8999236
 * It's very good and tested it work very well without any form of break changes to the plugin api.
*/
public class UTF8Control extends ResourceBundle.Control {
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
                                    boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        // The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
            try {
                // Only this line is changed to make it to read properties files as UTF-8.
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }
}