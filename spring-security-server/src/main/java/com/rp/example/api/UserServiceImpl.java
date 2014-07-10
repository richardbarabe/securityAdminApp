package com.rp.example.api;

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
			if(user1 == null && user2 == null)
				return 0;
			if(user1 != null && user2 == null)
				return 1;
			if(user1 == null && user2 != null)
				return -1;
			return user1.getUsername().compareTo(user2.getUsername());
		}
		
	});
	static {
		RoleDTO roleAgencyAdmin = new RoleDTO("Agency Administrator", "AGENCY_ADMIN");
		RoleDTO roleSystemAdmin = new RoleDTO("System Administrator", "SYSTEM_ADMIN");
		RoleDTO roleAdvertizer = new RoleDTO("Advertizer", "ADVERTYSER");
		users.add(new UserDTO("AAA", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("BBB", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("CCC", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("DDD", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("EEE", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("FFF", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("GGG", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("HHH", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("III", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("JJJ", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("KKK", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("LLL","PASSWORD",  roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("MMM", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("NNN", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("OOO", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("PPP", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("QQQ", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("RRR", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("SSS", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("TTT", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("UUU", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("VVV", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("WWW", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("XXX", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("YYY", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ZZZ", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));

		users.add(new UserDTO("aaa", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("bbb", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ccc", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ddd", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("eee", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("fff", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ggg", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("hhh", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("iii", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("jjj", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("kkk", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("lll", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("mmm", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("nnn", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ooo", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ppp", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("qqq", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("rrr", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("sss", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("ttt","PASSWORD",  roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("uuu", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("vvv", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("www", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("xxx", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("yyy", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
		users.add(new UserDTO("zzz", "PASSWORD", roleAgencyAdmin, roleSystemAdmin, roleAdvertizer));
	}
	
}
