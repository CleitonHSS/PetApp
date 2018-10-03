package br.com.ipet;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ApplicationIdInstrumentedTest {
    @Test
    public void useAppContext() {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            assertEquals("br.com.ipet.debug", BuildConfig.APPLICATION_ID);
        }
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            assertEquals("br.com.ipet.release", BuildConfig.APPLICATION_ID);
        }
    }
}
