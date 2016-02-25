package es.macero.cqgame.util;

import static org.junit.Assert.*;

import org.junit.Test;

import es.macero.cqgame.util.Utils;

public class UtilsTest {

	@Test
	public void testDateWithDaysWorks() {
		String input = "1d2h50min";
		assertEquals(1610, Utils.durationTranslator(input).toStandardMinutes().getMinutes());
	}

	@Test
	public void testDateWithoutDaysWorks() {
		String input = "2h50min";
		assertEquals(170, Utils.durationTranslator(input).toStandardMinutes().getMinutes());
	}

	@Test
	public void testDateOnlyWithMin() {
		String input = "50min";
		assertEquals(50, Utils.durationTranslator(input).toStandardMinutes().getMinutes());
	}

	@Test
	public void testDateOnlyWithHours() {
		String input = "3h";
		assertEquals(180, Utils.durationTranslator(input).toStandardMinutes().getMinutes());
	}

	@Test
	public void testDateOnlyWithDays() {
		String input = "3d";
		assertEquals(4320, Utils.durationTranslator(input).toStandardMinutes().getMinutes());
	}

}
