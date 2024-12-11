package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.enums.Decision;

class ToujoursCoopererTest {
    @Test
    void testExecute(){
        ToujoursCooperer tc = new ToujoursCooperer();
        Decision res = tc.execute(null, null);
        assertEquals(Decision.COOPERER,res);
    }
}
