package com.alexbt.appdirect.sampleapp.ui;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.alexbt.appdirect.sampleapp.util.WebConstants;

public class JavascriptUriSanityCheckTest {

    @Test
    public void test() throws IOException {
        List<String> uriToTest = new ArrayList<String>() {
            {
                add(WebConstants.WEB_URI_CURRENT_USER);
                add(WebConstants.WEB_URI_SUBSCRIPTIONS);
            }
        };

        String folder = getClass().getClassLoader().getResource("public/components").getFile();
        Collection<File> listFiles = FileUtils.listFiles(new File(folder), new String[] { "js" }, true);

        for (File file : listFiles) {
            List<String> lines = FileUtils.readLines(file, "UTF8");
            lines = lines.stream().filter(l -> l.contains("jquery.ajax")).collect(Collectors.toList());

            for (String line : lines) {
                Iterator<String> iter = uriToTest.iterator();
                while (iter.hasNext()) {
                    String uri = iter.next();
                    if (line.contains(uri)) {
                        iter.remove();
                    }
                }
            }
        }
        assertTrue("At least one ajax call was not matched to expected URI", uriToTest.isEmpty());
    }
}
