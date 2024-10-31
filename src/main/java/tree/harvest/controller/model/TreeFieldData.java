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
public class TreeFieldData {

	private Long treeFieldId;
	private String treeFieldName;			
	private String treeFieldDescription;	
	private String treeFieldStateOrProvince;	
	private String treeFieldCountry;		
	private GeoLocation fieldGeoLocation;
	private TreeFieldForester forrester;
	private Set<String> trees = new HashSet<>();
	
	public TreeFieldData(TreeField treeField) {
		treeFieldId = treeField.getTreeFieldId();
		treeFieldName = treeField.getTreeFieldName();
		treeFieldDescription = treeField.getTreeFieldDescription();
		treeFieldStateOrProvince = treeField.getTreeFieldStateOrProvince();
		treeFieldCountry = treeField.getTreeFieldCountry();
		fieldGeoLocation = treeField.getFieldGeoLocation();
		forrester = new TreeFieldForester(treeField.getForester());
		
		for(Tree tree : treeField.getTrees()) {
			trees.add(tree.getTree());
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class TreeFieldForester {
		private Long foresterId;
		private String foresterFirstName;
		private String foresterLastName;
		private String foresterEmail;
	
		public TreeFieldForester(Forester forester) {
			foresterId = forester.getForesterId();
			foresterFirstName = forester.getForesterFirstName();
			foresterLastName = forester.getForesterLastName();
			foresterEmail = forester.getForesterEmail();
			
		}
	}
	
}
