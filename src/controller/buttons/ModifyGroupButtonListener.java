package controller.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.LocalUser;
import model.entities.Entity;
import model.entities.Group;
import model.entities.User;
import view.MainWin;
import view.tabs.GroupTab;

public class ModifyGroupButtonListener implements ActionListener {
	private Group group;
	private GroupTab groupTab;
	
	public ModifyGroupButtonListener(Group group, GroupTab groupTab) {
		this.group = group;
		this.groupTab = groupTab;
	}
	public ModifyGroupButtonListener(GroupTab groupTab) {
		this.groupTab = groupTab;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (group == null) { // Ajout d'un nouveau groupe
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", groupTab.getName());
			values.put("owner", LocalUser.localUser.getUser());
			
			Group group;
			try {
				group = (Group) Group.factory.insert(values);
				group.addMember(LocalUser.localUser.getUser());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		else {
			// Mise à jour des attributs du groupe
			group.setName(groupTab.getName());
			try {
				group.update();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			// Mise à jour des membres du groupe
			List<Entity> newMembersList = groupTab.getMembersSelector().getSelectedList();
			List<User> oldMembersList = group.getMembers();
			
			// Ajout des nouveaux membres
			for (int i = 0; i < newMembersList.size(); i++) {
				User user = (User) newMembersList.get(i);
				
				if (!oldMembersList.contains(user)) {
					try {
						group.addMember(user);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			
			// Suppression des anciens membres
			for (int i = 0; i < oldMembersList.size(); i++) {
				User user = oldMembersList.get(i);
				
				if (!newMembersList.contains(user)) {
					try {
						group.removeMember(user);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		
		groupTab.close();
		MainWin.mainWin.refresh();
	}
}