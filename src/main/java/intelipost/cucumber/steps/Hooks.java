package intelipost.cucumber.steps;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class Hooks {

	private static Collection<String> tags;
	public static Scenario scenario;

	@Before
	public void runBeforeWithOrder(Scenario scenario) throws Throwable {
		Hooks.scenario = scenario;
        Hooks.tags = scenario.getSourceTagNames();
	}

}

