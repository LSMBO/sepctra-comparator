/*
 * 
 */
package fr.lsmbo.msda.spectra.comp.model;

/**
 * Contains default values for the algorithm and getter and setter.
 * 
 * @author Aromdhani
 *
 */
public class ConstantComparisonSpectra {
	private static float deltaMoz = 0.007F;
	private static Integer deltaRT = 90;
	private static Integer nbPeaksMin = 4;
	private static Integer thetaMin = 11;
	private static Integer nbPeaks = 8;

	public static Float getDeltaMoz() {
		return deltaMoz;
	}

	public static Integer getDeltaRT() {
		return deltaRT;
	}

	public static Integer getNbPeaks() {
		return nbPeaks;
	}

	public static Integer getNbPeaksMin() {
		return nbPeaksMin;
	}

	public static Integer getThetaMin() {
		return thetaMin;
	}

	public static void initialValue() {
		deltaMoz = 0.007F;
		deltaRT = 90;
		nbPeaksMin = 4;
		thetaMin = 11;
		nbPeaks = 8;
	}

	public static void setDeltamoz(float _deltaMoz) {
		deltaMoz = _deltaMoz;
	}

	public static void setDeltaRT(Integer _deltaRT) {
		deltaRT = _deltaRT;
	}

	public static void setNbPeaks(Integer _nbPeaks) {
		nbPeaks = _nbPeaks;
	}

	public static void setNbPeaksMin(Integer _nbPeaksMin) {
		nbPeaksMin = _nbPeaksMin;
	}

	public static void setThetaMin(Integer _thetaMin) {
		thetaMin = _thetaMin;
	}

}
