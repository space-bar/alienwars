package com.spacebar.alienwars;

import com.spacebar.alienwars.screen.cli.CLIScreen;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public abstract class AbstractCLITest {

    public CLIScreen screen;

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Before
    public void setUp() throws Exception {
        screen = new CLIScreen(100, 20);
    }

    @After
    public void tearDown() throws Exception {
    }
}
