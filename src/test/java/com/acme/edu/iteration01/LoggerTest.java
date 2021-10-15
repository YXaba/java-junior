package com.acme.edu.iteration01;

import com.acme.edu.Logger;
import com.acme.edu.SysoutCaptureAndAssertionAbility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static com.acme.edu.Logger.*;
import static java.lang.System.lineSeparator;

public class LoggerTest implements SysoutCaptureAndAssertionAbility {

    public static final String sep = lineSeparator();
    private static String type;

    //region given
    @Before
    public void setUpSystemOut() throws IOException {
        resetOut();
        captureSysout();
    }

    @After
    public void tearDown() {
        resetOut();
    }
    //endregion

    @Test
    public void shouldLogInteger() throws IOException {
        type = typePrimitive;
        //region when
        log(1, 0, -1);
        //endregion

        //region then
        checkLog("1", "0", "-1");
        //endregion
    }

    @Test
    public void shouldLogByte() throws IOException {
        type = typePrimitive;
        //region when
        log((byte)1, (byte)0, (byte)-1);
        //endregion

        //region then
        checkLog("1", "0", "-1");
        //endregion
    }

    @Test
    public void shouldLogChar() throws IOException {
        type = typeChar;
        //region when
        log('a', 'b');
        //endregion

        //region then
        checkLog("a", "b");
        //endregion
    }

    @Test
    public void shouldLogString() throws IOException {
        type = typeString;
        //region when
        log("test string 1", "other str");
        //endregion

        //region then
        checkLog("test string 1", "other str");
        //endregion
    }

    @Test
    public void shouldLogBoolean() throws IOException {
        type = typePrimitive;
        //region when
        log(true, false);
        //endregion

        //region then
        checkLog("true", "false");
        //endregion
    }

    @Test
    public void shouldLogReference() throws IOException {
        type = typeReference;
        //region when
        log(new Object());
        //endregion

        //region then
        assertSysoutContains(type);
        assertSysoutContains("@");
        //endregion
    }

    private void log(Object... valuesToLog) throws IOException {
        for (Object valueToLog : valuesToLog) {
            Logger.log(valueToLog);
            Logger.flush();
        }
    }

    private void checkLog(String... valuesToCheck) throws IOException {
        for (String valueToCheck : valuesToCheck) {
            assertSysoutContains(type + valueToCheck);
        }
    }
}