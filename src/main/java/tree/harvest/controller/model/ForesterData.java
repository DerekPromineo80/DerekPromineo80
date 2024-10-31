package tree.harvest.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import tree.harvest.entity.Forester;
import tree.harvest.entity.GeoLocation;
import tree.harvest.entity.Tree;
import tree.harvest.entity.TreeField;

@Data
@NoArgsConstructor
public class ForesterData {

	private Long foresterId;
	private String foresterFirstName;
	private String foresterLastName;
	private String foresterEmail;
	
	private Set<TreeFieldResponse> treeFields = new HashSet<>();
	
	public ForesterData(Forester forester) {
		foresterId = forester.getForesterId();
		foresterFirstName = forester.getForesterFirstName();
		foresterLastName = forester.getForesterLastName();
		foresterEmail = forester.getForesterEmail();
		
		for(TreeField treeField : forester.getTreeFields()) {
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
