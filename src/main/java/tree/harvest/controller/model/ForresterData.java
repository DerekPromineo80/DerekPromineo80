package tree.harvest.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import tree.harvest.entity.Forrester;
import tree.harvest.entity.GeoLocation;
import tree.harvest.entity.Tree;
import tree.harvest.entity.TreeField;

@Data
@NoArgsConstructor
public class ForresterData {

	private Long forresterId;
	private String forresterFirstName;
	private String forresterLastName;
	private String forresterEmail;
	
	private Set<TreeFieldResponse> treeFields = new HashSet<>();
	
	public ForresterData(Forrester forrester) {
		forresterId = forrester.getForresterId();
		forresterFirstName = forrester.getForresterFirstName();
		forresterLastName = forrester.getForresterLastName();
		forresterEmail = forrester.getForresterEmail();
		
		for(TreeField treeField : forrester.getTreeFields()) {
			treeFields.add(new TreeFieldResponse(treeField));
		}
	}
	
	@Data
	@NoArgsConstructor
	static class TreeFieldResponse {
		private Long treeFieldId;
		private String treeFieldName;				
		private String treeFieldDescription;		
		private String treeFieldStateOrProvince;	
		private String treeFieldCountry;			
		private GeoLocation fieldGeoLocation;	
		
		private Set<String> trees = new HashSet<>();
		
		
		TreeFieldResponse(TreeField treeField) {
			treeFieldId = treeField.getTreeFieldId();
			treeFieldName = treeField.getTreeFieldName();
			treeFieldDescription = treeField.getTreeFieldDescription();
			treeFieldStateOrProvince = treeField.getTreeFieldStateOrProvince();
			treeFieldCountry = treeField.getTreeFieldCountry();
			fieldGeoLocation = treeField.getFieldGeoLocation();
			
			for (Tree tree : treeField.getTrees()) {
				trees.add(tree.getTree());
			}
		}
		
		
	}
	

}
