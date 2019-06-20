package fr.lsmbo.msda.spectra.comp.io;

import java.io.File;
import java.sql.SQLException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.lsmbo.msda.spectra.comp.Session;
import fr.lsmbo.msda.spectra.comp.db.DBSpectraHandler;
import fr.lsmbo.msda.spectra.comp.db.DataSource;
import fr.lsmbo.msda.spectra.comp.list.ListOfSpectra;
import fr.lsmbo.msda.spectra.comp.model.SpectraComparator;
import fr.lsmbo.msda.spectra.comp.settings.SpectraComparatorParams;
import fr.lsmbo.msda.spectra.comp.utils.StringsUtils;

/**
 * Load spectra from Proline projects or from peak list files.
 * 
 * @author Aromdhani
 *
 */
public class PeakListProvider {
	private static final Logger logger = LogManager.getLogger(PeakListProvider.class);
	private static String projectName;
	private static String firstPklList;
	private static String secondPklList;

	/**
	 * @return the reference peaklist file path
	 */
	public static final String getRefPkl() {
		return firstPklList;
	}

	/**
	 * @return the project name
	 */
	public static final String getProjectName() {
		return projectName;
	}

	/**
	 * @return the tested peaklist file path
	 */
	public static final String getTestedPkl() {
		return secondPklList;
	}

	/**
	 * Load reference peak list from a Proline project.
	 * 
	 * @param dbName The database name to connect to. It's always in this form
	 *               msi_db_project_ID
	 * @param rsmIds the result_summary ids from where to compute the spectra.
	 *
	 * @throws SQLException
	 */
	@SuppressWarnings("restriction")
	public static void loadRefSpectraFrmProline(String dbName, Set<Long> rsmIds) throws SQLException {
		if (DataSource.getType(Session.USER_PARAMS.getDataSource()) == DataSource.DATABASE) {
			assert !StringsUtils.isEmpty(dbName) : "Project name must not be null nor empty!";
			assert !rsmIds.isEmpty() : "Rsm Ids must not be empty!";
			logger.info("--- Start to retrieve spectra from reference peaklist from Proline project. Please wait ...");
			System.out.println(
					"INFO | Start to retrieve spectra from reference peaklist from Proline project. Please wait ...");
			// Find the msi_search_ids
			Set<Long> msiSearchIds = DBSpectraHandler.fillMsiSerachIds(dbName, rsmIds);
			DBSpectraHandler.fillSpecByPeakList(dbName, msiSearchIds);
			ListOfSpectra.getFirstSpectra().getSpectraAsObservable()
					.setAll(DBSpectraHandler.getSpectra().getSpectraAsObservable());

		}
	}

	/**
	 * Load the reference spectra from a peaklist file.
	 * 
	 * @param refPklFilePath The path of the reference peaklist file.
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static void loadRefSpectraFromFile(String refPklFilePath) throws Exception {
		if (DataSource.getType(Session.USER_PARAMS.getDataSource()) == DataSource.FILE) {
			assert (!StringsUtils.isEmpty(refPklFilePath)
					&& (new File(refPklFilePath).exists())) : "Invalid file path!";
			logger.info("Load reference spectra from the file : {} . Please wait ...", refPklFilePath);
			System.out.println(
					"INFO | --- Load reference spectra from the file  : " + refPklFilePath + " . Please wait ...");
			File firstPklListFile = new File(refPklFilePath);
			PeaklistReader.load(firstPklListFile);
		}
	}

	/**
	 * Load tested peaklist from a Proline project.
	 * 
	 * @param dbName The database name to connect to. It's always in this form
	 *               msi_db_project_ID
	 * @param rsmIds the result_summary ids from where to compute the spectra.
	 *
	 * @throws SQLException
	 */
	@SuppressWarnings("restriction")
	public static void loadTestedSpectraFrmProline(String dbName, Set<Long> rsmIds) throws Exception {
		if (DataSource.getType(Session.USER_PARAMS.getDataSource()) == DataSource.DATABASE) {
			assert !StringsUtils.isEmpty(dbName) : "Project name must not be null nor empty!";
			logger.info("--- Start to retrieve spectra from reference peaklist from Proline project. Please wait ...");
			System.out.println(
					"INFO | Start to retrieve spectra from reference peaklist from Proline project. Please wait ...");
			// Find the msi_search_ids
			Set<Long> msiSearchIds = DBSpectraHandler.fillMsiSerachIds(dbName, rsmIds);
			DBSpectraHandler.fillSpecByPeakList(dbName, msiSearchIds);
			ListOfSpectra.getSecondSpectra().getSpectraAsObservable()
					.setAll(DBSpectraHandler.getSpectra().getSpectraAsObservable());
		}
	}

	/**
	 * Load the tested spectra from a peaklist file.
	 * 
	 * @param testPklFilePath The path of the reference peaklist file.
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static void loadTestedSpectraFromFile(String testPklFilePath) throws Exception {
		if (DataSource.getType(Session.USER_PARAMS.getDataSource()) == DataSource.FILE) {
			assert (!StringsUtils.isEmpty(testPklFilePath)
					&& (new File(testPklFilePath).exists())) : "Invalid file path !";
			logger.info("Load spectra to test from the file : {} . Please wait ...", testPklFilePath);
			System.out.println(
					"INFO | --- Load spectra to test from the file  : " + testPklFilePath + " . Please wait ...");
			File testPklFile = new File(testPklFilePath);
			PeaklistReader.load(testPklFile);
		}
	}

	/**
	 * Compare spectra using dot product method.
	 * 
	 * @see SpectraComparator
	 * @see SpectraComparatorParams
	 */
	public static void compareSpectra() {
		logger.info("Start to compare: {} as a reference sepctra vs  {} spectra. please wait ...",
				ListOfSpectra.getFirstSpectra().getSpectraAsObservable().size(),
				ListOfSpectra.getSecondSpectra().getSpectraAsObservable().size());
		logger.info(Session.USER_PARAMS.getComparison().toString());
		ListOfSpectra.getFirstSpectra().getSpectraAsObservable().forEach(sepctrum -> {
			SpectraComparator.run(sepctrum);
		});
		logger.info("{} valid spectra found.", SpectraComparator.getValidSpectra().getNbSpectra());
	}

	/**
	 * @param firstPklList the first peak list to set
	 */
	public static final void setFirstPklList(String firstPklList) {
		PeakListProvider.firstPklList = firstPklList;
	}

	/**
	 * @param projectName the project name to set
	 */
	public static final void setProjectName(String projectName) {
		PeakListProvider.projectName = projectName;
	}

	/**
	 * @param secondPklList the second peak list to set
	 */
	public static final void setSecondPklList(String secondPklList) {
		PeakListProvider.secondPklList = secondPklList;
	}

}
