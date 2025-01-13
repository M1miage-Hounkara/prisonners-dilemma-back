package fr.uga.l3miage.pc.prisonersdilemma.strategies;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import fr.uga.l3miage.pc.prisonersdilemma.domain.enums.Decision;
import fr.uga.l3miage.pc.prisonersdilemma.domain.strategies.ToujoursCooperer;

class ToujoursCoopererTest {
    @Test
    void testExecute(){
        ToujoursCooperer tc = new ToujoursCooperer();
        Decision res = tc.execute(null, null);
        assertEquals(Decision.COOPERER,res);
    }
}
