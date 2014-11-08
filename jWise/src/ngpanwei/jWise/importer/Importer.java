package ngpanwei.jWise.importer;

import ngpanwei.jWise.core.Domain;

/**
 * Import domain definitions and combinations from data sources 
 * instead of using code.
 * @author panwei
 *
 */
public abstract class Importer {

	/**
	 * Import problem domain.
	 * @param source data source
	 * @param parameters
	 * @return
	 */
	public abstract Domain importData(String source,String ... parameters) ;
	/**
	 * Import required combinations.
	 * @param source data source
	 * @param parameters
	 * @return
	 */
	public abstract Domain importCombination(String source,String ... parameters) ;
	
}
