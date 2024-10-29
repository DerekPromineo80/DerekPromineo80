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
public class TreeFieldData {

	private Long treeFieldId;
	private String treeFieldName;			
	private String treeFieldDescription;	
	private String treeFieldStateOrProvince;	
	private String treeFieldCountry;		
	private GeoLocation fieldGeoLocation;
	private TreeFieldForrester forrester;
	private Set<String> trees = new HashSet<>();
	
	public TreeFieldData(TreeField treeField) {
		treeFieldId = treeField.getTreeFieldId();
		treeFieldName = treeField.getTreeFieldName();
		treeFieldDescription = treeField.getTreeFieldDescription();
		treeFieldStateOrProvince = treeField.getTreeFieldStateOrProvince();
		treeFieldCountry = treeField.getTreeFieldCountry();
		fieldGeoLocation = treeField.getFieldGeoLocation();
		forrester = new TreeFieldForrester(treeField.getForrester());
		
		for(Tree tree : treeField.getTrees()) {
			trees.add(tree.getTree());
		}
		
	}
	
	@Data
	@NoArgsConstructor
	public static class TreeFieldForrester {
		private Long forresterId;
		private String forresterFirstName;
		private String forresterLastName;
		private String forresterEmail;
	
		public TreeFieldForrester(Forrester forrester) {
			forresterId = forrester.getForresterId();
			forresterFirstName = forrester.getForresterFirstName();
			forresterLastName = forrester.getForresterLastName();
			forresterEmail = forrester.getForresterEmail();
			
		}
	}
	
}
