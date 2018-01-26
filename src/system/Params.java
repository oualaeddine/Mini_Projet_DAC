/******************************************************************************
 *                                                                            *
 *  (C) Copyright ${year} Berrehal Ouala Eddine & Benghezal Ines.             *
 *  G2 L3 GL.                                                                 *
 *  Mini projet Module DAC.                                                   *
 *  Simulation d'un parking                                                   *
 *  avec gestion des concurrences en utilisant des sémaphores                 *
 *                                                                            *
 ******************************************************************************/
package system;

public class Params {
    public static final int
            PARKING_SIZE = 9,//de preference diviseur de 550 pour des limitations SWING
            NBR_VOITURES_HANDI = 57,
            NBR_VOITURES_NRML = 69,
            NBR_VOITURES_ABO = 42,
            NBR_SECONDES_PARKING_MIN = 6,//temps d'attente min d'une voiture dans le parking
            NBR_SECONDES_PARKING_MAX = 10;//temps d'attente max d'une voiture dans le parking
}
