package br.com.ipet.infrastructure.file;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import timber.log.Timber;

public class FileUtil {

    // TODO: vai ser usado para os instrumentationTest
    public static String readRawJson(int rawId, Resources resources) {
        InputStream inputStream = resources.openRawResource(rawId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Timber.e(exception, "Error writing/reading from the JSON file.");
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Timber.e(exception, "Error closing the input stream.");
            }
        }
        return writer.toString();
    }
}