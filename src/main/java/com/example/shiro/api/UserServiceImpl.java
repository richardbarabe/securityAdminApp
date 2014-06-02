package com.example.shiro.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public void create(String username, RoleDTO... roles) {
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setRoles(roles);
		users.add(user);
	}

	@Override
	public UserDTO get(String username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String username) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(UserDTO user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<UserDTO> queryByUsername(String searchString, int pageNumber, int pageSize) {
		int start = (pageNumber * pageSize);
		int end = (start + pageSize);

		List<UserDTO> found;
		ArrayList<UserDTO> usersSnapshot = new ArrayList<>(users);
		if (searchString.length() == 0) {
			if (end > usersSnapshot.size())
				end = usersSnapshot.size();
			if (end < start)
				start = end;
			found = usersSnapshot.subList(start, end);
			return found;
		} else {
			found = getByUsername(usersSnapshot, searchString);
			if (end > found.size())
				end = found.size();
			if (end < start)
				start = end;
			return found.subList(start, end);
		}
	}

	private static List<UserDTO> getByUsername(List<UserDTO> users, String searchString) {
		List<UserDTO> found = new LinkedList<>();
		for (UserDTO user : users) {
			if (user.getUsername().contains(searchString)) {
				found.add(user);
			}
		}
		return found;
	}

	private static Set<UserDTO> users = new ConcurrentSkipListSet<>(new Comparator<UserDTO>() {

		@Override
		public int compare(UserDTO user1, UserDTO user2) {
			return user1.getUsername().compareTo(user2.getUsername());
		}
		
	});
	static {
		RoleDTO roleAgencyAdmin = new RoleDTO("Agency Administrator", "AGENCY_ADMIN");
		RoleDTO roleSystemAdmin = new RoleDTO("System Administrator", "SYSTEM_ADMIN");
		RoleDTO roleAdvertizer = new RoleDTO("Advertizer", "ADVERTYSER");
		users.add(new UserDTO("AAA", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("BBB", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("CCC", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("DDD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("EEE", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("FFF", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("GGG", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("HHH", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("III", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("JJJ", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("KKK", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("LLL", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("MMM", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("NNN", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("OOO", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("PPP", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("QQQ", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("RRR", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("SSS", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("TTT", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("UUU", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("VVV", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("WWW", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("XXX", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("YYY", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ZZZ", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));

		users.add(new UserDTO("aaa", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("bbb", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ccc", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ddd", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("eee", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("fff", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ggg", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("hhh", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("iii", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("jjj", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("kkk", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("lll", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("mmm", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("nnn", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ooo", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ppp", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("qqq", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("rrr", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("sss", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ttt", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("uuu", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("vvv", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("www", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("xxx", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("yyy", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("zzz", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
	}
	
}
