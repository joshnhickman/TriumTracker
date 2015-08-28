package com.joshnhickman.triumtracker.domain;

import junit.framework.Assert;

import org.junit.Test;

public class InitiativeTest {

    @Test
    public void testNormalTie() {
        Assert.assertEquals(0, new Initiative(5).compareTo(new Initiative(5)));
    }

}
