/** 
  *  Copyright (c) 2014  Ng Pan Wei
  *  
  *  Permission is hereby granted, free of charge, to any person 
  *  obtaining a copy of this software and associated documentation files 
  *  (the "Software"), to deal in the Software without restriction, 
  *  including without limitation the rights to use, copy, modify, merge, 
  *  publish, distribute, sublicense, and/or sell copies of the Software, 
  *  and to permit persons to whom the Software is furnished to do so, 
  *  subject to the following conditions: 
  *  
  *  The above copyright notice and this permission notice shall be 
  *  included in all copies or substantial portions of the Software. 
  *  
  *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
  *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
  *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
  *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
  *  BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN 
  *  ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
  *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
  *  SOFTWARE. 
  */ 
package ngpanwei.jCukeTool.model;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class World extends Element {
	public List<Feature> features ;
	public World() {
		super() ;
		features = new ArrayList<Feature>() ;
	}
	private Feature parseFeature(World world, JSONObject featureObject) throws Exception {
		Feature feature = new Feature(featureObject,world) ;
		if("Feature".equals(feature.keyword)==false) {
			throw new Exception("Expects feature at line "+feature.line) ;
		}
		world.features.add(feature) ;
		return feature ;
	}
	
	private Scenario parseScenario(Feature feature,JSONObject scenarioObject) throws Exception {
		Scenario scenario = new Scenario(scenarioObject,feature) ;
		if("Scenario".equals(scenario.keyword)==false) {
			throw new Exception("Expects scenario at line "+scenario.line) ;
		}
		feature.scenarios.add(scenario) ;
		return scenario ;
	}
	private Step parseStep(Scenario scenario,JSONObject stepObject) {
		Step step = new Step(stepObject,scenario) ;
		scenario.steps.add(step) ;
		return step ;
	}
	public void parseCukeJson(String content) throws Exception {
		JSONArray featuresArray = (JSONArray) JSONValue.parse(content);
		for (int i = 0; i < featuresArray.size(); i++) {
			JSONObject featureObject = (JSONObject) featuresArray.get(i);
			Feature feature = parseFeature(this,featureObject) ;
			JSONArray scenariosArray = (JSONArray) featureObject.get(JSON_ELEMENTS);
			for (int j = 0; j < scenariosArray.size(); j++) {
				JSONObject scenarioObject = (JSONObject) scenariosArray.get(j);
				Scenario scenario = parseScenario(feature,scenarioObject) ;
				JSONArray stepsArray = (JSONArray) scenarioObject
						.get(JSON_STEPS);
				if (stepsArray != null) {
					for(int k=0;k<stepsArray.size();k++) {
						JSONObject stepObject = (JSONObject) stepsArray.get(k);
						Step step = parseStep(scenario,stepObject);
					}
				}
			}
		}
	}
	private static final String JSON_STEPS = "steps";
	private static final String JSON_ELEMENTS = "elements";
}
