package es.macero.cqgame.domain.users;

import java.util.ArrayList;
import java.util.List;

public final class SonarUserFactory {

    public static List<SonarUser> buildFromIds(List<String> ids) {
    	List<SonarUser> list = new ArrayList<SonarUser>();
    	for(String id : ids) {
    		list.add(new SonarUser(id));
    	}
    	
    	return list;
    	
        //return ids.stream().map(s -> new SonarUser(s)).collect(Collectors.toList());
    }
}
